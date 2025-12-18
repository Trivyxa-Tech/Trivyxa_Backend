package com.trivyxa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trivyxa.dto.ContactRequest;
import com.trivyxa.dto.ApiResponse;
import com.trivyxa.service.EmailService;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(
    origins = "https://trivyxafrontend-production-6c71.up.railway.app",
    allowedHeaders = "*",
    methods = { RequestMethod.POST, RequestMethod.OPTIONS }
)
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> sendContact(@RequestBody ContactRequest req) {

        emailService.sendContactMail(req);

        return ResponseEntity.ok().body(
            new ApiResponse("SUCCESS", "Contact email sent successfully")
        );
    }
}
