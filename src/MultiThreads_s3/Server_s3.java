package MultiThreads_s3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_s3 extends Thread {

    private Socket socket;
    private String nom;

    public Server_s3(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {


        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //Demanem el nom al Client
            nom = in.readUTF();
            System.out.println("New Client Connexion : "+ nom);

            String missatge = "";
            String sortida;

            while (!missatge.equals("FI")) {
                if (in.available() > 0) {//Evitem que readUTF quedi bloquejat
                    missatge = in.readUTF();
                    System.out.println("Client "+nom+" diu: "+missatge);
                    sortida = missatge.toUpperCase();
                    out.writeUTF(sortida);
                    out.flush();
                }
            }
            in.close();
            out.close();

        } catch (IOException e) {
            System.out.println("Error en la comunicació amb el client " + nom + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Error tancant el socket de " + nom + ": " + e.getMessage());
            }
        }
    }


    ///MAIN
    private static final int PORT = 5000;
    private static final int MAX_CLIENTS = 10;  // Límite de clientes simultáneos
    private static ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

    public static void main(String[] args) {

        try {
            //Iniciem el Server
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Servidor Iniciat");

            Socket socket = null;
            DataInputStream in = null;

            while (true) {

                socket = server.accept();

                //Una vegada tinguem un client i l'acceptem, crearem un fil nou del servidor
                Server_s3 serverThread = new Server_s3(socket);

                Thread thread = new Thread(serverThread);
                thread.start();
                //pool.execute(serverThread);
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
