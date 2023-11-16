import java.util.ArrayList;
import java.awt.Point;

public abstract class chessPiece {
   protected boolean color;
   protected String type;
   protected boolean hasMoved = false;
   protected boolean passing = false;
   protected ArrayList<Point> getLegalMoves (chessPiece[][] board, chessPiece piece, int y, int x) {
      ArrayList<Point> output = new ArrayList<Point>();
      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            boolean legal = piece.isLegal(board, y, x, i, j);
            if (legal) {
               output.add(new Point(i,j));
            }
         }
      }
      return output;
   }
   public void move (chessPiece[][] board, chessPiece piece, int y, int x, Point a) {
      board[y][x] = null;
      board[a.y][a.x] = piece;
      hasMoved = true;
   }
   public abstract boolean isLegal(chessPiece[][] board, int y, int x, int i, int j);
}
