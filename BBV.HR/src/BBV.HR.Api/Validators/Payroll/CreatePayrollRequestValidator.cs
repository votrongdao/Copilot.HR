using FluentValidation;

public class CreatePayrollRequestValidator : AbstractValidator<CreatePayrollRequest>
{
    public CreatePayrollRequestValidator()
    {
        RuleFor(x => x.EmployeeId).NotEmpty().WithMessage("EmployeeId is required.");
        RuleFor(x => x.PayPeriod).NotEmpty().WithMessage("PayPeriod is required.");
        RuleFor(x => x.BaseSalary).GreaterThanOrEqualTo(0).WithMessage("BaseSalary cannot be negative.");
        RuleFor(x => x.GrossPay).GreaterThanOrEqualTo(0).WithMessage("GrossPay cannot be negative.");
        RuleFor(x => x.TotalDeductions).GreaterThanOrEqualTo(0).WithMessage("TotalDeductions cannot be negative.");
        RuleFor(x => x.NetPay).GreaterThanOrEqualTo(0).WithMessage("NetPay cannot be negative.");
        RuleFor(x => x.Currency).NotEmpty().WithMessage("Currency is required.");
    }
}
