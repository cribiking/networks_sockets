package HelloWorldSocket;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Inici Programa");
        String host = "localHost";
        int port = 12345;

        try {
            Socket s = new Socket(host , port);
            OutputStream os = s.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF("Hola Albert");

        } catch (Exception e){
            System.out.println("Error 1");
        }

    }
}