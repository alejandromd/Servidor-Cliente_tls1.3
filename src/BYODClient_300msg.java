import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.time.Duration;
import java.time.Instant;

public class BYODClient_300msg {

    private static final String[] protocols = new String[] { "TLSv1.3" };
    private static final String[] cipher_suites = new String[] { "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384",
            "TLS_CHACHA20_POLY1305_SHA256" };

    public BYODClient_300msg(String username, String password, String message) {
        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
            socket.setEnabledProtocols(protocols);
            socket.setEnabledCipherSuites(cipher_suites);

            // Creates a PrintWriter to send message/MAC to the server
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            output.println(username);
            output.println(password);
            output.println(message);

            // Important for the message to be sent
            output.flush();

            // The connection is closed
            output.close();
            socket.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Instant start = Instant.now();
        for (int i = 0; i <= 299; i++) {
            String username = "usuario" + i;
            String password = "contraseña" + i;
            String message = "Mensaje de prueba " + i;
            new BYODClient_300msg(username, password, message);
        }
        Instant end = Instant.now();
        Duration interval = Duration.between(start, end);
        System.err.println("Se han enviado 300 mensajes al servidor.");
        System.err.println("Tiempo de ejecución: " + (interval.toMillis() / 1000.0));
    }
}
