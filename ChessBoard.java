import java.awt.Point;
import java.util.ArrayList;
public class ChessBoard{
    final private int BOARD_SIZE = 8;
    public int moveCount = 0;
    private ChessPiece[][] board;
    PieceColor WHITE = PieceColor.WHITE;
    PieceColor BLACK= PieceColor.BLACK;
    public ChessBoard copyBoard(){
        ChessBoard newBoard = new ChessBoard();
        newBoard.moveCount = this.moveCount;
        for(int i =0; i<BOARD_SIZE;i++){
            for(int j =0; j< BOARD_SIZE;j++){
                ChessPiece thisPiece = get(i,j);
                if(thisPiece!=null){
                    newBoard.set(new Point(i,j), thisPiece.deepCopy(newBoard));
                }
            }
        }
        return newBoard;
    }
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

    public ArrayList<Point> getNonSuicidalMoves(Point p){
        ChessPiece piece = get(p);
        ArrayList<Point> output = new ArrayList<>();
        if(piece==null){
            return output;
        }
        ArrayList<Point> legalMoves = piece.getLegalMoves();
        for(Point move : legalMoves){
            if(!futureCheck(p, move, piece.color)){
                output.add(move);
            }
        }
        return output;
    }
    public boolean isCheckMate(PieceColor player){
        return(isCheck(player) && isMate(player));
    }
    public boolean isStaleMate(PieceColor player){
        return(!isCheck(player) && isMate(player));
    }
    public boolean isMate(PieceColor player){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                ChessPiece piece = get(i,j);
                if(piece==null){
                    continue;
                }
                if(piece.color!=player){
                    continue;
                }
                if(!getNonSuicidalMoves(new Point(i,j)).isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean futureCheck(Point start, Point end, PieceColor player){ // check if move puts king in check
        ChessBoard simBoard = this.copyBoard();
        ChessPiece simPiece = simBoard.get(start);
        simBoard.set(simPiece.posx,simPiece.posy,null);
        simPiece.move(end);
        simBoard.set(end,simPiece);
        return simBoard.isCheck(player);
    }
    public boolean isCheck(PieceColor player){
        return !isTileSafe(findKing(player), player);
    }
    public boolean isTileSafe(Point p, PieceColor player){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                ChessPiece piece = get(i,j);
                if(piece==null){
                    continue;
                }
                if(piece.color.equals(player)){
                    continue;
                }
                if(piece instanceof King){ // if neither king has moved they are absolutely not a risk to each other. 
                    //holy the amount of stack overflows i had to deal with to find the source of this bug
                    if(!get(findKing(WHITE)).hasMoved && !get(findKing(BLACK)).hasMoved){
                        continue;
                    }
                }
                ArrayList<Point> pieceMoves = piece.getLegalMoves();
                for(Point point : pieceMoves){
                    if(point.x == p.x && point.y==p.y){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Point findKing(PieceColor player) {
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
            board[6][i] = new Pawn(this, WHITE, "assets\\white_pawn.png", new Point(i,6));
            board[1][i] = new Pawn(this, BLACK, "assets\\black_pawn.png", new Point(i,1));
        }
        // Initialize other pieces
        board[7][7] = new Rook(this,WHITE, "assets\\white_rook.png", new Point(7,7));
        board[7][0] = new Rook(this,WHITE, "assets\\white_rook.png", new Point(0,7));
        board[0][7] = new Rook(this,BLACK, "assets\\black_rook.png", new Point(7,0));
        board[0][0] = new Rook(this,BLACK, "assets\\black_rook.png", new Point(0,0));

        board[7][6] = new Knight(this,WHITE, "assets\\white_knight.png", new Point(6,7));
        board[7][1] = new Knight(this,WHITE, "assets\\white_knight.png", new Point(1,7));
        board[0][6] = new Knight(this,BLACK, "assets\\black_knight.png", new Point(6,0));
        board[0][1] = new Knight(this,BLACK, "assets\\black_knight.png", new Point(1,0));

        board[7][5] = new Bishop(this,WHITE, "assets\\white_bishop.png", new Point(5,7));
        board[7][2] = new Bishop(this,WHITE, "assets\\white_bishop.png", new Point(2,7));
        board[0][5] = new Bishop(this,BLACK, "assets\\black_bishop.png", new Point(5,0));
        board[0][2] = new Bishop(this,BLACK, "assets\\black_bishop.png", new Point(2,0));

        board[7][3] = new Queen(this,WHITE, "assets\\white_queen.png", new Point(3,7));
        board[0][3] = new Queen(this,BLACK, "assets\\black_queen.png", new Point(3,0));

        board[7][4] = new King(this,WHITE, "assets\\white_king.png", new Point(4,7));
        board[0][4] = new King(this,BLACK, "assets\\black_king.png", new Point(4,0));
    }
}