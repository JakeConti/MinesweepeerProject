import java.lang.Math;
public class Tile
{
    private int minesNear; //Number of mines directly around the tile, initialized in the board constructor
    private boolean isMine; //Whether or not a tile is a bomb
    private boolean isFlagged; //Whether or not a tile is flagged
    private boolean isDiscovered; //Whether or not a tile's info is visible to the user
    private int xPos; //X-position of the tile on a grid, instantiated with the creation of a board
    private int yPos; //Y-position of the tile on a grid, instantiated with the creation of a board
    public Tile(int difficulty)//Constructor
    {
        isFlagged = false; //Default state
        isDiscovered = false; //Default state
        if(difficulty == 1) //Determines the probability of a mine appearing on a tile
        {
            isMine = Math.random() <= 0.125; //Easy difficulty
        } else {
            isMine = Math.random() <= 0.25; //Hard Difficulty
        }
    }   
    
    public void flagTile() //Flags a tile, call again to unflag
    {
        isFlagged = !isFlagged;
    }
    
    public boolean isMine() //Returns whether or not a tile contains a mine
    {
        return isMine;
    }
    
    public boolean isDiscovered() //Returns whether or not a tile has been checked by the player
    {
        return isDiscovered;
    }
    
    public boolean isFlagged() //Returns whether or not a tile has been flagged by the player 
    {
        return isFlagged;
    }
    
    public void discover() //"discovers" a tile, done by the user.
    {
        isDiscovered = true;
    }
    
    public int getX() //Returns the x position of a tile on a board
    {
        return xPos;
    }
    
    public int getY() //Returns the y position of a tile on a board
    {
        return yPos;
    }
    
    public void setX(int x) //Sets the x position of a tile on a board 
    {
        xPos = x;
    }
    
    public void setY(int y) //Sets the y position of a tile on a board
    {
        yPos = y;
    }
    
    public void setMinesNear(int in) //Sets the number of mines surrounding a certain tile
    {
        minesNear = in;
    }
    
    public int minesNear() //Returns the number of mines near a certain tile
    {
        return minesNear;
    }
    
    public String toString() //Returns the symbol of a tile depending on whether the tile was discovered or flagged, and whether the tile was a mine or not
    {
        String symbol = "";
        if(isDiscovered())
        {
            if(isMine()){
                symbol = "*";
            } else {
                if(minesNear == 0)
                {
                    symbol = " ";
                } else {
                    symbol = "" + minesNear;
                }
            }
        } else {
            symbol = "?";
        }
        return symbol;
    }
}
