using FluentValidation;
using Microsoft.Extensions.DependencyInjection;

public static class DependencyInjectionApplication
{
    public static IServiceCollection AddApplication(
        this IServiceCollection services)
    {
        services.AddValidatorsFromAssembly(
            typeof(DependencyInjectionApplication).Assembly);

        services.AddScoped<IPayrollService, PayrollService>();
        return services;
    }
}
