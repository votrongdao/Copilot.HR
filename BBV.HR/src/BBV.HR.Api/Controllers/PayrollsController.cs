using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

[Route("api/[controller]")]
[ApiController]
public class PayrollsController : ControllerBase
{
    private readonly IPayrollService _payrollService;

    public PayrollsController(IPayrollService payrollService)
    {
        _payrollService = payrollService;
    }

    [HttpGet]
    public async Task<IActionResult> GetPayrolls([FromQuery] PayrollFilterRequest filter)
    {
        var pagedResult = await _payrollService.GetAllAsync(filter);
        return Ok(pagedResult);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetPayrollById(int id)
    {
        var payroll = await _payrollService.GetByIdAsync(id);
        if (payroll == null)
        {
            return NotFound("Payroll not found");
        }
        return Ok(payroll);
    }

    [HttpPost]
    public async Task<IActionResult> CreatePayroll([FromBody] CreatePayrollRequest request)
    {
        var payroll = await _payrollService.CreateAsync(request);
        return CreatedAtAction(nameof(GetPayrollById), new { id = payroll.Id }, payroll);
    }

    [HttpPut("{id}")]
    public async Task<IActionResult> UpdatePayroll(int id, [FromBody] UpdatePayrollRequest request)
    {
        try
        {
            await _payrollService.UpdateAsync(id, request);
            return Ok();
        }
        catch (KeyNotFoundException ex)
        {
            return NotFound(ex.Message);
        }
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeletePayroll(int id)
    {
        try
        {
            await _payrollService.DeleteAsync(id);
            return Ok();
        }
        catch (KeyNotFoundException ex)
        {
            return NotFound(ex.Message);
        }
    }
}
