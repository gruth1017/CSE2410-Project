import java.awt.Point;
import java.util.ArrayList;
public abstract class ChessPiece{
    // all attributes must be final or primitive
    final protected String fileName; 
    final protected PieceColor color;
    final protected ChessBoard board;
    protected boolean hasMoved = false;
    protected int posx;
    protected int posy;
   //  protected String type; Dont need; use instanceOf()
    public ChessPiece(ChessBoard board, PieceColor color, String fileName, Point position){
        this.fileName = fileName;
        this.color = color;
        this.board = board;
        this.posx = position.x;
        this.posy = position.y;
    }
    public abstract ChessPiece deepCopy(ChessBoard board);
    public String getFileName() {
        return fileName;
    }
    public abstract ArrayList<Point> getLegalMoves ();
    public void move (Point endPoint) {

        board.set(this.posx,this.posy,null);
        posx= endPoint.x;
        posy=endPoint.y;
        hasMoved = true;
        board.set(endPoint,this);
     }
    
    // Generate all moves in a piece's path
    protected ArrayList<Point> vectorPath(ArrayList<Point> output, Point start, Point direction){
        while(true){
            start = new Point(start.x+direction.x,start.y+direction.y);
            if(outOfBounds(start)){ // if hit wall return current list
                return output;
            }
            ChessPiece newPiece = board.get(start);
            if(newPiece==null){ // if no occupying piece, is valid move keep traveling along vector in next loop
                    output.add(start);
                    continue;
            }
            // if is occupying piece, piece can not capture if own color, can capture but cant go past if different color
            if(newPiece.color.equals(this.color)){
                return output; 
            }else{
                output.add(start);
                return output;  
            }
            }
        }
    protected boolean outOfBounds(Point p){
        if(p.x<0 || p.y<0){
            return true;
        }
        if(p.x>7 || p.y>7){
            return true;
        }
        return false;
    }
    protected void addIfValid(ArrayList<Point> output,Point p){
        if(outOfBounds(p)){
            return;
        }
        ChessPiece newPiece = board.get(p);
        if(newPiece==null){
            output.add(p);
            return;
        }
        if(newPiece.color.equals(this.color)){
            return;
        }
        output.add(p);
    }
    }
