import java.awt.Point;
import java.util.ArrayList;

public class ChessBoard {
    public final int BOARD_SIZE = 8;
    ChessPiece[][] board;

    public ChessBoard() {
        board = new ChessPiece[BOARD_SIZE][BOARD_SIZE]; // Initialize the board
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize pawns
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[1][i] = new Pawn(true, "assets\\pawn_white.png");
            board[6][i] = new Pawn(false, "assets\\pawn_black.png");
        }

        // Initialize other pieces
        board[0][0] = new Rook(true, "assets\\white_rook.png");
        board[0][7] = new Rook(true, "assets\\white_rook.png");
        board[7][0] = new Rook(false, "assets\\black_rook.png");
        board[7][7] = new Rook(false, "assets\\black_rook.png");

        board[0][1] = new Knight(true, "assets\\white_knight.png");
        board[0][6] = new Knight(true, "assets\\white_knight.png");
        board[7][1] = new Knight(false, "assets\\black_knight.png");
        board[7][6] = new Knight(false, "assets\\black_knight.png");

        board[0][2] = new Bishop(true, "assets\\white_bishop.png");
        board[0][5] = new Bishop(true, "assets\\white_bishop.png");
        board[7][2] = new Bishop(false, "assets\\black_bishop.png");
        board[7][5] = new Bishop(false, "assets\\black_bishop.png");

        board[0][3] = new Queen(true, "assets\\white_queen.png");
        board[7][3] = new Queen(false, "assets\\black_queen.png");

        board[0][4] = new King(true, "assets\\white_king.png");
        board[7][4] = new King(false, "assets\\black_king.png");
    }

    public boolean isCheck(String player) {
        Point kingPosition = findKing(player);

        // Check if any opponent's piece can attack the king
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(player) && piece.isLegal(board, i, j, kingPosition.x, kingPosition.y)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCheckmate(String player) {
        if (!isCheck(player)) {
            return false; // If not in check, not in checkmate
        }

        // Check if the player has any legal moves
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor().equals(player)) {
                    ArrayList<Point> legalMoves = piece.getLegalMoves(board, i, j);
                    for (Point move : legalMoves) {
                        if (isMoveValid(i, j, move.x, move.y, player)) {
                            return false; // Player has at least one legal move
                        }
                    }
                }
            }
        }

        return true; // Player is in checkmate
    }

    public boolean isStalemate(String player) {
        if (isInStalemate(player)) {
            // Additional conditions for stalemate (if needed)
            return true;
        }

        return false;
    }

    private boolean isInStalemate(String player) {
        // Check if the player has no legal moves but is not in check
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor().equals(player)) {
                    ArrayList<Point> legalMoves = piece.getLegalMoves(board, i, j);
                    for (Point move : legalMoves) {
                        if (isMoveValid(i, j, move.x, move.y, player)) {
                            return false; // Player has at least one legal move
                        }
                    }
                }
            }
        }

        return true; // Player is in stalemate
    }

    private Point findKing(String player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.getColor().equals(player)) {
                    return new Point(j, i);
                }
            }
        }

        return null; // King not found (should not happen in a valid chess game)
    }

    private boolean isMoveValid(int startX, int startY, int endX, int endY, String player) {
        ChessPiece startPiece = board[startY][startX];
        ChessPiece endPiece = board[endY][endX];

        if (startPiece == null || !startPiece.getColor().equals(player)) {
            return false; // Trying to move an empty square or opponent's piece
        }

        ArrayList<Point> legalMoves = startPiece.getLegalMoves(board, startY, startX);

        // Check if the destination is within the list of legal moves
        if (legalMoves.stream().anyMatch(p -> p.x == endX && p.y == endY)) {
            // Simulate the move to check if the king is in check after the move
            ChessPiece[][] tempBoard = simulateMove(startX, startY, endX, endY);
            if (!isCheck(player, tempBoard)) {
                // The move is valid
                return true;
            }
        }

        return false;
    }

    private ChessPiece[][] simulateMove(int startX, int startY, int endX, int endY) {
        ChessPiece[][] tempBoard = copyBoard(board);
        tempBoard[endY][endX] = tempBoard[startY][startX];
        tempBoard[startY][startX] = null;
        return tempBoard;
    }

    private boolean isCheck(String player, ChessPiece[][] tempBoard) {
        Point kingPosition = findKing(player, tempBoard);

        // Check if any opponent's piece can attack the king
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = tempBoard[i][j];
                if (piece != null && !piece.getColor().equals(player) && piece.isLegal(tempBoard, i, j, kingPosition.x, kingPosition.y)) {
                    return true;
                }
            }
        }

        return false;
    }

    private Point findKing(String player, ChessPiece[][] tempBoard) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = tempBoard[i][j];
                if (piece instanceof King && piece.getColor().equals(player)) {
                    return new Point(j, i);
                }
            }
        }

        return null; // King not found (should not happen in a valid chess game)
    }

    private ChessPiece[][] copyBoard(ChessPiece[][] original) {
        ChessPiece[][] copy = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, BOARD_SIZE);
        }
        return copy;
    }
}
