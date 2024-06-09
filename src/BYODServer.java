import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class BYODServer {

    private static final String CREDENTIALS_FILE = "credentials.txt";
    private static final String MESSAGES_FILE = "messages.txt";
    private SSLServerSocket serverSocket;
    private static final String[] protocols = new String[] { "TLSv1.3" };
    private static final String[] cipher_suites = new String[] { "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384",
            "TLS_CHACHA20_POLY1305_SHA256" };

    public BYODServer() throws Exception {
        SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
        serverSocket.setEnabledProtocols(protocols);
        serverSocket.setEnabledCipherSuites(cipher_suites);
    }

    // Running the server to listen for requests from clients
    private void runServer() {
        while (true) {
            // Waits for client requests to check message/MAC
            try {
                System.err.println("Esperando conexiones de clientes...");
                Socket socket = (Socket) serverSocket.accept();
                // Opens a BufferedReader to read the client data
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Opens a PrintWriter to send data to the client.
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                // Client's message is read
                String username = input.readLine();
                String password = input.readLine();
                String message = input.readLine();

                // Validate user
                if (username.isEmpty() || password.isEmpty() || message.isEmpty()) {
                    output.println("Error, los datos del usuario no pueden estar vacíos.");
                } else {

                    List<String> credentials = Files.readAllLines(Paths.get(CREDENTIALS_FILE));
                    Boolean isRegisteredUser = credentials.contains(username + " - " + password);

                    if (!isRegisteredUser) {
                        // Invalid user, added to credentials.txt
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CREDENTIALS_FILE, true))) {
                            bw.write(username + " - " + password + "\n");
                            output.println("Usuario agregado correctamente. Se ha almacenado el mensaje.");
                        } catch (IOException e) {
                            output.println("Error al registrar el usuario.");
                        }
                    }

                    File filename = new File(MESSAGES_FILE);
                    BufferedWriter bw = null;
                    bw = new BufferedWriter(new FileWriter(filename, true));
                    bw.write("usuario: " + username + "\n" + "mensaje: " + message + "\n\n");
                    bw.close();
                    output.println("Su mensaje se ha guardado correctamente.");
                }

                output.close();
                input.close();
                socket.close();

            } catch (SSLHandshakeException exception) {
                // Output expected SSLHandshakeExceptions.
                System.err.println("Error: " + exception);

            } catch (IOException exception) {
                // Output unexpected IOExceptions.
                System.err.println("Error: " + exception);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        BYODServer server = new BYODServer();
        server.runServer();
    }
}
