package APPLICATIONS.CHAT.CHAT2USER;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Scanner;

public class Panel extends JPanel implements MouseListener {

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


    public Panel() throws IOException {

        this.add(yourMessage);
        yourMessage.setBounds(30,170,320,50);
        yourMessage.setVisible(true);

        this.add(scrollPane);

        scrollPane.setBounds(30,10,320,150);
        reciveArea.setVisible(true);
        reciveArea.setEditable(false);
        scrollPane.setVisible(true);

        this.add(send);
        send.setBounds(100,220,150,30);
        send.setVisible(true);
        send.addMouseListener(this);

        Thread recieving = new Thread(reciveAndSend);
    }

    public void getMessage() throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt("4001"));
        System.out.println("Serwer odpalony, nasłuch na 4001");

        byte[] reciveData = new byte[1024];
        DatagramPacket recivepacket;

        while (true) {
            //czeka na wiadomosc
            recivepacket = new DatagramPacket(reciveData, reciveData.length);
            serverSocket.receive(recivepacket);
            //zbiera IP klienta i port
            InetAddress IPAdress = recivepacket.getAddress();
            int port = recivepacket.getPort();
            // konwersja widomosci w byte do stringa
            String clientMessage = new String(recivepacket.getData(), 0, recivepacket.getLength());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("[" + timestamp.toString() + " ,IP: " + IPAdress + " ,Port: " + port + "]");
            System.out.println(clientMessage);


            reciveArea.append("[" + timestamp.toString() + " ,IP: " + IPAdress + " ,Port: " + port + "]" + "\n");
            reciveArea.append("My friend from 'Chat 1': " + "\n");
            reciveArea.append(clientMessage + "\n");

        }
    }


    public void sendMessage(String myMessage) throws IOException {
        DatagramPacket sendPacket;
        byte[] sendData;

        //stworz gniazdo z danymi
        DatagramSocket clientSocket = new DatagramSocket();
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
}
