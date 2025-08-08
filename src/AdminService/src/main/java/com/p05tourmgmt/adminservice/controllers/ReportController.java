package com.p05tourmgmt.adminservice.controllers;

import com.p05tourmgmt.adminservice.entities.Payment;
import com.p05tourmgmt.adminservice.services.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class ReportController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/report")
    public void generateReport(HttpServletResponse response) throws IOException {
        List<Payment> payments = paymentService.getAllPayments();

        // Set CSV content type
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"payments_report.csv\"");

        PrintWriter writer = response.getWriter();
        writer.println("Payment ID,Amount,Date,Mode ID,Booking ID");
        for (Payment p : payments) {
            writer.printf("%d,%.2f,\"%s\",%d,%d%n",
                    p.getPaymentId(),
                    p.getAmount(),
                    new SimpleDateFormat("yyyy-MM-dd").format(p.getPaymentDate()),
                    p.getModeId().getModeId(),  // assuming PaymentMode object
                    p.getBookingId());
        }

        writer.flush();
        writer.close();
    }
}
