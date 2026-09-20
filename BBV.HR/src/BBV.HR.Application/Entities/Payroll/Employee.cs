using System;
using System.Collections.Generic;

public class Employee
{
    public string Id { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string Role { get; set; } = string.Empty;
    public string Department { get; set; } = string.Empty;
    public string EmploymentType { get; set; } = string.Empty;

    public Dictionary<string, string> Metadata { get; set; } = new Dictionary<string, string>();

    public virtual ICollection<Payroll> Payrolls { get; set; } = new List<Payroll>();
    public virtual ICollection<EmployeeCompensation> EmployeeCompensations { get; set; } = new List<EmployeeCompensation>();
    public virtual ICollection<EmailLog> EmailLogs { get; set; } = new List<EmailLog>();
}
