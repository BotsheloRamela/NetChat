import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable{

    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private String nickname;
    private Server server;
    private String secretKey = "secrete";
    AESEncryptionDecryption aesEncryptionDecryption;
    
    public ConnectionHandler(Socket client, Server server) {
        this.client = client;
        this.server = server;
        this.aesEncryptionDecryption = new AESEncryptionDecryption();
    }
    
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
                    shutdownClient();
                } else {
                    String encryptedMessage = aesEncryptionDecryption.encrypt(message, secretKey);
                    server.broadcastMessage(nickname + ": " + encryptedMessage);
                } 
            }
            
        } catch (IOException e) {
            shutdownClient();
        }
    }

    public void sendMessage(String message) {
        // String decryptedMessage = aesEncryptionDecryption.decrypt(message, secretKey);
        output.println(message);
    }


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