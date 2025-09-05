using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class TourPackage
{
    public int PackageId { get; set; }

    public string? Pname { get; set; }

    public string? Description { get; set; }

    public string? Destination { get; set; }

    public int? TourAgencyId { get; set; }

    public int? CatId { get; set; }

    public virtual Category? Cat { get; set; }

    public virtual TourAgency? TourAgency { get; set; }

    public virtual ICollection<TourSchedule> TourSchedules { get; set; } = new List<TourSchedule>();
}
