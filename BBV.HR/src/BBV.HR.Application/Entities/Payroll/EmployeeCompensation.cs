using System;

public class EmployeeCompensation
{
    public int Id { get; set; }
    public string EmployeeId { get; set; } = string.Empty;
    public int CompensationId { get; set; }
    public decimal AppliedAmount { get; set; }
    public DateTime EffectiveFrom { get; set; }
    public DateTime EffectiveTo { get; set; }
    public string Status { get; set; } = string.Empty;

    public virtual Employee Employee { get; set; } = null!;
    public virtual Compensation Compensation { get; set; } = null!;
}
