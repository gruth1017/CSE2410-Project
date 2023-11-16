
// Final implementation
// Deals with mouse clicks
// Uses pressed because click has quirks.
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
public class MouseHandler implements MouseListener{
    private MouseEvent lastClick = null;
    public boolean hasInput(){
        return lastClick!=null;
    }
    public MouseEvent getClick(){
        MouseEvent temp = lastClick;
        lastClick = null;
        return temp;
    }
    @Override
    public void mouseClicked(MouseEvent e){
        //dont care
    }
    @Override
    public void mouseExited(MouseEvent e){
        //dont care
    }
    @Override
    public void mouseEntered(MouseEvent e){
        //dont care
    }
    @Override
    public void mousePressed(MouseEvent e){
        if(lastClick==null){
            lastClick = e;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
        //dont care
    }
}
