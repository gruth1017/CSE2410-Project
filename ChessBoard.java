import java.awt.Point;
import java.util.ArrayList;
public class ChessBoard{ // TODO @RUTH
    final private int BOARD_SIZE = 8;
    private ChessPiece[][] board;
    PieceColor WHITE = PieceColor.WHITE;
    PieceColor BLACK= PieceColor.BLACK;
    public ChessPiece get(Point p){
        return board[p.y][p.x];
    }
    public ChessPiece get(int x, int y){
        return board[y][x];
    }
    public void set(Point p, ChessPiece piece){
        board[p.y][p.x] = piece;
    }
    public void set(int x, int y, ChessPiece piece){
        board[y][x] = piece;
    }
    public int size(){
        return BOARD_SIZE;
    }
    public ChessBoard(){
        board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
    }
    public boolean isCheck(PieceColor player) {
        Point kingPos = findKing(player);
        for(int i =0; i<8;i++){
            for(int j =0; j<8;j++){
                ChessPiece currentPiece = get(i,j);
                if(currentPiece!=null){
                    ArrayList<Point> legalMoves = currentPiece.getLegalMoves();
                for(Point element: legalMoves){
                    if(element.equals(kingPos)){
                        return true;
                    }
                }
            }
        }
        }
        return false;
    }

    private Point findKing(PieceColor player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = get(i,j);
                if (piece instanceof King && piece.color.equals(player)) {
                    return new Point(i, j);
                }
            }
        }

        return null; // King not found (should not happen in a valid chess game)
    }
    public void initializeBoard(Game Chess) {
        // Initialize pawns for white and black
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[6][i] = new Pawn(Chess,WHITE, "assets\\white_pawn.png", new Point(i,6));
            board[1][i] = new Pawn(Chess,BLACK, "assets\\black_pawn.png", new Point(i,1));
        }

        // Initialize other pieces
        board[7][7] = new Rook(Chess,WHITE, "assets\\white_rook.png", new Point(7,7));
        board[7][0] = new Rook(Chess,WHITE, "assets\\white_rook.png", new Point(0,7));
        board[0][7] = new Rook(Chess,BLACK, "assets\\black_rook.png", new Point(7,0));
        board[0][0] = new Rook(Chess,BLACK, "assets\\black_rook.png", new Point(0,0));

        board[7][6] = new Knight(Chess,WHITE, "assets\\white_knight.png", new Point(6,7));
        board[7][1] = new Knight(Chess,WHITE, "assets\\white_knight.png", new Point(1,7));
        board[0][6] = new Knight(Chess,BLACK, "assets\\black_knight.png", new Point(6,0));
        board[0][1] = new Knight(Chess,BLACK, "assets\\black_knight.png", new Point(1,0));

        board[7][5] = new Bishop(Chess,WHITE, "assets\\white_bishop.png", new Point(5,7));
        board[7][2] = new Bishop(Chess,WHITE, "assets\\white_bishop.png", new Point(2,7));
        board[0][5] = new Bishop(Chess,BLACK, "assets\\black_bishop.png", new Point(5,0));
        board[0][2] = new Bishop(Chess,BLACK, "assets\\black_bishop.png", new Point(2,0));

        board[7][3] = new Queen(Chess,WHITE, "assets\\white_queen.png", new Point(3,7));
        board[0][3] = new Queen(Chess,BLACK, "assets\\black_queen.png", new Point(3,0));

        board[7][4] = new King(Chess,WHITE, "assets\\white_king.png", new Point(4,7));
        board[0][4] = new King(Chess,BLACK, "assets\\black_king.png", new Point(4,0));
    }

}
