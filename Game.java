import java.awt.Point;
public class Game {
    public GameFrame gameFrame;
    public GamePanel IO;
    public ChessBoard board; 
    public LogicController controller;
    public boolean gameOver = false;
    public boolean gameStarted = false;
    public boolean BLACK = true;
    public boolean WHITE = false;
    // ALL CODE FOR ENTIRE PROJECT IS RUN HERE
    public boolean update(){
        Point click = IO.update();
        // if no input
        if(click == null){
            IO.repaint();
            return false;
        }

        //startmenu
        if(!gameStarted){
            sleep();
            gameStarted = true;
            IO.drawScreen(null);
            IO.repaint();
            return false;
        }                
        //restart
        if(gameOver){
            gameFrame.dispose();
            return true;
        }        

        // normal update
        controller.update(click);
        return false;
    }
    private void sleep(){
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // INIT
    public Game(){
        board = new ChessBoard();
        controller = new LogicController();
        IO = new GamePanel(this);
        controller.init(this);
        gameFrame = new GameFrame("Chess",IO);
        IO.drawScreen("assets\\main_menu.png");
    }
}