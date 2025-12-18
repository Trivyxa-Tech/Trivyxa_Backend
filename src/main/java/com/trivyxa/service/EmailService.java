package com.trivyxa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trivyxa.dto.ContactRequest;

import java.util.*;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String fromEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    // Admin / receiver email (can be same as sender)
    @Value("${brevo.sender.email}")
    private String toEmail;

    private static final String BREVO_URL =
            "https://api.brevo.com/v3/smtp/email";

    public void sendContactMail(ContactRequest req) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            Map<String, Object> payload = new HashMap<>();

            // Sender
            payload.put("sender", Map.of(
                    "email", fromEmail,
                    "name", senderName
            ));

            // Receiver
            payload.put("to", List.of(
                    Map.of("email", toEmail, "name", "TRIVYXA Admin")
            ));

            payload.put("subject", "📩 New Project Inquiry – TRIVYXA");

            String htmlContent =
                    "<pre style='font-family: monospace'>" +
                    "========================================\n" +
                    "        🚀 NEW PROJECT INQUIRY\n" +
                    "========================================\n\n" +

                    "👤 CLIENT DETAILS\n" +
                    "----------------------------------------\n" +
                    "• Name: " + req.getName() + "\n" +
                    "• Email: " + req.getEmail() + "\n" +
                    "• Phone: " + safe(req.getPhone(), "Not Provided") + "\n\n" +

                    "🧩 PROJECT INFORMATION\n" +
                    "----------------------------------------\n" +
                    "• Selected Service: " + safe(req.getService(), "Not Selected") + "\n" +
                    "• Estimated Budget: " + safe(req.getBudget(), "Not Specified") + "\n\n" +

                    "📝 PROJECT DESCRIPTION\n" +
                    "----------------------------------------\n" +
                    safe(req.getMessage(), "No description provided") + "\n\n" +

                    "========================================\n" +
                    "📅 Submitted via TRIVYXA.COM\n" +
                    "========================================" +
                    "</pre>";

            payload.put("htmlContent", htmlContent);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    BREVO_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Brevo email failed");
            }

        } catch (Exception ex) {
            System.err.println("❌ Brevo Email Error: " + ex.getMessage());
            throw new RuntimeException("Email sending failed");
        }
    }

    private String safe(String value, String fallback) {
        return (value != null && !value.trim().isEmpty())
                ? value
                : fallback;
    }
}
