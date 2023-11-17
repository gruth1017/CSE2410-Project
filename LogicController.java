import java.awt.Point;
import java.util.ArrayList;
public class LogicController { // TODO @TARIQ
    // any time the user clicks on a tile, this function will get called with the x and y coordinate clicked,
    // as well as the necesary objects to call in order to perform function.
    // the only visual methods you should need are IO.highlight, IO.resetHighlight(), and this.movePiece()
    public GamePanel IO;
    public ChessBoard board;
    public void init(GamePanel IO, ChessBoard board){
        this.IO = IO;
        this.board = board;
    } 
    public void update(int x, int y){
        test1(x,y); 
    }
    private void movePiece(int x1, int y1,int x2,int y2){ // readability method
        board.board[y1][x1].move(IO, board.board,new Point(x1,y1),new Point(x2,y2));
    }
    private void test1(int x,int y){ 
        /////////////////////////// 
        //     Basic Test 1:     //
        ///////////////////////////
        //every time the user clicks on a square:
        System.out.printf("x: %d y: %d %n",x,y); // 1. print what square the user clicked on 
        IO.resetHighlight(); // 2. unhighlight all squares 
        board.board[0][0] = new DebugPiece(true,"assets\\debug.png"); // 3. create a new debugPiece at the top left
        ArrayList<Point> validMoves = board.board[0][0].getLegalMoves(board.board, y, x); // 4. get the list of valid moves for the piece in the top left (debugPiece returns a random list)
        for(Point p : validMoves){ // 5. highlight all squares in the retrieved list 
            IO.highlight(p.x, p.y, "assets\\basichighlight.png");
        }
        movePiece(0,0,x,y); // 6. move the piece in the top left to the square the user clicked on.
        
    }
}
