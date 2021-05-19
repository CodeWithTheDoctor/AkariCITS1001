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
    private int cell_width;
    private Font gridFont;
    private Font outputFont;

    private Font buttonFont;
    private Font winFont;
    private Font consoleFont;

    /**
     * Constructor for objects of class AkariViewer.
     * Sets all fields and displays the initial puzzle.
     */
    public AkariViewer(Akari puzzle)
    {
        // TODO 10 - COMPLETED (might need to change canvas size to scale)
        this.puzzle = puzzle;
        this.canvasSize = 520;
        this.cell_width = this.canvasSize/puzzle.getSize();
        
        // init fonts for displayPuzzle method
        gridFont = new Font("Courier", Font.PLAIN, 30);
        outputFont = new Font("Courier", Font.PLAIN, 20);
        buttonFont = new Font("Courier",Font.PLAIN, 20);
        consoleFont = new Font("Courier", Font.PLAIN, 17);
        winFont = new Font("Courier",Font.PLAIN, 40);
        
        sc = new SimpleCanvas("Akari Game", canvasSize, canvasSize + 125, Color.white);
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
        Color strColor;
        
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
                sc.setFont(gridFont);
                if (charDisp != "") {sc.drawString(charDisp, c * cell_width + (cell_width/2), r * cell_width + (cell_width/2), strColor);}
            }
        }
        
        // draw grid
        for (int i = 0; i <= puzzle.getSize(); i++) {
            sc.drawLine(0, i * cell_width,puzzle.getSize() * cell_width, i * cell_width, Color.BLACK);
            sc.drawLine(i * cell_width, 0, i * cell_width, puzzle.getSize() * cell_width, Color.BLACK);
        }
        
        //Drawing the U.I beneath the grid.
        sc.drawRectangle(0,this.canvasSize,this.canvasSize,this.canvasSize+150,new Color(204, 255, 204));
        sc.drawRectangle(10,this.canvasSize+30,312,this.canvasSize+100,Color.WHITE);
        //sc.drawString("Console:",10,this.canvasSize+30, Color.BLACK);
        
        //the Clear and Solved? button
        sc.drawRectangle(330, this.canvasSize+30, this.canvasSize - 10, this.canvasSize+60,new Color(240, 240, 245));
        sc.drawRectangle(330, this.canvasSize+70, this.canvasSize - 10, this.canvasSize+100,new Color(240, 240, 245));
        sc.setFont(buttonFont);
        sc.drawString("CLEAR",390,this.canvasSize+50, Color.RED);
        sc.drawString("SOLVED?",380,this.canvasSize+90, Color.BLACK);

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
                
            }
        } else {
            // do some cool visual thing when puzzle is solved
            System.out.println("COMPLETE");
        } 
        if(e.getX() >= 300 && e.getX() <=  this.canvasSize - 10 && e.getY() >= this.canvasSize+30 && e.getY() <= this.canvasSize+60) {
            clearBoard();
            displayPuzzle();
        }
        if(e.getX() >= 300 && e.getX() <=  this.canvasSize - 10 && e.getY() >= this.canvasSize+70 && e.getY() <= this.canvasSize+100) {
            if(puzzle.isSolution() == "\u2713\u2713\u2713"){
                sc.setFont(winFont);
                sc.drawString(puzzle.isSolution(),30, this.canvasSize+65,Color.GREEN);
            }
            else {
                sc.setFont(consoleFont);
                sc.drawString(puzzle.isSolution(),20, this.canvasSize+50,Color.RED);
            }
        }
    }

    public void mouseClicked (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
    
    // helper methods
    private void clearBoard() {
        this.puzzle.clear();
    }
}
