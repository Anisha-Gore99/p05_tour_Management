using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class Booking
{
    public int BookingId { get; set; }

    public DateOnly BookingDate { get; set; }

    public int NoOfTourist { get; set; }

    public string Status { get; set; } = null!;

    public int? ScheduleId { get; set; }

    public int? TouristId { get; set; }

    public virtual ICollection<Cancellation> Cancellations { get; set; } = new List<Cancellation>();

    public virtual ICollection<Payment> Payments { get; set; } = new List<Payment>();

    public virtual TourSchedule? Schedule { get; set; }

    public virtual Tourist? Tourist { get; set; }

    public virtual ICollection<TouristDetail> TouristDetails { get; set; } = new List<TouristDetail>();
}
