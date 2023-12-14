import java.util.ArrayList;
import java.awt.Point;
public class Rook extends ChessPiece{
    public Rook(ChessBoard board, PieceColor color,String fileName, Point position){
        super(board, color, fileName,position);
    }
    public ChessPiece deepCopy(ChessBoard newBoard) {
        Rook newRook = new Rook(newBoard, color, fileName, new Point(posx,posy));
        newRook.hasMoved = this.hasMoved;
        return newRook;
    }
    public ArrayList<Point> getLegalMoves(){
        Point p = new Point(posx,posy);
        ArrayList<Point> legalMoves = new ArrayList<Point>();
        // Square
        vectorPath(legalMoves, p, new Point(1,0));
        vectorPath(legalMoves, p, new Point(-1,0));
        vectorPath(legalMoves, p, new Point(0,1));
        vectorPath(legalMoves, p, new Point(0,-1));
        return legalMoves;
    }
}
