package Figura;


import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSimple {

    EmailSimple() {

    }

    public void sendSimpleEmail(String user, String pass, String from, String to, String text, String subject) {

        // Konfiguracja parametrów serwera SMTP
        String host = "smtp.poczta.onet.pl"; // Adres serwera SMTP
        String username = from; // Twój login do skrzynki e-mail
        String password = pass; // Twoje hasło do skrzynki e-mail

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");

        // Tworzenie sesji e-mail
        Session session = Session.getInstance(props);

        try {
            // Tworzenie wiadomości e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Adres odbiorcy
            message.setSubject(subject);
            message.setText(text);

            // Autoryzacja i wysłanie wiadomości
            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Wiadomość została wysłana pomyślnie.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


