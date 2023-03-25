// Jafeng Vang (vang3397)
// CSCI 1933
// Project 2


public class Cell {
    private int row;
    private int col;
    private char status;

     /*
    Status:
    '-' = has not guessed yet, no boat present
    'B' = has not been guessed yet, boat present
    'H' = has been guessed, boat present
    'M' = has been guessed, no boat present
     */

    public Cell(int row, int col, char status) { // constructor
        this.row = row;
        this.col = col;
        this.status = status;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char newStatus) {
        status = newStatus;
    }

} // Cell
