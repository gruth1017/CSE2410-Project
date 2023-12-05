public class Queen extends ChessPiece {
   public Queen (boolean color, String filename) {
      super (color, filename);
   }
   public boolean isLegal (ChessPiece[][] board, int y, int x, int a, int b) {
      if (a == x && b == y) {
         return false;
      }
      if (a == x && b < y) {
         for (int i = y; i >= b; i--) {
            if (board[i][x] != null) {
               if (i == b && board[i][x].color != board[y][x].color) {
                  return true;
               } else {
                  return false;
               }
            }
         }
         return true;
      }
      if (a == x && b > y) {
         for (int i = y; i <= b; i++) {
            if (board[i][x] != null) {
               if (i == b && board[i][x].color != board[y][x].color) {
                  return true;
               } else {
                  return false;
               }
            }
         }
         return true;
      }
      if (b == y && a < x) {
         for (int i = x; i >= a; i--) {
            if (board[y][i] != null) {
               if (i == b && board[y][i].color != board[y][x].color) {
                  return true;
               } else {
                  return false;
               }
            }
         }
         return true;
      }
      if (b == y && a > x) {
         for (int i = x; i <= a; i++) {
            if (board[y][i] != null) {
               if (i == b && board[y][i].color != board[y][x].color) {
                  return true;
               } else {
                  return false;
               }
            }
         }
         return true;
      }
      if (a < x && b < y) {
         if (x - a == y - b) {
            for (int i = 1; x - i >= a; i++) {
               if (board[y-i][x-i] != null) {
                  if (y - i == b && x - i == a && board[y-i][x-i].color != board[y][x].color) {
                     return true;
                  } else {
                     return false;
                  }
               }
            }
            return true;
         }
      }
      if (a < x && b > y) {
         if (x - a == b - y) {
            for (int i = 1; x - i >= a; i++) {
               if (board[y+i][x-i] != null) {
                  if (y + i == b && x - i == a && board[y+i][x-i].color != board[y][x].color) {
                     return true;
                  } else {
                     return false;
                  }
               }
            }
            return true;
         }
      }
      if (a > x && b < y) {
         if (a - x == y - b) {
            for (int i = 1; x + i <= a; i++) {
               if (board[y-i][x+i] != null) {
                  if (y - i == b && x + i == a && board[y-i][x+i].color != board[y][x].color) {
                     return true;
                  } else {
                     return false;
                  }
               }
            }
            return true;
         }
      }
      if (a > x && b > y) {
         if (a - x == b - y) {
            for (int i = 1; x + i <= a; i++) {
               if (board[y+i][x+i] != null) {
                  if (y + i == b && x + i == a && board[y+i][x+i].color != board[y][x].color) {
                     return true;
                  } else {
                     return false;
                  }
               }
            }
            return true;
         }
      }
      return false;
   }
}