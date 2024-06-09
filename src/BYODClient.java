import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

public class BYODClient {

    private static final String[] protocols = new String[] { "TLSv1.3" };
    private static final String[] cipher_suites = new String[] { "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384",
            "TLS_CHACHA20_POLY1305_SHA256" };

    public BYODClient() {
        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
            socket.setEnabledProtocols(protocols);
            socket.setEnabledCipherSuites(cipher_suites);

            // Creates a PrintWriter to send message/MAC to the server
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String username = JOptionPane.showInputDialog(null, "Introduzca su nombre de usuario:");
            output.println(username);

            String password = JOptionPane.showInputDialog(null, "Introduzca su contraseña:");
            output.println(password);

            String msg = JOptionPane.showInputDialog(null, "Introduzca su mensaje:");
            output.println(msg);

            // Important for the message to be sent
            output.flush();

            // Creates a BufferedReader object to read the response from the server
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read the server response
            String response = input.readLine();

            // Displays the server response
            JOptionPane.showMessageDialog(null, response);

            // The connection is closed
            output.close();
            input.close();
            socket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        new BYODClient();
    }
}
