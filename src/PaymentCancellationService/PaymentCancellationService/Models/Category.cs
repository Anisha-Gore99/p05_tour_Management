using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class Category
{
    public int CatId { get; set; }

    public string Cname { get; set; } = null!;

    public virtual ICollection<TourPackage> TourPackages { get; set; } = new List<TourPackage>();
}
