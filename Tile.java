import java.lang.Math;
public class Tile
{
    private int minesNear; //Number of mines directly around the tile, initialized in the board constructor
    private boolean isMine; //Whether or not a tile is a bomb
    private boolean isFlagged; //Whether or not a tile is flagged
    private boolean isDiscovered; //Whether or not a tile's info is visible to the user
    private int xPos;
    private int yPos;
    public Tile(int difficulty)
    {
        isFlagged = false;
        isDiscovered = false;
        if(difficulty == 1)
        {
            isMine = Math.random() <= 0.125;
        } else {
            isMine = Math.random() <= 0.25;
        }
    }   
    
    public void flagTile()
    {
        isFlagged = !isFlagged;
    }
    
    public boolean isMine()
    {
        return isMine;
    }
    
    public boolean isDiscovered()
    {
        return isDiscovered;
    }
    
    public boolean isFlagged()
    {
        return isFlagged;
    }
    
    public void discover()
    {
        isDiscovered = true;
    }
    
    public int getX()
    {
        return xPos;
    }
    
    public int getY()
    {
        return yPos;
    }
    
    public void setX(int x)
    {
        xPos = x;
    }
    
    public void setY(int y)
    {
        yPos = y;
    }
    
    public void setMinesNear(int in)
    {
        minesNear = in;
    }
    
    public int minesNear()
    {
        return minesNear;
    }
    
    public String toString()
    {
        String symbol = "";
        if(isFlagged())
        {
            symbol = "#";
        } else if (!isDiscovered()){
            symbol = "?";
        } else if (isMine()){
            symbol = "*";
        } else {
            if(minesNear == 0)
            {
                symbol = " ";
            } else {
                symbol = "" + minesNear;
            }
        }
        return symbol;
    }
}
