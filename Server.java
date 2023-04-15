import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> connections;
    
    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(6666);
            Socket client = server.accept();
            ConnectionHandler handler = new ConnectionHandler(client);
            connections.add(handler);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void broadcastMessage(String message) {
        for (ConnectionHandler ch: connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }
    
}