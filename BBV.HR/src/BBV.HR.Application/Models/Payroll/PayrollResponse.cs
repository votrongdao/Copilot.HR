using System;
using System.Collections.Generic;

public class PayrollResponse
{
    public int Id { get; set; }
    public string EmployeeId { get; set; } = string.Empty;
    public DateTime PayPeriod { get; set; }
    public decimal BaseSalary { get; set; }
    public decimal GrossPay { get; set; }
    public decimal TotalDeductions { get; set; }
    public decimal NetPay { get; set; }
    public string Currency { get; set; } = string.Empty;
    public string Status { get; set; } = string.Empty;
    public DateTime CreatedAt { get; set; }
    public Dictionary<string, string> Metadata { get; set; } = new Dictionary<string, string>();
}
