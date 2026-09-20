using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class EmailLogConfiguration : IEntityTypeConfiguration<EmailLog>
{
    public void Configure(EntityTypeBuilder<EmailLog> builder)
    {
        builder.ToTable("EmailLogs");

        builder.HasKey(x => x.Id);

        builder.Property(x => x.EmployeeId)
            .IsRequired()
            .HasMaxLength(50);

        builder.Property(x => x.RecipientEmail)
            .IsRequired()
            .HasMaxLength(150);

        builder.Property(x => x.Subject)
            .HasMaxLength(255);

        builder.Property(x => x.Status)
            .HasMaxLength(20);

        builder.HasOne(x => x.Employee)
            .WithMany(x => x.EmailLogs)
            .HasForeignKey(x => x.EmployeeId)
            .OnDelete(DeleteBehavior.Restrict);

        builder.HasOne(x => x.Payslip)
            .WithMany(x => x.EmailLogs)
            .HasForeignKey(x => x.PayslipId)
            .OnDelete(DeleteBehavior.Cascade);
    }
}
