
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
public class GamePanel extends JPanel{
    //////////////////////////////////
    //         constants            //
    //////////////////////////////////
    public final int TILE_HEIGHT = 96;
    public final int TILE_WIDTH = 96;
    public final int BOARD_SIZE = 8;
    public final int WINDOW_X_RESOLUTION = TILE_WIDTH*BOARD_SIZE;
    public final int WINDOW_Y_RESOLUTION = TILE_HEIGHT*BOARD_SIZE;
    public final double ANIMATION_TIME = 0.5; // how many seconds animations should take
    public final String WHITE_TILE = "assets\\white_tile.png";
    public final String BLACK_TILE = "assets\\black_tile.png";
    //////////////////////////////////
    //       non-constants          //
    //////////////////////////////////
    private MouseHandler mouse;
    private BufferedImage whiteTile;
    private BufferedImage blackTile;
    private BufferedImage screenOverlay;
    private BufferedImage[][] highlightMatrix;
    private int remainingFrames;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private ChessPiece aniPiece;
    private int animationFrames;
    private Game Chess;
    //////////////////////////////////
    //            init              //
    //////////////////////////////////
    public GamePanel(Game Chess){
        this.Chess = Chess;
        highlightMatrix = new BufferedImage[BOARD_SIZE][BOARD_SIZE];
        mouse = new MouseHandler();
        try{
            whiteTile = ImageIO.read(new File(WHITE_TILE));
            blackTile = ImageIO.read(new File(BLACK_TILE));
        } catch(IOException e){
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(WINDOW_X_RESOLUTION,WINDOW_Y_RESOLUTION));
        this.addMouseListener(mouse);
        this.setFocusable(true);
        this.animationFrames = (int)Math.round((ANIMATION_TIME * EntryPoint.FPS));
        remainingFrames = 0;
    }
    // Run submethod for readability. Will parse and pass input to logic controller to decide action
    public Point update(){
        if(remainingFrames==1){ // if last frame of animation
            completeMove();
        }
        if(remainingFrames>0){ // if in middle of animation 
            remainingFrames--;
        } else if(mouse.hasInput()){ // update controller if no active animations and user has given input
            MouseEvent e = mouse.getClick();
            if(e==null){
                return null;
            }
            final int mx = e.getX()/TILE_WIDTH;
            final int my = e.getY()/TILE_HEIGHT;
            if(mx>=BOARD_SIZE || my >=BOARD_SIZE || mx<0 || my <0){
                return null;
            }
            return new Point(mx,my);
        }
        return null;
    }

    // <Highlight Control Code>
    public void highlight(int x, int y, String fileName){
        if(fileName==null){
            highlightMatrix[y][x] = null;
        }else{
            try{
                highlightMatrix[y][x] = ImageIO.read(new File(fileName));
            }catch(IOException e){
                e.printStackTrace();
            } 
        }

    }
    //
    public void resetHighlight(){
        highlightMatrix = new BufferedImage[BOARD_SIZE][BOARD_SIZE];
    }
    // <Animation Control Code>
    public void movePiece(ChessPiece aniPiece, int startx, int starty, int endx, int endy){
        this.aniPiece = aniPiece;
        if(aniPiece==null){
            return;
        }
        x1 = startx;
        x2 = endx;
        y1 = starty;
        y2 = endy;
        remainingFrames = animationFrames;
    }
    //
    private void completeMove(){
        aniPiece = null;
        x1 = -1;
        x2 = -1;
        y1 = -1;
        y2 = -1;
    }
    public void drawScreen(String fileName){
        if(fileName==null){
            screenOverlay = null;
        }else{
            try{
                screenOverlay= ImageIO.read(new File(fileName));
            }catch(IOException e){
                e.printStackTrace();
            } 
        }
    }
    // JComponent inherited method to render a frame. ORDER OF OPERATIONS MATTERS
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        paintBackground(g);
        paintHighlight(g);
        paintPieces(g);
        paintAnimation(g);
        paintOverlay(g);
    }

    // <Sub Methods of paintComponent()> 
    private void paintHighlight(Graphics g){
        for(int i =0; i <highlightMatrix[0].length; i++){
            for(int j =0; j <highlightMatrix.length; j++){
                if(highlightMatrix[j][i]!=null){
                    drawSprite(g, highlightMatrix[j][i], i*TILE_WIDTH, j*TILE_HEIGHT);
                }
        }
    }
    }
    //
    private void paintPieces(Graphics g){ 
        for(int i = 0; i < Chess.board.size();i++){
            for(int j = 0; j < Chess.board.size();j++){
                try{
                    ChessPiece p = Chess.board.get(i,j);
                    if(p!=null){
                        drawSprite(g, ImageIO.read(new File(p.getFileName())), i*TILE_WIDTH, j*TILE_HEIGHT);   
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
                }
        }
    }
    //
    private void paintBackground(Graphics g){
        for(int i =0;i<BOARD_SIZE;i++){
            for(int j =0;j<BOARD_SIZE;j++){
                if((i+j)%2 ==0){
                    drawSprite(g, whiteTile, i*TILE_WIDTH, j*TILE_HEIGHT);
                }else{
                    drawSprite(g, blackTile, i*TILE_WIDTH, j*TILE_HEIGHT);
                }
            }
        }
    }
    //
    private void paintAnimation(Graphics g){
        if(aniPiece==null){
            return;
        }
        try{
        BufferedImage image = ImageIO.read(new File(aniPiece.getFileName())); 
        final int dx = LERP(x1*TILE_WIDTH,x2*TILE_WIDTH,animationFrames-remainingFrames,animationFrames);
        final int dy = LERP(y1*TILE_HEIGHT,y2*TILE_HEIGHT,animationFrames-remainingFrames,animationFrames);
        drawSprite(g, image,dx, dy);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //   
    private int LERP(int start, int end, int elapsedFrames, int totalFrames){
        return (int)Math.round(start + (end-start) * ((double)elapsedFrames) /totalFrames);
    }
    private void drawSprite(Graphics g, BufferedImage i,int x ,int y){
        g.drawImage(i,x, y,TILE_WIDTH,TILE_HEIGHT,null);
    }
    private void paintOverlay(Graphics g){
        if(screenOverlay==null){
            return;
        }
        g.drawImage(screenOverlay,0, 0,WINDOW_X_RESOLUTION,WINDOW_Y_RESOLUTION,null);
    }
}
