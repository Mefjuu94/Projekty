import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;


public class ReciveMessage {

    static SendMessage sendMessage;
    HashMap <Integer,Integer> usersMap = new HashMap<>();

    static {
        try {
            sendMessage = new SendMessage();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt("5001"));

    public static HashSet<Integer> ports = new HashSet<>();

    public ReciveMessage() throws SocketException {
    }

    public void getMessage() throws IOException {
        System.out.println("Serwer odpalony, nasłuch na 5001");

        byte[] reciveData = new byte[1024];
        DatagramPacket recivepacket;
        recivepacket = new DatagramPacket(reciveData, reciveData.length);

        String clientMessage;

        String prevoiusMessage = "";



        while (true) {

            serverSocket.receive(recivepacket);
            int port = recivepacket.getPort();
            ports.add(port); // dodaje unikalny port od użytkoawnika
            // konwersja widomosci w byte do stringa

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            clientMessage = new String(recivepacket.getData(), 0, recivepacket.getLength());





            if (!prevoiusMessage.equals(clientMessage)) {
                System.out.println(ports);
                System.out.println("[" + "Port: " + port + "]");
                for (int i = 1; i < ports.size() + 1; i++) {
                    usersMap.put(i,port);
                    System.out.println(usersMap);
                }
                System.out.println(clientMessage);

                for (int i = 1; i < ports.size() + 1; i++) {
                    System.out.println(ports.size() + " users");
                    String enchacedClientMessage = clientMessage + "`" + ports.size();
                    sendMessage.sendMessage(enchacedClientMessage, i);
                }
                prevoiusMessage = clientMessage;
            }

        }
    }
}
