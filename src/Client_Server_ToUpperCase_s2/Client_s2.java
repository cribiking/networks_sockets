package Client_Server_ToUpperCase_s2;

import java.io.*;
import java.net.Socket;

 /*
        - An InputStreamReader is a bridge from byte streams to character streams: It reads bytes and decodes
        them into characters using a specified charset. The charset that it uses may be specified by name or
        may be given explicitly, or the default charset may be used.

        - Class System cannot be instantiated

        - Class BufferedReader comes from class Reader.

        - Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines.
        The buffer size may be specified, or the default size may be used. The default is large enough for most purposes.
        In general, each read request made of a Reader causes a corresponding read request to be made of the underlying character or byte stream.
        It is therefore advisable to wrap a BufferedReader around any Reader whose read() operations may be costly, such as FileReaders and
        InputStreamReaders.
        For example, Without buffering, each invocation of read() or readLine() could cause bytes to be read from the file, converted into characters,
        and then returned, which can be very inefficient.

  */

public class Client_s2 {

    public static void main(String[] args) {

        final String HOST = "127.0.0.1";
        final int PORT = 5000;

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            Socket socket = new Socket(HOST, PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String missatge = "";
            String missatgeUperCase;


            while (!missatge.equals("FI")) {

                System.out.println("Escriu el text: ");
                missatge = br.readLine();
                out.writeUTF(missatge);
                out.flush();

                missatgeUperCase = in.readUTF();
                System.out.println("Resultat del servidor: " + missatgeUperCase);

            }

            out.close();
            in.close();
            br.close();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}



