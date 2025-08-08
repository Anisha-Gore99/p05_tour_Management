using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class Cancellation
{
    public int CancellationId { get; set; }

    public DateOnly CancelDate { get; set; }

    public string CancelReason { get; set; } = null!;

    public string CancellationStatus { get; set; } = null!;

    public int? BookingId { get; set; }

    public virtual Booking? Booking { get; set; }

    public virtual ICollection<Refund> Refunds { get; set; } = new List<Refund>();
}
