using System;
using System.Collections.Generic;

public class Payslip
{
    public int Id { get; set; }
    public int PayrollId { get; set; }
    public string PayslipNumber { get; set; } = string.Empty;
    public DateTime IssueDate { get; set; }
    public string FileUrl { get; set; } = string.Empty;
    public string Status { get; set; } = string.Empty;
    public DateTime GeneratedAt { get; set; }

    public Dictionary<string, string> Metadata { get; set; } = new Dictionary<string, string>();

    public virtual Payroll Payroll { get; set; } = null!;
    public virtual ICollection<EmailLog> EmailLogs { get; set; } = new List<EmailLog>();
}
