import java.awt.Point;
import java.util.ArrayList;
public class ChessBoard{ // TODO @RUTH
    final private int BOARD_SIZE = 8;
    private ChessPiece[][] board;
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
    public ChessPiece[][] getBoard() {
        return board;
    }
    public boolean isCheck(boolean player) {
        Point kingPos = findKing(player);
        ArrayList<Point> legalMoves = new ArrayList<Point>();
        for(int i =0; i<8;i++){
            for(int j =0; j<8;j++){
                ChessPiece currentPiece = get(i,j);
                if(currentPiece!=null){
                    if (currentPiece.color != player) {
                       legalMoves = currentPiece.getLegalMoves(board, j, i);
                    }
                }
                for(Point element: legalMoves){
                    if(element.equals(kingPos)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Point findKing(boolean player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = get(i,j);
                if (piece instanceof King && piece.color == player) {
                    return new Point(i, j);
                }
            }
        }

        return null; // King not found (should not happen in a valid chess game)
    }
    public void initializeBoard(Game Chess) {
        // Initialize pawns for white and black
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[6][i] = new Pawn(true, "assets\\white_pawn.png");
            board[1][i] = new Pawn(false, "assets\\black_pawn.png");
        }

        // Initialize other pieces
        board[7][7] = new Rook(true, "assets\\white_rook.png");
        board[7][0] = new Rook(true, "assets\\white_rook.png");
        board[0][7] = new Rook(false, "assets\\black_rook.png");
        board[0][0] = new Rook(false, "assets\\black_rook.png");

        board[7][6] = new Knight(true, "assets\\white_knight.png");
        board[7][1] = new Knight(true, "assets\\white_knight.png");
        board[0][6] = new Knight(false, "assets\\black_knight.png");
        board[0][1] = new Knight(false, "assets\\black_knight.png");

        board[7][5] = new Bishop(true, "assets\\white_bishop.png");
        board[7][2] = new Bishop(true, "assets\\white_bishop.png");
        board[0][5] = new Bishop(false, "assets\\black_bishop.png");
        board[0][2] = new Bishop(false, "assets\\black_bishop.png");

        board[7][3] = new Queen(true, "assets\\white_queen.png");
        board[0][3] = new Queen(false, "assets\\black_queen.png");

        board[7][4] = new King(true, "assets\\white_king.png");
        board[0][4] = new King(false, "assets\\black_king.png");
    }

}