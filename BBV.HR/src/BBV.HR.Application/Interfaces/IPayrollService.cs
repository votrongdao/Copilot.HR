using System.Collections.Generic;
using System.Threading.Tasks;

public interface IPayrollService
{
    Task<PagedResult<PayrollResponse>> GetAllAsync(PayrollFilterRequest filter);
    Task<PayrollResponse?> GetByIdAsync(int id);
    Task<PayrollResponse> CreateAsync(CreatePayrollRequest request);
    Task UpdateAsync(int id, UpdatePayrollRequest request);
    Task DeleteAsync(int id);
    Task<int> ImportAsync(IEnumerable<CreatePayrollRequest> requests);
}
