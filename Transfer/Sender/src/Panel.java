import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class Panel extends JPanel implements MouseListener {

    JButton button = new JButton("Wybierz plik");
    JButton sendButton = new JButton("Wyślij");
    JButton changeReciver = new JButton("zmień odbiorcę");
    JTextArea textArea = new JTextArea();
    JScrollPane attachmentPanel = new JScrollPane(textArea);

    JFileChooser fileChooser;
    String path = "";
    String nameOfFile = "";

    public String adresOdbiorcy = "192.168.0.106";

    JTextArea sentFiles= new JTextArea();



    Panel(){

        this.add(button);
        button.setBounds(10,15,120,30);
        button.addMouseListener(this);
        button.setVisible(true);

        this.add(sendButton);
        sendButton.setBounds(130,15,100,30);
        sendButton.addMouseListener(this);
        sendButton.setVisible(true);

        this.add(changeReciver);
        changeReciver.setBounds(230,15,150,30);
        changeReciver.addMouseListener(this);
        changeReciver.setVisible(true);

        this.add(attachmentPanel);
        attachmentPanel.setBounds(50,70,300,150);
        textArea.setEditable(false);
        textArea.setVisible(true);
        attachmentPanel.setVisible(true);



    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() == button){
            System.out.println("klik!");


            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this directory: " +
                        chooser.getSelectedFile().getAbsolutePath());
                path = chooser.getSelectedFile().getAbsolutePath();
                nameOfFile = chooser.getSelectedFile().getName();
            }
        }

        if (e.getSource() == sendButton){

            FileTransfer fileTransfer = new FileTransfer();
            fileTransfer.send(path,nameOfFile,adresOdbiorcy);
            textArea.setText(nameOfFile + " \n");
        }

        if (e.getSource() == changeReciver){
            adresOdbiorcy = JOptionPane.showInputDialog("wpisz adres ip odbiorcy - domyślnie 192.168.0.106");
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
