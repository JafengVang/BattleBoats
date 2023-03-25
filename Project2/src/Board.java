// Jafeng Vang (vang3397)
// CSCI 1933
// Project 2


public class Board {
    private int row;
    private int col;
    private Cell[][] cells;
    private Boat[] boats;
    private Boat[][] boatCells;
    private int[] boatSizes;
    private boolean[] boatSunk;
    private int totalShots;
    private int turns;
    private int numShips;

    public Board(int row, int col) { // constructor
        this.row = row;
        this.col = col;
        this.cells = new Cell[row][col];
        this.boatCells = new Boat[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j] = new Cell(i, j, '-'); // creates Cell object in every 2D array index
                boatCells[i][j] = new Boat(0,0,0,true, false); // creates Boat object in every 2D array index
            }
        }
        setBoatSizes();
        boats = new Boat[boatSizes.length];
        boatSunk = new boolean[boats.length];
        placeBoats();
        totalShots = 0;
        turns = 1;
        numShips = boats.length;
    }

    public int getTurns() {
        return turns;
    }

    public int getNumShips() {
        return numShips;
    }

    public int getTotalShots() {
        return totalShots;
    }

    /*
    method that sets the sizes of the boats based on the number of rows and columns
    */
    public void setBoatSizes() {
        if (row == 3 || col == 3) {
            boatSizes = new int[]{2};
        } else if (row <= 4 || col <= 4) {
            boatSizes = new int[]{2, 3};
        } else if (row <= 6 || col <= 6) {
            boatSizes = new int[]{2, 3, 3};
        } else if (row <= 8 || col <= 8) {
            boatSizes = new int[]{2, 3, 3, 4};
        } else if (row <= 10 || col <= 10) {
            boatSizes = new int[]{2, 3, 3, 4, 5};
        } else {
            boatSizes = new int[]{};
        }
    }

    /*
    method that places boats randomly on the board based on number of rows and columns
     */
    public void placeBoats() {
        boolean horizontal; // true for horizontal, false for vertical
        int maxXRange;
        int startingX;
        int maxYRange;
        int startingY;
        boolean openSpace;

        for (int i = 0; i < boatSizes.length; i++) {
            if (Math.random() < 0.5) { // randomizes direction for each boat
                horizontal = true;
            }
            else {
                horizontal = false;
            }

            if (horizontal) {
                do {
                    openSpace = true;
                    maxXRange = row - boatSizes[i]; // sets max amount of range based on the boat length
                    startingX = (int) (Math.random() * maxXRange + 1); // found online to find random number in a specific range
                    startingY = (int) (Math.random() * col);
                    for (int x = startingX; x < startingX + boatSizes[i]; x++) { // starts at a random spot until the length of the boat
                        if (cells[x][startingY].getStatus() == 'B') {
                            openSpace = false; // if there is already an existing boat in this range then start over
                            break;
                        }
                    }
                }
                while (!openSpace);
                for (int x = startingX; x < startingX + boatSizes[i]; x++) { // once random open spots are found the status of the boats are set
                    cells[x][startingY].setStatus('B');
                    boatCells[x][startingY].setBoatLength(boatSizes[i]);
                    boatCells[x][startingY].setOrientation(horizontal);
                    boatCells[x][startingY].setBoatExists(true);
                }
                boats[i] = new Boat(boatSizes[i], startingX, startingY, horizontal, true); // keeps track of each existing boat
            }
            else {
                do {
                    openSpace = true;
                    maxYRange = col - boatSizes[i]; // sets max amount of range based on the boat length
                    startingY = (int) (Math.random() * maxYRange + 1); // found online to find random number in a specific range
                    startingX = (int) (Math.random() * row);
                    for (int y = startingY; y < startingY + boatSizes[i]; y++) { // starts at a random spot until the length of the boat
                        if (cells[startingX][y].getStatus() == 'B') {
                            openSpace = false; // if there is already an existing boat in this range then start over
                            break;
                        }
                    }
                }
                while (!openSpace);
                for (int y = startingY; y < startingY + boatSizes[i]; y++) {
                    cells[startingX][y].setStatus('B');
                    boatCells[startingX][y].setBoatLength(boatSizes[i]);
                    boatCells[startingX][y].setOrientation(horizontal);
                    boatCells[startingX][y].setBoatExists(true);
                }
                boats[i] = new Boat(boatSizes[i], startingX, startingY, horizontal, true); // keeps track of each existing boat
            }
        }
    }

    /*
    method that checks if a boat is sunk or not
    @returns a boolean true if the boat is sunk and a boolean false if the boat is not sunk
     */
    public boolean isSunk() {
        for (int i = 0; i < boats.length; i++) {
            if (!boatSunk[i]) { // if a specific boat is not sunk
                boatSunk[i] = true; // assumes boat is sunk until checked if false
                if (boats[i].getOrientation()) { // if boat is horizontal
                    for (int j = boats[i].getXPosition(); j < boats[i].getXPosition() + boatSizes[i]; j++) {
                        if (cells[j][boats[i].getYPosition()].getStatus() != 'H') { // if every spot of the boat is not 'H' then the boat is not sunk
                            boatSunk[i] = false;
                        }
                    }
                }
                else { // if boat is vertical
                    for (int j = boats[i].getYPosition(); j < boats[i].getYPosition() + boatSizes[i]; j++) {
                        if (cells[boats[i].getXPosition()][j].getStatus() != 'H') { // if every spot of the boat is not 'H' then the boat is not sunk
                            boatSunk[i] = false;
                        }
                    }
                }
                if (boatSunk[i]) {
                    numShips--;

                    return true;
                }
            }
        }
        return false;
    }

    /*
    method that displays the player board state after every turn
     */
    public void display() {
        System.out.print(" ");
        for (int k = 0; k < col; k++) { // labels the columns on the board
            System.out.print("  " + k);
        }
        System.out.println();
        for (int i = 0; i < row; i++) {
            System.out.print(i + "  "); // labels the rows on the board
            for (int j = 0; j < col; j++) {
                if (cells[i][j].getStatus() != 'H') { // if spot already hit then does not hide boat
                    if (boatCells[i][j].getBoatExists()) { // hides the boats on the board as '-'
                        cells[i][j].setStatus('-');
                    }
                }
                System.out.print(cells[i][j].getStatus() + "  "); // displays player board
                if (cells[i][j].getStatus() != 'H') { // if spot already hit then does not revert boat
                    if (boatCells[i][j].getBoatExists()) { // revert boats on the board as 'B'
                        cells[i][j].setStatus('B');
                    }
                }
            }
            System.out.println(); // creates a new line for each row
        }
    }

    /*
    method that prints the fully revealed board with boats and spots that have been hit or missed
     */
    public void print() {
        System.out.print(" ");
        for (int k = 0; k < col; k++) { // labels the columns on the board
            System.out.print("  " + k);
        }
        System.out.println();
        for (int i = 0; i < row; i++) {
            System.out.print(i + "  "); // labels the rows on the board
            for (int j = 0; j < col; j++) {
                if (cells[i][j].getStatus() != 'H') { // if spot already hit then does not revert boat back to 'B'
                    if (boatCells[i][j].getBoatExists()) {
                        cells[i][j].setStatus('B'); // reverts every existing boat that was hidden as '-' back to 'B' to show where boats are
                    }
                }
                System.out.print(cells[i][j].getStatus() + "  "); // prints fully revealed board
            }
            System.out.println(); // creates new line for each row
        }
    }

    /*
    method that fires a single shot at a coordinate and changes the coordinate status based on its current status.
           It also increases the amount of turns each time it is called
    @param x is the x coordinate the user inputs
    @param y is the y coordinate the user inputs
     */
    public void fire(int x, int y) {
        if (x < 0 || x >= row || y < 0 || y >= col) { // checks to see if coordinates are in bounds
            System.out.println("Penalty! Coordinate (" + y + "," + x + ") out of bounds");
            turns++;
            System.out.println("Turn " + turns + ": skipped"); // skips next turn
            turns++; // turns increased again to display correct turn number after skipped turn
        }
        else {
            if (cells[x][y].getStatus() == 'M') {
                System.out.println("Penalty! Coordinate (" + y + "," + x + ") already fire at");
                turns++;
                System.out.println("Turn " + turns + ": skipped"); // skips next turn
                turns++; // turns increased again to display correct turn number after skipped turn
            }
            if (cells[x][y].getStatus() == '-') {
                System.out.println("Coordinate (" + y + "," + x + ") miss");
                cells[x][y].setStatus('M');
                totalShots++;
                turns++;
            }
            if (cells[x][y].getStatus() == 'H') {
                System.out.println("Penalty! Coordinate (" + y + "," + x + ") already hit");
                turns++;
                System.out.println("Turn " + turns + ": skipped"); // skips next turn
                turns++; // turns increased again to display correct turn number after skipped turn
            }
            else if (cells[x][y].getStatus() == 'B' || boatCells[x][y].getBoatExists()) { // second part of boolean is used when board is hidden in non debug mode
                System.out.println("Coordinate (" + y + "," + x + ") hit!");
                cells[x][y].setStatus('H');
                if (isSunk()) { // checks if boat is sunk
                    System.out.println("Boat Sunk");
                    System.out.println("Boats remaining: " + numShips);
                }
                totalShots++;
                turns++;
            }
        }
    }

    /*
    method is a helper function to set the status of boats in the missile function
    @param i is the x coordinate from the missile function
    @param j is the y coordinate from the missile function
     */
    public void missileHelper(int i, int j) {
        if (cells[i][j].getStatus() == 'M') {
            System.out.println("Coordinate ("+j+","+i+") already fired at");
        }
        if (cells[i][j].getStatus() == '-') {
            System.out.println("Coordinate ("+j+","+i+") miss");
            cells[i][j].setStatus('M');
        }
        if (cells[i][j].getStatus() == 'H') {
            System.out.println("Coordinate ("+j+","+i+") already hit");
        }
        if (cells[i][j].getStatus() == 'B') {
            System.out.println("Coordinate ("+j+","+i+") hit!");
            cells[i][j].setStatus('H');
            if (isSunk()) { // checks if boat is sunk
                System.out.println("Boat Sunk");
                System.out.println("Boats remaining: " + numShips);
            }
        }
    }

    /*
    method that fires a 3x3 missile that changes the coordinate status based on its current status.
           It also increases the amount of turns each time it is called
    @param x is the x coordinate the user inputs
    @param y is the y coordinate the user inputs
     */
    public void missile(int x, int y) {
        // each case is unique because it starts iterating at a spot depending on the position on the board
        if (x == 0 && y == 0) {// case for top left corner
            for (int i = x; i < x + 2; i++) {
                for (int j = y; j < y + 2; j++) {
                    missileHelper(i,j);
                }
            }
            totalShots++;
            turns++;
        }
        if (x == row-1 && y == col-1) { //case for bottom right corner
            for (int i = x-1; i < x+1; i++) {
                for (int j = y-1; j < y+1; j++) {
                    missileHelper(i,j);
                }
            }
            totalShots++;
            turns++;
        }
        if (y == col - 1 && (0<x && x<row-1)) { // case for any point along right border
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 1; j++) {
                    missileHelper(i, j);
                }
            }
            totalShots++;
            turns++;
        }
        if (x == row-1 && (0<y && y<col-1)) { // case for any point along bottom border
            for (int i = x - 1; i < x + 1; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    missileHelper(i, j);
                }
            }
            totalShots++;
            turns++;
        }
        if (x == 0 && y == col-1) { // case for top right corner
            for (int i = x; i < x + 2; i++) {
                for (int j = y - 1; j < y + 1; j++) {
                    missileHelper(i, j);
                }
            }
            totalShots++;
            turns++;
        }
        if (x == 0 && (0 < y && y < col-1)) { // case for any point along top border
            for (int i = x; i < x + 2; i++) {
                for (int j = y-1; j < y+2; j++) {
                    missileHelper(i,j);
                }
            }
            totalShots++;
            turns++;
        }
        if (y == 0 && x == row-1) { // case for bottom left corner
            for (int i = x - 1; i < x + 1; i++) {
                for (int j = y; j < y + 2; j++) {
                    missileHelper(i, j);
                }
            }
            totalShots++;
            turns++;
        }
        if (y == 0 && (0 < x && x < row-1)) { // case for any point along left border
            for (int i = x-1; i < x + 2; i++) {
                for (int j = y; j < y+2; j++) {
                    missileHelper(i,j);
                }
            }
            totalShots++;
            turns++;
        }
        if ((0<x && x < row-1) && (0<y && y<col-1)) { // for open 3x3 space
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    missileHelper(i,j);
                }
            }
            totalShots++;
            turns++;
        }
    }

    /*
    method that drone scans a row or column for the number of targets in it.
           It also increases the amount of turns each time it is called
    @param is either a row or column that the user inputs
    @param is the row or column number that the user inputs
    @returns the number of targets found in the row or column
     */
    public int drone(String direction, int num) {
        int targets = 0;
        if (direction.equals("r")) {
            for (int i = 0; i < col; i++) { // goes through every index on the row and checks for boats
                    if (cells[num][i].getStatus() == 'B') {
                        targets++;
                    }
            }
        }
        else {
            for (int i = 0; i < row; i++) { // goes through every index on the column and checks for boats
                if (cells[i][num].getStatus() == 'B') {
                    targets++;
                }
            }
        }
        turns++;
        return targets;
    }

    /*
    method that scans and hits a coordinate and checks if a boat is present. If a boat is present then the method
           tells the user the boat's orientation and size. It also increases the amount of turns each time it is called
    @param x is the x coordinate the user inputs
    @param y is the y coordinate the user inputs
     */
    public void scanner(int x, int y) {
        if (x < 0 || x >= row || y < 0 || y >= col) { // checks to see if coordinates are in bounds
            System.out.println("Penalty! Coordinate (" + y + "," + x + ") out of bounds");
            turns++;
            System.out.println("Turn " + turns + ": skipped"); // skips next turn
            turns++; // turns increased again to display correct turn number after skipped turn
        }
        else {
            if (cells[x][y].getStatus() == 'M') {
                System.out.println("Penalty! Coordinate (" + y + "," + x + ") already fire at");
                turns++;
                System.out.println("Turn " + turns + ": skipped"); // skips next turn
                turns++; // turns increased again to display correct turn number after skipped turn
            }
            if (cells[x][y].getStatus() == '-') {
                System.out.println("Coordinate (" + y + "," + x + ") miss");
                cells[x][y].setStatus('M');
                totalShots++;
                turns++;
            }
            if (cells[x][y].getStatus() == 'H') {
                System.out.println("Coordinate (" + y + "," + x + ") already hit");
                if (boatCells[x][y].getOrientation()) { // if orientation is vertical
                    System.out.println("Boat is vertical and size " + boatCells[x][y].getBoatLength());
                } else { // if orientation is horizontal
                    System.out.println("Boat is horizontal and size " + boatCells[x][y].getBoatLength());
                }
                totalShots++;
                turns++;
            }
            if (cells[x][y].getStatus() == 'B') {
                System.out.println("Coordinate (" + y + "," + x + ") hit!");
                cells[x][y].setStatus('H');
                if (isSunk()) { // checks if boat is sunk
                    System.out.println("Boat Sunk");
                    System.out.println("Boats remaining: " + numShips);
                } else {
                    if (boatCells[x][y].getOrientation()) { // if orientation is vertical
                        System.out.println("Boat is vertical and size " + boatCells[x][y].getBoatLength());
                    } else { // if orientation is horizontal
                        System.out.println("Boat is horizontal and size " + boatCells[x][y].getBoatLength());
                    }
                }
                totalShots++;
                turns++;
            }
        }
    }
} // Board
