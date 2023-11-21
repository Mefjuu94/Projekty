import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Timestamp;

public class UDPServer {
    public static void main(String[] args) throws IOException {

        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt("5001"));
        System.out.println("Serwer odpalony, nasłuch na 5001");

        byte[] reciveData = new byte[1024];
        DatagramPacket recivepacket;

        while (true){
            //czeka na wiadomosc
            recivepacket = new DatagramPacket(reciveData, reciveData.length);
            serverSocket.receive(recivepacket);
            //zbiera IP klienta i port
            InetAddress IPAdress = recivepacket.getAddress();
            int port = recivepacket.getPort();
            // konwersja widomosci w byte do stringa
            String clientMessage = new String(recivepacket.getData(),0,recivepacket.getLength());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("[" + timestamp.toString() + " ,IP: " + IPAdress + " ,Port: " + port + "]" );
            System.out.println(clientMessage);
        }


    }
}
