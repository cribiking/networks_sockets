package MultiThreads_s3;

import java.io.*;
import java.net.Socket;

public class Client_s3 extends Thread {

    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader br;
    private Socket socket;

    public Client_s3(Socket socket, DataInputStream in, DataOutputStream out, BufferedReader br) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.br = br;
    }

    @Override
    public void run() {
        String missatge = "";

        try {
            while (!missatge.equals("FI")) {
                System.out.println("Escriu un text:");
                missatge = br.readLine();
                out.writeUTF(missatge);
                out.flush();

                System.out.println("Retorn: " + in.readUTF());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Error tancant el socket: " + e.getMessage());
            }
        }
    }

    private static final int PORT = 5000;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {

        try {
            //Crearem el socket fora del while per nomes crear un socket per client
            Socket socket = new Socket(HOST, PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //Volem llegir de consola
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            System.out.println("Client connectat, Indica el teu nom:");
            out.writeUTF(br.readLine());

            //Creacio del thread
            Client_s3 clientThread = new Client_s3(socket, in, out, br);
            clientThread.start();
            clientThread.join(); // Espera que acabi el fil abans de tancar

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
