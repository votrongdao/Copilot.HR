using System;

public class PayrollFilterRequest : PagedRequest
{
    public string? SearchTerm { get; set; }
    public string? EmployeeId { get; set; }
    public string? Status { get; set; }
    public DateTime? StartDate { get; set; }
    public DateTime? EndDate { get; set; }
}
