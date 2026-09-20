using FluentValidation;
using Microsoft.AspNetCore.Mvc.Filters;

public class ValidationFilterAttribute : IAsyncActionFilter
{
    public async Task OnActionExecutionAsync(ActionExecutingContext context, ActionExecutionDelegate next)
    {
        var parameters = context.ActionDescriptor.Parameters;

        foreach (var parameter in parameters)
        {
            var argument = context.ActionArguments.TryGetValue(parameter.Name, out var val) ? val : null;
            if (argument == null)
            {
                continue;
            }

            var validatorType = typeof(IValidator<>).MakeGenericType(argument.GetType());
            var validator = context.HttpContext.RequestServices.GetService(validatorType) as IValidator;

            if (validator != null)
            {
                var validationContext = new ValidationContext<object>(argument);
                var validationResult = await validator.ValidateAsync(validationContext);

                if (!validationResult.IsValid)
                {
                    var errors = validationResult.Errors
                        .GroupBy(x => x.PropertyName)
                        .ToDictionary(
                            g => g.Key,
                            g => g.Select(x => x.ErrorMessage).ToArray());

                    throw new ApplicationValidationException(errors);
                }
            }
        }

        await next();
    }
}
