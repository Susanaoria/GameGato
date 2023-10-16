/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GameG;

import Network.Cliente;
import Network.Servidor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author sebas
 */
public class GameMainONLINE extends javax.swing.JFrame {
    
    private String currentPlayer = "X";
    private int xCount = 0;// contador de ganador de x
    private int oCount = 0;// contar ganador de 0
    private int drawCount = 0; // Nueva variable para contar empates
    private int moveCount = 0;
    private boolean isGameActive = true;  // controla que el juego este activo
    private boolean shouldIncrementMoveCount = true;
    

    /**
     * Creates new form GameMainONLINE
     */
    public GameMainONLINE() {
        initComponents();
      addActionListenersToButtons();
       setLocationRelativeTo(null);
    }
 
    private void addActionListenersToButtons() {
        JButton[] buttons = {JBTN1, JBTN2, JBTN3, JBTN4, JBTN5,JBTN6, JBTN7, JBTN8, JBTN9};
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(button);
                    
                    
                }
            });
        }
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
    
}
    
 private void switchPlayer() {
    currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
    
    if (currentPlayer.equals("X")) {
        turnLabelO.setText("Turno del Jugador: X");
        turnLabelO.setForeground(Color.RED);
    } else {
        turnLabelO.setText("Turno del Jugador: O");
        turnLabelO.setForeground(Color.BLUE);
    }    
 }
    
    
    
    
