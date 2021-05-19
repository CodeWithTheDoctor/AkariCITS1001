/**
 * AkariViewer represents an interface for a player of Akari.
 *
 * @author Lyndon While
 * @version 2021
 */
import java.awt.*;
import java.awt.event.*; 
import java.util.Arrays;
import java.awt.Color;

public class AkariViewer implements MouseListener
{    
    private Akari puzzle;    // the internal representation of the puzzle
    private SimpleCanvas sc; // the display window
    private int canvasSize;  // the size of the display window
    /**
     * Constructor for objects of class AkariViewer.
     * Sets all fields and displays the initial puzzle.
     */
    public AkariViewer(Akari puzzle)
    {
        // TODO 10 - COMPLETED (might need to change canvas size to scale)
        this.puzzle = puzzle;
        this.canvasSize = 520;
        
        sc = new SimpleCanvas("Akari Game", canvasSize, canvasSize + 125, Color.white);
        //sc.setFont();
        sc.addMouseListener(this);
        
        displayPuzzle();
    }
    
    /**
     * Selects from among the provided files in folder Puzzles. 
     * The number xyz selects pxy-ez.txt. 
     */
    public AkariViewer(int n)
    {
        this(new Akari("Puzzles/p" + n  / 10 + "-e" + n % 10 + ".txt"));
    }
    
    /** 
     * Uses the provided example file on the LMS page.
     */
    public AkariViewer()
    {
        this(77);
    }
    
    /**
     * Returns the internal state of the puzzle.
     */
    public Akari getPuzzle()
    {
        // TODO 9a - COMPLETED
        return puzzle;
    }
    
    /**
     * Returns the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        // TODO 9b - COMPLETED
        return sc;
    }
    
    /**
     * Displays the initial puzzle; see the LMS page for a suggested layout. 
     */
    private void displayPuzzle()
    {
        // TODO 11 - In Progress (not sure how to account for lit squares)
        Color col;
        int cell_width = this.canvasSize/puzzle.getSize();
        
        for (int r = 0; r < puzzle.getSize(); r++) {
            for (int c = 0; c < puzzle.getSize(); c++) {
                String charDisp = "";
                if (puzzle.canSeeBulb(r, c)) {
                    col = Color.YELLOW;
                    if (puzzle.getBoard(r, c) == Space.BULB) {
                        // set char to print
                        charDisp = "\u1F4A1";
                    }
                } else {
                    if (puzzle.getBoard(r, c) == Space.EMPTY){
                        col = Color.WHITE;
                    // square is black in this case
                    } else {
                        col = Color.BLACK;
                        switch (puzzle.getBoard(r, c)) {
                            // set char to print
                            case BLACK:
                                break;
                            case ZERO:
                                charDisp = "0";
                                break;
                            case ONE:
                                charDisp = "1";
                                break;
                            case TWO:
                                charDisp = "2";
                                break;
                            case THREE:
                                charDisp = "3";
                                break;
                            case FOUR:
                                charDisp = "4";
                                break;
                        }
                    }
                }
                sc.drawRectangle(c * cell_width, r * cell_width, (c + 1) * cell_width, (r + 1) * cell_width, col);               
                // draw text here
                if (charDisp != "") {sc.drawString(charDisp, c * cell_width + (cell_width/2), r * cell_width + (cell_width/2), Color.WHITE);}
            }
        }
        
        // draw grid
        for (int i = 0; i <= puzzle.getSize(); i++) {
            sc.drawLine(0, i * cell_width,puzzle.getSize() * cell_width, i * cell_width, Color.BLACK);
            sc.drawLine(i * cell_width, 0, i * cell_width, puzzle.getSize() * cell_width, Color.BLACK);
        }
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * Updates both puzzle and the display. 
     */
    public void leftClick(int r, int c)
    {
        // TODO 12
        puzzle.leftClick(r, c);
        displayPuzzle();
    }
    
    // TODO 13 (only implementing basic left click functionality for now)
    // change code in mousePressed() to mouseClicked()
    public void mousePressed (MouseEvent e) {
    }

    public void mouseClicked (MouseEvent e) {
        //call finished method on puzzle -> returns boolean
        boolean complete = puzzle.finished();
        if (!complete) {
            puzzle.leftClick((e.getX() / (puzzle.getSize()/600)), (e.getX() / (puzzle.getSize()/600)));
        } else {
            // do some cool visual thing when puzzle is solved
            System.out.println("COMPLETE");
        }
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
}
