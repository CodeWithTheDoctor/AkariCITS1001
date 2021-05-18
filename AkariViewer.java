/**
 * AkariViewer represents an interface for a player of Akari.
 *
 * @author Lyndon While
 * @version 2021
 */
import java.awt.*;
import java.awt.event.*; 
import java.util.Arrays;

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
        this.canvasSize = 500;
        
        sc = new SimpleCanvas("Akari Game", canvasSize, canvasSize + 100, Color.white);
        sc.addMouseListener(this);
        
        displayPuzzle();
    }
    
    /**
     * Selects from among the provided files in folder Puzzles. 
     * The number xyz selects pxy-ez.txt. 
     */
    public AkariViewer(int n)
    {
        this(new Akari("Puzzles/p" + n / 10 + "-e" + n % 10 + ".txt"));
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
        // to string deep
        //System.out.println()
        Color c;
        Space[] blackBlocks = new Space[] {Space.BLACK,Space.ZERO,Space.ONE,Space.TWO,Space.THREE,Space.FOUR};
        boolean isBlack = false;
        for (int i = 0; i < puzzle.getSize(); i++) {
            for (int j = 0; j < puzzle.getSize(); j++) {
                if (puzzle.getBoard(i, j) == Space.EMPTY) {
                    if(puzzle.canSeeBulb(i, j)){
                        c = Color.YELLOW;
                    }
                    else {
                        c = Color.WHITE;
                    }
                }
                else{
                    for(Space b : blackBlocks) { 
                        if(b == puzzle.getBoard(i,j)){
                            isBlack = true;
                        } 
                    }
                    if (isBlack) {c = Color.BLACK;}
                    else{ c = Color.YELLOW; }
                }
                /*sc.drawRectangle(j * this.canvasSize/puzzle.getSize(),
                                    i * this.canvasSize/puzzle.getSize(),
                                    (j+1) * this.canvasSize/puzzle.getSize(),
                                    (i + 1) * this.canvasSize/puzzle.getSize(), c);*/
                // draw text for black squares
            }
        }
        
        for(int i=0; i<=puzzle.getSize(); i++) {
            sc.drawLine(i*this.canvasSize/puzzle.getSize(),
                        0,
                        i*this.canvasSize/puzzle.getSize(),
                        puzzle.getSize()* this.canvasSize/puzzle.getSize(),
                        Color.BLACK);
        }
        for(int j=0; j<=puzzle.getSize(); j++) {
            sc.drawLine(0,
                        j*this.canvasSize/puzzle.getSize(),
                        puzzle.getSize()*this.canvasSize,
                        j*this.canvasSize/puzzle.getSize(),
                        Color.BLACK);
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
