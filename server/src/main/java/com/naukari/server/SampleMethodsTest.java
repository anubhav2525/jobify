package com.naukari.server;

import com.naukari.server.utils.EmailService;
import com.naukari.server.utils.RandomStringGenerator;
public class SampleMethodsTest {

    private static RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
    private static EmailService emailService = new EmailService();

    public static void main(String[] args) {
        System.out.println(randomStringGenerator.generate(6));

        EmailService.Response<Void> response = emailService.sendEmail("infohuntofficial@gmail.com", "Sample mail by Spring boot", "Hello from naukari");
        System.out.println(response.getStatus() + " : " + response.getMessage());
    }
}
