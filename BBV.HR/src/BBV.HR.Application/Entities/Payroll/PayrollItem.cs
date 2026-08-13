using System;

public class PayrollItem
{
    public int Id { get; set; }
    public int PayrollId { get; set; }
    public int CompensationId { get; set; }
    public decimal AppliedAmount { get; set; }

    public virtual Payroll Payroll { get; set; } = null!;
    public virtual Compensation Compensation { get; set; } = null!;
}
