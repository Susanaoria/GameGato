
package GameG;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class GameMain extends javax.swing.JFrame {

    /**
     * Creates new form GameMain
     */
    private String currentPlayer = "X";
    private int xCount = 0;// contador de ganador de x
    private int oCount = 0;// contar ganador de 0
    private int drawCount = 0; // Nueva variable para contar empates
    private int moveCount = 0;
    private boolean isGameActive = true;  // controla que el juego este activo
     private boolean shouldIncrementMoveCount = true;
    
    
    private IAPlayer iaPlayer;
    private boolean isVsIA = false; //  Indica si se está jugando contra la IA
    private String playerIA = "O"; //  Representación de la IA en el tablero
    private String playerHuman = "X"; //  Representación del jugador humano en el tablero
     
    
    
    
   
    
    
    
    public  GameMain() {
        initComponents();
         addActionListenersToButtons();
        setLocationRelativeTo(null);
         iaPlayer = new IAPlayer("easy");
         turnLabel.setText("Turno: X"); // Establece el turno inicial
        }
    
  
     
         private void addActionListenersToButtons() {
        JButton[] buttons = {jbtnTic1, jbtnTic2, jbtnTic3, jbtnTic4, jbtnTic5, jbtnTic6, jbtnTic7, jbtnTic8, jbtnTic9};
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(button);
                    
                    if (isVsIA && currentPlayer.equals(playerIA)) {
                        handleIAMove();
                    }
                }
            });
        }
    }
      
