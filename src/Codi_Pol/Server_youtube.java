package Codi_Pol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_youtube {

    public static void main(String[] args){

        int port = 5000;

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor Iniciat.");
            Socket socket = server.accept();//Socket del client;
            System.out.println("Cliente conectado.");
            DataInputStream in = new DataInputStream(socket.getInputStream());;
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while(true){

                //El servidor llegirà primer el missatge del client
                String m = in.readUTF();
                System.out.println(m);

                if (m.equalsIgnoreCase("exit")) {
                    System.out.println("Cliente desconectado.");
                    break;
                }

                //Després el client rebrà un missatge de confirmació
                out.writeUTF("Mensaje recibido, siguiente mensaje.");
                out.flush();
            }

            in.close();
            out.close();
            socket.close();
            server.close();

        } catch (Exception e){
            System.out.println("Error");
        }
    }
}
