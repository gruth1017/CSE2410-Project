import java.util.ArrayList;
import java.awt.Point;
public class Bishop extends ChessPiece{
    public Bishop(Game Chess, PieceColor color, String fileName, Point position){
        super(Chess, color, fileName, position);
    }
    @Override
    public ArrayList<Point> getLegalMoves(){
        Point p = position;
        ArrayList<Point> legalMoves = new ArrayList<Point>();
        // Diagonal
        vectorPath(legalMoves, p, new Point(1,1));
        vectorPath(legalMoves, p, new Point(1,-1));
        vectorPath(legalMoves, p, new Point(-1,1));
        vectorPath(legalMoves, p, new Point(-1,-1));
        return legalMoves;
        
    }
}
