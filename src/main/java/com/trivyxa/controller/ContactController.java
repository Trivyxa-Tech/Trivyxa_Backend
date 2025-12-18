package com.trivyxa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trivyxa.dto.ContactRequest;
import com.trivyxa.service.EmailService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String sendContact(@RequestBody ContactRequest req) {

        // Send full request object to email service
        emailService.sendContactMail(req);

        return "Success";
    }
}
