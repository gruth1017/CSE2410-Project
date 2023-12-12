import java.awt.Point;
public class King extends ChessPiece {
   public King (boolean color, String filename) {
      super (color, filename);
   }
   public boolean isLegal (ChessPiece[][] board, int y, int x, int a, int b) {
      if (a == x && b == y) {
         return false;
      }
      if (a >= x - 1 && a <= x + 1 && b >= y - 1 && b <= y + 1) {
         if (board[b][a] != null) {
            if (board[b][a].color != board[y][x].color) {
               return true;
            }
         }
         if (board[b][a] == null) {
            return true;
         }
      }
      // todo add castling
      return false;
   }
   public void move (GamePanel IO, ChessPiece[][] board, Point a, Point b) {
      super.move(IO, board, a,b);
      hasMoved = true;
   }
}
