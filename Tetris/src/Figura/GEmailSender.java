package Figura;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class GEmailSender {

    public boolean sendEmail(String from,String to,String subject,String text){
        boolean flag = false;

        System.out.println("proces wysyłania Email...");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);  //"mail.smtp.starttls.enable",true
        properties.put("mail.smtp.host","poczta.o2.pl"); // smtp.gmail.com  //"smtp.poczta.onet.pl"
        properties.put("mail.smtp.port","587");  //587 - 465  onet,gmail dzxiała na 587

        // avoid hang by setting timeout; 5 seconds
        properties.put("mail.smtp.timeout", "15000");
        properties.put("mail.smtp.connectiontimeout", "15000");


        String username = "adameagle@o2.pl";
        String password = "VVRRSCL4TUP3JSGS";             //"gtjn uqdz twxn ymzw";  // wygenerowane haslo specjalnie pod aplikacje

        System.out.println("otwieram sesję, sprawdzam dane...");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });


        try{
            System.out.println("Gemneruję wiadomość do wysłania...");

            //bez załącznika
//            Message message = new MimeMessage(session);
//
//            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
//            message.setFrom(new InternetAddress(from));
//
//            message.setSubject(subject);
//
//            message.setText(text);

            ////załącznik
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);
            message.setText(text);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile("C:\\Users\\mateu\\OneDrive\\Dokumenty\\18.05.2023\\Skan_20231108.pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            //koniec załącznika

            message.setContent(multipart);


            System.out.println("Wysyłam...");
            Transport.send(message);

            flag = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }
}
