using BBV.HR.Application.Entities;
using Microsoft.EntityFrameworkCore;


namespace BBV.HR.Infrastructure.Data;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
    {
    }

    public DbSet<Employee> Employees => Set<Employee>();
    public DbSet<Project> Projects => Set<Project>();
    public DbSet<ProjectMember> ProjectMembers => Set<ProjectMember>();
    public DbSet<Capability> Capabilities => Set<Capability>();
    public DbSet<EmployeeCapability> EmployeeCapabilities => Set<EmployeeCapability>();
    public DbSet<ProjectRequiredCapability> ProjectRequiredCapabilities => Set<ProjectRequiredCapability>();
    public DbSet<Timesheet> Timesheets => Set<Timesheet>();
    public DbSet<TimeEntry> TimeEntries => Set<TimeEntry>();
    public DbSet<BudgetAdjustment> BudgetAdjustments => Set<BudgetAdjustment>();
    public DbSet<ProductivitySession> ProductivitySessions => Set<ProductivitySession>();
    public DbSet<ProductivityAppLog> ProductivityAppLogs => Set<ProductivityAppLog>();
    public DbSet<ProductivityWebsiteLog> ProductivityWebsiteLogs => Set<ProductivityWebsiteLog>();
    public DbSet<ProductivityScreenshot> ProductivityScreenshots => Set<ProductivityScreenshot>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        // Employee
        modelBuilder.Entity<Employee>(entity =>
        {
            entity.ToTable("employees");
            entity.HasKey(e => e.Id);
            entity.HasIndex(e => e.EmployeeCode).IsUnique();
            entity.Property(e => e.FirstName).IsRequired();
            entity.Property(e => e.LastName).IsRequired();
            entity.Property(e => e.EmployeeCode).IsRequired();
        });

        // Project
        modelBuilder.Entity<Project>(entity =>
        {
            entity.ToTable("projects");
            entity.HasKey(p => p.Id);
            entity.HasIndex(p => p.Code).IsUnique();
            entity.Property(p => p.Code).IsRequired();
            entity.Property(p => p.Name).IsRequired();
            entity.Property(p => p.Status).IsRequired();

            entity.HasOne(p => p.Manager)
                .WithMany(e => e.ManagedProjects)
                .HasForeignKey(p => p.ManagerId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(p => p.Creator)
                .WithMany(e => e.CreatedProjects)
                .HasForeignKey(p => p.CreatedBy)
                .OnDelete(DeleteBehavior.SetNull);
        });

        // ProjectMember
        modelBuilder.Entity<ProjectMember>(entity =>
        {
            entity.ToTable("project_members");
            entity.HasKey(pm => pm.Id);
            entity.HasIndex(pm => new { pm.ProjectId, pm.EmployeeId });

            entity.HasOne(pm => pm.Project)
                .WithMany(p => p.ProjectMembers)
                .HasForeignKey(pm => pm.ProjectId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(pm => pm.Employee)
                .WithMany(e => e.ProjectMembers)
                .HasForeignKey(pm => pm.EmployeeId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        // Capability
        modelBuilder.Entity<Capability>(entity =>
        {
            entity.ToTable("capabilities");
            entity.HasKey(c => c.Id);
            entity.HasIndex(c => c.Name).IsUnique();
            entity.Property(c => c.Name).IsRequired();
        });

        // EmployeeCapability
        modelBuilder.Entity<EmployeeCapability>(entity =>
        {
            entity.ToTable("employee_capabilities");
            entity.HasKey(ec => ec.Id);
            entity.HasIndex(ec => new { ec.EmployeeId, ec.CapabilityId }).IsUnique();

            entity.HasOne(ec => ec.Employee)
                .WithMany(e => e.EmployeeCapabilities)
                .HasForeignKey(ec => ec.EmployeeId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(ec => ec.Capability)
                .WithMany(c => c.EmployeeCapabilities)
                .HasForeignKey(ec => ec.CapabilityId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        // ProjectRequiredCapability
        modelBuilder.Entity<ProjectRequiredCapability>(entity =>
        {
            entity.ToTable("project_required_capabilities");
            entity.HasKey(prc => prc.Id);
            entity.HasIndex(prc => new { prc.ProjectId, prc.CapabilityId }).IsUnique();

            entity.HasOne(prc => prc.Project)
                .WithMany(p => p.ProjectRequiredCapabilities)
                .HasForeignKey(prc => prc.ProjectId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(prc => prc.Capability)
                .WithMany(c => c.ProjectRequiredCapabilities)
                .HasForeignKey(prc => prc.CapabilityId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        // Timesheet
        modelBuilder.Entity<Timesheet>(entity =>
        {
            entity.ToTable("timesheets");
            entity.HasKey(t => t.Id);
            entity.HasIndex(t => new { t.EmployeeId, t.WeekStartDate }).IsUnique();
            entity.Property(t => t.WeekStartDate).IsRequired();
            entity.Property(t => t.Status).IsRequired();

            entity.HasOne(t => t.Employee)
                .WithMany(e => e.Timesheets)
                .HasForeignKey(t => t.EmployeeId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(t => t.Reviewer)
                .WithMany(e => e.ReviewedTimesheets)
                .HasForeignKey(t => t.ReviewedBy)
                .OnDelete(DeleteBehavior.SetNull);
        });

        // TimeEntry
        modelBuilder.Entity<TimeEntry>(entity =>
        {
            entity.ToTable("time_entries");
            entity.HasKey(te => te.Id);
            entity.Property(te => te.WorkDate).IsRequired();
            entity.Property(te => te.TimeType).IsRequired();

            entity.HasOne(te => te.Timesheet)
                .WithMany(t => t.TimeEntries)
                .HasForeignKey(te => te.TimesheetId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(te => te.Project)
                .WithMany(p => p.TimeEntries)
                .HasForeignKey(te => te.ProjectId)
                .OnDelete(DeleteBehavior.SetNull);
        });

        // BudgetAdjustment
        modelBuilder.Entity<BudgetAdjustment>(entity =>
        {
            entity.ToTable("budget_adjustments");
            entity.HasKey(ba => ba.Id);
            entity.Property(ba => ba.AdjustmentType).IsRequired();
            entity.Property(ba => ba.Amount).IsRequired();
            entity.Property(ba => ba.Status).IsRequired();

            entity.HasOne(ba => ba.Project)
                .WithMany(p => p.BudgetAdjustments)
                .HasForeignKey(ba => ba.ProjectId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(ba => ba.Requester)
                .WithMany(e => e.RequestedBudgetAdjustments)
                .HasForeignKey(ba => ba.RequestedBy)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(ba => ba.Approver)
                .WithMany(e => e.ApprovedBudgetAdjustments)
                .HasForeignKey(ba => ba.ApprovedBy)
                .OnDelete(DeleteBehavior.SetNull);
        });

        // ProductivitySession
        modelBuilder.Entity<ProductivitySession>(entity =>
        {
            entity.ToTable("productivity_sessions");
            entity.HasKey(ps => ps.Id);
            entity.Property(ps => ps.StartedAt).IsRequired();

            entity.HasOne(ps => ps.Employee)
                .WithMany(e => e.ProductivitySessions)
                .HasForeignKey(ps => ps.EmployeeId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(ps => ps.Project)
                .WithMany(p => p.ProductivitySessions)
                .HasForeignKey(ps => ps.ProjectId)
                .OnDelete(DeleteBehavior.SetNull);
        });

        // ProductivityAppLog
        modelBuilder.Entity<ProductivityAppLog>(entity =>
        {
            entity.ToTable("productivity_app_logs");
            entity.HasKey(pal => pal.Id);
            entity.Property(pal => pal.AppName).IsRequired();

            entity.HasOne(pal => pal.Session)
                .WithMany(ps => ps.AppLogs)
                .HasForeignKey(pal => pal.SessionId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        // ProductivityWebsiteLog
        modelBuilder.Entity<ProductivityWebsiteLog>(entity =>
        {
            entity.ToTable("productivity_website_logs");
            entity.HasKey(pwl => pwl.Id);

            entity.HasOne(pwl => pwl.Session)
                .WithMany(ps => ps.WebsiteLogs)
                .HasForeignKey(pwl => pwl.SessionId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        // ProductivityScreenshot
        modelBuilder.Entity<ProductivityScreenshot>(entity =>
        {
            entity.ToTable("productivity_screenshots");
            entity.HasKey(psc => psc.Id);
            entity.Property(psc => psc.CapturedAt).IsRequired();
            entity.Property(psc => psc.FileUrl).IsRequired();

            entity.HasOne(psc => psc.Session)
                .WithMany(ps => ps.Screenshots)
                .HasForeignKey(psc => psc.SessionId)
                .OnDelete(DeleteBehavior.Cascade);
        });
    }
}
