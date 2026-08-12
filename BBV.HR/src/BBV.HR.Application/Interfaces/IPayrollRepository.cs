public interface IPayrollRepository
{
    Task<IEnumerable<Payroll>> GetAllAsync();
    Task<Payroll?> GetByIdAsync(int id);
    Task<Payroll> AddAsync(Payroll payroll);
    Task UpdateAsync(Payroll payroll);
    Task DeleteAsync(int id);
    Task<int> AddRangeAsync(IEnumerable<Payroll> payrolls);
}
