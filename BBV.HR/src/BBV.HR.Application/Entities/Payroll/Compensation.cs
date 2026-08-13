public class Compensation
{
    public int Id { get; set; }
    public CompensationType Type { get; set; } = CompensationType.Allowance;
    public string Name { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public decimal DefaultAmount { get; set; }
    public string Frequency { get; set; } = string.Empty;
    public string Status { get; set; } = string.Empty;

    public Dictionary<string, string> Metadata { get; set; } = new Dictionary<string, string>();

    public virtual ICollection<PayrollItem> PayrollItems { get; set; } = new List<PayrollItem>();
    public virtual ICollection<EmployeeCompensation> EmployeeCompensations { get; set; } = new List<EmployeeCompensation>();
}
