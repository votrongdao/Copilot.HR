using System.Text;
using BBV.HR.Api.Middlewares;
using BBV.HR.Application;
using BBV.HR.Application.Common.Models;
using BBV.HR.Infrastructure;
using BBV.HR.Infrastructure.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.OpenApi;

namespace BBV.HR.Api
{
    public class Program
    {
        public static async Task Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add Controllers & Configure Custom Validation Response
            builder.Services.AddControllers()
                .ConfigureApiBehaviorOptions(options =>
                {
                    options.InvalidModelStateResponseFactory = context =>
                    {
                        var errors = context.ModelState
                            .Where(e => e.Value?.Errors.Count > 0)
                            .SelectMany(e => e.Value!.Errors.Select(x => string.IsNullOrEmpty(x.ErrorMessage) ? x.Exception?.Message ?? "Invalid input" : x.ErrorMessage))
                            .ToList();

                        var response = ApiResponse<object>.FailureResult("Validation failed for request payload.", errors, 400);
                        return new BadRequestObjectResult(response);
                    };
                });

            builder.Services.AddEndpointsApiExplorer();

            // Register Layers Dependency Injection
            builder.Services.AddApplicationServices();
            builder.Services.AddInfrastructureServices(builder.Configuration);

            // Configure Swagger
            builder.Services.AddSwaggerGen(options =>
            {
                options.SwaggerDoc("v1", new OpenApiInfo
                {
                    Title = "BBV.HR API",
                    Version = "v1",
                });
            });

            builder.Services.AddAuthorization();

            var app = builder.Build();

            // Seed Sample Data into PostgreSQL Database
            using (var scope = app.Services.CreateScope())
            {
                var dbContext = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
                await DataSeeder.SeedAsync(dbContext);
            }

            // Register Global Exception Handling Middleware
            app.UseMiddleware<ExceptionHandlingMiddleware>();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI(c =>
                {
                    c.SwaggerEndpoint("/swagger/v1/swagger.json", "BBV.HR API v1");
                });
            }

            app.UseHttpsRedirection();

            app.UseAuthentication();
            app.UseAuthorization();

            app.MapControllers();
            app.MapGet("/", () => Results.Redirect("/swagger")).ExcludeFromDescription();

            await app.RunAsync();
        }
    }
}
