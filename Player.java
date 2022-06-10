import java.util.Scanner;
import java.util.ArrayList;
public class Player
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in); //Scanner object for user input
        
        //Lines 9-16 provides instructions for the user.
        System.out.println("Welcome to Minesweeper!");
        System.out.println("In this variation, flags are represented by '#', and bombs are represented by '*'.");
        System.out.println("To play, enter the coordinate positive of the tile, and the action, either 'c' for check,");
        System.out.println("or 'f' for flag. (You can unflag a tile by flagging it again.)"); 
        System.out.println("You do this command as the following: 'x y a', where x is the x coordinate,");
        System.out.println("y is the y coordinate, and a is the action.");
        System.out.println("\nPlease enter the length and height of the board:");
        
        //Handles the "length" variable from the user. 
        //Precondition to check: Length must be a non-zero, positive integer.
        System.out.print("Length: ");
        String userInput = input.nextLine().trim(); 
        while(!containsOnlyPositiveNumbers(userInput) || userInput.length() == 0 || userInput.equals("0")) //Prevents problematic inputs from causing the program to throw an error.
        {
            System.out.println("Error: Make sure your input only contains numbers. Try again.");
            System.out.print("Length: ");
            userInput = input.nextLine().trim();
        }   
        int length = Integer.parseInt(userInput); //Sets length after ensuring that input satisfies the "Non-zero positive integer" precondition
        
        //Handles the "height" variable from the user. 
        //Precondition to check: Height must be a non-zero, positive integer.
        System.out.print("Height: ");
        userInput = input.nextLine().trim();
        while(!containsOnlyPositiveNumbers(userInput) || userInput.length() == 0 || userInput.equals("0")) //Prevents problematic inputs from causing the program to throw an error.
        {
            System.out.println("Error: Make sure your input only contains numbers. Try again.");
            System.out.print("Height: ");
            userInput = input.nextLine().trim();
        }
        int height = Integer.parseInt(userInput); //Sets height after ensuring that input satisifes the "Non-zero positive integer" precondition
        
        System.out.println("Now, enter your difficulty. Enter 1 for an easy difficulty (1/8 tiles are mines), or 2 for a hard difficulty (1/4 tiles are mines)"); //Allows user to choose between two difficulty settings
        userInput = input.nextLine().trim();
        //Ensures that the difficulty entered is either "1" or "2" to prevent errors.
        while(!userInput.equals("1") && !userInput.equals("2"))
        {
            System.out.println("Error. Try again.");
            userInput = input.nextLine().trim();
        }
        int difficulty = Integer.parseInt(userInput);
        
        //The only difference between Board and VisibleBoard is that the headers and dividers in VisibleBoard are spaces and are thus not visible
        //A separate class was made to avoid an annoying amount of logic statements.
        
        //VisibleBoard game = new VisibleBoard(length, height, difficulty); //Board without the grids (may improve readiblity)
        Board game = new Board(length, height, difficulty); //Comment this out if you use the "VisibleBoard" constructor instead
        
        game.printBoard(); //Prints the generated board.
        
        
        while(!game.mineDiscovered() && !game.onlyMinesUndiscovered()) //While game-end conditions haven't been met (all mines haven't been discovered, or not only mines are undiscovered)
        {
            System.out.print("Enter your move: ");
            userInput = input.nextLine().trim();
            
            if(userInput.toUpperCase().equals("EXIT")) //allows user to exit the game.
            {
                System.out.println("Qutting game...");
                System.exit(0);
            } 
            
            String[] actionHandler = userInput.split(" "); //Splits user input into 3 sections
            while( 
                actionHandler.length != 3 ||
                !(actionHandler[2].toUpperCase().equals("C") || actionHandler[2].toUpperCase().equals("F")) ||
                !containsOnlyPositiveNumbers(actionHandler[0]) ||
                !containsOnlyPositiveNumbers(actionHandler[1])) //Prevents input not in "x y c" order from being passed into the code
            {
                System.out.println("Error: Invalid action command. Please try again.");
                System.out.print("Enter your move: ");
                userInput = input.nextLine().trim();
                if(userInput.toUpperCase().equals("EXIT"))
                {
                    System.out.println("Qutting game...");
                    System.exit(0);
                }
                actionHandler = userInput.split(" ");
            }
            int x = -1;
            x = Integer.parseInt(actionHandler[0]); //Sets x coordinate
            int y = -1;
            y = Integer.parseInt(actionHandler[1]); //Sets y coordinate
            
            if(game.isValidPosition(y, x)) //Checks if the x and y coordinate return a valid position
            {
                game.input(x, y, actionHandler[2]); //Inputs the proper command for the game
                game.printBoard(); //Prints the updated board
            } else {
                System.out.println("Error: Your position does not exist. Try again.");
            }
        } 
        //Game ends here
        if(game.mineDiscovered()) //If a mine has been discovered (loss)
        {
            System.out.println("You checked a tile that had a mine on it. Game Over.");
            System.out.println("The solved minefield looked like this: ");
            game.solveBoard();
            game.printBoard();
        } else if(game.onlyMinesUndiscovered()){ //if the only remaining undiscovered tiles are mines
            System.out.println("You cleared the minefield without checking a mine! Good job."); //Game complete
        }
    }

    public static boolean containsOnlyPositiveNumbers(String in) //Returns a boolean representing whether or not a string only contains positive integers.
    {
        String nums = "0123456789";
        String trimmed = in.trim();
        for(int i = 0; i < trimmed.length(); i++)
        {
            String character = trimmed.substring(i, i+1);
            if(!nums.contains(character))
            {
                return false;
            }
        }
        return true;
    }
    
}