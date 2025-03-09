package s4_tug_of_war;

import java.io.*;
import java.net.Socket;

public class Client_war {

    final static int PORT=61234;
    final static String HOST="127.0.0.1";
    public static boolean end=false;

    public static void main(String[] args) {

        try {

            Socket socket = new Socket(HOST,PORT);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println(in.readUTF()); //Nom del equip

            //Crearem un thread per només escoltar si el joc ha finalitzat
            Thread listenThread = new Thread(new EndingClient(in));
            listenThread.start();

            int i=0;
            while(!end) {
//                System.out.println("client while "+i);
                br.readLine();
                out.writeInt(1);
                out.flush();
                i++;
            }

            socket.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static class EndingClient implements Runnable {
        DataInputStream in ;
        public EndingClient(DataInputStream in) {
            this.in = in;
        }
        @Override
        public void run() {

            while (!end) {
                try {
                    //Llegeix el resultat dels equips abans de finalitzar el joc
                    String resultat = in.readUTF();
                    System.out.println(resultat);

                    //D'aquesta manera aconseguim que el while acabi, i així podem tancar el socket.
                    //Escoltem de la funció 'SendEndMessage()'
                    if (in.readUTF().equals("fi")){
                        end = true;
                    }
                    System.out.println("Listen Thread: He parat el joc");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
