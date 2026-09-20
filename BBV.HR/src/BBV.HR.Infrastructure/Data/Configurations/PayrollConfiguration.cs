using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using System.Collections.Generic;
using System.Text.Json;

public class PayrollConfiguration : IEntityTypeConfiguration<Payroll>
{
    public void Configure(EntityTypeBuilder<Payroll> builder)
    {
        builder.ToTable("Payrolls");

        builder.HasKey(x => x.Id);

        builder.Property(x => x.EmployeeId)
            .IsRequired()
            .HasMaxLength(50);

        builder.Property(x => x.Currency)
            .IsRequired()
            .HasMaxLength(10);

        builder.Property(x => x.Status)
            .IsRequired()
            .HasMaxLength(20);

        builder.Property(x => x.BaseSalary)
            .HasColumnType("decimal(18,2)");

        builder.Property(x => x.GrossPay)
            .HasColumnType("decimal(18,2)");

        builder.Property(x => x.TotalDeductions)
            .HasColumnType("decimal(18,2)");

        builder.Property(x => x.NetPay)
            .HasColumnType("decimal(18,2)");

        builder.Property(x => x.Metadata)
            .HasConversion(
                v => JsonSerializer.Serialize(v, (JsonSerializerOptions?)null),
                v => JsonSerializer.Deserialize<Dictionary<string, string>>(v, (JsonSerializerOptions?)null) ?? new Dictionary<string, string>()
            );

        // Relationships
        builder.HasOne(x => x.Employee)
            .WithMany(x => x.Payrolls)
            .HasForeignKey(x => x.EmployeeId)
            .OnDelete(DeleteBehavior.Restrict);

        builder.HasMany(x => x.PayrollItems)
            .WithOne(x => x.Payroll)
            .HasForeignKey(x => x.PayrollId)
            .OnDelete(DeleteBehavior.Cascade);

        builder.HasOne(x => x.Payslip)
            .WithOne(x => x.Payroll)
            .HasForeignKey<Payslip>(x => x.PayrollId)
            .OnDelete(DeleteBehavior.Cascade);
    }
}
