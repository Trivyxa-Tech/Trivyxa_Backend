package com.trivyxa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.trivyxa.dto.ContactRequest;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendContactMail(ContactRequest req) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("trivyxatech@gmail.com");
        mail.setSubject("📩 New Project Inquiry – TRIVYXA");

        String body =
                "========================================\n" +
                "        🚀 NEW PROJECT INQUIRY\n" +
                "========================================\n\n" +

                "Dear TRIVYXA Team,\n\n" +
                "You have received a new project inquiry from your website.\n\n" +

                "----------------------------------------\n" +
                "👤 CLIENT DETAILS\n" +
                "----------------------------------------\n" +
                "• Name: " + req.getName() + "\n" +
                "• Email: " + req.getEmail() + "\n" +
                "• Phone: " + (req.getPhone() != null && !req.getPhone().isEmpty() ? req.getPhone() : "Not Provided") + "\n\n" +

                "----------------------------------------\n" +
                "🧩 PROJECT INFORMATION\n" +
                "----------------------------------------\n" +
                "• Selected Service: " + (req.getService() != null && !req.getService().isEmpty() ? req.getService() : "Not Selected") + "\n" +
                "• Estimated Budget: " + (req.getBudget() != null && !req.getBudget().isEmpty() ? req.getBudget() : "Not Specified") + "\n\n" +

                "----------------------------------------\n" +
                "📝 PROJECT DESCRIPTION\n" +
                "----------------------------------------\n" +
                req.getMessage() + "\n\n" +

                "========================================\n" +
                "       📅 Submitted via TRIVYXA.COM\n" +
                "========================================\n";

        mail.setText(body);

        mailSender.send(mail);
    }
}
