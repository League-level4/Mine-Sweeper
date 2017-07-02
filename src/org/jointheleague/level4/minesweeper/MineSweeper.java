package org.jointheleague.level4.minesweeper;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
    
    final JFrame frame = new JFrame();
    private final JButton[][] buttons = new JButton[HEIGHT][WIDTH];
    private final Random rng = new Random();
    private Optional<boolean[][]> mines = Optional.empty();
    private int numCellsRemaining;
    
    /**
     * Initializes:
     * 1. The `mines` variable with `NUM_MINES` randomly distributed mines
     * 2. The `numCellsRemaining` variable to the number of non-mine
     *    cells (`WIDTH` * `HEIGHT` - `NUM_MINES`).
     * 
     * @param firstCellX X-index of first cell opened. This cannot be a mine.
     * @param firstCellY Y-index of first cell opened. This cannot be a mine.
     */
    private void initializeMines(int firstCellX, int firstCellY) {
        boolean[][] mines = new boolean[HEIGHT][WIDTH];
        
        Stream.
            generate(
                () -> new Point(rng.nextInt(WIDTH), rng.nextInt(HEIGHT))
            ).
            filter(
                pt -> pt.x != firstCellX && pt.y != firstCellY
            ).
            distinct().
            limit(NUM_MINES).
            forEach(
                pt -> mines[pt.y][pt.x] = true
            );
        
        this.mines = Optional.of(mines);
        this.numCellsRemaining = HEIGHT * WIDTH - NUM_MINES;
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
        return mines.
            map(mines ->
                IntStream.range(max(0, y-1), min(HEIGHT, y+2)).flatMap(neighborY ->
                    IntStream.range(max(0, x-1), min(WIDTH, x+2)).map(neighborX ->
                        !(neighborX == x && neighborY == y) && mines[neighborY][neighborX] ? 1 : 0
                    )
                ).
                reduce(0, (left, right) -> left + right)
            ).
            orElse(0);
    }
    
    /**
     * Resets the game by:
     * 1. Setting the `mines` variable back to empty.
     * 2. Clearing the button texts, and setting button states to enabled.
     * 
     * @param unused Just here so that method can be passed as ActionListener.
     */
    private void resetGame(Object unused) {
        mines = Optional.empty();
        IntStream.range(0, HEIGHT).forEach(y ->
            IntStream.range(0, WIDTH).forEach(x -> {
                buttons[y][x].setText(null);
                buttons[y][x].setEnabled(true);
            })
        );
        frame.repaint();
    }
    
    private void createAndShowFrame() {
        final JMenuItem resetMenuItem = new JMenuItem("Reset");
        resetMenuItem.addActionListener(this::resetGame);
        
        final JMenu gameMenu = new JMenu("Game");
        gameMenu.add(resetMenuItem);
        
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(gameMenu);
        
        final JPanel controlPanel = new JPanel();
        final JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(this::resetGame);
        controlPanel.add(resetButton);
        
        final JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        gameBoardPanel.setBackground(Color.WHITE);
        gameBoardPanel.setLayout(new GridLayout(HEIGHT, WIDTH));
        IntStream.range(0, HEIGHT).forEach(y ->
            IntStream.range(0, WIDTH).forEach(x -> {
                // This code loops through the X and Y indexes,
                // creating a button for each cell.
                final JButton b = new JButton();
                buttons[y][x] = b;
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
                b.addActionListener(
                    e -> {
                        if (!mines.isPresent()) initializeMines(x, y);
                        b.setEnabled(false);
                        if (mines.get()[y][x]) {
                            b.setText("X");
                            JOptionPane.showMessageDialog(null, "You Lose!");
                        } else {
                            int neighboringMinesCount = getNeighboringMinesCount(x, y);
                            if (neighboringMinesCount > 0) {
                                b.setText(Integer.toString(neighboringMinesCount));
                            } else {
                                IntStream.range(max(0, y-1), min(HEIGHT, y+2)).forEach(neighborY ->
                                    IntStream.range(max(0, x-1), min(WIDTH, x+2)).forEach(neighborX ->
                                        buttons[neighborY][neighborX].doClick(0)
                                    )
                                );
                                frame.repaint();
                            }
                            
                            numCellsRemaining --;
                            if (numCellsRemaining == 0) {
                                JOptionPane.showMessageDialog(null, "You Win!");
                            }
                        }
                    }
                );
                gameBoardPanel.add(b);
            })
        );
        
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
