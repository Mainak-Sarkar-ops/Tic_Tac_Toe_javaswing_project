// Importing required AWT and Swing libraries for GUI
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TicTacToe {

    // Window size
    int boardWidth = 600;
    int boardHeight = 650;

    // Main frame and panels
    JFrame frame = new JFrame("TIC-TAC-TOE");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    // 3x3 board of buttons
    JButton[][] board = new JButton[3][3];

    // Player symbols
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    // Score variables
    int xScore = 0;
    int oScore = 0;
    int tieScore = 0;

    // Game mode flag
    boolean vsComputer = false;

    // Score display and reset button
    JLabel scoreLabel = new JLabel();
    JButton resetButton = new JButton("Reset Game");

    // Game state variables
    boolean gameOver = false;
    int turns = 0;

    // Constructor
    TicTacToe() {

        // Game mode selection popup
        String[] options = {"Player vs Player", "Player vs Computer"};

        int choice = JOptionPane.showOptionDialog(
                frame,
                "Choose Game Mode",
                "Tic Tac Toe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 1) {
            vsComputer = true;
        }

        // Frame settings
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title label settings
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setOpaque(true);

        // Showing selected game mode
        if(vsComputer){
            textLabel.setText("Player vs Computer");
        }else{
            textLabel.setText("Player vs Player");
        }

        // Top panel layout
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);

        // Score label settings
        scoreLabel.setText("X: 0    O: 0    Tie: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setOpaque(true);

        textPanel.add(scoreLabel, BorderLayout.SOUTH);
        frame.add(textPanel, BorderLayout.NORTH);

        // Board panel (3x3 grid)
        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        // Reset button settings
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setFocusable(false);

        frame.add(resetButton, BorderLayout.SOUTH);

        // Reset button action
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                resetBoard();
            }
        });

        // Creating the 3x3 buttons for the board
        for(int r = 0 ; r < 3 ; r++ ){
            for(int c = 0 ; c < 3 ; c++){

                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial" , Font.BOLD, 120));
                tile.setFocusable(false);

                // Button click event
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        if(gameOver) return;

                        JButton tile = (JButton) e.getSource();

                        if (tile.getText().equals("")){

                            // Place player's symbol
                            tile.setText(currentPlayer);
                            turns++;

                            // Check winner
                            checkWinner();

                            // Computer move if game mode is vs computer
                            if(vsComputer && !gameOver && currentPlayer.equals(playerX)){
                                currentPlayer = playerO;
                                computerMove();
                                return;
                            }

                            // Switch player turn
                            if(!gameOver){
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }
    }

    // Method to check all winning conditions
    void checkWinner(){

        // Horizontal check
        for(int r = 0; r < 3 ; r++){

            if(board[r][0].getText().equals("")) continue;

            if(board[r][0].getText().equals(board[r][1].getText()) &&
               board[r][1].getText().equals(board[r][2].getText())){

                if(currentPlayer.equals(playerX)) xScore++;
                else oScore++;

                scoreLabel.setText("X: " + xScore + "    O: " + oScore + "    Tie: " + tieScore);

                for(int i = 0 ; i < 3 ; i++){
                    setWinner(board[r][i]);
                }

                textLabel.setText(currentPlayer + " is the winner!");
                gameOver = true;
                return;
            }
        }

        // Vertical check
        for(int c = 0 ; c < 3 ; c++){

            if(board[0][c].getText().equals("")) continue;

            if(board[0][c].getText().equals(board[1][c].getText()) &&
               board[1][c].getText().equals(board[2][c].getText())){

                if(currentPlayer.equals(playerX)) xScore++;
                else oScore++;

                scoreLabel.setText("X: " + xScore + "    O: " + oScore + "    Tie: " + tieScore);

                for(int i  = 0 ; i < 3 ; i++){
                    setWinner(board[i][c]);
                }

                textLabel.setText(currentPlayer + " is the winner!");
                gameOver = true;
                return;
            }
        }

        // Diagonal check
        if(board[0][0].getText().equals(board[1][1].getText()) &&
           board[1][1].getText().equals(board[2][2].getText()) &&
           !board[0][0].getText().equals("")){

            if(currentPlayer.equals(playerX)) xScore++;
            else oScore++;

            scoreLabel.setText("X: " + xScore + "    O: " + oScore + "    Tie: " + tieScore);

            for(int i = 0 ; i < 3 ; i++){
                setWinner(board[i][i]);
            }

            textLabel.setText(currentPlayer + " is the winner!");
            gameOver = true;
            return;
        }

        // Anti-diagonal check
        if(board[0][2].getText().equals(board[1][1].getText()) &&
           board[1][1].getText().equals(board[2][0].getText()) &&
           !board[0][2].getText().equals("")){

            if(currentPlayer.equals(playerX)) xScore++;
            else oScore++;

            scoreLabel.setText("X: " + xScore + "    O: " + oScore + "    Tie: " + tieScore);

            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);

            textLabel.setText(currentPlayer + " is the winner!");
            gameOver = true;
            return;
        }

        // Tie condition
        if(turns == 9){

            tieScore++;
            scoreLabel.setText("X: " + xScore + "    O: " + oScore + "    Tie: " + tieScore);

            for(int r = 0 ; r < 3 ; r++){
                for(int c = 0 ; c < 3 ; c++ ){
                    setTie(board[r][c]);
                }
            }

            textLabel.setText("Tie!");
            gameOver = true;
        }
    }

    // Highlight winning tiles
    void setWinner(JButton tile){
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
    }

    // Highlight tie tiles
    void setTie(JButton tile){
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
    }

    // Reset board for next round
    void resetBoard(){

        for(int r = 0 ; r < 3 ; r++){
            for(int c = 0 ; c < 3 ; c++){
                board[r][c].setText("");
                board[r][c].setBackground(Color.darkGray);
                board[r][c].setForeground(Color.white);
            }
        }

        turns = 0;
        gameOver = false;
        currentPlayer = playerX;

        if(vsComputer){
            textLabel.setText("Player vs Computer");
        }else{
            textLabel.setText("Player vs Player");
        }
    }

    // Computer move logic (simple AI)
    void computerMove(){

        for(int r = 0; r < 3 ; r++){
            for(int c = 0 ; c < 3 ; c++){

                if(board[r][c].getText().equals("")){
                    board[r][c].setText(playerO);
                    turns++;
                    checkWinner();

                    if(!gameOver){
                        currentPlayer = playerX;
                        textLabel.setText(currentPlayer + "'s turn");
                    }
                    return;
                }
            }
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        new TicTacToe();
    }
}