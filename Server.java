import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }
    
    @Override
    public void run() {
        try {
            server = new ServerSocket(6666);
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
            }
        } catch (Exception e) {
            shutdownServer();
        }
    }

    public void shutdownServer() {
        try {
            done = true;
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections) {
                ch.shutdownServer();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}