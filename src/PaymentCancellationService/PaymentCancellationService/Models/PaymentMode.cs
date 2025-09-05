using System;
using System.Collections.Generic;

namespace PaymentCancellationService.Models;

public partial class PaymentMode
{
    public int ModeId { get; set; }

    public string ModeName { get; set; } = null!;

    public virtual ICollection<Payment> Payments { get; set; } = new List<Payment>();
}
