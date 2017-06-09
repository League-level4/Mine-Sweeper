package org.jointheleague.level4.minesweeper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MineSweeper {
    static final int WIDTH = 10;
    static final int HEIGHT = 15;
    static final int NUM_MINES = 15;
    
    final JFrame frame;
    Optional<boolean[][]> mines = Optional.empty();
    
    public MineSweeper() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH * 20, HEIGHT * 20);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(HEIGHT, WIDTH));
        
        for (int y = 0; y < HEIGHT; ++ y) {
            for (int x = 0; x < WIDTH; ++ x) {
                final JButton b = new JButton();
                // Uncomment the following line, adding a listener that:
                // 1. Initializes the mines if necessary.
                // 2. Display either the mine if there is one, or the number of neighboring cells contain mines.
                // Use a lambda here.
                // b.addActionListener(...);
                // Uncomment the following line, add a listener that displays a dialog box if mines have been swept.
                // Use a method handle here.
                // b.addActionListener(...);
                frame.add(b);
            }
        }
        
        frame.setVisible(true);
    }
    
    public void initializeMines(int firstCellX, int firstCellY) {
        // TODO initialize the mines variable with NUM_MINES randomly distributed mines.
    }
    
    public int getNeighboringMinesCount(int x, int y) {
        // TODO for a given cell, count the number of neighboring cells that are mines.
        throw new UnsupportedOperationException("pending");
    }
    
    public void showDialogIfAllMinesSwept(ActionEvent evt) {
        // TODO check and see if all non-mine cells is open, and if so, display a dialog to indicate that mines have been swept
    }
    
    public static void main(String[] args) {
        new MineSweeper();
    }
}
