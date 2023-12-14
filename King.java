import java.util.ArrayList;
import java.awt.Point;
public class King extends ChessPiece{
    public King(ChessBoard board, PieceColor color,String fileName, Point position){
        super(board, color, fileName, position);
    }
    public ChessPiece deepCopy(ChessBoard newBoard) {
        King newKing = new King(newBoard, color, fileName, new Point(posx,posy));
        newKing.hasMoved = this.hasMoved;
        return newKing;
    }
    public ArrayList<Point> getLegalMoves(){
        Point p = new Point(posx,posy);
        ArrayList<Point> legalMoves = new ArrayList<Point>();
        //Square
        addIfValid(legalMoves, new Point(p.x+1,p.y));
        addIfValid(legalMoves, new Point(p.x-1,p.y));
        addIfValid(legalMoves, new Point(p.x,p.y+1));
        addIfValid(legalMoves, new Point(p.x,p.y-1));

        //Diagonal
        addIfValid(legalMoves, new Point(p.x+1,p.y+1));
        addIfValid(legalMoves, new Point(p.x+1,p.y-1));
        addIfValid(legalMoves, new Point(p.x-1,p.y+1));
        addIfValid(legalMoves, new Point(p.x-1,p.y-1));
        for(int i =0; i<board.size();i++){
            for(int j = 0; j<board.size();j++){
                ChessPiece piece = board.get(i,j);        
                if(piece==null){
                    continue;
                }
                if(piece.color==this.color && piece instanceof Rook){
                    tryCastling(legalMoves, (Rook)piece);
                }
            }
        }
        
        return legalMoves;
    }
    public void tryCastling(ArrayList<Point> legalMoves, Rook rook){
        if(!canCastle(rook)){
            return;
        }
        if(rook.posx==0){
            legalMoves.add(new Point(2,this.posy));
        }
        if(rook.posx==7){
            legalMoves.add(new Point(6,this.posy));
        }
    }
    public boolean canCastle(Rook rook){
        if(this.hasMoved || rook.hasMoved){
            return false;
        }
        int minx = Math.min(this.posx,rook.posx);
        int maxx= Math.max(this.posx,rook.posx);
        for(int i= minx+1; i <maxx;i++){
            if(board.get(i,posy)!=null){
                return false;
            }
        }
         if(board.isCheck(color)){
             return false;
         }
         return true;
    }
    @Override
    public void move(Point endPoint) {
        if ((Math.abs(endPoint.x - posx)>1)){ // execute castle
            Rook rook;
            Point rookEnd;
            if(endPoint.x==6){
                rook = (Rook)board.get(7,endPoint.y);
                rookEnd= new Point(5,endPoint.y);
            }else{
                rook = (Rook)board.get(0,endPoint.y);
                rookEnd= new Point(3,endPoint.y);
            }
            board.set(rook.posx,rook.posy, null);
            rook.move(rookEnd);
            board.set(rookEnd, rook);
        }
        super.move(endPoint);
    }
}
