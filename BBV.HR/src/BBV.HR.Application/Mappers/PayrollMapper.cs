using System;

public static class PayrollMapper
{
    public static PayrollResponse ToResponse(this Payroll entity)
    {
        if (entity == null) return null!;

        return new PayrollResponse
        {
            Id = entity.Id,
            EmployeeId = entity.EmployeeId,
            PayPeriod = entity.PayPeriod,
            BaseSalary = entity.BaseSalary,
            GrossPay = entity.GrossPay,
            TotalDeductions = entity.TotalDeductions,
            NetPay = entity.NetPay,
            Currency = entity.Currency,
            Status = entity.Status,
            CreatedAt = entity.CreatedAt,
            Metadata = entity.Metadata
        };
    }

    public static Payroll ToEntity(this CreatePayrollRequest request)
    {
        if (request == null) return null!;

        return new Payroll
        {
            EmployeeId = request.EmployeeId,
            PayPeriod = request.PayPeriod,
            BaseSalary = request.BaseSalary,
            GrossPay = request.GrossPay,
            TotalDeductions = request.TotalDeductions,
            NetPay = request.NetPay,
            Currency = request.Currency,
            Status = "Draft", // Default status
            CreatedAt = DateTime.UtcNow,
            Metadata = request.Metadata
        };
    }
}
