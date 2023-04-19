import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;


/**
    The ConnectionHandler class is responsible for handling the communication between
    a client and the server. It implements the Runnable interface to run in a separate
    thread and communicates with the Server class to broadcast messages to other clients.
    The class uses AES encryption/decryption to secure messages sent between the client and
    server. The encryption key is stored in the class and can be changed as needed.
*/
public class ConnectionHandler implements Runnable{

    private Socket client; // the client socket
    private BufferedReader input; // input stream from client
    private PrintWriter output; // output stream to client
    private String nickname; // the nickname of the client
    private Server server; // reference to the server instance
    private SecretKeySpec secretKey; // the encryption key
    AESEncryptionDecryption aesEncryptionDecryption; // AES encryption/decryption object

    /**
     * Constructor for ConnectionHandler class.
     * @param client the client socket
     * @param server the server instance
     */
    public ConnectionHandler(Socket client, Server server) {
        this.client = client;
        this.server = server;
        this.aesEncryptionDecryption = new AESEncryptionDecryption();
        try {
            this.secretKey = aesEncryptionDecryption.generateSecretKey("secret");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * The main method for the ConnectionHandler class.
     * It initializes the input and output streams for the client socket,
     * prompts the client for a nickname, and starts a while loop to read
     * messages from the client until the connection is closed.
     */
    @Override
    public void run() {
        
        try {
            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output.println("Enter a nickname: ");
            nickname = input.readLine();
            System.out.println(nickname + " connected!");
            server.broadcastMessage(nickname + " joined the room!");
            String message;
            
            while ((message = input.readLine()) != null) {
                if (message.startsWith("/quit" )) {
                    server.broadcastMessage(nickname + " left the room!");
                    System.out.println(nickname + " disconnected from the server!");
                    shutdownClient();
                } else {
                    String encryptedMessage = aesEncryptionDecryption.encrypt(message, secretKey);
                    server.broadcastMessage(nickname + ": " + encryptedMessage);
                } 
            }
            
        } catch (Exception e) {
            shutdownClient();
        }
    }

    /**
     * Method for sending a message to the client.
     * @param message the message to send
     */
    public void sendMessage(String message) {
        // String decryptedMessage = aesEncryptionDecryption.decrypt(message, secretKey);
        output.println(message);
    }

    /**
     * Method for closing the client connection and cleaning up resources.
     */
    public void shutdownClient() {
        try {
            input.close();
            output.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
}