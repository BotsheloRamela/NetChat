import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable{

    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private String nickname;
    private static ArrayList<ConnectionHandler> connections;

    public ConnectionHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output.println("Enter a nickname: ");
            nickname = input.readLine();
            System.out.println(nickname + " connected!");
            broadcastMessage(nickname + " joined the room!");
            String message;
            while ((message = input.readLine()) != null) {
                if (message.startsWith("/quit" )) {
                     // TODO: hanndle nickname
                } else {
                    broadcastMessage(nickname + ": " + message);
                } 

            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void sendMessage(String messaeg) {
        output.println(messaeg);
    }

    public static void addNewConnection(ConnectionHandler handler) {
        connections.add(handler);
    }

    public void broadcastMessage(String message) {
        for (ConnectionHandler ch: connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }
    
}
