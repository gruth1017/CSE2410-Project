import java.awt.Point;
import java.util.ArrayList;
public class LogicController { 
    private Game Chess;
    private PieceColor playerTurn = PieceColor.WHITE;
    private ChessPiece prevPiece;
    Point lastMoveStart;
    Point lastMoveEnd;
    public void init(Game Chess){
        this.Chess = Chess;
        Chess.board.initializeBoard(Chess);
    }
    public void update(Point click){
        if(prevPiece==null){ // if user is selecting piece to move
            selectPiece(click);
        }else{ // if user is selecting where to move selected piece to
            tryMove(click);
        }
        if(Chess.board.isCheckMate(PieceColor.BLACK)){
            Chess.IO.drawScreen("assets\\white_wins.png");
            Chess.gameOver = true;
        }
        if(Chess.board.isCheckMate(PieceColor.WHITE)){
            Chess.IO.drawScreen("assets\\black_wins.png");
            Chess.gameOver = true;
        }
        if(Chess.board.isStaleMate(PieceColor.BLACK) || Chess.board.isStaleMate(PieceColor.WHITE)){
            Chess.IO.drawScreen("assets\\stalemate.png");
            Chess.gameOver = true;
        }
    }

    private void movePiece(ChessPiece piece, Point endPoint){ // ORDER MATTERS
        Chess.IO.movePiece(piece, piece.posx, piece.posy, endPoint.x, endPoint.y);
        piece.move(endPoint);
    }
    private void tryMove(Point click){ 
        ArrayList<Point> legalMoves = Chess.board.getNonSuicidalMoves(new Point(prevPiece.posx,prevPiece.posy));
        for(Point point : legalMoves){ 
            if(point.equals(click)){ // if user clicked on a legal move
                // code to execute a move
                lastMoveEnd = click;
                lastMoveStart = new Point(prevPiece.posx,prevPiece.posy);
                movePiece(prevPiece, click);
                Chess.board.moveCount++;
                unHighlight();
                prevPiece = null;
                if(playerTurn==PieceColor.BLACK){
                    playerTurn= PieceColor.WHITE;
                }else{
                    playerTurn = PieceColor.BLACK;
                }
                return;
            }
        }
        // select different piece if user clicks on their piece
        ChessPiece currentPiece = Chess.board.get(click);
        if(currentPiece!=null){
            if(currentPiece!=prevPiece && currentPiece.color.equals(playerTurn)){;
                highlightClick(click);
                prevPiece = currentPiece;
                return;
            }
        }
        prevPiece = null;
        unHighlight();
    }
    private void selectPiece(Point click){
        ChessPiece currentPiece = Chess.board.get(click);
        if(currentPiece==null){
            return;
        }
        if(!currentPiece.color.equals(playerTurn)){
            return;
        }
        highlightClick(click);
        prevPiece = currentPiece;
    }

    private void highlightClick(Point p){
        unHighlight();
        ArrayList<Point> legalMoves = Chess.board.getNonSuicidalMoves(p);
        for(Point point: legalMoves){
            Chess.IO.highlight(point.x,point.y, "assets\\move_highlight.png");
        }
        Chess.IO.highlight(p.x, p.y, "assets\\yellow_highlight.png");
    }
    private void unHighlight(){
        Chess.IO.resetHighlight();
        if(Chess.board.isCheck(PieceColor.BLACK)){
            Point blackKingPos = Chess.board.findKing(PieceColor.BLACK);
            Chess.IO.highlight(blackKingPos.x, blackKingPos.y, "assets\\red_highlight.png");
        }
        if(Chess.board.isCheck(PieceColor.WHITE)){
            Point whiteKingPos = Chess.board.findKing(PieceColor.WHITE);
            Chess.IO.highlight(whiteKingPos.x, whiteKingPos.y, "assets\\red_highlight.png");
        }
        if(lastMoveStart!=null){
            Chess.IO.highlight(lastMoveStart.x, lastMoveStart.y, "assets\\green_highlight.png");
        }
        if(lastMoveEnd!=null){
            Chess.IO.highlight(lastMoveEnd.x, lastMoveEnd.y, "assets\\green_highlight.png");
        }
    }
}
