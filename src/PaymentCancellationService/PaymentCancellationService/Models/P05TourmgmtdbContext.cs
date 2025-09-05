using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Pomelo.EntityFrameworkCore.MySql.Scaffolding.Internal;

namespace PaymentCancellationService.Models;

public partial class P05TourmgmtdbContext : DbContext
{
    public P05TourmgmtdbContext()
    {
    }

    public P05TourmgmtdbContext(DbContextOptions<P05TourmgmtdbContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Booking> Bookings { get; set; }

    public virtual DbSet<Cancellation> Cancellations { get; set; }

    public virtual DbSet<Category> Categories { get; set; }

    public virtual DbSet<Payment> Payments { get; set; }

    public virtual DbSet<PaymentMode> PaymentModes { get; set; }

    public virtual DbSet<Refund> Refunds { get; set; }

    public virtual DbSet<Role> Roles { get; set; }

    public virtual DbSet<TourAgency> TourAgencies { get; set; }

    public virtual DbSet<TourPackage> TourPackages { get; set; }

    public virtual DbSet<TourSchedule> TourSchedules { get; set; }

    public virtual DbSet<Tourist> Tourists { get; set; }

    public virtual DbSet<TouristDetail> TouristDetails { get; set; }

    public virtual DbSet<User> Users { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see https://go.microsoft.com/fwlink/?LinkId=723263.
        => optionsBuilder.UseMySql("server=localhost;port=3306;user=root;password=root;database=p05_tourmgmtdb", Microsoft.EntityFrameworkCore.ServerVersion.Parse("8.2.0-mysql"));

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder
            .UseCollation("utf8mb4_0900_ai_ci")
            .HasCharSet("utf8mb4");

        modelBuilder.Entity<Booking>(entity =>
        {
            entity.HasKey(e => e.BookingId).HasName("PRIMARY");

            entity.ToTable("booking");

            entity.HasIndex(e => e.ScheduleId, "schedule_id");

            entity.HasIndex(e => e.TouristId, "tourist_id");

            entity.Property(e => e.BookingId).HasColumnName("booking_id");
            entity.Property(e => e.BookingDate).HasColumnName("booking_date");
            entity.Property(e => e.NoOfTourist).HasColumnName("no_of_tourist");
            entity.Property(e => e.ScheduleId).HasColumnName("schedule_id");
            entity.Property(e => e.Status)
                .HasMaxLength(20)
                .HasColumnName("status");
            entity.Property(e => e.TouristId).HasColumnName("tourist_id");

            entity.HasOne(d => d.Schedule).WithMany(p => p.Bookings)
                .HasForeignKey(d => d.ScheduleId)
                .HasConstraintName("booking_ibfk_1");

            entity.HasOne(d => d.Tourist).WithMany(p => p.Bookings)
                .HasForeignKey(d => d.TouristId)
                .HasConstraintName("booking_ibfk_2");
        });

        modelBuilder.Entity<Cancellation>(entity =>
        {
            entity.HasKey(e => e.CancellationId).HasName("PRIMARY");

            entity.ToTable("cancellation");

            entity.HasIndex(e => e.BookingId, "booking_id");

            entity.Property(e => e.CancellationId).HasColumnName("cancellation_id");
            entity.Property(e => e.BookingId).HasColumnName("booking_id");
            entity.Property(e => e.CancelDate).HasColumnName("cancel_date");
            entity.Property(e => e.CancelReason)
                .HasMaxLength(255)
                .HasColumnName("cancel_reason");
            entity.Property(e => e.CancellationStatus)
                .HasMaxLength(20)
                .HasColumnName("cancellation_status");

            entity.HasOne(d => d.Booking).WithMany(p => p.Cancellations)
                .HasForeignKey(d => d.BookingId)
                .HasConstraintName("cancellation_ibfk_1");
        });

        modelBuilder.Entity<Category>(entity =>
        {
            entity.HasKey(e => e.CatId).HasName("PRIMARY");

            entity.ToTable("category");

            entity.HasIndex(e => e.Cname, "cname").IsUnique();

            entity.Property(e => e.CatId).HasColumnName("cat_id");
            entity.Property(e => e.Cname).HasColumnName("cname");
        });

        modelBuilder.Entity<Payment>(entity =>
        {
            entity.HasKey(e => e.PaymentId).HasName("PRIMARY");

            entity.ToTable("payment");

            entity.HasIndex(e => e.BookingId, "booking_id");

            entity.HasIndex(e => e.ModeId, "mode_id");

            entity.Property(e => e.PaymentId).HasColumnName("payment_id");
            entity.Property(e => e.Amount)
                .HasPrecision(10, 2)
                .HasColumnName("amount");
            entity.Property(e => e.BookingId).HasColumnName("booking_id");
            entity.Property(e => e.ModeId).HasColumnName("mode_id");
            entity.Property(e => e.PaymentDate).HasColumnName("payment_date");

            entity.HasOne(d => d.Booking).WithMany(p => p.Payments)
                .HasForeignKey(d => d.BookingId)
                .HasConstraintName("payment_ibfk_2");

            entity.HasOne(d => d.Mode).WithMany(p => p.Payments)
                .HasForeignKey(d => d.ModeId)
                .HasConstraintName("payment_ibfk_1");
        });

        modelBuilder.Entity<PaymentMode>(entity =>
        {
            entity.HasKey(e => e.ModeId).HasName("PRIMARY");

            entity.ToTable("payment_mode");

            entity.HasIndex(e => e.ModeName, "mode_name").IsUnique();

            entity.Property(e => e.ModeId).HasColumnName("mode_id");
            entity.Property(e => e.ModeName)
                .HasMaxLength(45)
                .HasColumnName("mode_name");
        });

        modelBuilder.Entity<Refund>(entity =>
        {
            entity.HasKey(e => e.RefundId).HasName("PRIMARY");

            entity.ToTable("refund");

            entity.HasIndex(e => e.CancellationId, "cancellation_id");

            entity.HasIndex(e => e.PaymentId, "payment_id");

            entity.Property(e => e.RefundId).HasColumnName("refund_id");
            entity.Property(e => e.CancellationId).HasColumnName("cancellation_id");
            entity.Property(e => e.PaymentId).HasColumnName("payment_id");
            entity.Property(e => e.RefundAmount)
                .HasPrecision(10, 2)
                .HasColumnName("refund_amount");
            entity.Property(e => e.RefundDate).HasColumnName("refund_date");
            entity.Property(e => e.RefundStatus)
                .HasMaxLength(45)
                .HasColumnName("refund_status");

            entity.HasOne(d => d.Cancellation).WithMany(p => p.Refunds)
                .HasForeignKey(d => d.CancellationId)
                .HasConstraintName("refund_ibfk_1");

            entity.HasOne(d => d.Payment).WithMany(p => p.Refunds)
                .HasForeignKey(d => d.PaymentId)
                .HasConstraintName("refund_ibfk_2");
        });

        modelBuilder.Entity<Role>(entity =>
        {
            entity.HasKey(e => e.Rid).HasName("PRIMARY");

            entity.ToTable("role");

            entity.HasIndex(e => e.Rname, "rname").IsUnique();

            entity.Property(e => e.Rid).HasColumnName("rid");
            entity.Property(e => e.Rname).HasColumnName("rname");
        });

        modelBuilder.Entity<TourAgency>(entity =>
        {
            entity.HasKey(e => e.TourAgencyId).HasName("PRIMARY");

            entity.ToTable("tour_agency");

            entity.HasIndex(e => e.Uid, "uid").IsUnique();

            entity.Property(e => e.TourAgencyId).HasColumnName("tour_agency_id");
            entity.Property(e => e.Address)
                .HasMaxLength(255)
                .HasColumnName("address");
            entity.Property(e => e.AgencyEmail)
                .HasMaxLength(255)
                .HasColumnName("agency_email");
            entity.Property(e => e.LicenseNumber)
                .HasMaxLength(255)
                .HasColumnName("license_number");
            entity.Property(e => e.LiscenceNumber)
                .HasMaxLength(255)
                .HasColumnName("liscence_number");
            entity.Property(e => e.PhoneNo)
                .HasMaxLength(255)
                .HasColumnName("phone_no");
            entity.Property(e => e.TourAgencyName)
                .HasMaxLength(255)
                .HasColumnName("tour_agency_name");
            entity.Property(e => e.Uid).HasColumnName("uid");

            entity.HasOne(d => d.UidNavigation).WithOne(p => p.TourAgency)
                .HasForeignKey<TourAgency>(d => d.Uid)
                .HasConstraintName("tour_agency_ibfk_1");
        });

        modelBuilder.Entity<TourPackage>(entity =>
        {
            entity.HasKey(e => e.PackageId).HasName("PRIMARY");

            entity.ToTable("tour_package");

            entity.HasIndex(e => e.CatId, "cat_id");

            entity.HasIndex(e => e.Pname, "pname").IsUnique();

            entity.HasIndex(e => e.TourAgencyId, "tour_agency_id");

            entity.Property(e => e.PackageId).HasColumnName("package_id");
            entity.Property(e => e.CatId).HasColumnName("cat_id");
            entity.Property(e => e.Description)
                .HasMaxLength(255)
                .HasColumnName("description");
            entity.Property(e => e.Destination)
                .HasMaxLength(255)
                .HasColumnName("destination");
            entity.Property(e => e.Pname).HasColumnName("pname");
            entity.Property(e => e.TourAgencyId).HasColumnName("tour_agency_id");

            entity.HasOne(d => d.Cat).WithMany(p => p.TourPackages)
                .HasForeignKey(d => d.CatId)
                .HasConstraintName("tour_package_ibfk_2");

            entity.HasOne(d => d.TourAgency).WithMany(p => p.TourPackages)
                .HasForeignKey(d => d.TourAgencyId)
                .HasConstraintName("tour_package_ibfk_1");
        });

        modelBuilder.Entity<TourSchedule>(entity =>
        {
            entity.HasKey(e => e.ScheduleId).HasName("PRIMARY");

            entity.ToTable("tour_schedule");

            entity.HasIndex(e => e.PackageId, "package_id");

            entity.Property(e => e.ScheduleId).HasColumnName("schedule_id");
            entity.Property(e => e.AvailableBookings).HasColumnName("available_bookings");
            entity.Property(e => e.Cost)
                .HasPrecision(38, 2)
                .HasColumnName("cost");
            entity.Property(e => e.Duration).HasColumnName("duration");
            entity.Property(e => e.EndDate).HasColumnName("end_date");
            entity.Property(e => e.PackageId).HasColumnName("package_id");
            entity.Property(e => e.StartDate).HasColumnName("start_date");

            entity.HasOne(d => d.Package).WithMany(p => p.TourSchedules)
                .HasForeignKey(d => d.PackageId)
                .HasConstraintName("tour_schedule_ibfk_1");
        });

        modelBuilder.Entity<Tourist>(entity =>
        {
            entity.HasKey(e => e.TouristId).HasName("PRIMARY");

            entity.ToTable("tourist");

            entity.HasIndex(e => e.Uid, "uid");

            entity.Property(e => e.TouristId).HasColumnName("tourist_id");
            entity.Property(e => e.Address)
                .HasMaxLength(255)
                .HasColumnName("address");
            entity.Property(e => e.Dob)
                .HasMaxLength(6)
                .HasColumnName("dob");
            entity.Property(e => e.Fname)
                .HasMaxLength(255)
                .HasColumnName("fname");
            entity.Property(e => e.Lname)
                .HasMaxLength(255)
                .HasColumnName("lname");
            entity.Property(e => e.Uid).HasColumnName("uid");

            entity.HasOne(d => d.UidNavigation).WithMany(p => p.Tourists)
                .HasForeignKey(d => d.Uid)
                .HasConstraintName("tourist_ibfk_1");
        });

        modelBuilder.Entity<TouristDetail>(entity =>
        {
            entity.HasKey(e => e.TravellerId).HasName("PRIMARY");

            entity.ToTable("tourist_details");

            entity.HasIndex(e => e.BookingId, "booking_id");

            entity.Property(e => e.TravellerId).HasColumnName("traveller_id");
            entity.Property(e => e.Age).HasColumnName("age");
            entity.Property(e => e.BookingId).HasColumnName("booking_id");
            entity.Property(e => e.Fname)
                .HasMaxLength(100)
                .HasColumnName("fname");
            entity.Property(e => e.Gender)
                .HasMaxLength(10)
                .HasColumnName("gender");
            entity.Property(e => e.IdProof)
                .HasMaxLength(100)
                .HasColumnName("id_proof");
            entity.Property(e => e.Lname)
                .HasMaxLength(100)
                .HasColumnName("lname");

            entity.HasOne(d => d.Booking).WithMany(p => p.TouristDetails)
                .HasForeignKey(d => d.BookingId)
                .HasConstraintName("tourist_details_ibfk_1");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.Uid).HasName("PRIMARY");

            entity.ToTable("user");

            entity.HasIndex(e => e.Email, "email").IsUnique();

            entity.HasIndex(e => e.Password, "password").IsUnique();

            entity.HasIndex(e => e.PhoneNo, "phone_no").IsUnique();

            entity.HasIndex(e => e.Rid, "rid");

            entity.Property(e => e.Uid).HasColumnName("uid");
            entity.Property(e => e.Email).HasColumnName("email");
            entity.Property(e => e.Password).HasColumnName("password");
            entity.Property(e => e.PhoneNo).HasColumnName("phone_no");
            entity.Property(e => e.Rid).HasColumnName("rid");
            entity.Property(e => e.Uname)
                .HasMaxLength(255)
                .HasColumnName("uname");

            entity.HasOne(d => d.RidNavigation).WithMany(p => p.Users)
                .HasForeignKey(d => d.Rid)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("user_ibfk_1");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
