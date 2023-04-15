import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class InputHandler implements Runnable{
    private BufferedReader consoleInput;
    private PrintWriter socketOutput;
    private boolean done;

    public InputHandler(PrintWriter socketOutput) {
        this.consoleInput = new BufferedReader(new InputStreamReader(System.in));
        this.socketOutput = socketOutput;
        this.done = false;
    }

    @Override
    public void run() {
        try {
            while (!done) {
                String message = consoleInput.readLine();
                if (message.equals("/quit")) {
                    socketOutput.println(message);
                    consoleInput.close();
                    done = true;
                } else {
                    socketOutput.println(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
