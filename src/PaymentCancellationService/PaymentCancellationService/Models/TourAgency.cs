using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class TourAgency
{
    public int TourAgencyId { get; set; }

    public string? TourAgencyName { get; set; }

    public string? PhoneNo { get; set; }

    public string? AgencyEmail { get; set; }

    public string Address { get; set; } = null!;

    public string? LicenseNumber { get; set; }

    public int? Uid { get; set; }

    public string? LiscenceNumber { get; set; }

    public virtual ICollection<TourPackage> TourPackages { get; set; } = new List<TourPackage>();

    public virtual User? UidNavigation { get; set; }
}
