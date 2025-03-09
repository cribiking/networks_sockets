package Client_Server_ToUpperCase_s2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_s2 {
    private static final int PORT = 5000;

    public static void main(String[] args) {


        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Servidor Iniciat");
            for (;;) {
                Socket socket = server.accept();
                System.out.println("Client connectat");
                //Per aprendre d'on provenen les coses
                InputStream si = socket.getInputStream();
                DataInputStream in = new DataInputStream(si);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                String missatge = "";
                String sortida;

                while (!missatge.equals("FI")) {

                    missatge = in.readUTF();
                    sortida = missatge.toUpperCase();
                    out.writeUTF(sortida);

                }
                in.close();
                out.close();
                socket.close();
                System.out.println("Client desconnectat, esperant nova connexió.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
