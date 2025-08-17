using Microsoft.EntityFrameworkCore;
using PaymentCancellationService.Data;

var builder = WebApplication.CreateBuilder(args);

// Controllers
builder.Services.AddControllers();

// === CORS for React (http://localhost:3000) ===
builder.Services.AddCors(o =>
{
    o.AddPolicy("react", p => p
        .WithOrigins("http://localhost:3000")   // your React dev server
        .AllowAnyHeader()
        .AllowAnyMethod()
    // .AllowCredentials() // only if you send cookies/auth
    );
});

// MySQL DbContext
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseMySql(
        builder.Configuration.GetConnectionString("DefaultConnection"),
        new MySqlServerVersion(new Version(8, 0, 31))
    ));

// Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Swagger UI in dev
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// HTTPS redirect (keep this if your API listens on HTTPS;
// if you only expose HTTP in dev, comment this out)
app.UseHttpsRedirection();

// *** Enable CORS BEFORE MapControllers ***
app.UseCors("react");

app.UseAuthorization();

app.MapControllers();

app.Run();
