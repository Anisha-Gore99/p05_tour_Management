using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PaymentCancellationService.Data;
using PaymentCancellationService.Models;

namespace PaymentCancellationService.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PaymentsController : ControllerBase
    {
        private readonly ApplicationDbContext _context;

        public PaymentsController(ApplicationDbContext context)
        {
            _context = context;
        }

        // -------------------- CRUD --------------------

        // GET: api/Payments
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Payment>>> GetPayments()
        {
            return await _context.Payments.AsNoTracking().ToListAsync();
        }

        // GET: api/Payments/5
        [HttpGet("{id:int}")]
        public async Task<ActionResult<Payment>> GetPayment(int id)
        {
            var payment = await _context.Payments.FindAsync(id);
            if (payment == null) return NotFound();
            return payment;
        }

        // POST: api/Payments
        [HttpPost]
        public async Task<ActionResult<Payment>> PostPayment(Payment payment)
        {
            if (string.IsNullOrWhiteSpace(GetStatus(payment))) SetStatus(payment, "PENDING");
            SetCreatedAtIfExists(payment, DateTime.UtcNow);

            _context.Payments.Add(payment);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetPayment), new { id = GetId(payment) }, payment);
        }

        // PUT: api/Payments/5
        [HttpPut("{id:int}")]
        public async Task<IActionResult> PutPayment(int id, Payment payment)
        {
            if (!IdEquals(payment, id)) return BadRequest();

            _context.Entry(payment).State = EntityState.Modified;
            SetUpdatedAtIfExists(payment, DateTime.UtcNow);

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!await _context.Payments.AnyAsync(p => GetId(p) == id))
                    return NotFound();
                throw;
            }

            return NoContent();
        }

        // DELETE: api/Payments/5
        [HttpDelete("{id:int}")]
        public async Task<IActionResult> DeletePayment(int id)
        {
            var payment = await _context.Payments.FindAsync(id);
            if (payment == null) return NotFound();

            _context.Payments.Remove(payment);
            await _context.SaveChangesAsync();
            return NoContent();
        }

        // --------------- Checkout + Confirm ---------------

        public record CheckoutRequest(int BookingId, decimal Amount, string Currency = "INR");
        public record CheckoutResponse(int PaymentId, string RedirectUrl);

        // POST: api/Payments/checkout
        [HttpPost("checkout")]
        public async Task<ActionResult<CheckoutResponse>> CreateCheckout([FromBody] CheckoutRequest req)
        {
            if (req.BookingId <= 0) return BadRequest("BookingId is required.");
            if (req.Amount <= 0) return BadRequest("Amount must be > 0.");

            // Create a pending payment (no Currency field used)
            var payment = new Payment
            {
                // Make sure these exist in your model:
                // PaymentId (key), BookingId, Amount, Status, CreatedAt
                BookingId = req.BookingId,
                Amount = req.Amount,
                // Currency not set because your model doesn't have it
            };
            SetStatus(payment, "PENDING");
            SetCreatedAtIfExists(payment, DateTime.UtcNow);

            _context.Payments.Add(payment);
            await _context.SaveChangesAsync();

            // Redirect to your React "gateway" page
            var redirectUrl = $"http://localhost:3000/pay/{GetId(payment)}";
            return Ok(new CheckoutResponse(GetId(payment), redirectUrl));
        }

        public record ConfirmRequest(string Status, string? ProviderRef);

        // POST: api/Payments/{paymentId}/confirm
        [HttpPost("{paymentId:int}/confirm")]
        public async Task<IActionResult> Confirm(int paymentId, [FromBody] ConfirmRequest req)
        {
            var p = await _context.Payments.FirstOrDefaultAsync(x => GetId(x) == paymentId);
            if (p is null) return NotFound();

            var normalized = (req.Status ?? "").Trim().ToUpperInvariant();
            SetStatus(p, normalized == "SUCCESS" ? "SUCCESS" : "FAILED");
            TrySetProviderRef(p, req.ProviderRef);
            SetUpdatedAtIfExists(p, DateTime.UtcNow);

            await _context.SaveChangesAsync();
            return NoContent();
        }

        // Optional: GET status
        [HttpGet("{paymentId:int}/status")]
        public async Task<ActionResult<object>> GetStatus(int paymentId)
        {
            var payment = await _context.Payments
                .AsNoTracking()
                .Where(p => GetId(p) == paymentId)
                .Select(p => new
                {
                    PaymentId = GetId(p),
                    Status = GetStatus(p),
                    Amount = GetAmount(p),
                    // Currency intentionally omitted (model doesn’t have it)
                    BookingId = GetBookingId(p)
                })
                .FirstOrDefaultAsync();

            if (payment == null) return NotFound();
            return Ok(payment);
        }

        // -------------------- helper accessors via reflection (robust to small model differences) --------------------
        private static int GetId(Payment p) =>
            (int)(p.GetType().GetProperty("PaymentId")?.GetValue(p) ?? 0);
        private static bool IdEquals(Payment p, int id) => GetId(p) == id;

        private static void SetStatus(Payment p, string status)
        {
            var prop = p.GetType().GetProperty("Status");
            if (prop != null && prop.CanWrite) prop.SetValue(p, status);
        }
        private static string GetStatus(Payment p) =>
            p.GetType().GetProperty("Status")?.GetValue(p)?.ToString() ?? "";

        private static void TrySetProviderRef(Payment p, string? val)
        {
            var prop = p.GetType().GetProperty("ProviderReference");
            if (prop != null && prop.CanWrite) prop.SetValue(p, val);
        }

        private static void SetCreatedAtIfExists(Payment p, DateTime dt)
        {
            var prop = p.GetType().GetProperty("CreatedAt");
            if (prop != null && prop.CanWrite && prop.PropertyType == typeof(DateTime))
                prop.SetValue(p, dt);
        }
        private static void SetUpdatedAtIfExists(Payment p, DateTime dt)
        {
            var prop = p.GetType().GetProperty("UpdatedAt");
            if (prop != null && prop.CanWrite && prop.PropertyType == typeof(DateTime))
                prop.SetValue(p, dt);
        }

        private static decimal GetAmount(Payment p)
        {
            var prop = p.GetType().GetProperty("Amount");
            var v = prop?.GetValue(p);
            return v is decimal d ? d : 0m;
        }

        private static int GetBookingId(Payment p)
        {
            var prop = p.GetType().GetProperty("BookingId");
            var v = prop?.GetValue(p);
            return v is int i ? i : 0;
        }
    }
}
