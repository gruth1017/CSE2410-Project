import java.awt.Point;

public class pawn extends chessPiece {
   public pawn (final boolean color) {
   }
   public boolean isLegal (chessPiece[][] board, int y, int x, int a, int b) {
      if (color) {
         if (a == x && b == y + 1 && board[b][a] == null) {
            return true;
         }
         if (a == x && b == y + 2 && board [b][a] == null && board [b-1][a] == null && !hasMoved) {
            return true;
         }
         if (a == x + 1 && b == y + 1 && board[b][a].color != board[y][x].color) {
            return true;
         }
         if (a == x - 1 && b == y + 1 && board[b][a].color != board[y][x].color) {
            return true;
         }
         if (a == x + 1 && b == y + 1 && board[y][x+1].color != board[y][x].color && board[y][x+1].passing) {
            return true;
         }
         if (a == x - 1 && b == y + 1 && board[y][x-1].color != board[y][x].color && board[y][x-1].passing) {
            return true;
         }
      } else {
         if (!color) {
            if (a == x && b == y - 1 && board[b][a] == null) {
               return true;
            }
            if (a == x && b == y - 2 && board [b][a] == null && board [b-1][a] == null && !hasMoved) {
               return true;
            }
            if (a == x + 1 && b == y - 1 && board[b][a].color != board[y][x].color) {
               return true;
            }
            if (a == x - 1 && b == y - 1 && board[b][a].color != board[y][x].color) {
               return true;
            }
            if (a == x + 1 && b == y - 1 && board[y][x+1].color != board[y][x].color && board[y][x+1].passing) {
               return true;
            }
            if (a == x - 1 && b == y - 1 && board[y][x-1].color != board[y][x].color && board[y][x-1].passing) {
               return true;
            }
         }
      }
      return false;
   }
   @Override public void move (chessPiece[][] board, chessPiece piece, int y, int x, Point a) {
      board[y][x] = null;
      if (a.y == 0 || a.y == 7) {
         board[a.y][a.x] = new queen (piece.color);
      }
      board[a.y][a.x] = piece;
      if (a.y == y + 2) {
         passing = true;
      }
      hasMoved = true;
   }
}