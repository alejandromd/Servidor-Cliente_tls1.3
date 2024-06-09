import java.io.*;
import java.net.Socket;

public class BYODClient_no_SSL {

    public BYODClient_no_SSL(String username, String password, String message) {
        Socket socket = null;
        PrintWriter output = null;

        try {

            socket = new Socket("localhost", 7070);
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            output.println(username);
            output.println(password);
            output.println(message);
            output.flush();

        } catch (IOException ioException) {
            // The server rejects the connection if SSL is not used.
            // If we capture the messages we can see the users, passwords and messages
            // It can be seen in dumpfile_no_SSL.pcap
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        for (int i = 0; i <= 299; i++) {
            String username = "usuario" + i;
            String password = "contrasena" + i;
            String message = "Mensaje de prueba " + i;
            new BYODClient_no_SSL(username, password, message);
        }
        System.err.println("Se han enviado 300 mensajes al servidor");
    }
}
