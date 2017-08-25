
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeSwing extends JApplet implements ActionListener {
    
    // some constants to define parameters of the game
    private static final int GAME_SIZE = 3;
    private static final Color BUTTON_COLOR = Color.yellow;

    // Denote value of a square to show who has played there
    private static final int EMPTY = 0;
    private static final int X_MARK = 1;
    private static final int O_MARK = 2;

    // The grid contents: int values that will contain the int
    // values above, and the actual JButton objects we will
    // use for game play
    private int[][] marks;
    private JButton[][] buttons;

    // Whose turn it is next
    private int nextMark = X_MARK;

    // Whether the game has been won or not
    private boolean gameOver = false;

    // the label that will give game status
    private JLabel messageLabel;
    
    // the play again button
    private JButton playAgain;
    
    public void init() {
        
        Container contentPane = getContentPane();
        
        // create our int representation of the game status
        marks = new int[GAME_SIZE][GAME_SIZE];
        
        // we start with a panel at the top for a message
        JPanel topPanel = new JPanel();
        
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Serif", Font.BOLD, 36));
        topPanel.add(messageLabel);
        contentPane.add(topPanel, BorderLayout.NORTH);
        
        // the grid for game play
        buttons = new JButton[GAME_SIZE][GAME_SIZE];
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(GAME_SIZE, GAME_SIZE));
        for (int row = 0; row < GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
                buttons[row][col] = new JButton("X");
                buttons[row][col].addActionListener(this);
                buttons[row][col].setFont(new Font("Sans Serif", Font.BOLD, 72));
                buttons[row][col].addActionListener(this);
                buttons[row][col].setBackground(Color.black);
                buttons[row][col].setOpaque(true);
                gamePanel.add(buttons[row][col]);
            }
        }
        contentPane.add(gamePanel, BorderLayout.CENTER);
        
        // and a button at the bottom for restarting the game
        JPanel bottomPanel = new JPanel();
        playAgain = new JButton("Play Again");
        playAgain.addActionListener(this);
        bottomPanel.add(playAgain);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        
        contentPane.validate();
        
        reset(); // initialize game play
    }
    
    // everything that needs to happen for the start of a new game
    private void reset() {
    
        for (int row = 0; row < GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
                buttons[row][col].setText("");
                marks[row][col] = EMPTY;
            }
        }
        gameOver = false;
        messageLabel.setText("Welcome! X goes first!");
        nextMark = X_MARK;
        playAgain.setEnabled(false);
    }
    
    /**
     * Check to see if the game is won as a result of a mark being placed
     * at position row, col.  The game is over if an entire row, column, or
     * diagonal has the same pieces.
     * 
     * @param row the row number (0, 1, 2) of the cell just marked
     * @param col the column number (0, 1, 2) of the cell just marked
     * @param matchMark the mark, X_MARK or O_MARK, just placed
     * 
     * @return true if the current player now has won the game, false otherwise
     */
    private boolean checkGameWon(int row, int col, int matchMark) {
        return checkForRowWin(row, matchMark)
        || checkForColWin(col, matchMark)
        || checkForDiagWin(matchMark)
        || checkForDiag2Win(matchMark); 
    }

    /**
     * Check to see if the game is a tie: all positions are occupied,
     * but no one has won.
     * 
     * @return true if all positions are occupied
     */
    private boolean checkAllFilled() {
        for (int row = 0; row < GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
                if (marks[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check for a win in the given row by the player with the 
     * given mark.
     * 
     * @param row the row number to check
     * @param matchMark the mark, X_MARK or O_MARK, just placed
     * 
     * @return whether the given row contains all matchMark entries
     */
    private boolean checkForRowWin(int row, int matchMark) {
        for (int col = 0; col < GAME_SIZE; ++col) {
            if (marks[row][col] != matchMark) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check for a win in the given column by the player with the 
     * given mark.
     * 
     * @param col the column number to check
     * @param matchMark the mark, X_MARK or O_MARK, just placed
     * 
     * @return whether the given column contains all matchMark entries
     */
    private boolean checkForColWin(int col, int matchMark) {
        for (int row=0; row < GAME_SIZE; ++row) {
            if (marks[row][col] != matchMark) {
                return false;
            }   
        }
        return true;
    }

    /**
     * Check for a win in the main diagonal by the player with the 
     * given mark.  This is the diagonal from the top left to bottom right.
     * 
     * @param matchMark the mark, X_MARK or O_MARK, just placed
     * 
     * @return whether the main diagonal contains all matchMark entries
     */
    private boolean checkForDiagWin(int matchMark) {
        for (int i = 0; i < GAME_SIZE; ++i) {
            if (marks[i][i] != matchMark) {
                return false;   
            }
        }
        return true;
    }

    /**
     * Check for a win in the off diagonal by the player with the 
     * given mark.  This is the diagonal from the bottom left to top right.
     * 
     * @param matchMark the mark, X_MARK or O_MARK, just placed
     * 
     * @return whether the off diagonal contains all matchMark entries
     */
    private boolean checkForDiag2Win(int matchMark) {
        for (int i = 0; i < GAME_SIZE; ++i) {
            if (marks[i][GAME_SIZE-i-1] != matchMark) {
                return false;   
            }
        }
        return true;
    }

        /**
     * Draw the appropriate mark in the appropriate row/col in
     * the Tic-Tac-Toe board
     * 
     * @param row the row number (0, 1, 2) of the cell to mark
     * @param col the column number (0, 1, 2) of the cell to mark
     * @param mark what to mark, X_MARK or O_MARK
     */
    private void drawMark(int row, int col, int mark) {
        if (mark == X_MARK) {
            buttons[row][col].setText("X");
        } else {
            buttons[row][col].setText("O");
        }
    }


    public void actionPerformed(ActionEvent eventInfo) {
        
        if (eventInfo.getSource() == playAgain) {
            // this can only happen if the button is enabled, so
            // we reset unconditionally here
            reset();
        }
        else {
            // Figure out which cell in the grid was clicked in
            int col = 0;
            int row = 0;
            
            for (int r = 0; r < GAME_SIZE; r++) {
                for (int c = 0; c < GAME_SIZE; c++) {
                    if (eventInfo.getSource() == buttons[r][c]) {
                        col = c;
                        row = r;
                    }
                }
            }

            // Make sure the grid is empty before adding a mark.
            // Also make sure the game is not already over
            if (marks[row][col] == EMPTY && !gameOver) {
                // Add the mark
                marks[row][col] = nextMark;

                drawMark(row, col, nextMark);

                // See if the game is won
                if (checkGameWon(row, col, nextMark)) {
                    if (nextMark == X_MARK) {
                        messageLabel.setText("X Wins!");
                    } else {
                        messageLabel.setText("O Wins!");
                    }
                    gameOver = true;
                }

                // see if it's a tie
                if (!gameOver && checkAllFilled()) {
                    messageLabel.setText("It's a tie!");
                    gameOver = true;
                }

                if (!gameOver) {
                    // switch players
                    if (nextMark == X_MARK) {
                        nextMark = O_MARK;
                        messageLabel.setText("O's Turn");
                    } else {
                        nextMark = X_MARK;
                        messageLabel.setText("X's Turn");
                    }
                }
                else {
                    // if the game is over, we enable the play again button
                    playAgain.setEnabled(true);
                }
            }
        }
    }
}
