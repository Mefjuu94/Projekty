package APPLICATIONS.EMAIL;

import javax.swing.*;

public class Buttons {

    JButton wczytaj = new JButton();
    JButton send = new JButton();
    JButton sendEmail = new JButton();
    JButton returnToIncomeBox = new JButton();
    JButton chooseFile = new JButton();
    JRadioButton automaticSender = new JRadioButton("spamer");
    JRadioButton automaticSenderAttach = new JRadioButton("+ Attach");

    public void setButtons(){
        wczytaj.setVisible(true);
        wczytaj.setBounds(20,50,170,30);
        wczytaj.setText("load Emails");
        wczytaj.setLayout(null);


        send.setVisible(true);
        send.setBounds(300,50,150,30);
        send.setText("create message");
        send.setLayout(null);


        sendEmail.setVisible(false);
        sendEmail.setBounds(300,50,150,30);
        sendEmail.setText("send message");
        sendEmail.setLayout(null);

        returnToIncomeBox.setVisible(false);
        returnToIncomeBox.setBounds(20,50,170,30);
        returnToIncomeBox.setText("return to inbox");
        returnToIncomeBox.setLayout(null);

        chooseFile.setVisible(false);
        chooseFile.setBounds(20,210,170,30);
        chooseFile.setText("Choose Attachment");
        chooseFile.setLayout(null);

        automaticSender.setVisible(false);
        automaticSender.setBounds(200,40,80,30);
        automaticSender.setText("Spamer");
        automaticSender.setLayout(null);

        automaticSenderAttach.setVisible(false);
        automaticSenderAttach.setBounds(200,60,80,30);
        automaticSenderAttach.setText("Attach");
        automaticSenderAttach.setLayout(null);
    }

}

