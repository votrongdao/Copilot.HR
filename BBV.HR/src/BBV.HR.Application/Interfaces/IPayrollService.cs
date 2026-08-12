public interface IPayrollService
{
    Task<IEnumerable<Payroll>> GetAllAsync();
    Task<Payroll?> GetByIdAsync(int id);
    Task<Payroll> CreateAsync(Payroll payroll);
    Task UpdateAsync(Payroll payroll);
    Task DeleteAsync(int id);
    Task<int> ImportAsync(IEnumerable<Payroll> payrolls);
}
