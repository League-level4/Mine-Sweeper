package org.jointheleague.level4.minesweeper;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Implement the Mine Sweeper game by following the instructions in the
 * comments. All instructions are marked with "TODO".
 */
public class MineSweeper {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 15;
    private static final int CELL_SIZE = 20;
    private static final int NUM_MINES = 15;
    
    private Optional<boolean[][]> mines = Optional.empty();
    private int numCellsRemaining;
    
    /**
     * Initialize the `mines` variable with `NUM_MINES` randomly distributed
     * mines, and the `numCellsRemaining` variable to the number of non-mine
     * cells (`WIDTH` * `HEIGHT` - `NUM_MINES`).
     * 
     * @param firstCellX X-index of first cell opened. This cannot be a mine.
     * @param firstCellY Y-index of first cell opened. This cannot be a mine.
     */
    private void initializeMines(int firstCellX, int firstCellY) {
        // TODO fill in
        // Hint, use `Optional.of(...)` to create a non-empty `Optional`.
    }
    
    /**
     * Inspects a given cell, and count the number of neighboring cells that
     * are mines.
     * 
     * @param x X-index of cell to inspect.
     * @param y Y-index of cell to inspect.
     * @return The number of neighboring cells that are mines (0-8).
     */
    private Integer getNeighboringMinesCount(int x, int y) {
        // TODO fill in
        return null;
    }
    
    /**
     * Resets the game by setting the `mines` variable back to empty.
     * 
     * @param unused Just here so that method can be passed as ActionListener.
     */
    private void resetGame(Object unused) {
        // TODO fill in
    }
    
    private void createAndShowFrame() {
        final JMenuItem resetMenuItem = new JMenuItem("Reset");
        resetMenuItem.addActionListener(null); // TODO replace null with method reference that resets the game
        
        final JMenu gameMenu = new JMenu("Game");
        gameMenu.add(resetMenuItem);
        
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(gameMenu);
        
        final JPanel controlPanel = new JPanel();
        final JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(null); // TODO replace null with method reference that resets the game
        controlPanel.add(resetButton);
        
        final JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        gameBoardPanel.setLayout(new GridLayout(HEIGHT, WIDTH));
        IntStream.range(0, HEIGHT).forEach(y ->
            IntStream.range(0, WIDTH).forEach(x -> {
                // This code loops through the X and Y indexes,
                // creating a button for each cell.
                final JButton b = new JButton();
                // When the cell button is pressed, it should:
                // 1. Initializes the mines if it is not yet initialized.
                // 2. If the button is a mine:
                //    - Change the button text to an "X"
                //    - Display "You Lose" in a dialog box
                //    Otherwise:
                //    - Change the button text to the number of neighboring
                //      cells are mines.
                //    - Decrement `numCellsRemaining`
                //    - If all cells are open, display "You Win" in a dialog box
                //    - Extra credit: If the number of neighboring cells is 0,
                //      automatically open all neighboring cells
                b.addActionListener(null); // TODO replace null with lambda expression
                gameBoardPanel.add(b);
            })
        );
        
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH * CELL_SIZE, (HEIGHT + 1) * CELL_SIZE + 27);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setJMenuBar(menuBar);
        frame.add(gameBoardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.PAGE_END);
        
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        final MineSweeper mineSweeper = new MineSweeper();
        SwingUtilities.invokeLater(mineSweeper::createAndShowFrame);
    }
}
