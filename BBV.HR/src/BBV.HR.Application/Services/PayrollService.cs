using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

public class PayrollService : IPayrollService
{
    private readonly IPayrollRepository _repository;

    public PayrollService(IPayrollRepository repository)
    {
        _repository = repository;
    }

    public async Task<PagedResult<PayrollResponse>> GetAllAsync(PayrollFilterRequest filter)
    {
        var (items, totalCount) = await _repository.GetAllAsync(filter);
        var responseItems = items.Select(p => p.ToResponse());

        return new PagedResult<PayrollResponse>(
            responseItems,
            totalCount,
            filter.PageNumber,
            filter.PageSize);
    }

    public async Task<PayrollResponse?> GetByIdAsync(int id)
    {
        var payroll = await _repository.GetByIdAsync(id);
        return payroll?.ToResponse();
    }

    public async Task<PayrollResponse> CreateAsync(CreatePayrollRequest request)
    {
        var entity = request.ToEntity();
        var createdEntity = await _repository.AddAsync(entity);
        return createdEntity.ToResponse();
    }

    public async Task UpdateAsync(int id, UpdatePayrollRequest request)
    {
        var existingPayroll = await _repository.GetByIdAsync(id);
        if (existingPayroll == null)
        {
            throw new KeyNotFoundException($"Payroll with ID {id} not found.");
        }

        existingPayroll.EmployeeId = request.EmployeeId;
        existingPayroll.PayPeriod = request.PayPeriod;
        existingPayroll.BaseSalary = request.BaseSalary;
        existingPayroll.GrossPay = request.GrossPay;
        existingPayroll.TotalDeductions = request.TotalDeductions;
        existingPayroll.NetPay = request.NetPay;
        existingPayroll.Currency = request.Currency;
        existingPayroll.Status = request.Status;
        existingPayroll.Metadata = request.Metadata;

        await _repository.UpdateAsync(existingPayroll);
    }

    public async Task DeleteAsync(int id)
    {
        var existingPayroll = await _repository.GetByIdAsync(id);
        if (existingPayroll == null)
        {
            throw new KeyNotFoundException($"Payroll with ID {id} not found.");
        }

        await _repository.DeleteAsync(id);
    }

    public async Task<int> ImportAsync(IEnumerable<CreatePayrollRequest> requests)
    {
        var entities = requests.Select(r => r.ToEntity());
        return await _repository.AddRangeAsync(entities);
    }
}
