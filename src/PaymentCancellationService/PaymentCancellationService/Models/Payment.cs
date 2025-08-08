using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class Payment
{
    public int PaymentId { get; set; }

    public decimal Amount { get; set; }

    public DateOnly PaymentDate { get; set; }

    public int? ModeId { get; set; }

    public int? BookingId { get; set; }

    public virtual Booking? Booking { get; set; }

    public virtual PaymentMode? Mode { get; set; }

    public virtual ICollection<Refund> Refunds { get; set; } = new List<Refund>();
}
