
package Network;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
    private ServerSocket serverSocket;
    private Socket clientSocket1;
    private Socket clientSocket2;

    public Servidor(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            System.out.println("Esperando jugadores...");
            clientSocket1 = serverSocket.accept();
            System.out.println("Jugador 1 conectado");
            clientSocket2 = serverSocket.accept();
            System.out.println("Jugador 2 conectado");
            GameHandler gameHandler = new GameHandler(clientSocket1, clientSocket2);
            gameHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}