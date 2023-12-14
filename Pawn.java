import java.util.ArrayList;
import java.awt.Point;
public class Pawn extends ChessPiece{
    public Pawn(ChessBoard board, PieceColor color,String fileName, Point position){
        super(board, color, fileName, position);
    }
    private int enPassant = -999;
    private int mirror = color.equals(PieceColor.WHITE) ? -1:1;
    public ChessPiece deepCopy(ChessBoard newBoard) {
        Pawn newPawn = new Pawn(newBoard, color, fileName, new Point(posx,posy));
        newPawn.hasMoved = this.hasMoved;
        newPawn.enPassant = this.enPassant;
        newPawn.mirror = this.mirror;
        return newPawn;
    }
    public ArrayList<Point> getLegalMoves(){
        ArrayList<Point> legalMoves = new ArrayList<Point>();
        forward(legalMoves);
        tryDiag(legalMoves, true);
        tryDiag(legalMoves, false);
        tryEnPassant(legalMoves, true);
        tryEnPassant(legalMoves, false);
        return legalMoves;
    }
    public int getFirstMove(){
        return enPassant;
    }
    private void tryEnPassant(ArrayList<Point> output, boolean mirrorx){
        int xoffset = mirrorx ? 1: -1;
        Point p = new Point(posx+xoffset, posy);
        if(outOfBounds(p)){
            return;
        } 
        ChessPiece piece = board.get(p);
        if(piece==null){
            return;
        }
        if(piece.color==color || !(piece instanceof Pawn)){
            return;
        }
        Pawn pawn = (Pawn)(piece);
        if(board.moveCount-pawn.getFirstMove()==1){
            output.add(new Point(p.x,p.y+mirror));
        }
    }
    private void firstMove(ArrayList<Point> output){
        if(hasMoved){
            return;
        }
        Point p = new Point(posx, posy +2*mirror);
        if(outOfBounds(p)){
            return;
        }
        ChessPiece piece = board.get(p);
        if(piece==null){
            output.add(p);
        }
    }
    private void tryDiag(ArrayList<Point> output, boolean mirrorx){
        int xoffset = mirrorx ? 1: -1;
        Point p = new Point(posx+xoffset, posy +1*mirror);
        if(outOfBounds(p)){
            return;
        }
        ChessPiece piece = board.get(p);
        if(piece==null){
            return;
        }
        if(piece.color.equals(color)){
            return;
        }
        output.add(p);
    }
    private void forward(ArrayList<Point> output){
        Point p = new Point(posx, posy +1*mirror);
        if(outOfBounds(p)){
            return;
        }
        if(board.get(p)==null){
            output.add(p);
            firstMove(output);
        }
    }
    @Override
    public void move(Point endPoint) {
        if(Math.abs(endPoint.y - posy)==2){ // if move twice set flag for enpassant
            enPassant = board.moveCount;
        }
        if ((Math.abs(endPoint.x -posx)==1) && (Math.abs(endPoint.x -posx)==1)){ // execute enpassant
            ChessPiece piece = board.get(endPoint);
            if(piece==null){
                board.set(new Point(endPoint.x,posy), null);
            } 
        }
        super.move(endPoint);
        if(endPoint.y==0 || endPoint.y==7){
            String f;
            if(color.equals(PieceColor.BLACK)){
                f = "assets\\black_queen.png";
            }else{
                f = "assets\\white_queen.png";
            }
            board.set(endPoint, new Queen(this.board,color, f, endPoint));
        }
    }
}