private void resetGame() {
    resetBoard();
    moveCount = 0;
    currentPlayer = "X";
    
    JButton[] buttons = {jbtnTic1, jbtnTic2, jbtnTic3, jbtnTic4, jbtnTic5, jbtnTic6, jbtnTic7, jbtnTic8, jbtnTic9};
    for (JButton button : buttons) {
        button.setEnabled(true);
        button.setText("");
        button.setBackground(Color.LIGHT_GRAY);
    }
    Stats1.setText("0");
    Stats2.setText("0");
    Stats3.setText("0");
    jbtnTic1.setForeground(Color.BLACK);
    jbtnTic2.setForeground(Color.BLACK);
    jbtnTic3.setForeground(Color.BLACK);
    jbtnTic4.setForeground(Color.BLACK);
    jbtnTic5.setForeground(Color.BLACK);
    jbtnTic6.setForeground(Color.BLACK);
    jbtnTic7.setForeground(Color.BLACK);
    jbtnTic8.setForeground(Color.BLACK);
    jbtnTic9.setForeground(Color.BLACK);
    isGameActive = true;
}

 
 
      
    
 private void handleButtonClick(JButton button) {
    if (button.getText().isEmpty() && isGameActive) {
        button.setText(currentPlayer);
        if (currentPlayer.equals("X")) {
            button.setForeground(Color.RED);
        } else {
            button.setForeground(Color.BLUE);
        }
        
        checkWinner();
        if (isGameActive && shouldIncrementMoveCount) { // Solo incrementa si el juego está activo y se debe incrementar
            moveCount++;
        }
        Empate(); // Comprobación de empate añadida
        switchPlayer();
    }
    if (isVsIA && currentPlayer.equals(playerIA) && isGameActive) {
        handleIAMove();
    }
}
   

    private void switchPlayer() {
    currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
    
    if (currentPlayer.equals("X")) {
        turnLabel.setText("Turno del Jugador: X");
        turnLabel.setForeground(Color.RED);
    } else {
        turnLabel.setText("Turno del Jugador: O");
        turnLabel.setForeground(Color.BLUE);
    }
}

    private void checkWinner() {
        String[] board = {
            jbtnTic1.getText(), jbtnTic2.getText(), jbtnTic3.getText(),
            jbtnTic4.getText(), jbtnTic5.getText(), jbtnTic6.getText(),
            jbtnTic7.getText(), jbtnTic8.getText(), jbtnTic9.getText()
        };

        // Define las combinaciones ganadoras
        int[][] winningCombinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},  // Filas
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},  // Columnas
            {0, 4, 8}, {2, 4, 6}             // Diagonales
        };

        for (int[] combination : winningCombinations) {
            String a = board[combination[0]];
            String b = board[combination[1]];   
            String c = board[combination[2]];

            if (a.equals(b) && b.equals(c) && !a.isEmpty()) {
                showWinnerDialog(a);
                return;
            }
        }
         
         
    }
    
         //mensaje gandor ya sea "x" o "o"
    private void showWinnerDialog(String winner) {
    shouldIncrementMoveCount = false;
    
    JDialog dialog = new JDialog(this, "Ganador", true);
    dialog.setLayout(new BorderLayout());
    dialog.add(new JLabel("Jugador " + winner + " gano"), BorderLayout.CENTER);
    JButton okButton = new JButton("OK");
    okButton.addActionListener(e -> dialog.dispose());
    dialog.add(okButton, BorderLayout.SOUTH);
    dialog.pack();
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
    
    dialog.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            shouldIncrementMoveCount = true;
            if (winner.equals("X")) {
                xCount++;
            } else {
                oCount++;
            }
            updateScores();
            moveCount = 0;
            resetBoard();  // Solo reinicia el tablero
            isGameActive = true;  // indica si el juego esta activo
            if (isVsIA && winner.equals("X")) {
        handleIAMove();
             } 
        }
    });
}
    private void Empate() {
    
    System.out.println("Entrando a Empate, moveCount: " + moveCount); 
    if (moveCount == 9) {
        
        JOptionPane.showMessageDialog(this, "Empate", "Empate", JOptionPane.INFORMATION_MESSAGE);
         drawCount++;
         updateScores();
        moveCount = 0;
        resetBoard(); // Solo reinicia el tablero
    }
}
    
    //  Manejo del movimiento de la IA
    private void handleIAMove() {
        String[] board = getCurrentBoard();
        String move = iaPlayer.makeMove(board);

        JButton selectedButton = getButtonByMove(move);
        if (selectedButton != null && selectedButton.isEnabled()) {
            selectedButton.setText(playerIA);
            selectedButton.setForeground(Color.BLUE);
            checkWinner();
            moveCount++;
            switchPlayer();
        }
        
    }
    // Obtener el estado actual del tablero
    private String[] getCurrentBoard() {
        return new String[]{
            jbtnTic1.getText(), jbtnTic2.getText(), jbtnTic3.getText(),
            jbtnTic4.getText(), jbtnTic5.getText(), jbtnTic6.getText(),
            jbtnTic7.getText(), jbtnTic8.getText(), jbtnTic9.getText()
        };
    }

    //  Obtener el botón correspondiente al movimiento de la IA
    private JButton getButtonByMove(String move) {
        switch (move) {
            case "1":
                return jbtnTic1;
            case "2":
                return jbtnTic2;
            case "3":
                return jbtnTic3;
            case "4":
                return jbtnTic4;
            case "5":
                return jbtnTic5;
            case "6":
                return jbtnTic6;
            case "7":
                return jbtnTic7;
            case "8":
                return jbtnTic8;
            case "9":
                return jbtnTic9;
            default:
                return null;
        }
    }

    private void updateScores() {
        Stats1.setText(String.valueOf(xCount));
        Stats2.setText(String.valueOf(oCount));
        Stats3.setText(String.valueOf(drawCount));
    }

    private void resetBoard() {
        JButton[] buttons = {jbtnTic1, jbtnTic2, jbtnTic3, jbtnTic4, jbtnTic5, jbtnTic6, jbtnTic7, jbtnTic8, jbtnTic9};
        for (JButton button : buttons) {
            button.setText("");
            button.setForeground(Color.BLACK);
        }
        
    }
    
    
    


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jbtnTic3 = new javax.swing.JButton();
        jbtnTic2 = new javax.swing.JButton();
        jbtnTic4 = new javax.swing.JButton();
        jbtnTic5 = new javax.swing.JButton();
        jbtnTic6 = new javax.swing.JButton();
        jbtnTic8 = new javax.swing.JButton();
        jbtnTic1 = new javax.swing.JButton();
        jbtnTic9 = new javax.swing.JButton();
        jbtnTic7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Stats1 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Stats2 = new javax.swing.JTextField();
        Stats3 = new javax.swing.JTextField();
        labelEmpate = new javax.swing.JLabel();
        turnLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        vsIAmenu = new javax.swing.JMenu();
        Nivel1IA = new javax.swing.JMenuItem();
        Nivel2IA = new javax.swing.JMenuItem();
        Nivel3IA = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jPanel2.setBackground(new java.awt.Color(102, 0, 102));
        jPanel2.setForeground(new java.awt.Color(255, 255, 153));

        jPanel1.setBackground(new java.awt.Color(255, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setForeground(new java.awt.Color(255, 51, 255));

        jbtnTic3.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic3.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic3.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic3.setPreferredSize(new java.awt.Dimension(200, 158));

        jbtnTic2.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic2.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic2.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic2.setPreferredSize(new java.awt.Dimension(200, 158));

        jbtnTic4.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic4.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic4.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic4.setPreferredSize(new java.awt.Dimension(200, 158));

        jbtnTic5.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic5.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic5.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic5.setPreferredSize(new java.awt.Dimension(200, 158));

        jbtnTic6.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic6.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic6.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic6.setPreferredSize(new java.awt.Dimension(200, 158));

        jbtnTic8.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic8.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic8.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic8.setPreferredSize(new java.awt.Dimension(200, 158));

        jbtnTic1.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic1.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic1.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic1.setPreferredSize(new java.awt.Dimension(200, 158));
        jbtnTic1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnTic1ActionPerformed(evt);
            }
        });

        jbtnTic9.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic9.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic9.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic9.setPreferredSize(new java.awt.Dimension(200, 158));

        jbtnTic7.setFont(new java.awt.Font("Tahoma", 1, 96)); // NOI18N
        jbtnTic7.setMaximumSize(new java.awt.Dimension(200, 158));
        jbtnTic7.setMinimumSize(new java.awt.Dimension(200, 158));
        jbtnTic7.setPreferredSize(new java.awt.Dimension(200, 158));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnTic4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnTic1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnTic7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnTic8, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnTic9, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnTic2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnTic3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnTic5, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnTic6, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnTic3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnTic2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnTic1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnTic4, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnTic5, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnTic6, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnTic8, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnTic9, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnTic7, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        jButton1.setText("Nuevo juego");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        jButton2.setText("Reiniciar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Stats1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Stats1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Stats1.setText("0");

        jButton12.setFont(new java.awt.Font("123Marker", 1, 14)); // NOI18N
        jButton12.setText("Salir");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        jLabel1.setText("Victorias jugador X");

        jLabel2.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        jLabel2.setText("Victorias Jugador 0");

        Stats2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Stats2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Stats2.setText("0");

        Stats3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Stats3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Stats3.setText("0");

        labelEmpate.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        labelEmpate.setText(" Empate");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelEmpate, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Stats2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Stats1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(Stats3)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Stats1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Stats2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Stats3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEmpate, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        turnLabel.setFont(new java.awt.Font("123Marker", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(turnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(turnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        vsIAmenu.setText("vs IA dificutad");

        Nivel1IA.setText("Nivel 1");
        Nivel1IA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nivel1IAActionPerformed(evt);
            }
        });
        vsIAmenu.add(Nivel1IA);

        Nivel2IA.setText("Nivel 2");
        Nivel2IA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nivel2IAActionPerformed(evt);
            }
        });
        vsIAmenu.add(Nivel2IA);

        Nivel3IA.setText("Nivel 3");
        Nivel3IA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nivel3IAActionPerformed(evt);
            }
        });
        vsIAmenu.add(Nivel3IA);

        jMenuBar1.add(vsIAmenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private JFrame frame;
    private void Nivel1IAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nivel1IAActionPerformed
      iaPlayer.setDifficulty("easy");   //eleccion de dificultad
        iniciarJuegoContraIA();
       
    }//GEN-LAST:event_Nivel1IAActionPerformed

    private void Nivel2IAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nivel2IAActionPerformed
          iaPlayer.setDifficulty("medium"); //eleccion de dificultad  
         iniciarJuegoContraIA();
    }//GEN-LAST:event_Nivel2IAActionPerformed

    private void Nivel3IAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nivel3IAActionPerformed
        iaPlayer.setDifficulty("hard"); //eleccion de dificultad
        iniciarJuegoContraIA();

       
    }//GEN-LAST:event_Nivel3IAActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        frame = new JFrame("salir");
        if(JOptionPane.showConfirmDialog(frame,"Quieres salir del juego","Tic Tac",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        moveCount = 0;
        isGameActive = true;
        jbtnTic1.setEnabled(true);
        jbtnTic2.setEnabled(true);
        jbtnTic3.setEnabled(true);
        jbtnTic4.setEnabled(true);
        jbtnTic5.setEnabled(true);
        jbtnTic6.setEnabled(true);
        jbtnTic7.setEnabled(true);
        jbtnTic8.setEnabled(true);
        jbtnTic9.setEnabled(true);

        jbtnTic1.setText("");
        jbtnTic2.setText("");
        jbtnTic3.setText("");
        jbtnTic4.setText("");
        jbtnTic5.setText("");
        jbtnTic6.setText("");
        jbtnTic7.setText("");
        jbtnTic8.setText("");
        jbtnTic9.setText("");

        jbtnTic1.setBackground(Color.LIGHT_GRAY);
        jbtnTic2.setBackground(Color.LIGHT_GRAY);
        jbtnTic3.setBackground(Color.LIGHT_GRAY);
        jbtnTic4.setBackground(Color.LIGHT_GRAY);
        jbtnTic5.setBackground(Color.LIGHT_GRAY);
        jbtnTic6.setBackground(Color.LIGHT_GRAY);
        jbtnTic7.setBackground(Color.LIGHT_GRAY);
        jbtnTic8.setBackground(Color.LIGHT_GRAY);
        jbtnTic9.setBackground(Color.LIGHT_GRAY);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        resetGame();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbtnTic1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnTic1ActionPerformed

    }//GEN-LAST:event_jbtnTic1ActionPerformed
// Agregado: Iniciar el juego contra la IA
    private void iniciarJuegoContraIA() {
        isVsIA = true;
        resetGame();
        currentPlayer = playerHuman; // Agregado: Asegurarse de que el jugador humano comience
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Nivel1IA;
    private javax.swing.JMenuItem Nivel2IA;
    private javax.swing.JMenuItem Nivel3IA;
    private javax.swing.JTextField Stats1;
    private javax.swing.JTextField Stats2;
    private javax.swing.JTextField Stats3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnTic1;
    private javax.swing.JButton jbtnTic2;
    private javax.swing.JButton jbtnTic3;
    private javax.swing.JButton jbtnTic4;
    private javax.swing.JButton jbtnTic5;
    private javax.swing.JButton jbtnTic6;
    private javax.swing.JButton jbtnTic7;
    private javax.swing.JButton jbtnTic8;
    private javax.swing.JButton jbtnTic9;
    private javax.swing.JLabel labelEmpate;
    private javax.swing.JLabel turnLabel;
    private javax.swing.JMenu vsIAmenu;
    // End of variables declaration//GEN-END:variables
}
