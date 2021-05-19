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
    private int sidebarSize;
    private int cell_width;
    private Font gridFont, outputFont;
    private int buttonLen, buttonHei;
    /**
     * Constructor for objects of class AkariViewer.
     * Sets all fields and displays the initial puzzle.
     */
    public AkariViewer(Akari puzzle)
    {
        // TODO 10 - COMPLETED (might need to change canvas size to scale)
        this.puzzle = puzzle;
        this.canvasSize = 520;
        this.sidebarSize = 125;
        this.cell_width = this.canvasSize/puzzle.getSize();
        
        // init fonts for displayPuzzle method
        gridFont = new Font("Courier", Font.PLAIN, 30);
        outputFont = new Font("Courier", Font.PLAIN, 20);
        
        sc = new SimpleCanvas("Akari Game", canvasSize, canvasSize + sidebarSize, Color.white);
        sc.addMouseListener(this);
        
        displayButtons();
        
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
        Color strColor;
        sc.setFont(gridFont);
        
        for (int r = 0; r < puzzle.getSize(); r++) {
            for (int c = 0; c < puzzle.getSize(); c++) {
                String charDisp = "";
                strColor = Color.WHITE;  
                if (puzzle.getBoard(r, c) == Space.BULB) {
                    // set char to print
                    col = Color.YELLOW;
                    charDisp = "\uD83D\uDCA1";
                    strColor = Color.BLACK;
                } else {
                    if (puzzle.getBoard(r, c) == Space.EMPTY) {
                        col = Color.WHITE;
                        if (puzzle.canSeeBulb(r, c)) {
                            col = Color.YELLOW ;
                        }
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
                if (charDisp != "") {sc.drawString(charDisp, c * cell_width + (cell_width/2), r * cell_width + (cell_width/2), strColor);}
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
        //call finished method on puzzle -> returns boolean
        boolean complete = puzzle.finished();
        if (!complete) {
            if (puzzle.isLegal((e.getY() / this.cell_width), (e.getX() / (this.cell_width)))) {
                this.leftClick((e.getY() / this.cell_width), (e.getX() / (this.cell_width)));
            // use this for button presses
            } else {
                if (e.getX() >= cell_width/2 & e.getX() <= cell_width/2 + buttonLen &
                    e.getY() <= canvasSize + (sidebarSize/10) + buttonHei & e.getY() >= canvasSize + + (sidebarSize/10)) {
                    this.clearBoard();
                }
            }
        } else {
            // do some cool visual thing when puzzle is solved
            System.out.println("COMPLETE");
        } 
    }

    public void mouseClicked (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
    
    // helper methods
    private void clearBoard() {
        puzzle.clear();
        displayPuzzle();
    }
    
    private void checkBoard() {
        
    }
    
    private void displayButtons() {
        int buttonLen = cell_width * 2;
        int buttonHei = cell_width / 2;
        
        sc.setFont(outputFont);
        
        sc.drawRectangle(cell_width/2, canvasSize + (sidebarSize/10), cell_width/2 + buttonLen, canvasSize + (sidebarSize/10) + buttonHei, Color.BLACK);
        sc.drawString("Clear", cell_width/2 + 45, canvasSize + (sidebarSize/10) + 25, Color.WHITE);
    }
}
