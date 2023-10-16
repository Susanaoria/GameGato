
package Network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Cliente {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Cliente(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMove() {
        try {
            return input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendMove(String move) {
        output.println(move);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}