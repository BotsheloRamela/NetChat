import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    public void broadcastMessage(String message) {
        for (ConnectionHandler ch: connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    class ConnectionHandler implements Runnable{

        private Socket client;
        private BufferedReader input;
        private PrintWriter output;
        private String nickname;
    
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
                        broadcastMessage(nickname + " left the room!");
                        shutdownClient();
                    } else {
                        broadcastMessage(nickname + ": " + message);
                    } 
                }
                
            } catch (Exception e) {
                shutdownClient();
            }
        }
    
        public void sendMessage(String messaeg) {
            output.println(messaeg);
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

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}