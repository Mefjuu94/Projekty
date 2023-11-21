import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SendMessage {
    DatagramSocket clientSocket = new DatagramSocket();

    public SendMessage() throws SocketException {
    }


    public void sendMessage(String myMessage, int i) throws IOException {
        DatagramPacket sendPacket;
        byte[] sendData;

        //stworz gniazdo z danymi

        // 1 sek
        clientSocket.setSoTimeout(1000);

        String cmd = myMessage;
        // jak wpiszesz quit, to wywali z programu
        if (cmd.equals("QUIT")) {
            clientSocket.close();
            System.exit(1);
        }
        sendData = cmd.getBytes();

        //////zebranie portów od wiadomości przychodzących

        sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("127.0.0.1"), i);
        clientSocket.send(sendPacket);

    }
}
