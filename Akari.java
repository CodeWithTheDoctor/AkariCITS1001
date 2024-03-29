/**
 * Peralta Garcia, Sean - 23088091
 * Ithape, Ashish (Ash) - 23066342
 * Akari represents a single puzzle of the game Akari.
 *
 * @author Lyndon While 
 * @version 2021
 */
import java.util.ArrayList;
import java.util.Arrays; 

public class Akari
{
    private String filename; // the name of the puzzle file
    private int size;        // the board is size x size
    private Space[][] board; // the board is a square grid of spaces of various types

    /**
     * Constructor for objects of class Akari. 
     * Creates and initialises the fields with the contents of filename. 
     * The layout of a puzzle file is described on the LMS page; 
     * you may assume the layout is always correct. 
     */
    public Akari(String filename)
    {
        // TODO 3 - COMPLETED
       FileIO file = new FileIO(filename);
       this.filename = filename;
       this.size = Integer.parseInt(file.getLines().get(0));
       this.board = new Space[this.size][this.size];

       for(int i = 0; i<this.size; i++) {
           for(int j=0; j<this.size; j++) {
               board[i][j] = Space.EMPTY;
            }
       } 

       for (int i = 1; i <= 6; i++) {
           String[] a = file.getLines().get(i).split(" ");
           for (String x: a) {
               for (int z = 1; z < x.length(); z++) {
                   board[parseIndex(x.charAt(0))][parseIndex(x.charAt(z))] = Space.values()[i - 1];
               }
           }
       }
    }
    
    /**
     * Uses the example file from the LMS page.
     */
    public Akari()
    {
        this("Puzzles/p25-e1.txt");
    }
    
    /**
     * Returns the name of the puzzle file.
     */
    public String getFilename()
    {
        // TODO 1a - COMPLETED
        return filename;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 1b - COMPLETED
        return size;
    }
    
    /**
     * Returns true iff k is a legal index into the board. 
     */
    public boolean isLegal(int k)
    {
        // TODO 5 - COMPLETED
        if(k>=0 && k<size)  
            return true;
        else 
            return false;
    }
    
    /**
     * Returns true iff r and c are both legal indices into the board. 
     */
    public boolean isLegal(int r, int c)
    {
        // TODO 6 - COMPLETED
        if(r>=0 && r<size && c>=0 && c<size)
            return true;
        else
            return false;
    }
    
    /**
     * Returns the contents of the square at r,c if the indices are legal, 
     * o/w throws an illegal argument exception. 
     */
    public Space getBoard(int r, int c)
    {
        // TODO 7 - COMPLETED
        if(isLegal(r,c)) {
            return board[r][c];
        }
        else {
            throw new IllegalArgumentException("Coordinates aren't within the boundaries of the board.");
        }
            
    }
    
