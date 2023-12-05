import java.awt.Point;
public class Pawn extends ChessPiece{
    public Pawn(boolean color,String fileName){
        super(color, fileName);
    }
    public boolean isLegal (ChessPiece[][] board, int y, int x, int a, int b) {
        if (color) {
            if (a == x && b == y) {
                return false;
            }
            if (a == x && b == y + 1 && board[b][a] == null) {
               return true;
            }
            if (a == x && b == y + 2 && board [b][a] == null && board [b-1][a] == null && !hasMoved) {
               return true;
            }
            if (a == x + 1 && b == y + 1 && board[b][a] != null) {
                if (board[b][a].color != board[y][x].color) {
                    return true;
                }
            }
            if (a == x - 1 && b == y + 1 && board[b][a] != null) {
                if (board[b][a].color != board[y][x].color) {
                    return true;
                }
            }
    //        if (a == x + 1 && b == y + 1 && board[y][x+1].color != board[y][x].color && board[y][x+1].passing) {
    //           return true;
    //        }
    //        if (a == x - 1 && b == y + 1 && board[y][x-1].color != board[y][x].color && board[y][x-1].passing) {
    //           return true;
    //        }
        } else {
            if (!color) {
                if (a == x && b == y - 1 && board[b][a] == null) {
                   return true;
                }
               if (a == x && b == y - 2 && board [b][a] == null && board [b-1][a] == null && !hasMoved) {
                   return true;
               }
               if (a == x + 1 && b == y - 1 && board[b][a] != null) {
                  if (board[b][a].color != board[y][x].color) {
                     return true;
                  }
               }
                if (a == x - 1 && b == y - 1 && board[b][a] != null) {
                    if (board[b][a].color != board[y][x].color) {
                        return true;
                    }
                }
    //           if (a == x + 1 && b == y - 1 && board[y][x+1].color != board[y][x].color && board[y][x+1].passing) {
    //              return true;
    //           }
    //           if (a == x - 1 && b == y - 1 && board[y][x-1].color != board[y][x].color && board[y][x-1].passing) {
    //              return true;
    //           }
           }
        }
        return false;
    }
    public void move (GamePanel IO, ChessPiece[][] board, Point a, Point b) {
        super.move(IO, board, a,b);
        if (b.y == 0 || b.y == 7) {
           board[b.y][b.x] = new Queen(this.color, "assets\\queen.png");
        }
        if (a.y == b.y + 2) {
           passing = true;
        }
        hasMoved = true;
     }
}
