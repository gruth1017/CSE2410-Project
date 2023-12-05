import java.awt.Point;
import java.util.ArrayList;
public class LogicController { 
    private Game Chess;
    private PieceColor playerTurn = PieceColor.WHITE;
    private Point prevPiece;
    public void init(Game Chess){
        this.Chess = Chess;
        Chess.board.initializeBoard(Chess);
    }
    public void update(Point click){
        ChessBoard board = Chess.board;
        ChessPiece currentPiece = board.get(click);
        if(prevPiece==null){
            if(currentPiece!=null){
                if(currentPiece.color.equals(playerTurn)){
                    ArrayList<Point> tempMoves=currentPiece.getLegalMoves();
                    if(tempMoves.size()==0){
                        return;
                    } 
                    for(Point point: tempMoves){
                        Chess.IO.highlight(point.x,point.y, "assets\\basichighlight.png");
                    }
                    prevPiece = click;
                }
            }
        }else{
            ArrayList<Point> legalMoves = Chess.board.get(prevPiece).getLegalMoves();
            for(Point point : legalMoves){
                if(point.equals(click)){
                    board.get(prevPiece).move(click);
                    prevPiece = null;
                    Chess.IO.resetHighlight();
                    if(playerTurn==PieceColor.BLACK){
                        playerTurn= PieceColor.WHITE;
                    }else{
                        playerTurn = PieceColor.BLACK;
                    }
                }else{
                    
                    Chess.IO.resetHighlight();
                }
            }
            prevPiece = null;
        }
        if(board.isCheck(playerTurn)){
            System.out.println("Check");
        }

    }
}
