package com.alkemy.ong.service;

import com.alkemy.ong.util.EmailConstants;
import com.sendgrid.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SendGridEmailService implements IEmailService {
    @Value("${sendgrid.email}")
    private String sender;

    @Autowired
    private SendGrid sendGridClient;

    @Autowired
    private MessageSource messageSource;


    @Override
    public void sendText(String from, String to, String subject, String body) throws IOException {
    }

    @Override
    public void sendHTML(String from, String to, String subject, String body) throws IOException {
    }

    @Override
    public void sendWelcomeEmail(String email) throws IOException {
        Email from = new Email(sender);
        String subject = EmailConstants.SUBJECT_WELCOME;
        Email to = new Email(email);
        Content content = new Content("text/html", htmlToString(EmailConstants.TEMPLATE_WELCOME));
        Mail mail = new Mail(from, subject, to, content);
        sendMail(mail);
    }

    public void sendMail(Mail mail) throws IOException{
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint(EmailConstants.ENDPOINT);
            request.setBody(mail.build());
            sendGridClient.api(request);
        } catch (IOException ex) {
            throw new IOException(messageSource.getMessage("email.wasNotSend",null, Locale.US));
        }
    }
    public void sendThanksContactEmail(String email) throws IOException {
        Email from = new Email(sender);
        Email to = new Email(email);
        String subject = EmailConstants.THANKS_CONTACT;
        Content content = new Content("text/html", htmlToString(EmailConstants.TEMPLATE_CONTACT));
        Mail mail = new Mail(from, subject, to, content);
        sendMail(mail);
    }

    public String htmlToString(String pathFile) throws IOException {
        StringBuilder builder = new StringBuilder();
        String str;
        File htmlFile = new File(pathFile);
        BufferedReader in = new BufferedReader(new FileReader(htmlFile));
        while((str = in.readLine())!=null)
            builder.append(str);
        in.close();
        return builder.toString();
    }
}