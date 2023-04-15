import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable{
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean done;

    public ClientConnection(Socket socket) {
        this.socket = socket;
        done = false;
    }

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

    public void sendMessage(String message) {
        output.println(message);
    }

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
