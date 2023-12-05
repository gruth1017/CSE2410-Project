// JFrame boiler plate
import javax.swing.JFrame;
import java.awt.BorderLayout;
public class GameFrame extends JFrame{
    public GameFrame(String name, GamePanel gamePanel){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        add(gamePanel,BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
