using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class PayrollItemConfiguration : IEntityTypeConfiguration<PayrollItem>
{
    public void Configure(EntityTypeBuilder<PayrollItem> builder)
    {
        builder.ToTable("PayrollItems");

        builder.HasKey(x => x.Id);

        builder.Property(x => x.AppliedAmount)
            .HasColumnType("decimal(18,2)");

        builder.HasOne(x => x.Compensation)
            .WithMany(x => x.PayrollItems)
            .HasForeignKey(x => x.CompensationId)
            .OnDelete(DeleteBehavior.Restrict);
    }
}
