// JFrame boiler plate
import javax.swing.JFrame;
import java.awt.BorderLayout;
public class GameFrame extends JFrame{
    public final int FPS = 60;
    private GamePanel gamePanel;
    public GameFrame(String name){
        super(name);
    }
    public void run(LogicController controller, ChessBoard board){
        gamePanel = new GamePanel(controller, board);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(new BorderLayout());
        add(gamePanel,BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    // FPS LOOP DO NOT TOUCH
    // a certain number of times per second, update all visual component, then render them.
    // uses a dynamic measurement of time to account for processing lag.
    long frameDelay = 1000000000/FPS;
    long nextUpdate = System.nanoTime()+(long)frameDelay;
     while(true){
        if(nextUpdate<System.nanoTime()){
            nextUpdate+=frameDelay;
            gamePanel.update();
            gamePanel.repaint();
        }
     }   
    }
}
