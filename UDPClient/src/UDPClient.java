import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws IOException {

//        1. stworz datagram ktory trzyma dane ktore maja byc wyslane
//2. stworz gniazdo przez ktore będą przechodzic wiadomosci do wysłania i odebrania
//        3.ustawić czas timeout
//        4. stworzyć pętlę gdzie będzie mozna wpisać wiadomość i ją wysłać
//        5. po stworzeniu wiadomosci będzie ona konwertowania do tablicy byte
//        6. stworz pakied datagram z danymi do wysyłki ( host, co ma być wysłane, numerem poretu ect)
//        7. wysłanie wiadomości

        DatagramPacket sendPacket;
        byte[] sendData;

        //stworz gniazdo z danymi
        DatagramSocket clientSocket = new DatagramSocket();
        // 1 sek
        clientSocket.setSoTimeout(1000);
        Scanner input = new Scanner(System.in);

        while (true){
            String cmd = input.nextLine();
            // jak wpiszesz quit, to wywali z programu
            if (cmd.equals("QUIT")){
                clientSocket.close();
                System.exit(1);
            }
            sendData = cmd.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("127.0.0.1"),5001);
            clientSocket.send(sendPacket);
        }


    }
}
