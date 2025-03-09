package HelloWorldSocket;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        String host = "localHost";
        int port = 12345;

        try {
            //obrir el port
            ServerSocket ss = new ServerSocket(port);
            Socket s = ss.accept();
            InputStream in = s.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            System.out.println(dis.readUTF());

        } catch (Exception e ){
            System.out.println("Error 2");
        }
    }
}
