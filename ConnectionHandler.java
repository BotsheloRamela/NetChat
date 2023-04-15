import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable{

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
            System.out.println(nickname + " joined the room!");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
}
