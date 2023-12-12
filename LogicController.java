import java.awt.Point;
import java.util.ArrayList;
public class LogicController { 
    private Game Chess;
    private boolean playerTurn = true;
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
                if(currentPiece.color == playerTurn){
                    ArrayList<Point> tempMoves=currentPiece.getLegalMoves(board.getBoard(), click.y, click.x);
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
            ArrayList<Point> legalMoves = Chess.board.get(prevPiece).getLegalMoves(board.getBoard(), prevPiece.y, prevPiece.x);
            for(Point point : legalMoves){
                if(point.equals(click)){
                    board.get(prevPiece).move(Chess.IO, board.getBoard(), click, point);
                    prevPiece = null;
                    Chess.IO.resetHighlight();
                    if(board.isCheck(playerTurn)){
                        System.out.println("Check");
                    }
                    if(playerTurn == false){
                        playerTurn= true;
                    }else{
                        playerTurn = false;
                    }
                }else{
                    
                    Chess.IO.resetHighlight();
                }
            }
            prevPiece = null;
        }
    }
}
