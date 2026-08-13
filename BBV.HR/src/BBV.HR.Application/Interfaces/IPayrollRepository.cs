using System.Collections.Generic;
using System.Threading.Tasks;

public interface IPayrollRepository
{
    Task<(IEnumerable<Payroll> Items, int TotalCount)> GetAllAsync(PayrollFilterRequest filter);
    Task<Payroll?> GetByIdAsync(int id);
    Task<Payroll> AddAsync(Payroll payroll);
    Task UpdateAsync(Payroll payroll);
    Task DeleteAsync(int id);
    Task<int> AddRangeAsync(IEnumerable<Payroll> payrolls);
}
