import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import static org.simplejavamail.mailer.MailerBuilder.buildMailer;

public class Main {
    public static void main(String[] args) {

        Email email = EmailBuilder.startingBlank()
                .from("orzel990@vp.pl", "orzel990@vp.pl")
                .to("Mateusz94@gmail.com", "Mateusz94@gmail.com")
                .withSubject("Email Subject")
                .withPlainText("Email Body")
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.poczta.onet.pl", 587, "orzel990@vp.pl", "mateusz1")
                .withTransportStrategy(TransportStrategy.SMTPS).buildMailer();

        mailer.testConnection();

        mailer.sendMail(email);



    }
}
