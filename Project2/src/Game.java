// Jafeng Vang (vang3397)
// CSCI 1933
// Project 2


import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        String[] temp;
        boolean gameRunning = true;
        String debugStatus;
        boolean debugState = false;
        int x = 0;
        int y = 0;

        System.out.println("Play Battleships!");

        Scanner boardScanner = new Scanner(System.in); // exception handling for inputs other than integer idea from Kode Java
        do {
            System.out.println("Enter the number of rows (3-10): ");
            while (!boardScanner.hasNextInt()) {
                System.out.println("Invalid input, try again");
                boardScanner.next();
            }
            row = boardScanner.nextInt();
            if (row < 3 || 10 < row) {
                System.out.println("Invalid input, try again");
            }
        }
        while (row < 3 || 10 < row);

        do {
            System.out.println("Enter the number of columns (3-10): ");
            while (!boardScanner.hasNextInt()) {
                System.out.println("Invalid input, try again");
                boardScanner.next();
            }
            col = boardScanner.nextInt();
            if (col < 3 || 10 < col) {
                System.out.println("Invalid input, try again");
            }
        }
        while (col < 3 || 10 < col);

        Board myBoard = new Board(row, col); // initializes Board object

        Scanner debugScanner = new Scanner(System.in);
        System.out.println("Do you want to run game in debug mode? (yes/no): ");
        while (debugScanner.hasNextLine()) {
            debugStatus = debugScanner.nextLine();
            if (debugStatus.equals("yes")) {
                debugState = true;
                break;
            }
            else if (debugStatus.equals("no")) {
                debugState = false;
                break;
            }
            else {
                System.out.println("Invalid input, try again");
                continue;
            }
        }

        System.out.println("Options- " + "\nattack: hits a single point" + "\nmissile: fire at 3x3 square" + "\ndrone: scans a row or column for targets" +
                "\nscanner: finds orientation and size of boat at a single point" + "\nprint: prints fully revealed board");
        System.out.println("Boats remaining: " + myBoard.getNumShips());

        while (gameRunning) { // start of main game with Scanner user inputs
            if (debugState) { // if yes for debug mode, prints full board after every turn
                myBoard.print();
            }
            else {
                myBoard.display(); // if no for debug mode, displays player board state after every turn
            }
            System.out.println("Turn " + myBoard.getTurns() + ": ");
            System.out.println("Enter option: ");
            Scanner option = new Scanner(System.in);
            String optionInput = option.nextLine();
            if (optionInput.equals("attack") || optionInput.equals("missile") || optionInput.equals("drone") ||
                    optionInput.equals("scanner") || optionInput.equals("print")) {
                switch (optionInput) { // switch statement to take in player options
                    case "print":
                        System.out.println("Fully revealed board (for debugging): ");
                        myBoard.print(); // calls print() method from Board
                        System.out.println("Player board: ");
                        break;
                    case "attack":
                        System.out.println("Enter x coordinate: ");
                        Scanner attack = new Scanner(System.in);
                        while (attack.hasNext()) {
                            while (!attack.hasNextInt()) { // if next input is not an integer, keep on repeating until valid
                                System.out.println("Invalid input, please type in an integer");
                                attack.next();
                            }
                            y = attack.nextInt();

                            System.out.println("Enter y coordinate: ");
                            while (!attack.hasNextInt()) {
                                System.out.println("Invalid input, please type in an integer");
                                attack.next();
                            }
                            x = attack.nextInt();
                            myBoard.fire(x,y); // calls fire() method from Board
                            break;
                        }
                        break;
                    case "missile":
                        System.out.println("Enter x coordinate: ");
                        Scanner missile = new Scanner(System.in);
                        while (missile.hasNext()) {
                            while (!missile.hasNextInt()) { // if next input is not an integer, keep on repeating until valid
                                System.out.println("Invalid input, please type in an integer");
                                missile.next();
                            }
                            y = missile.nextInt();

                            System.out.println("Enter y coordinate: ");
                            while (!missile.hasNextInt()) {
                                System.out.println("Invalid input, please type in an integer");
                                missile.next();
                            }
                            x = missile.nextInt();
                            if (x < 0 || x >= row || y < 0 || y >= col) { // checks if coordinates within range, prompts user to re-enter coordinates
                                System.out.println("Coordinate ("+y+","+x+") out of bounds, try again");
                                System.out.println("Enter x coordinate: ");
                                continue;
                            } else {
                                myBoard.missile(x,y); // calls missile() method from Board
                                break;
                            }
                        }
                        break;
                    case "drone":
                        int num = 0;
                        System.out.println("Would you like to drone scan a row(r) or column(c)?: ");
                        Scanner drone = new Scanner(System.in);
                        while (drone.hasNext()) {
                            while (!drone.hasNextLine()) { // if next input is not a String, keep on repeating until valid
                                System.out.println("Invalid input, please type in an integer within row length");
                                drone.next();
                            }
                            String droneOrientation = drone.nextLine();
                            if (droneOrientation.equals("r")) {
                                System.out.println("Which number row to drone scan?");
                                Scanner number = new Scanner(System.in);
                                while (number.hasNext()) {
                                    while (!number.hasNextInt()) { // if next input is not an integer, keep on repeating until valid
                                        System.out.println("Invalid input, please type in an integer within row length");
                                        number.next();
                                    }
                                    num = number.nextInt();
                                    if (num >= 0 && num < row) {
                                        int droneTargets = myBoard.drone(droneOrientation, num); // calls drone() method from Board and assigns return value to new variable
                                        System.out.println("Drone scanned " + droneTargets + " targets");
                                        break;
                                    } else {
                                        System.out.println("Invalid input, please type a number within row length");
                                        continue;
                                    }
                                }
                                break;
                            }
                            if (droneOrientation.equals("c")) { // same as previous if statement but with different wording
                                System.out.println("Which number column to drone scan?");
                                Scanner number = new Scanner(System.in);
                                while (number.hasNext()) {
                                    while (!number.hasNextInt()) {
                                        System.out.println("Invalid input, please type in an integer within column length");
                                        number.next();
                                    }
                                    num = number.nextInt();
                                    if (num >= 0 && num < col) {
                                        int droneTargets = myBoard.drone(droneOrientation, num);
                                        System.out.println("Drone scanned " + droneTargets + " targets");
                                        break;
                                    } else {
                                        System.out.println("Invalid input, please type a number within column length");
                                        continue;
                                    }
                                }
                                break;
                            }
                            else {
                                System.out.println("Invalid input, enter r for row or c for column"); // repeats question if invalid input
                                continue;
                            }
                        }
                        break;
                    case "scanner":
                        System.out.println("Enter x coordinate: ");
                        Scanner scanner = new Scanner(System.in);
                        while (scanner.hasNext()) {
                            while (!scanner.hasNextInt()) { // if next input is not an integer, keep on repeating until valid
                                System.out.println("Invalid input, please type in an integer");
                                scanner.next();
                            }
                            y = scanner.nextInt();

                            System.out.println("Enter y coordinate: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input, please type in an integer");
                                scanner.next();
                            }
                            x = scanner.nextInt();
                            myBoard.scanner(x,y); // calls scanner() method from Board
                            break;
                        }
                        break;
                }
                if (myBoard.getNumShips() == 0) {
                    myBoard.display();
                    System.out.println("All boats sunk, you win!");
                    gameRunning = false; // ends program
                }
            }
            else {
                System.out.println("Invalid option, try again"); // repeats option choice if wrong option
                continue;
            }
        }
        int finalTurns = myBoard.getTurns() - 1; // reduce turns one more time when game ends since turns is incremented after every method call
        System.out.println("Total turns: " + finalTurns);
        System.out.println("Total shots fired: " + myBoard.getTotalShots());
    } // main
} // Game

