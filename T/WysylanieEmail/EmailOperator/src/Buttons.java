import javax.swing.*;

public class Buttons {

    JButton wczytaj = new JButton();
    JButton send = new JButton();
    JButton sendEmail = new JButton();
    JButton returnToIncomeBox = new JButton();

    public void setButtons(){
         wczytaj.setVisible(true);
         wczytaj.setBounds(20,50,170,30);
         wczytaj.setText("wczytaj Emaile");
         wczytaj.setLayout(null);


        send.setVisible(true);
        send.setBounds(300,50,150,30);
        send.setText("stwórz wiadomość");
        send.setLayout(null);


        sendEmail.setVisible(false);
        sendEmail.setBounds(300,50,150,30);
        sendEmail.setText("wyślij wiadomość");
        sendEmail.setLayout(null);

        returnToIncomeBox.setVisible(false);
        returnToIncomeBox.setBounds(20,50,170,30);
        returnToIncomeBox.setText("wróć do Odebranych");
        returnToIncomeBox.setLayout(null);
     }

}
