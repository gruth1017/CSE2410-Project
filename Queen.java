import java.util.ArrayList;
import java.awt.Point;
public class Queen extends ChessPiece{
    public Queen(ChessBoard board,PieceColor color,String fileName, Point position){
        super(board, color, fileName, position);
    }
    public ChessPiece deepCopy(ChessBoard newBoard) {
        Queen newQueen = new Queen(newBoard,color, fileName, new Point(posx,posy));
        newQueen.hasMoved = this.hasMoved;
        return newQueen;
    }
    public ArrayList<Point> getLegalMoves(){
        Point p = new Point(posx,posy);
        ArrayList<Point> legalMoves = new ArrayList<Point>();
        // Square
        vectorPath(legalMoves, p, new Point(1,0));
        vectorPath(legalMoves, p, new Point(-1,0));
        vectorPath(legalMoves, p, new Point(0,1));
        vectorPath(legalMoves, p, new Point(0,-1));
        // Diagonal
        vectorPath(legalMoves, p, new Point(1,1));
        vectorPath(legalMoves, p, new Point(1,-1));
        vectorPath(legalMoves, p, new Point(-1,1));
        vectorPath(legalMoves, p, new Point(-1,-1));
        return legalMoves;
        
    }
}
