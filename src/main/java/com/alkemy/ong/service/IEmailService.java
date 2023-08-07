package com.alkemy.ong.service;

import java.io.IOException;

public interface IEmailService {

    void sendText(String from, String to, String subject, String body) throws IOException;
    

    void sendHTML(String from, String to, String subject, String body) throws IOException;
    
    void sendWelcomeEmail(String to) throws IOException;

    void sendThanksContactEmail(String email) throws IOException;
}

