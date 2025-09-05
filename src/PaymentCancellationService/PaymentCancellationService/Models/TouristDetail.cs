using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class TouristDetail
{
    public int TravellerId { get; set; }

    public string? Fname { get; set; }

    public string? Lname { get; set; }

    public int? Age { get; set; }

    public string? Gender { get; set; }

    public string? IdProof { get; set; }

    public int? BookingId { get; set; }

    public virtual Booking? Booking { get; set; }
}
