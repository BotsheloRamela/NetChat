import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService threadPool;

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }
    
    @Override
    public void run() {
        try {
            server = new ServerSocket(6666);
            threadPool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                handler.addNewConnection(handler);
                threadPool.execute(handler);
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
                ch.shutdownClient();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}