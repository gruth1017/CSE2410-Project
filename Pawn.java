import java.util.ArrayList;
public class Pawn extends ChessPiece{ //TODO Braden
    public Pawn(){
        super("assets\\pawn.png");
    }
    // read super class comments.
    @Override
    public ArrayList<int[]> moveList(){ // TODO method stub
        ArrayList<int[]>output = new ArrayList<>();
        output.add(new int[]{1,2});
        return output;
    } 
}
