import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
    The Server class represents a chat server that listens for incoming connections
    from clients and handles them by creating a new thread to handle each connection.
    The Server class maintains a list of all active connections and provides methods to
    broadcast messages to all connected clients and shutdown the server and all active
    connections.
*/
public class Server {
    private ArrayList<ConnectionHandler> connections; // The list of all active connections to the server.
    private ServerSocket server; // The ServerSocket object that listens for incoming client connections.
    private boolean done; // A boolean flag indicating whether the server should continue running.
    private ExecutorService threadPool; // A thread pool to manage the threads handling client connections.

    /**
     * Constructs a new Server object with an empty list of connections and sets the
     * done flag to false.
     */
    public Server() {
        connections = new ArrayList<>();
        done = false;
    }
    
    /**
     * Starts the server and listens for incoming client connections. For each connection,
     * a new ConnectionHandler object is created and added to the list of active connections.
     * A new thread is then created to handle the connection using the ConnectionHandler object,
     * and the thread is submitted to the thread pool for execution.
     * 
     * If an IOException occurs while waiting for incoming connections, the server is shut down.
     */
    public void start() {
        try {
            server = new ServerSocket(6666);
            threadPool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client, this);
                connections.add(handler);
                threadPool.execute(handler);
            }
        } catch (IOException e) {
            shutdownServer();
        }
    }

    /**
     * Shuts down the server and all active connections by setting the done flag to true,
     * shutting down the thread pool, closing the ServerSocket, and calling the shutdownClient
     * method on each active ConnectionHandler object.
     * 
     * If an IOException occurs while shutting down the server or active connections, the
     * exception is caught and ignored.
     */
    public void shutdownServer() {
        try {
            done = true;
            threadPool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections) {
                ch.shutdownClient();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    /**
     * Broadcasts a message to all connected clients by calling the sendMessage method
     * on each active ConnectionHandler object.
     * 
     * @param message The message to be broadcasted.
     */
    public void broadcastMessage(String message) {
        for (ConnectionHandler ch: connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    /**
     * Main method that creates a new Server object and starts the server by calling the
     * start method.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}