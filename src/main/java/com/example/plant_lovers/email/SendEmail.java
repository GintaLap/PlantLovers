package com.example.plant_lovers.email;

import com.example.plant_lovers.data.DataManagerGarden;
import com.example.plant_lovers.data.User;
import com.example.plant_lovers.dto.EmailDTO;
import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    public void sendNotification(String email) {

        var user = new User();
        var dmg = new DataManagerGarden();

        var emailLog = new EmailDTO();
        var username = emailLog.getUsername();
        var password = emailLog.getPassword();
        var subject = "Plant Lover App :)";
        var text = "Dear " + user.getName() + "! It's time to water Your plants now!"
                + "\n\n Watering today: " + dmg.getYourPlants(0)
                + "\n\n With kind regards, Your Plant Lover app!";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailLog.getUsername()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void sending_notification() {
        sendNotification("arta.blumberga@gmail.com");
    }
}