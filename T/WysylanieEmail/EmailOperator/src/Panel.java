import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Panel extends JPanel implements MouseListener {

    String host = "poczta.o2.pl";
    String mailStoreType = "pop3";
    String username= "adameagle@o2.pl";
    String password= "VVRRSCL4TUP3JSGS";

    String text = "jakiś tekst";
    String from = "adameagle@o2.pl";
    String to = "qasa21@gmail.com";
    String subject = "Temat";
    EmailSettings emailSettings = new EmailSettings();
    ReceiveMail receiveMail = new ReceiveMail();
    Buttons buttons = new Buttons();

    JTextArea newEmailContent = new JTextArea("your text message");
    JTextArea subjectArea = new JTextArea("subject");
    JTextArea toWho = new JTextArea("to :");
    JTextArea textArea = new JTextArea("your content");
    JTextArea attachmentPatch = new JTextArea("path");
    JRadioButton attachment = new JRadioButton();
    JScrollPane scroll = new JScrollPane(textArea);

    boolean b = false;
    Panel(){
        this.buttons.wczytaj.addMouseListener(this);
        add(buttons.wczytaj);

        this.buttons.sendEmail.addMouseListener(this);
        add(buttons.sendEmail);

        this.buttons.returnToIncomeBox.addMouseListener(this);
        add(buttons.returnToIncomeBox);

        this.buttons.send.addMouseListener(this);
        buttons.setButtons();
        add(buttons.send);


        add(toWho);
        toWho.setVisible(false);
        add(subjectArea);
        subjectArea.setVisible(false);
        add(attachment);
        attachment.setVisible(false);
        add(attachmentPatch);
        attachmentPatch.setVisible(false);
        this.attachment.addMouseListener(this);


        add(scroll);
        scroll.setVisible(true);
        scroll.setBounds(20,120,450,350);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        Font font = new Font("Arial",Font.BOLD,18);
        g2d.setFont(font);

        g2d.drawString("Active Email: " + username,20,30);

    }

    public void CheckIncoming()  {
        receiveMail.incoming(host,mailStoreType,username,password,textArea);
    }

    public void ifEmailSend(){

        //b = emailSettings.sendEmail(from,to,subject,text);
        if (b){
            System.out.println("wysłano");
        }else {
            System.out.println("coś poszło nie tak");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getSource() == buttons.wczytaj) {
            textArea.setText("");
            System.out.println("klik!");
            CheckIncoming();
        }

        if (e.getSource() == buttons.send) {
            System.out.println("klik to send!");
            scroll.setVisible(!scroll.isVisible());
        }

        if (!scroll.isVisible()) {
            System.out.println("niewidoczny");

            this.add(newEmailContent);
            subjectArea.setBounds(20,170,450,30);
            subjectArea.setVisible(true);

            toWho.setVisible(true);
            toWho.setBounds(20,100,450,30);

            newEmailContent.setBounds(20,300,450,150);
            newEmailContent.setVisible(true);

            buttons.send.setVisible(false);
            buttons.sendEmail.setVisible(true);

            buttons.wczytaj.setVisible(false);
            buttons.returnToIncomeBox.setVisible(true);

            attachment.setVisible(true);
            attachment.setBounds(20,200,130,30);
            attachment.setText("attachment");

        }else {
            toWho.setVisible(false);
            subjectArea.setVisible(false);
            attachment.setVisible(false);
            newEmailContent.setVisible(false);
            buttons.send.setVisible(true);
            buttons.sendEmail.setVisible(false);
            attachmentPatch.setVisible(false);

            buttons.wczytaj.setVisible(true);
            buttons.returnToIncomeBox.setVisible(false);
        }


        if (e.getSource() == buttons.returnToIncomeBox){
            toWho.setVisible(false);
            subjectArea.setVisible(false);
            attachment.setVisible(false);
            newEmailContent.setVisible(false);
            buttons.send.setVisible(true);
            buttons.sendEmail.setVisible(false);
            attachmentPatch.setVisible(false);

            buttons.wczytaj.setVisible(true);
            buttons.returnToIncomeBox.setVisible(false);
            scroll.setVisible(true);
        }

        if (attachment.isSelected()){
            System.out.println("załącznik!");
            attachmentPatch.setVisible(true);
            attachmentPatch.setBounds(20,250,450,30);
        }else {
            attachmentPatch.setVisible(false);
        }

        if (e.getSource() == buttons.sendEmail){
            System.out.println("wysyłam email!!!!");
            to = toWho.getText();
            subject = subjectArea.getText();
            text = newEmailContent.getText();
            if (attachment.isSelected()){
                String path = attachmentPatch.getText();
                boolean b = emailSettings.sendEmailwithAttachment(from,to,subject,text,path);
                if (b){
                    System.out.println("pomyślnie wysłano");

                    JOptionPane.showMessageDialog(this,"Pomyślnie wysłano wiadomość z załącznikiem!");
                }else {
                    JOptionPane.showMessageDialog(null,"Coś poszło nie tak");
                    System.out.println("wysyłanie nie powidło się");
                }
            }else {
                boolean b = emailSettings.sendEmail(from,to,subject,text);;
                if (b){
                    System.out.println("pomyślnie wysłano");
                    JOptionPane.showMessageDialog(this,"Pomyślnie wysłano wiadomość!");
                }else {
                    JOptionPane.showMessageDialog(this,"Coś poszło nie tak");
                    System.out.println("wysyłanie nie powidło się");
                }

            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
