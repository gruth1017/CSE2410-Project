public class Knight extends ChessPiece {
   public Knight (boolean color, String filename) {
      super (color, filename);
   }
   public boolean isLegal (ChessPiece[][] board, int y, int x, int a, int b) {
      if (a == x && b == y) {
         return false;
      }
      if (a == x - 2 || a == x + 2) {
         if (b == y - 1 || b == y + 1) {
            if (board[b][a] != null) {
               if (board[b][a].color != board[y][x].color) {
                  return true;
               }
            }
         }
      }
      if (b == y - 2 || b == y + 2) {
         if (a == x - 1 || a == x + 1) {
            if (board[b][a] != null) {
               if (board[b][a].color != board[y][x].color) {
                  return true;
               }
            }
         }
      }
      return false;
   }
}