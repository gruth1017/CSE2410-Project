import java.awt.Point;
import java.util.ArrayList;


public class LogicController { // TODO @TARIQ
    // any time the user clicks on a tile, this function will get called with the x and y coordinate clicked,
    // as well as the necesary objects to call in order to perform function.
    // the only visual methods you should need are IO.highlight, IO.resetHighlight(), and this.movePiece()
    public GamePanel IO;
    public boolean playerturn= false;
    public ChessBoard board;
    public boolean firstinput= false, secondinput= false;
    public int x1,y1,x2,y2;
    ChessPiece sendoff;
     ArrayList<Point> validMoves;
    public void init(GamePanel IO, ChessBoard board){
        this.IO = IO;
        this.board = board;
    } 
    public void update(int x, int y){
        if(board.emptyspace(x, y )&& firstinput==false){return;}
        else
        {test1(x,y);} 
    }
    private void movePiece(int x1, int y1,int x2,int y2){ // readability method
        if (sendoff.color==playerturn) {
            playerturn=!playerturn;
            sendoff.move(IO,board,new Point(x1,y1),new Point(x2,y2));
        }
        else{return;}
        
        
    }
    private void test1(int x,int y){ 
        /////////////////////////// 
        //     Basic Test 1:     //
        ///////////////////////////
        //every time the user clicks on a square:
        
        System.out.printf("x: %d y: %d %n",x,y); // 1. print what square the user clicked on
        IO.resetHighlight(); // 2. unhighlight all squares 
        //gets the piece you want to move and locks it in as the first position
        if (firstinput== false) {
            x1=x;
            y1=y;
            firstinput = true; 
            sendoff=board.SetPos(x1,y1);
            validMoves = sendoff.getLegalMoves(sendoff, y1, x1); // 4. get the list of valid moves for the piece in the top left (debugPiece returns a random list)
        for(Point p : validMoves){ // 5. highlight all squares in the retrieved list 
            IO.highlight(p.x, p.y, "assets\\basichighlight.png");
        }  
         
        }
        //gets where you want to move the piece to and locks it in as the second position
        else if(secondinput==false){
            int i =0;
            for (Point iterable_element : validMoves) {
               
                if (validMoves.get(i).getX()==x && validMoves.get(i).getY()==y) {
                x2=x;
                y2=y;
                secondinput = true;
                }    
                i++;
                if(i<validMoves.size()){firstinput=false;}

            }
        }
        // moves the piece when both positions are gained
        if (secondinput==true) {
            
        
        
        

        movePiece(x1,y1,x2,y2); // 6. move the piece in the top left to the square the user clicked on.
        firstinput=false;
        secondinput=false;
        }
    }
}
