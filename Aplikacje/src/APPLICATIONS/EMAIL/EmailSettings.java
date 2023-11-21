package APPLICATIONS.EMAIL;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class EmailSettings {


    public boolean sendEmailwithAttachment(String from, String to, String subject, String text,String attachmentPath) {
        boolean flag = false;
        System.out.println("proces wysyłania Email...");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "poczta.o2.pl");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // avoid hang by setting timeout; 5 seconds
        properties.put("mail.smtp.timeout", "15000");
        properties.put("mail.smtp.connectiontimeout", "15000");


        String username = "adameagle@o2.pl";
        String password = "VVRRSCL4TUP3JSGS";             //"gtjn uqdz twxn ymzw";  // wygenerowane haslo specjalnie pod aplikacje

        System.out.println("otwieram sesję, sprawdzam dane...");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        try {
            System.out.println("Gemneruję wiadomość do wysłania...");

            ////załącznik
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);
            message.setText(text);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachmentPath);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            //koniec załącznika

            message.setContent(multipart);


            System.out.println("Wysyłam...");
            Transport.send(message);

            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


    public boolean sendEmail(String from, String to, String subject, String text) {
        boolean flag = false;
        System.out.println("proces wysyłania Email...");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);  //"mail.smtp.starttls.enable",true
        properties.put("mail.smtp.host", "poczta.o2.pl"); // smtp.gmail.com  //"smtp.poczta.onet.pl"
        properties.put("mail.smtp.port", "587");  //587 - 465  onet,gmail dzxiała na 587
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // avoid hang by setting timeout; 5 seconds
        properties.put("mail.smtp.timeout", "15000");
        properties.put("mail.smtp.connectiontimeout", "15000");


        String username = "adameagle@o2.pl";
        String password = "VVRRSCL4TUP3JSGS";             //"gtjn uqdz twxn ymzw";  // wygenerowane haslo specjalnie pod aplikacje

        System.out.println("otwieram sesję, sprawdzam dane...");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        try {
            System.out.println("Gemneruję wiadomość do wysłania...");

            //bez załącznika
            Message message = new MimeMessage(session);

            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(text);

            System.out.println("Wysyłam...");
            Transport.send(message);

            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    ///////////////////////////////do spamu



    public boolean sendMultiEmailwithAttachment(String from, String[] to, String subject, String text, String attachmentPath) {
        boolean flag = false;
        System.out.println("proces wysyłania Email...");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "poczta.o2.pl");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // avoid hang by setting timeout; 5 seconds
        properties.put("mail.smtp.timeout", "15000");
        properties.put("mail.smtp.connectiontimeout", "15000");


        String username = "adameagle@o2.pl";
        String password = "VVRRSCL4TUP3JSGS";             //"gtjn uqdz twxn ymzw";  // wygenerowane haslo specjalnie pod aplikacje

        System.out.println("otwieram sesję, sprawdzam dane...");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        try {
            System.out.println("Gemneruję wiadomość do wysłania...");

            ////załącznik
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            for (int i = 0; i < to.length; i++) {
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
            }
            message.setRecipients(Message.RecipientType.TO,message.getAllRecipients());

            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);
            message.setText(text);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachmentPath);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            //koniec załącznika

            message.setContent(multipart);


            System.out.println("Wysyłam...");
            Transport.send(message);

            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


    public boolean sendMultiEmail(String from, String to[], String subject, String text) {
        boolean flag = false;
        System.out.println("proces wysyłania Email...");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);  //"mail.smtp.starttls.enable",true
        properties.put("mail.smtp.host", "poczta.o2.pl"); // smtp.gmail.com  //"smtp.poczta.onet.pl"
        properties.put("mail.smtp.port", "587");  //587 - 465  onet,gmail dzxiała na 587
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // avoid hang by setting timeout; 5 seconds
        properties.put("mail.smtp.timeout", "15000");
        properties.put("mail.smtp.connectiontimeout", "15000");


        String username = "adameagle@o2.pl";
        String password = "VVRRSCL4TUP3JSGS";             //"gtjn uqdz twxn ymzw";  // wygenerowane haslo specjalnie pod aplikacje

        System.out.println("otwieram sesję, sprawdzam dane...");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        try {
            System.out.println("Gemneruję wiadomość do wysłania...");

            //bez załącznika
            Message message = new MimeMessage(session);

            for (int i = 0; i < to.length; i++) {
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
            }
            message.setRecipients(Message.RecipientType.TO,message.getAllRecipients());
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(text);

            System.out.println("Wysyłam...");
            Transport.send(message);

            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


}


