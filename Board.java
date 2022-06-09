import java.util.ArrayList;
public class Board
{
    private Tile[][] board;
    private String header;
    private String divider;
    public Board(int length, int height, int difficulty)
    {
        board = new Tile[height][length];
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[0].length; j++)
            {
                board[i][j] = new Tile(difficulty);
            }
        }
        
        header = "  | ";
        for(int i = 0; i < board[0].length; i++)
        {
            if(i < 10)
            {
                header += i + " | ";
            }else{
                header += i + "| ";
            }
            
        }
        
        divider = "";
        for(int i = 0; i < header.length(); i++)
        {
            divider += "-";
        }
        
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
    
    public boolean isValidPosition(int r, int c)
    {
        return (r >= 0 && c >= 0) && (r < board.length && c < board[0].length);
    }
    
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