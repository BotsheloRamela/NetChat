import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
    This class represents a connection between a client and a server.
    It implements the Runnable interface to allow for it to be run on a separate thread.
*/


public class ClientConnection implements Runnable{
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean done;

    /**
        Constructor for a new ClientConnection object.
        @param socket The socket representing the connection between the client and server.
    */

    public ClientConnection(Socket socket) {
        this.socket = socket;
        done = false;
    }

    /**
        * This method is called when a new thread is started for this object.
        * It initializes the input and output streams and listens for incoming messages from the server.
        * When a message is received, it is printed to the console.
    */

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            String serverMessage;
            while (!done && (serverMessage = input.readLine()) != null) {
                System.out.println(serverMessage);
            }
        } catch (Exception e) {
            disconnect();
        }
    }

    /**
        This method sends a message to the server through the output stream.
        @param message The message to be sent.
    */
    public void sendMessage(String message) {
        output.println(message);
    }

    /**
        This method disconnects the client from the server by closing the input and output streams and the socket.
    */
    public void disconnect() {
        done = true;
        try {
            input.close();
            output.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
