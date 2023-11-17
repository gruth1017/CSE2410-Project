import java.util.Random;
public class DebugPiece extends ChessPiece{
    public DebugPiece(boolean color,String fileName){
        super(color, fileName);
    }
    @Override
    public boolean isLegal(ChessPiece[][] board, int y, int x, int i, int j) {
        Random r = new Random();
        return r.nextInt(100)<25;
    }
}
