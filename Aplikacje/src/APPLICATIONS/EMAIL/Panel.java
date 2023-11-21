package APPLICATIONS.EMAIL;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimerTask;

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

    String path = "";
    String[] multiPatch = new String[10]; // max 10 załączników
    String fileName = "";
    String[] allFileNames = new String[multiPatch.length];
    int attachCounter = 0;

    JTextArea newEmailContent = new JTextArea("your text message");
    JTextArea subjectArea = new JTextArea("subject");
    JTextArea toWho = new JTextArea("mail@gmail.com");
    JTextArea textArea = new JTextArea("your content");

    JScrollPane scroll = new JScrollPane(textArea);

    JTextArea AttachmentNames = new JTextArea();
    JScrollPane attachments = new JScrollPane(AttachmentNames);

    //spam

    String spamPatch = "C:\\Users\\mateu\\OneDrive\\Pulpit\\terminy.txt";
    String[] toMultAdress = {"qasa21@gmail.com", "orzel990@vp.pl","mateusz94orzel@gmail.com"};
    boolean sendSpam = false;
    int spamHour = 11;
    int spamMinute = 35;
    Timer timerNormal;
    Timer timerAttach;



    public Panel(){

        this.setLayout(null);

        this.buttons.wczytaj.addMouseListener(this);
        add(buttons.wczytaj);

        this.buttons.sendEmail.addMouseListener(this);
        add(buttons.sendEmail);

        this.buttons.returnToIncomeBox.addMouseListener(this);
        add(buttons.returnToIncomeBox);

        this.buttons.chooseFile.addMouseListener(this);
        add(buttons.chooseFile);

        this.buttons.send.addMouseListener(this);
        add(buttons.send);

        this.buttons.automaticSender.addMouseListener(this);
        add(buttons.automaticSender);
        this.buttons.automaticSenderAttach.addMouseListener(this);
        add(buttons.automaticSenderAttach);

        buttons.setButtons();



        add(toWho);
        toWho.setVisible(false);
        add(subjectArea);
        subjectArea.setVisible(false);

        add(scroll);
        scroll.setVisible(true);
        scroll.setBounds(20,120,450,350);

        add(attachments);
        attachments.setBounds(200,200,270,50);
        attachments.setVisible(false);


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

            JPanel Adress = new JPanel();
            Adress.setLayout(new BorderLayout());
            Adress.setBorder(new TitledBorder("Email Adress")); // zrobić bo coś nie działa
            toWho.setMaximumSize(new Dimension(450,55));
            toWho.setVisible(true);
            Adress.add(toWho);
            Adress.setBounds(20,100,450,45);
            this.add(Adress);
            Adress.setVisible(true);


            JPanel subject = new JPanel();
            subject.setLayout(new BorderLayout());
            subject.setBorder(new TitledBorder("Subject"));
            subject.setBounds(20,150,450,45);
            subject.setVisible(true);
            subject.add(subjectArea);
            subjectArea.setMaximumSize(new Dimension(450,55));
            subjectArea.setVisible(true);
            this.add(subject);


            JPanel content = new JPanel();
            content.setLayout(new BorderLayout());
            content.setBorder(new TitledBorder("Message"));
            content.setBounds(20,250,450,220);
            content.setVisible(true);
            content.add(newEmailContent);
            newEmailContent.setMaximumSize(new Dimension(450,150));
            newEmailContent.setVisible(true);
            this.add(content);
        }

        if (!scroll.isVisible()) {
            System.out.println("niewidoczny");

            buttons.send.setVisible(false);
            buttons.sendEmail.setVisible(true);

            buttons.chooseFile.setVisible(true);

            AttachmentNames.setVisible(true);
            attachments.setVisible(true);

            buttons.automaticSender.setVisible(true);
            buttons.automaticSenderAttach.setVisible(true);

            buttons.wczytaj.setVisible(false);
            buttons.returnToIncomeBox.setVisible(true);

        }else {
            toWho.setVisible(false);
            subjectArea.setVisible(false);
            newEmailContent.setVisible(false);
            buttons.send.setVisible(true);
            buttons.sendEmail.setVisible(false);
            buttons.chooseFile.setVisible(false);
            AttachmentNames.setVisible(false);
            attachments.setVisible(false);
            buttons.automaticSender.setVisible(false);
            buttons.automaticSenderAttach.setVisible(false);

            buttons.wczytaj.setVisible(true);
            buttons.returnToIncomeBox.setVisible(false);
        }


        if (e.getSource() == buttons.returnToIncomeBox){
            toWho.setVisible(false);
            subjectArea.setVisible(false);
            buttons.chooseFile.setVisible(false);
            newEmailContent.setVisible(false);
            buttons.send.setVisible(true);
            buttons.sendEmail.setVisible(false);
            buttons.chooseFile.setVisible(false);
            AttachmentNames.setVisible(false);
            attachments.setVisible(false);
            buttons.automaticSender.setVisible(false);
            buttons.automaticSenderAttach.setVisible(false);


            buttons.wczytaj.setVisible(true);
            buttons.returnToIncomeBox.setVisible(false);
            scroll.setVisible(true);
        }

        if (e.getSource() == buttons.chooseFile){
            System.out.println("załącznik!");

            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                fileName = fileChooser.getSelectedFile().getName();
                path = String.valueOf(file);
            }
            ///////////więcej załączników niż jeden!!////////////////////
            if (path.length()>0){
                multiPatch[attachCounter] = path;
                allFileNames[attachCounter] = fileName;
                attachCounter++;
            }
            showChosenAttachments();
            System.out.println(attachCounter);
        }


        if (e.getSource() == buttons.sendEmail){
            System.out.println("wysyłam email!!!!");
            to = toWho.getText();
            subject = subjectArea.getText();
            text = newEmailContent.getText();
            sendingEmail();
        }

        if (buttons.automaticSender.isSelected() ) {
            System.out.println("automatyczny email");
            if (buttons.automaticSenderAttach.isSelected()) {
                System.out.println("z załącznikiem!!!");
                spamSenderAttach();
                timerNormal.stop();
                if (timerNormal.isRunning()) {
                    timerNormal.stop();
                }
            } else if (!buttons.automaticSenderAttach.isSelected()) {
                System.out.println("bez załącznika");
                spamSender();
                if (timerAttach != null && timerAttach.isRunning()) {
                    timerAttach.stop();
                }
            }
        }

    }

    private void sendingEmail(){
        if (path.length() > 0){
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

    public void showChosenAttachments(){

        if(!scroll.isVisible() && attachCounter > 0) {
            for (int i = 0; i < multiPatch.length; i++) {
                if (multiPatch[i] != null) {
                    AttachmentNames.append(allFileNames[i] + "\n");
                }
            }
        }
    }


    private void spamSender(){
        String subjectmulti = "wysyłam spam";
        String textMulti = "testowy email ze spamem";
        timerNormal = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalTime lt = LocalTime.now();
                if (lt.getHour() == spamHour && lt.getMinute() == spamMinute) {
                    System.out.println(lt);
                    sendSpam = true;
                }
                if (sendSpam) {
                    System.out.println("wysyłam spam");
                    boolean b = emailSettings.sendMultiEmail(from, toMultAdress, subjectmulti, textMulti);
                    if (b) {
                        System.out.println("pomyślnie wysłano");
                    } else {
                        System.out.println("wysyłanie nie powidło się");
                    }
                }
                sendSpam = false;
            }
        });
        timerNormal.isRunning();
        timerNormal.start();
        timerNormal.setRepeats(true);
    }

    private void spamSenderAttach(){
        String subjectmulti = "wysyłam spam z załącznikiem";
        String textMulti = "wysyłam z załącznikiem";

        timerAttach = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalTime lt = LocalTime.now();
                System.out.println(lt);
                System.out.println("wysyłam spam z załącznikiem");
                if (lt.getHour() == spamHour && lt.getMinute() == spamMinute) {
                    boolean b = emailSettings.sendMultiEmailwithAttachment(from, toMultAdress, subjectmulti, textMulti, spamPatch);
                    if (b) {
                        System.out.println("pomyślnie wysłano");
                    } else {
                        System.out.println("wysyłanie nie powidło się");
                    }
                }
            }
        });
        timerAttach.isRunning();
        timerAttach.start();
        timerAttach.setRepeats(true);

    }



    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
