/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Network;

/**
 *
 * @author sebas
 */
import java.io.*;
import java.net.*;

public class GameHandler extends Thread {
    private Socket clientSocket1;
    private Socket clientSocket2;
    private BufferedReader input1;
    private BufferedReader input2;
    private PrintWriter output1;
    private PrintWriter output2;

    public GameHandler(Socket clientSocket1, Socket clientSocket2) {
        this.clientSocket1 = clientSocket1;
        this.clientSocket2 = clientSocket2;
        try {
            input1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
            output1 = new PrintWriter(clientSocket1.getOutputStream(), true);
            input2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
            output2 = new PrintWriter(clientSocket2.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            output1.println("X"); // Jugador 1 juega con "X"
            output2.println("O"); // Jugador 2 juega con "O"
            while (true) {
                String move1 = input1.readLine();
                if (move1 != null) {
                    output2.println(move1);
                }

                String move2 = input2.readLine();
                if (move2 != null) {
                    output1.println(move2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
