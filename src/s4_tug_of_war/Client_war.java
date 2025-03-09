package s4_tug_of_war;

import java.io.*;
import java.net.Socket;

public class Client_war {

    final static int PORT=61234;
    final static String HOST="127.0.0.1";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST,PORT);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println(in.readUTF()); //Nom del equip

            int i=0;
            boolean fi = false;
            while (!fi) {
                System.out.println("client while "+i);
                br.readLine();
                out.writeInt(1);
                out.flush();
                i++;
                if (in.readUTF().equals("fi")){
                    fi = true;
                }
            }
            socket.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
