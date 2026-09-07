using System;

public class EmailLog
{
    public int Id { get; set; }
    public string EmployeeId { get; set; } = string.Empty;
    public int PayslipId { get; set; }
    public string RecipientEmail { get; set; } = string.Empty;
    public string Subject { get; set; } = string.Empty;
    public string Body { get; set; } = string.Empty;
    public DateTime SentAt { get; set; }
    public string Status { get; set; } = string.Empty;

    public virtual Employee Employee { get; set; } = null!;
    public virtual Payslip Payslip { get; set; } = null!;
}