private void checkWinner() {
        String[] board = {
           JBTN1.getText(), JBTN2.getText(), JBTN3.getText(),
            JBTN4.getText(), JBTN5.getText(), JBTN6.getText(),
            JBTN7.getText(), JBTN8.getText(), JBTN9.getText()
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




private void updateScores() {
        jTextVX.setText(String.valueOf(xCount));
        jTVO1.setText(String.valueOf(oCount));
        jtextE.setText(String.valueOf(drawCount));
    }


private void resetBoard() {
        JButton[] buttons = { JBTN1,  JBTN2,  JBTN3,  JBTN4,  JBTN5,  JBTN6,  JBTN7, JBTN8,  JBTN9};
        for (JButton button : buttons) {
            button.setText("");
            button.setForeground(Color.BLACK);
        }
        
    }
private void resetGame() {
    resetBoard();
    moveCount = 0;
    currentPlayer = "X";
    
    JButton[] buttons = { JBTN1,  JBTN2,  JBTN3,  JBTN4,  JBTN5,  JBTN6,  JBTN7, JBTN8,  JBTN9};
    for (JButton button : buttons) {
        button.setEnabled(true);
        button.setText("");
        button.setBackground(Color.LIGHT_GRAY);
    }
     jTextVX.setText("0");
     jTVO1.setText("0");
     jtextE.setText("0");
     JBTN1.setForeground(Color.BLACK);
     JBTN2.setForeground(Color.BLACK);
     JBTN3.setForeground(Color.BLACK);
     JBTN4.setForeground(Color.BLACK);
     JBTN5.setForeground(Color.BLACK);
     JBTN6.setForeground(Color.BLACK);
     JBTN7.setForeground(Color.BLACK);
     JBTN8.setForeground(Color.BLACK);
     JBTN9.setForeground(Color.BLACK);
    isGameActive = true;
                        }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        JBTN2 = new javax.swing.JButton();
        JBTN3 = new javax.swing.JButton();
        JBTN4 = new javax.swing.JButton();
        JBTN5 = new javax.swing.JButton();
        JBTN6 = new javax.swing.JButton();
        JBTN7 = new javax.swing.JButton();
        JBTN8 = new javax.swing.JButton();
        JBTN9 = new javax.swing.JButton();
        JBTN1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        LabelVictoriasO = new javax.swing.JLabel();
        LabelVictoriasX = new javax.swing.JLabel();
        jTextVX = new javax.swing.JTextField();
        jtextE = new javax.swing.JTextField();
        Labelempate = new javax.swing.JLabel();
        jTVO1 = new javax.swing.JTextField();
        BtnIniciarpartida = new javax.swing.JButton();
        turnLabelO = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(1420, 827));

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setPreferredSize(new java.awt.Dimension(825, 660));

        JBTN2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN2.setPreferredSize(new java.awt.Dimension(200, 158));

        JBTN3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN3.setPreferredSize(new java.awt.Dimension(200, 158));

        JBTN4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN4.setPreferredSize(new java.awt.Dimension(200, 158));

        JBTN5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN5.setPreferredSize(new java.awt.Dimension(200, 158));

        JBTN6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN6.setPreferredSize(new java.awt.Dimension(200, 158));

        JBTN7.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN7.setPreferredSize(new java.awt.Dimension(200, 158));
        JBTN7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBTN7ActionPerformed(evt);
            }
        });

        JBTN8.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN8.setPreferredSize(new java.awt.Dimension(200, 158));
        JBTN8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBTN8ActionPerformed(evt);
            }
        });

        JBTN9.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN9.setPreferredSize(new java.awt.Dimension(200, 158));

        JBTN1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        JBTN1.setPreferredSize(new java.awt.Dimension(200, 158));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JBTN7, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(JBTN4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JBTN1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JBTN8, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(JBTN5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JBTN2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JBTN9, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(JBTN6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JBTN3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(68, 68, 68))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JBTN3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBTN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBTN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JBTN6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JBTN5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBTN4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(JBTN8, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(JBTN9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JBTN7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(486, 653));

        LabelVictoriasO.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        LabelVictoriasO.setText("Victorias  O");

        LabelVictoriasX.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        LabelVictoriasX.setText("Victorias X");

        jTextVX.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        jTextVX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextVX.setText("0");

        jtextE.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        jtextE.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtextE.setText("0");

        Labelempate.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        Labelempate.setText(" Empate");

        jTVO1.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        jTVO1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTVO1.setText("0");

        BtnIniciarpartida.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        BtnIniciarpartida.setText("Iniciar partida");
        BtnIniciarpartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIniciarpartidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(48, 48, 48)
                            .addComponent(LabelVictoriasO, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGap(39, 39, 39)
                            .addComponent(LabelVictoriasX, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(Labelempate, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextVX, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(jTVO1)
                    .addComponent(jtextE))
                .addGap(46, 46, 46))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(BtnIniciarpartida, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelVictoriasX, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextVX, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelVictoriasO, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTVO1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelempate, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtextE, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(BtnIniciarpartida, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        turnLabelO.setFont(new java.awt.Font("123Marker", 1, 18)); // NOI18N
        turnLabelO.setText("Turno de X");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(turnLabelO, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(turnLabelO, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1403, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JBTN8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBTN8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JBTN8ActionPerformed

    private void JBTN7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBTN7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JBTN7ActionPerformed

    private void BtnIniciarpartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIniciarpartidaActionPerformed
      
        JDialog dialog = new JDialog(GameMainONLINE.this, "Inicio del juego", true);
        dialog.setLayout(new BorderLayout());
        
        JButton btnInvitarJugador = new JButton("Invitar Jugador");
        btnInvitarJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                  Servidor servidor = new Servidor(12345);
                  servidor.start();
    
                dialog.dispose();
            }
        });
        
        JButton btnAceptarInvitacion = new JButton("Aceptar Invitación");
        btnAceptarInvitacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 Cliente cliente = new Cliente("127.0.0.1", 5000);
                dialog.dispose();
            }
        });
        
        dialog.add(btnInvitarJugador, BorderLayout.NORTH);
        dialog.add(btnAceptarInvitacion, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(GameMainONLINE.this);
        dialog.setVisible(true);
    
    }//GEN-LAST:event_BtnIniciarpartidaActionPerformed

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
            java.util.logging.Logger.getLogger(GameMainONLINE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameMainONLINE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameMainONLINE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameMainONLINE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameMainONLINE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnIniciarpartida;
    private javax.swing.JButton JBTN1;
    private javax.swing.JButton JBTN2;
    private javax.swing.JButton JBTN3;
    private javax.swing.JButton JBTN4;
    private javax.swing.JButton JBTN5;
    private javax.swing.JButton JBTN6;
    private javax.swing.JButton JBTN7;
    private javax.swing.JButton JBTN8;
    private javax.swing.JButton JBTN9;
    private javax.swing.JLabel LabelVictoriasO;
    private javax.swing.JLabel LabelVictoriasX;
    private javax.swing.JLabel Labelempate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTVO1;
    private javax.swing.JTextField jTextVX;
    private javax.swing.JTextField jtextE;
    private javax.swing.JLabel turnLabelO;
    // End of variables declaration//GEN-END:variables
}
