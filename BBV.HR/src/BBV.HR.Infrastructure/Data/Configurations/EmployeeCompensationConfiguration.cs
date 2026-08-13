using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class EmployeeCompensationConfiguration : IEntityTypeConfiguration<EmployeeCompensation>
{
    public void Configure(EntityTypeBuilder<EmployeeCompensation> builder)
    {
        builder.ToTable("EmployeeCompensations");

        builder.HasKey(x => x.Id);

        builder.Property(x => x.EmployeeId)
            .IsRequired()
            .HasMaxLength(50);

        builder.Property(x => x.Status)
            .HasMaxLength(20);

        builder.Property(x => x.AppliedAmount)
            .HasColumnType("decimal(18,2)");

        builder.HasOne(x => x.Employee)
            .WithMany(x => x.EmployeeCompensations)
            .HasForeignKey(x => x.EmployeeId)
            .OnDelete(DeleteBehavior.Cascade);

        builder.HasOne(x => x.Compensation)
            .WithMany(x => x.EmployeeCompensations)
            .HasForeignKey(x => x.CompensationId)
            .OnDelete(DeleteBehavior.Restrict);
    }
}
