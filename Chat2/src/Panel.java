import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Scanner;

public class Panel extends JPanel implements MouseListener, KeyListener {

    int port = 1;
    DatagramSocket serverSocket = new DatagramSocket(port);
    int myNumber = 1;

    DatagramPacket sendPacket;
    //stworz gniazdo z danymi
    DatagramSocket clientSocket = new DatagramSocket();
    String enchacedClientMessage = "";
    String clientMessage;
    int usersInChat = 0;
    boolean firstTimeConnectInformation = true;

    JTextField yourMessage = new JTextField();
    JTextArea reciveArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(reciveArea);

    JButton send = new JButton("send");
    String myMessage = "";

    Runnable reciveAndSend = new Runnable() {
        @Override
        public void run() {
            try {
                sendMessage(myMessage);
                getMessage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };


    Panel() throws IOException {

        this.add(yourMessage);
        yourMessage.setBounds(30,170,320,50);
        yourMessage.setVisible(true);
        yourMessage.addKeyListener(this);

        this.add(scrollPane);

        scrollPane.setBounds(30,10,320,150);
        reciveArea.setVisible(true);
        reciveArea.setEditable(false);
        scrollPane.setVisible(true);

        this.add(send);
        send.setBounds(100,220,150,30);
        send.setVisible(true);
        send.addMouseListener(this);
        send.setFocusable(false);

        Thread recieving = new Thread(reciveAndSend);
    }

    public void getMessage() throws IOException {


        byte[] reciveData = new byte[1024];
        DatagramPacket recivepacket;
        recivepacket = new DatagramPacket(reciveData, reciveData.length);

        while (true) {
            serverSocket.receive(recivepacket);

            // konwersja widomosci w byte do stringa
            enchacedClientMessage = new String(recivepacket.getData(), 0, recivepacket.getLength());
            //zbieranie informacji o porcie, ilości użytkowników
            getinformationAboutChatQueue(enchacedClientMessage);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());


            System.out.println(clientMessage);

            reciveArea.append("[" + timestamp.toString() + " My friend from 'Chat 1' say: " + "\n");
            reciveArea.append(clientMessage + "\n");



        }

    }

    public String getinformationAboutChatQueue(String clientmessage){

        String[] split = clientmessage.split("`");

        usersInChat = Integer.parseInt(split[1]);
        System.out.println(split[0] + " split 0");
        System.out.println(split[1] + " split 1");
        System.out.println(usersInChat);
        clientMessage = split[0];
        //System.out.println(clientMessage + " clientmesage");

        if (firstTimeConnectInformation){
            myNumber = Integer.parseInt(split[1]);
            port = myNumber;
            firstTimeConnectInformation = false;
        }

        return clientMessage;
    }


    public void sendMessage(String myMessage) throws IOException {

        byte[] sendData;

        // 1 sek
        clientSocket.setSoTimeout(1000);
        Scanner input = new Scanner(System.in);

            String cmd = myMessage;
            // jak wpiszesz quit, to wywali z programu
            if (cmd.equals("QUIT")) {
                clientSocket.close();
                System.exit(1);
            }
            sendData = cmd.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("127.0.0.1"), 5001);
            clientSocket.send(sendPacket);

        reciveArea.append("ME: " + myMessage + "\n");
        System.out.println(clientSocket.getPort() + " port wysyłki");

    }




    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() == send && yourMessage.getText().length() > 0){
            try {
                sendMessage(yourMessage.getText());
                yourMessage.setText(""); // cleanup textfield
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 10){
            try {
                sendMessage(yourMessage.getText());
                yourMessage.setText(""); // cleanup textfield
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
