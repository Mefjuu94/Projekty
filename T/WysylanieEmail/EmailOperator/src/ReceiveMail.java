import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;

import com.sun.mail.pop3.POP3Store;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ReceiveMail {

    String result = "";

    public void incoming(String pop3Host, String storeType, String user, String password, JTextArea label) {
        try {
            //1) get the session object
            Properties properties = new Properties();
            properties.put("mail.pop3.host", pop3Host);
            Session emailSession = Session.getDefaultInstance(properties);

            //2) create the POP3 store object and connect with the pop server
            POP3Store emailStore = (POP3Store) emailSession.getStore(storeType);
            emailStore.connect(user, password);

            //3) create the folder object and open it
            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            //4) retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];


                System.out.println("--------------------------------------------------");


                System.out.println("Email Number " + (i + 1));
                label.append("\n" +"Email Number " + (i + 1));

                //wycięcie nieportrzebnych znaków  /////////////////////////////////////////
                String emailFrom = Arrays.toString(message.getFrom());
                String s = emailFrom;
                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(s);
                while (m.find()) {
                    System.out.println(m.group());
                    emailFrom = m.group();
                }
                /////////////////////////////////////////////////////////////////////////////////

                //dodanie do labela i wyświetlenie w konsoli
                label.append("\n" +"From: " + emailFrom);
                System.out.println("From: " + emailFrom);


                System.out.println("Subject: " + message.getSubject());
                label.append("\n" +"Subject: " + message.getSubject());

                String contentType = message.getContentType();
                String messageContent = "";


                if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        messageContent = part.getContent().toString();

                    }
                }
                else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }

                /////////////////////////
                Document doc1 = Jsoup.parse(messageContent);
                String text = doc1.body().text();
                System.out.println(text);
                ////////////////////////

                String emailtext = text;


                System.out.println("Text: " + emailtext);
                label.append("\n" +"Text: " + "\n" + emailtext);
                label.append("\n" + "-----------------------------------------------------------------------------------------------" + "\n");

            }

            //5) close the store and folder objects
            emailFolder.close(false);
            emailStore.close();

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

}
