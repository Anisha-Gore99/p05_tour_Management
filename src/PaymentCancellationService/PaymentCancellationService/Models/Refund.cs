using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace PaymentCancellationService.Models;

public partial class Refund
{
    public int RefundId { get; set; }

    public decimal RefundAmount { get; set; }

    public DateOnly RefundDate { get; set; }

    public string RefundStatus { get; set; } = null!;

    public int? CancellationId { get; set; }

    public int? PaymentId { get; set; }

    public virtual Cancellation? Cancellation { get; set; }

    [JsonIgnore]
    public virtual Payment? Payment { get; set; }
}
