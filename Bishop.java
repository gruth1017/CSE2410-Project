import java.util.ArrayList;
import java.awt.Point;
public class Bishop extends ChessPiece{
    public Bishop(ChessBoard board, PieceColor color, String fileName, Point position){
        super(board, color, fileName, position);
    }
    public ChessPiece deepCopy(ChessBoard newBoard) {
        Bishop newBishop = new Bishop(newBoard, color, fileName, new Point(posx,posy));
        newBishop.hasMoved = this.hasMoved;
        return newBishop;
    }
    public ArrayList<Point> getLegalMoves(){
        Point p = new Point(posx,posy);
        ArrayList<Point> legalMoves = new ArrayList<Point>();
        // Diagonal
        vectorPath(legalMoves, p, new Point(1,1));
        vectorPath(legalMoves, p, new Point(1,-1));
        vectorPath(legalMoves, p, new Point(-1,1));
        vectorPath(legalMoves, p, new Point(-1,-1));
        return legalMoves;
        
    }
}
