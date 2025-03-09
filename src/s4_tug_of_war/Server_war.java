package s4_tug_of_war;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/*
TODO:
    - Els jugadors no finalitzen quan s'acaba el joc
    - Els jugadors no saben qui ha guanyat
    - Finalitzar el servidor d'una manera més neta
 */
public class Server_war {
    final static int PORT=61234;
    final static int MAX_USER=2;
    public static volatile int shared=0;//Creació de la variable compartida
    public static boolean gameOver=false;//Controlar final del joc

    public static void main(String[] args) {
        
        ServerSocket serverSocket;
        Socket socket;
        try {
            
            serverSocket = new ServerSocket(PORT);
            //Inici dels threads per atendre als equips
            System.out.println("Esperant Connexions...");
            Socket s_positive = serverSocket.accept();
            Thread t_positive = new Thread(new PositiveTeam(s_positive));
            System.out.println("Positive Team Connected");

            System.out.println("Esperant Connexions...");
            Socket s_negative = serverSocket.accept();
            Thread t_negative = new Thread(new NegativeTeam(s_negative));
            System.out.println("Negative Team Connected");

            t_positive.start();
            t_negative.start();

            while(!gameOver) {
                System.out.println("Valor shared: "+shared);
                //Cal comprovar si la variable compartida ha arribat a 10
                if (shared == 10){
                    //Enviar per socket positiu que el seu equip ha guanyat
                    sendLastMessage(s_positive , "Positive Team Won!");
                    sendLastMessage(s_negative , "You Lost..");
                    gameOver = true;
                } else if(shared == -10){
                    sendLastMessage(s_negative, "Negative Team Won");
                    sendLastMessage(s_positive , "You Lost..");
                    gameOver = true;
                }
                sendEndMessage(s_positive);
                sendEndMessage(s_negative);

            }
            // Esperar a que los threads terminen antes de cerrar el servidor
            t_positive.join();
            t_negative.join();

            //Si detectem que un equip ha guanyat sortim del bucle, tanquem conexions i avisem als jugadors
            s_positive.close();
            s_negative.close();
            serverSocket.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendLastMessage(Socket socket , String msg){
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(msg);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("Error de sendMessage(): "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void sendEndMessage (Socket socket){
        DataOutputStream out;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            if (gameOver) out.writeUTF("fi");
            if (!gameOver) out.writeUTF("not fi");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class PositiveTeam implements Runnable {
        Socket socket;
        public PositiveTeam(Socket socket) {
            this.socket = socket;
        }
        @Override
        public synchronized void run() { //Important utilizar synchronized
            //Per cada enter rebut, cal augmentar la variable en 1
            int incr=0;
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("Welcome to the Positive Team!");
                while(!gameOver) {
                    //El jugador enviara 1's , nosaltres incrementarem variable shared
                    incr = in.readInt();
                    shared+=incr;
                    //Si el main ens indica que shared ha arribat a 10, s'acaba el joc.
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static class NegativeTeam implements Runnable {
        Socket socket;
        public NegativeTeam(Socket socket) {
            this.socket = socket;
        }
        @Override
        public synchronized void run() { //Important utilizar synchronized
            //Per cada enter rebut, cal augmentar la variable en 1
            int incr=0;
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("Welcome to the Negative Team!");
                while(!gameOver) {
                    //El jugador enviara 1's , nosaltres incrementarem variable shared
                    incr = in.readInt();
                    shared-=incr;
                    //Si el main ens indica que shared ha arribat a 10, s'acaba el joc.
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
