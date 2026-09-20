using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

public class PayrollRepository : IPayrollRepository
{
    private readonly AppDbContext _context;

    public PayrollRepository(AppDbContext context)
    {
        _context = context;
    }

    public async Task<(IEnumerable<Payroll> Items, int TotalCount)> GetAllAsync(PayrollFilterRequest filter)
    {
        var query = _context.Payrolls.AsQueryable();

        if (!string.IsNullOrWhiteSpace(filter.EmployeeId))
        {
            query = query.Where(p => p.EmployeeId == filter.EmployeeId);
        }

        if (!string.IsNullOrWhiteSpace(filter.Status))
        {
            query = query.Where(p => p.Status == filter.Status);
        }

        if (filter.StartDate.HasValue)
        {
            query = query.Where(p => p.PayPeriod >= filter.StartDate.Value);
        }

        if (filter.EndDate.HasValue)
        {
            query = query.Where(p => p.PayPeriod <= filter.EndDate.Value);
        }

        if (!string.IsNullOrWhiteSpace(filter.SearchTerm))
        {
            query = query.Where(p => p.EmployeeId.Contains(filter.SearchTerm) || p.Currency.Contains(filter.SearchTerm));
        }

        var totalCount = await query.CountAsync();

        var items = await query
            .OrderByDescending(p => p.PayPeriod)
            .Skip((filter.PageNumber - 1) * filter.PageSize)
            .Take(filter.PageSize)
            .ToListAsync();

        return (items, totalCount);
    }

    public async Task<Payroll?> GetByIdAsync(int id)
    {
        return await _context.Payrolls.FirstOrDefaultAsync(p => p.Id == id);
    }

    public async Task<Payroll> AddAsync(Payroll payroll)
    {
        await _context.Payrolls.AddAsync(payroll);
        await _context.SaveChangesAsync();
        return payroll;
    }

    public async Task UpdateAsync(Payroll payroll)
    {
        _context.Payrolls.Update(payroll);
        await _context.SaveChangesAsync();
    }

    public async Task DeleteAsync(int id)
    {
        var payroll = await GetByIdAsync(id);
        if (payroll != null)
        {
            _context.Payrolls.Remove(payroll);
            await _context.SaveChangesAsync();
        }
    }

    public async Task<int> AddRangeAsync(IEnumerable<Payroll> payrolls)
    {
        await _context.Payrolls.AddRangeAsync(payrolls);
        return await _context.SaveChangesAsync();
    }
}
