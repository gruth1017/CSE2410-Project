import java.awt.Point;
public class Game {
    public GameFrame gameFrame;
    public GamePanel IO;
    public ChessBoard board; 
    public LogicController controller;
    public final int FPS = 240; // FPS CAP    
    private String menuState = "GAME";

    // ALL CODE FOR ENTIRE PROJECT IS RUN HERE
    private void update(){
        Point click = IO.update();
        if(menuState.equals("GAME")){
            if(click!=null){
                controller.update(click);
            }
        }
        // TODO menuing
    }

    // INIT
    public Game(){
        board = new ChessBoard();
        controller = new LogicController();
        IO = new GamePanel(this);
        controller.init(this);
        gameFrame = new GameFrame("Chess",IO);
    }
    // MAIN GAME LOOP DO NOT TOUCH
    public void run(){
        long frameDelay = 1000000000/FPS;
        long nextUpdate = System.nanoTime()+(long)frameDelay;
         while(true){
            if(nextUpdate<System.nanoTime()){
                nextUpdate+=frameDelay;
                update();
                IO.repaint();
            }
         }   
    }
}