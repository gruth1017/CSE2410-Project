import java.awt.Point;
import java.util.ArrayList;
public abstract class ChessPiece { //TODO @BRADEN
    protected String fileName; 
    protected boolean hasMoved = false;
    protected boolean passing = false;
    protected PieceColor color;
    protected Game Chess;
    Point starting_position; 
    Point position;
   //  protected String type; Dont need; use instanceOf()
    public ChessPiece(Game Chess, PieceColor color, String fileName, Point position){
        this.Chess = Chess;
        this.fileName = fileName;
        this.color = color;
        this.position = position;
        this.starting_position=position;
    }

    public String getFileName() {
        return fileName;
    }
    public abstract ArrayList<Point> getLegalMoves ();
    public void move (Point endPoint) {
        Chess.board.set(position,null);
        Chess.IO.movePiece(this, position.x, position.y, endPoint.x, endPoint.y);
        Chess.board.set(endPoint,this);
        hasMoved = true;
        position = endPoint;
     }
    
    // Generate all moves in a piece's path
    protected ArrayList<Point> vectorPath(ArrayList<Point> output, Point start, Point direction){
        while(true){
            start = new Point(start.x+direction.x,start.y+direction.y);
            if(outOfBounds(start)){ // if hit wall return current list
                return output;
            }
            ChessPiece newPiece = Chess.board.get(start);
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
        ChessPiece newPiece = Chess.board.get(p);
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
