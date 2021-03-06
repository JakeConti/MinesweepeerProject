import java.util.ArrayList;
public class Board
{
    private Tile[][] board; //ArrayList of tiles, representing the board
    private String header; //"Header" for a game: contains the numbers at the top of the board to reepresent the x-axis of the board
    private String divider; //"Divider" divides each row with dashes
    
    public Board(int length, int height, int difficulty)//Constructor for board
    {
        board = new Tile[height][length]; //Instantiates a 2D array of tiles
        
        //Initializes each tile in the board 2D array.
        for(int i = 0; i < board.length; i++) 
        {
            for(int j = 0; j < board[0].length; j++)
            {
                board[i][j] = new Tile(difficulty);
            }
        }
        
        header = "  | "; //Start of header
        //Creates the header of the board, based on the length of the board.
        for(int i = 0; i < board[0].length; i++)
        {
            if(i < 10)
            {
                header += i + " | ";
            }else{
                header += i + "| ";
            }
            
        }
        
        divider = ""; //Instantiates divider
        //Fills in divider with enough "-" characters for the board
        for(int i = 0; i < header.length(); i++)
        {
            divider += "-";
        }
        
        //Assigns each tile in the board an x and y coordinate, and finds the number of surrounding mines for each tile (minesNear) for each not-mine tile
        for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board[0].length; c++)
            {
                board[r][c].setX(c);

                board[r][c].setY(r);
                if(!board[r][c].isMine())
                {
                    int mineCount = 0;
                    ArrayList<Tile> surroundingTiles = getSurroundingTiles(r, c);
                    for(Tile e : surroundingTiles)
                    {
                        if(e.isMine())
                        {
                            mineCount++;
                        }
                    }
                    board[r][c].setMinesNear(mineCount);
                }
            }
        } 
    }
    
    //Prints out a visual representation of the board
    public void printBoard()
    {
        System.out.println("");
        System.out.println(header);
        System.out.println(divider);
        for(int i = 0; i < board.length; i++)
        {
             if(i < 10)
             {
                 System.out.print(i + " | ");
             } else {
                 System.out.println(i + "| ");
             }
             for(int j = 0; j < board[0].length; j++)
             {
                 System.out.print(board[i][j] + " | ");
             }
             System.out.println("");
             System.out.println(divider);
        }
    }
    
    //Solves the board for the user (done after a loss) to show the state of each undiscovered tile
    public void solveBoard()
    {
        for(Tile[] r : board)
        {
            for(Tile e : r)
            {
                e.discover();
                if(e.isFlagged())
                {
                    e.flagTile();
                }
            }
        }
    }
    
    //Returns whether or not a mine has been discovered in the board
    public boolean mineDiscovered()
    {
        for(Tile[] r : board)
        {
            for(Tile e : r)
            {
                if(e.isMine() && e.isDiscovered())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Returns whether or not only mines remain undiscovered on a baord
    public boolean onlyMinesUndiscovered()
    {
        for(Tile[] r : board)
        {
            for(Tile e : r)
            {
                if(!e.isMine() && !e.isDiscovered())
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    //Handles the user input for a command
    public void input(int c, int r, String a)
    {
        if(a.toUpperCase().equals("C"))
        {
            board[r][c].discover();
            if(!board[r][c].isMine() && board[r][c].minesNear() == 0)
            {
                discoverSurroundingTiles(r, c);                
            }
        } else if (a.toUpperCase().equals("F")){
            board[r][c].flagTile();
        }
    }  
    
    //Returns whether a given position is valid relative to the size of the board
    public boolean isValidPosition(int r, int c)
    {
        return (r >= 0 && c >= 0) && (r < board.length && c < board[0].length);
    }
    
    //Returns a list of the tiles surrounding a given tile
    private ArrayList<Tile> getSurroundingTiles(int r, int c)
    {
        ArrayList<Tile> temp = new ArrayList<Tile>();
        for(int i = r-1; i <= r+1; i++)
        {
            for(int j = c-1; j <= c+1; j++)
            {
                if(isValidPosition(i, j))
                {
                    if(!(r == i && c == j))
                    {
                        temp.add(board[i][j]);
                    }
                }
            }
        }
        return temp;
    }
    
    //Discovers all surrounding tiles if a tile has 0 mines near it. Calls recursively for each empy tile to help the user clear the board faster.
    private void discoverSurroundingTiles(int r, int c)
    {
        ArrayList<Tile> near = getSurroundingTiles(r, c);
        for(int i = 0; i < near.size(); i++)
        {
            if(near.get(i).isDiscovered())
            {
                near.remove(i);
                i--;
            }
        }
        
        if(near.size() != 0)
        {
            for(Tile e : near)
            {
                e.discover();
                if(e.minesNear() == 0)
                {
                    discoverSurroundingTiles(e.getY(), e.getX());
                }
            }
        }
    }  
}