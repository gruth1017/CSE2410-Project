import java.awt.Point;
import java.util.ArrayList;
public abstract class ChessPiece { //TODO @BRADEN
    protected String fileName; 
    protected boolean hasMoved = false;
    protected boolean color;
    protected boolean passing = false;
   //  protected String type; Dont need; use instanceOf()
    public ChessPiece(boolean color, String fileName){
        this.fileName = fileName;
        this.color = color;
    }
    public String getFileName() {
        return fileName;
    }
    protected ArrayList<Point> getLegalMoves (ChessPiece[][] board, int y, int x) {
        ArrayList<Point> output = new ArrayList<Point>();
        for (int i = 0; i < 8; i++) {
           for (int j = 0; j < 8; j++) {
              boolean legal = isLegal(board, y, x, i, j);
              if (legal) {
                 output.add(new Point(i,j));
              }
           }
        }
        return output;
     }
    public void move (GamePanel IO, ChessPiece[][] board, Point a, Point b) {
        board[a.y][a.x] = null;
        IO.movePiece(this, a.x, a.y, b.x, b.y);
        board[b.y][b.x] = this;
        hasMoved = true;
     }
     public abstract boolean isLegal(ChessPiece[][] board, int y, int x, int i, int j);
    }
