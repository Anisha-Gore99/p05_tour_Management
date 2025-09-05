using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class Tourist
{
    public int TouristId { get; set; }

    public DateTime? Dob { get; set; }

    public string Address { get; set; } = null!;

    public int? Uid { get; set; }

    public string? Fname { get; set; }

    public string? Lname { get; set; }

    public virtual ICollection<Booking> Bookings { get; set; } = new List<Booking>();

    public virtual User? UidNavigation { get; set; }
}
