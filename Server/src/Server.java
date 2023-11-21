import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Server {

    static SendMessage sendMessage;

    static {
        try {
            sendMessage = new SendMessage();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    static ReciveMessage reciveMessage;

    static {
        try {
            reciveMessage = new ReciveMessage();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {

        Runnable getMessage = new Runnable() {
            @Override
            public void run() {
                try {
                    reciveMessage.getMessage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread thread = new Thread(getMessage);
        thread.start();

    }








}
