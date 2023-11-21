import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.Timestamp;
import java.util.Scanner;


public class Panel extends JPanel implements MouseListener, KeyListener {


    int port = 1;
    DatagramSocket serverSocket = new DatagramSocket(port);

    JTextField yourMessage = new JTextField();
    JTextArea reciveArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(reciveArea);

    JButton send = new JButton("send");

    String myMessage = "";

    String previousMessage = new String();

    ///////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////

    Panel() throws IOException {

        this.addKeyListener(this);

        this.add(yourMessage);
        yourMessage.setBounds(30, 170, 320, 50);
        yourMessage.setVisible(true);
        yourMessage.addKeyListener(this);

        this.add(scrollPane);

        scrollPane.setBounds(30, 10, 320, 150);
        reciveArea.setVisible(true);
        reciveArea.setEditable(false);
        scrollPane.setVisible(true);


        this.add(send);
        send.setBounds(100, 220, 150, 30);
        send.setVisible(true);
        send.addMouseListener(this);
        send.setFocusable(false);

        Thread recieving = new Thread(reciveAndSend);

    }


    public void getMessage() throws IOException {
        getFreePort();
        serverSocket = new DatagramSocket(1);
        System.out.println("Serwer odpalony, nasłuch na " + port);

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

            reciveArea.append("[" + timestamp.toString() + " My friend from 'Chat 2' say: " + "\n"); //" ,IP: " + IPAdress + " ,Port: " + port + "]" + "\n"
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
        sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("127.0.0.1"), 4001);
        clientSocket.send(sendPacket);

        reciveArea.append("ME: " + myMessage + "\n");

    }

    public void getFreePort() {



    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() == send && yourMessage.getText().length() > 0) {
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


        if (e.getKeyCode() == 10) {
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
