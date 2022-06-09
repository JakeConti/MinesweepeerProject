import java.util.Scanner;
import java.util.ArrayList;
public class Player
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.println("In this variation, flags are represented by '#', and bombs are represented by '*'.");
        System.out.println("To play, enter the coordinate positive of the tile, and the action, either 'c' for check,");
        System.out.println("or 'f' for flag. (You can unflag a tile by flagging it again.)"); 
        System.out.println("You do this command as the following: 'x y a', where x is the x coordinate,");
        System.out.println("y is the y coordinate, and a is the action.");
        System.out.println("\nPlease enter the length and height of the board:");
        System.out.print("Length: ");
        String temp = input.nextLine().trim();
        while(!containsOnlyPositiveNumbers(temp) || temp.length() == 0 || temp.equals("0"))
        {
            System.out.println("Error: Make sure your input only contains numbers. Try again.");
            System.out.print("Length: ");
            temp = input.nextLine().trim();
        }   
        int length = Integer.parseInt(temp);
        System.out.print("Height: ");
        temp = input.nextLine().trim();
        while(!containsOnlyPositiveNumbers(temp) || temp.length() == 0 || temp.equals("0"))
        {
            System.out.println("Error: Make sure your input only contains numbers. Try again.");
            System.out.print("Height: ");
            temp = input.nextLine().trim();
        }
        int height = Integer.parseInt(temp);
        System.out.println("Now, enter your difficulty. Enter 1 for an easy difficulty (1/8 tiles are mines), or 2 for a hard difficulty (1/4 tiles are mines)");
        temp = input.nextLine().trim();
        while(!temp.equals("1") && !temp.equals("2"))
        {
            System.out.println("Error. Try again.");
            temp = input.nextLine().trim();
        }
        int difficulty = Integer.parseInt(temp);
        
        Board game = new Board(length, height, difficulty);
        game.printBoard();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        while(!game.mineDiscovered() && !game.onlyMinesUndiscovered())
        {
            System.out.print("Enter your move: ");
            temp = input.nextLine().trim();
            if(temp.toUpperCase().equals("EXIT"))
            {
                System.out.println("Qutting game...");
                System.exit(0);
            } 
            String[] actionHandler = temp.split(" ");
            while(
                actionHandler.length != 3 ||
                !(actionHandler[2].toUpperCase().equals("C") || actionHandler[2].toUpperCase().equals("F")) ||
                !containsOnlyPositiveNumbers(actionHandler[0]) ||
                !containsOnlyPositiveNumbers(actionHandler[1]))
            {
                System.out.println("Error: Invalid action command. Please try again.");
                System.out.print("Enter your move: ");
                temp = input.nextLine().trim();
                if(temp.toUpperCase().equals("EXIT"))
                {
                    System.out.println("Qutting game...");
                    System.exit(0);
                }
                actionHandler = temp.split(" ");
            }
            int x = -1;
            x = Integer.parseInt(actionHandler[0]);
            int y = -1;
            y = Integer.parseInt(actionHandler[1]);
            
            if(game.isValidPosition(y, x))
            {
                game.input(x, y, actionHandler[2]);
                game.printBoard();
            } else {
                System.out.println("Error: Your position does not exist. Try again.");
            }
        } 
        if(game.mineDiscovered())
        {
            System.out.println("You checked a tile that had a mine on it. Game Over.");
            System.out.println("The solved minefield looked like this: ");
            game.solveBoard();
            game.printBoard();
        } else if(game.onlyMinesUndiscovered()){
            System.out.println("You cleared the minefield without checking a mine! Good job.");
        }
    }

    /**
     * 
     */
    public static boolean containsOnlyPositiveNumbers(String in)
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