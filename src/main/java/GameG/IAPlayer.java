
package GameG;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class IAPlayer {

    private String difficulty;
    private Random random;

    public IAPlayer(String difficulty) {
        this.difficulty = difficulty;
        this.random = new Random();
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Método para hacer un movimiento dependiendo del nivel de dificultad
     */
    public String makeMove(String[] board) {
        if ("easy".equals(difficulty)) {
            return easyLevel(board);
        } else if ("medium".equals(difficulty)) {
            return hardLevel(board);
        } else if ("hard".equals(difficulty)) {
            return mediumLevel(board);
        }
        return "";
    }

    /**
     * Método para el nivel de dificultad fácil
     * La IA hace un movimiento aleatorio en el tablero     
     */
    private String easyLevel(String[] board) {
        while (true) {
            int index = random.nextInt(9);
            if (board[index].isEmpty()) {
                return String.valueOf(index + 1);
            }
        }
    }

    /**
     * Método para el nivel de dificultad medio
     * La IA intenta ganar si es posible, de lo contrario, bloquear al jugador
     * Si no puede ganar ni bloquear, hace un movimiento aleatorio
     */
    private String mediumLevel(String[] board) {
        // Nivel medio: Utilizar el algoritmo de minimax para encontrar el mejor movimiento
        int bestMove = minimax(board, "O");
        return String.valueOf(bestMove + 1);
    }

    /**
     * Método para el nivel de dificultad difícil
     * La IA intenta ganar si es posible, de lo contrario, bloquear al jugador
     * Si no puede ganar ni bloquear, hace un movimiento aleatorio
     */
    private String hardLevel(String[] board) {
        String[] possibleMoves = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (String move : possibleMoves) {
            int index = Integer.parseInt(move) - 1;
            if (board[index].isEmpty()) {
                String[] newBoard = board.clone();
                newBoard[index] = "O";
                if (isWinner(newBoard, "O")) {
                    return move;
                }
            }
        }

        for (String move : possibleMoves) {
            int index = Integer.parseInt(move) - 1;
            if (board[index].isEmpty()) {
                String[] newBoard = board.clone();
                newBoard[index] = "X";
                if (isWinner(newBoard, "X")) {
                    return move;
                }
            }
        }

        // Si el tablero está lleno, no hace un movimiento
        if (Arrays.stream(board).allMatch(cell -> !cell.isEmpty())) {
            return "";
        }

        return easyLevel(board);
    }

    private boolean isFull(String[] board) {
        for (String cell : board) {
            if (cell.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private int minimax(String[] board, String currentPlayer) {
        String opponent = (currentPlayer.equals("O")) ? "X" : "O";

        // Verifica si el estado actual del tablero es un estado terminal y devuelve el valor correspondiente
        if (isWinner(board, "O")) {
            return 10;
        } else if (isWinner(board, "X")) {
            return -10;
        } else if (isFull(board)) {
            return 0;
        }

        List<Integer> moves = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();

        // Genera todos los posibles movimientos para el jugador actual y calcula su valor
        for (int i = 0; i < board.length; i++) {
            if (board[i].isEmpty()) {
                String[] newBoard = board.clone();
                newBoard[i] = currentPlayer;

                moves.add(i);
                scores.add(minimax(newBoard, opponent));
            }
        }

        // Retorna el movimiento óptimo basado en el valor calculado
        if (currentPlayer.equals("O")) {
            int maxIndex = scores.indexOf(Collections.max(scores));
            return moves.get(maxIndex);
        } else {
            int minIndex = scores.indexOf(Collections.min(scores));
            return moves.get(minIndex);
        }
    }

    /**
     * Método para determinar si un jugador ha ganado
     */
   private boolean isWinner(String[] board, String player) {
    // Verifica si el jugador ha ganado en alguna fila
    for (int i = 0; i < 3; i++) {
        // Comprueba si todas las celdas de una fila son del jugador
        if (board[3 * i].equals(player) && board[3 * i + 1].equals(player) && board[3 * i + 2].equals(player)) {
            return true ; // Si es así, retorna verdadero
        }
    }

    // Verifica si el jugador ha ganado en alguna columna
    for (int i = 0; i < 3; i++) {
        // Comprueba si todas las celdas de una columna son del jugador
        if (board[i].equals(player) && board[i + 3].equals(player) && board[i + 6].equals(player)) {
            return true; // Si es así, retorna verdadero
        }
    }

    // Verifica si el jugador ha ganado en alguna diagonal
    // Comprueba si todas las celdas de una diagonal son del jugador
    if (board[0].equals(player) && board[4].equals(player) && board[8].equals(player)) {
        return true; // Si es así, retorna verdadero
    }
    // Comprueba si todas las celdas de la otra diagonal son del jugador
    if (board[2].equals(player) && board[4].equals(player) && board[6].equals(player)) {
        return true; // Si es así, retorna verdadero
    }

    return false; // Si no se cumple ninguna condición anterior, retorna falso
 }
}