package com.naukari.server.utils;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.company.name}")
    private String companyName;

    @Value("${app.company.support-url}")
    private String supportUrl;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    // Send simple text email
    public Response<Void> sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(fromEmail);
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
            return new Response<>("Success", "Email sent successfully", null);
        } catch (Exception e) {
            return new Response<>("Error", "Failed to send email: " + e.getMessage(), null);
        }
    }

    // Send HTML email using Thymeleaf template
    public Response<Void> sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Set basic email properties
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);

            // Create Thymeleaf context with variables
            Context context = new Context();

            // Add default variables
            context.setVariable("supportUrl", supportUrl);
            context.setVariable("currentYear", LocalDateTime.now().getYear());
            context.setVariable("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));

            // Add custom variables
            if (variables != null) {
                variables.forEach(context::setVariable);
            }

            // Process template
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            return new Response<>("Success", "HTML email sent successfully", null);
        } catch (MessagingException e) {
            return new Response<>("Error", "Failed to send HTML email: " + e.getMessage(), null);
        } catch (Exception e) {
            return new Response<>("Error", "Unexpected error while sending email: " + e.getMessage(), null);
        }
    }

    // Send account verification email
    public Response<Void> sendAccountVerificationEmail(String to, String userName, String otp) {
        try {
            Map<String, Object> variables = Map.of(
                    "userName", userName,
                    "otp", otp,
                    "otpExpiryMinutes", "15",
                    "supportUrl", frontendBaseUrl + "/help-support"
            );

            String subject = "Account Verification - OTP Required";
            return sendHtmlEmail(to, subject, "/email/account-verification-mail", variables);
        } catch (Exception e) {
            return new Response<>("Error", "Failed to send verification email: " + e.getMessage(), null);
        }
    }

    // Send password reset email
    public Response<Void> sendPasswordResetEmail(String to, String userName, String resetToken) {
        try {
            Map<String, Object> variables = Map.of(
                    "userName", userName,
                    "resetToken", resetToken,
                    "resetUrl", frontendBaseUrl + "/reset-password?token=" + resetToken,
                    "tokenExpiryMinutes", "15"
            );

            String subject = "Password Reset Request";
            return sendHtmlEmail(to, subject, "/email/password-reset-mail", variables);
        } catch (Exception e) {
            return new Response<>("Error", "Failed to send password reset email: " + e.getMessage(), null);
        }
    }

    // Send welcome email
    public Response<Void> sendWelcomeEmail(String to, String userName) {
        try {
            Map<String, Object> variables = Map.of(
                    "userName", userName,
                    "loginUrl", frontendBaseUrl + "/login",
                    "dashboardUrl", frontendBaseUrl + "/dashboard"
            );

            String subject = "Welcome to " + companyName + "!";
            return sendHtmlEmail(to, subject, "/email/welcome-mail", variables);
        } catch (Exception e) {
            return new Response<>("Error", "Failed to send welcome email: " + e.getMessage(), null);
        }
    }

    // Send account deactivation notification
    public Response<Void> sendAccountDeactivationEmail(String to, String userName) {
        try {
            Map<String, Object> variables = Map.of(
                    "userName", userName,
                    "reactivationUrl", frontendBaseUrl + "/reactivate-account"
            );

            String subject = "Account Deactivated - " + companyName;
            return sendHtmlEmail(to, subject, "account-deactivation-mail", variables);
        } catch (Exception e) {
            return new Response<>("Error", "Failed to send deactivation email: " + e.getMessage(), null);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response<T> {
        private String status;
        private String message;
        private T data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailRequest {
        private String to;
        private String subject;
        private String body;
    }
}