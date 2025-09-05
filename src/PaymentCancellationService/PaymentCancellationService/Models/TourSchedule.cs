using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class TourSchedule
{
    public int ScheduleId { get; set; }

    public DateOnly StartDate { get; set; }

    public DateOnly EndDate { get; set; }

    public int Duration { get; set; }

    public decimal? Cost { get; set; }

    public int AvailableBookings { get; set; }

    public int? PackageId { get; set; }

    public virtual ICollection<Booking> Bookings { get; set; } = new List<Booking>();

    public virtual TourPackage? Package { get; set; }
}
