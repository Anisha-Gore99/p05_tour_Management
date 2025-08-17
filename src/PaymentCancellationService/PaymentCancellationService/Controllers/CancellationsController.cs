using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PaymentCancellationService.Data;
using PaymentCancellationService.Models;

namespace PaymentCancellationService.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CancellationsController : ControllerBase
    {
        private readonly ApplicationDbContext _context;

        public CancellationsController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: api/Cancellations
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Cancellation>>> GetCancellations()
        {
            return await _context.Cancellations.ToListAsync();
        }

        // GET: api/Cancellations/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Cancellation>> GetCancellation(int id)
        {
            var cancellation = await _context.Cancellations.FindAsync(id);

            if (cancellation == null)
            {
                return NotFound();
            }

            return cancellation;
        }

        // POST: api/Cancellations
        [HttpPost]
        public async Task<ActionResult<Cancellation>> PostCancellation(Cancellation cancellation)
        {
            _context.Cancellations.Add(cancellation);
            await _context.SaveChangesAsync();

            // Returns a 201 CreatedAtAction response
            return CreatedAtAction("GetCancellation", new { id = cancellation.CancellationId }, cancellation);
        }

        // PUT: api/Cancellations/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCancellation(int id, Cancellation cancellation)
        {
            if (id != cancellation.CancellationId)
            {
                return BadRequest();
            }

            _context.Entry(cancellation).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CancellationExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // DELETE: api/Cancellations/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCancellation(int id)
        {
            var cancellation = await _context.Cancellations.FindAsync(id);
            if (cancellation == null)
            {
                return NotFound();
            }

            _context.Cancellations.Remove(cancellation);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool CancellationExists(int id)
        {
            return _context.Cancellations.Any(e => e.CancellationId == id);
        }
    }
}

