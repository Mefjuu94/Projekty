import java.io.*;
import java.net.Socket;


public class ChatClient {
    public static Socket socket;

    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 9999);

        Panel panel = new Panel(socket);
    }

}
