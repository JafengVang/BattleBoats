// Jafeng Vang (vang3397)
// CSCI 1933
// Project 2


public class Boat {
    private int boatLength;
    private boolean orientation; // true for horizontal, false for vertical
    private boolean boatExists; // true if boat object exists
    private Cell[] board;
    private int xPosition;
    private int yPosition;


    public Boat(int boatLength, int x, int y, boolean orientation, boolean boatExists) { // constructor
        this.boatLength = boatLength;
        xPosition = x;
        yPosition = y;
        this.orientation = orientation;
        this.boatExists = boatExists;
    }

    public void setBoatLength(int boatLength) {
        this.boatLength = boatLength;
    }

    public int getBoatLength() {
        return boatLength;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public boolean getOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }
    public void setBoatExists(boolean boatExists) {
        this.boatExists = boatExists;
    }

    public boolean getBoatExists() {
        return boatExists;
    }
}



