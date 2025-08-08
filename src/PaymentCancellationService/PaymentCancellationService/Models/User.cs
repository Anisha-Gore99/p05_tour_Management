using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class User
{
    public int Uid { get; set; }

    public string? Uname { get; set; }

    public string Password { get; set; } = null!;

    public string? PhoneNo { get; set; }

    public string? Email { get; set; }

    public int Rid { get; set; }

    public virtual Role RidNavigation { get; set; } = null!;

    public virtual TourAgency? TourAgency { get; set; }

    public virtual ICollection<Tourist> Tourists { get; set; } = new List<Tourist>();
}
