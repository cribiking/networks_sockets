package Codi_Pol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MainYoutube {

    //Vídeo Youtube : https://www.youtube.com/watch?v=3wJTI9LMOsk

    public static void main(String[] args){
        //representació de la Màquina del Client

        String host = "192.168.31.179";//ip per connectar-nos a la nostra maquina
        int port = 5000;


        DataInputStream in = null;
        DataOutputStream out = null;
        Scanner scanner = new Scanner(System.in);
        String m = "";

        try ( Socket sc = new Socket(host, port)){
            System.out.println("Servidor Iniciat");
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            while (!m.equals("exit")) {

                //primer enviem el missatge per a que el servidor processi el missatge
                System.out.println("Escriu un missatge o 'exit' per sortir:");
                m = scanner.nextLine();
                out.writeUTF(m);
                out.flush(); // Asegurar que se envía inmediatamente

                if (!m.equals("exit")) {
                    System.out.println("Servidor: " + in.readUTF()); // Leer respuesta server
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
