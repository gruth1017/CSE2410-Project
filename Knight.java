import java.util.ArrayList;
import java.awt.Point;
public class Knight extends ChessPiece{
    public Knight(ChessBoard board, PieceColor color,String fileName, Point position){
        super(board, color, fileName, position);
    }
    public ChessPiece deepCopy(ChessBoard newBoard) {
        Knight newKnight = new Knight(newBoard,color, fileName, new Point(posx,posy));
        newKnight.hasMoved = this.hasMoved;
        return newKnight;
    }
    public ArrayList<Point> getLegalMoves(){
        Point p = new Point(posx,posy);
        ArrayList<Point> legalMoves = new ArrayList<Point>();
        //Square
        addIfValid(legalMoves, new Point(p.x + 1, p.y + 2));
        addIfValid(legalMoves, new Point(p.x + 1, p.y - 2));
        addIfValid(legalMoves, new Point(p.x - 1, p.y + 2));
        addIfValid(legalMoves, new Point(p.x - 1, p.y - 2));
        
        addIfValid(legalMoves, new Point(p.x + 2, p.y + 1));
        addIfValid(legalMoves, new Point(p.x + 2, p.y - 1));
        addIfValid(legalMoves, new Point(p.x - 2, p.y + 1));
        addIfValid(legalMoves, new Point(p.x - 2, p.y - 1));
        return legalMoves;
    }
}