    /**
     * Returns the int equivalent of x. 
     * If x is a digit, returns 0 -> 0, 1 -> 1, etc; 
     * if x is an upper-case letter, returns A -> 10, B -> 11, etc; 
     * o/w throws an illegal argument exception. 
     */
    public static int parseIndex(char x)
    {
        // TODO 2 - COMPLETED
        if(Character.isDigit(x)) {
            return x - 48;
        }
        else if(x >= 'A' && x <= 'Z') {
            return x - 55;
        }
        else {
            throw new IllegalArgumentException("Must be an uppercase letter or a digit.");
        }
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * If r,c is empty, a bulb is placed; if it has a bulb, that bulb is removed.
     */
    public void leftClick(int r, int c)
    {
        // TODO 8 - COMPLETED
        if(isLegal(r,c)) {
            if(board[r][c] == Space.EMPTY) {
                board[r][c] = Space.BULB;
            }
            else if(board[r][c] == Space.BULB) {
                board[r][c] = Space.EMPTY;
            }
        }
    }
    
    /**
     * Sets all mutable squares on the board to empty.
     */
    public void clear()
    {
        // TODO 4 - COMPLETED
        for(int i = 0; i<this.size; i++)
            for(int j=0; j<this.size; j++)
                if(board[i][j] == Space.BULB) board[i][j] = Space.EMPTY;
    }
    
    /**
     * Returns the number of bulbs adjacent to the square at r,c. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public int numberOfBulbs(int r, int c)
    {
        // TODO 14 - COMPLETE
        int numBulbs = 0;
        if(!isLegal(r,c)) {
            throw new IllegalArgumentException("Coordinates entered for numberOfBulbs were not in the grid. Please enter valid coordinates.");
        }
        if(isLegal(r-1,c)) {
            if(board[r-1][c] == Space.BULB){
                numBulbs++;
            }
        }
        if(isLegal(r+1,c)) {
            if(board[r+1][c] == Space.BULB){
                numBulbs++;
            }
        }
        if(isLegal(r,c-1)) {
            if(board[r][c-1] == Space.BULB){
                numBulbs++;
            }
        }
        if(isLegal(r,c+1)) {
            if(board[r][c+1] == Space.BULB){
                numBulbs++;
            }
        }
        return numBulbs;
    }
    
    /**
     * Returns true iff the square at r,c is lit by a bulb elsewhere. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public boolean canSeeBulb(int r, int c)
    {
        // TODO 15 - COMPLETED
        if (!isLegal(r, c)) {throw new IllegalArgumentException("This square does not exist on the grid.");}
        int lookLeft = 0;
        int lookRight = 0;
        int lookUp = 0;
        int lookDown = 0;
        
        // init flags
        boolean leftStop = false;
        boolean rightStop = false;
        boolean upStop = false;
        boolean downStop = false;
        
        // return true inside of loop when       
        do {
            //if (board[r][c] != Space.BULB && board[r][c] != Space.EMPTY) {break;}
            
            if (leftStop == false) {
                lookLeft--;
                // check if square is legal, else square does not exist on grid
                if (isLegal(r + lookLeft, c)) {
                    // check if space is bulb, black or empty
                    if (board[r + lookLeft][c] == Space.BULB) {return true;}
                    else {
                        if (board[r + lookLeft][c] == Space.EMPTY) {}
                        else {leftStop = true;}
                    }
                } else {leftStop = true;}
            }
    
            if (rightStop == false) {
                lookRight++;
                if (isLegal(r + lookRight, c)) {
                    if (board[r + lookRight][c] == Space.BULB) {return true;}
                    else {
                        if (board[r + lookRight][c] == Space.EMPTY) {}
                        else {rightStop = true;}
                    }
                } else {rightStop = true;}
            }
            
            if (upStop == false) {
                lookUp--;
                if (isLegal(r, c + lookUp)) {
                    if (board[r][c + lookUp] == Space.BULB) {return true;}
                    else {
                        if (board[r][c + lookUp] == Space.EMPTY) {}
                        else {upStop = true;}
                    }
                } else {upStop = true;}
            }

            if (downStop == false) {
                lookDown++;
                if (isLegal(r, c + lookDown)) {
                    if (board[r][c + lookDown] == Space.BULB) {return true;}
                    else {
                        if (board[r][c + lookDown] == Space.EMPTY) {}
                        else {downStop = true;}
                    }
                } else {downStop = true;} 
            }
        } while (!leftStop || !rightStop || !upStop || !downStop);        
        return false;
    }
    
    /**
     * Returns an assessment of the state of the puzzle, either 
     * "Clashing bulb at r,c", 
     * "Unlit square at r,c", 
     * "Broken number at r,c", or
     * three ticks, as on the LMS page. 
     * r,c must be the coordinates of a square that has that kind of error. 
     * If there are multiple errors on the board, you may return any one of them. 
     */
    public String isSolution()
    {
        // TODO 16 - COMPLETE
        ArrayList<String> unlitBulbs = new ArrayList<>();
        ArrayList<String> clashingBulbs = new ArrayList<>();
        ArrayList<String> wrongBulbs = new ArrayList<>();
        
        for(int r = 0; r<size; r++) {
            for(int c = 0; c<size; c++) {
                if(board[r][c] == Space.EMPTY) {
                    if(!canSeeBulb(r,c)){
                        unlitBulbs.add(Integer.toString(r) + "," + Integer.toString(c));
                    }
                }
                if(board[r][c] == Space.BULB) {
                    if(canSeeBulb(r,c)) {
                        clashingBulbs.add(Integer.toString(r) + "," + Integer.toString(c));
                    }
                }
                if(board[r][c] == Space.ONE) {
                    if(!(numberOfBulbs(r,c) == 1)){
                        wrongBulbs.add(Integer.toString(r) + "," + Integer.toString(c));
                    }
                }
                if(board[r][c] == Space.TWO) {
                    if(!(numberOfBulbs(r,c) == 2)){
                        wrongBulbs.add(Integer.toString(r) + "," + Integer.toString(c));
                    }
                }
                if(board[r][c] == Space.THREE) {
                    if(!(numberOfBulbs(r,c) == 3)){
                        wrongBulbs.add(Integer.toString(r) + "," + Integer.toString(c));
                    }
                }
                if(board[r][c] == Space.FOUR) {
                    if(!(numberOfBulbs(r,c) == 4)){
                        wrongBulbs.add(Integer.toString(r) + "," + Integer.toString(c));
                    }
                }
            }
        }

        if(unlitBulbs.isEmpty() && clashingBulbs.isEmpty() && wrongBulbs.isEmpty()) {
            return "\u2713\u2713\u2713";
        }
        else{
            String message = "";
            if(!unlitBulbs.isEmpty()){
                message = "Unlit square at ";
                for(String a: unlitBulbs) {
                    if(message == "Unlit square at ") {message += a;}
                    else {message += "\n and other places."; break;}
                }
            }
            else if(!clashingBulbs.isEmpty()){
                message = "Clashing bulb at ";
                for(String a: clashingBulbs) {
                    if(message == "Clashing bulb at ") {message += a;}
                    else {message += "\n and other places."; break;}
                }
            }
            else if(!wrongBulbs.isEmpty()) {
                message = "Broken number at ";
                for(String a: wrongBulbs) {
                    if(message == "Broken number at ") {message += a;}
                    else {message += "\n and other places."; break;}
                }
            }
            return message;
        }
    }
}
