
import java.awt.Dimension;
import java.awt.Graphics;
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
    public final int ANIMATION_FRAMES= 15; // how many frames should animations take
    public final String WHITE_TILE = "assets\\white_tile.png";
    public final String BLACK_TILE = "assets\\black_tile.png";
    //////////////////////////////////
    //       non-constants          //
    //////////////////////////////////
    private LogicController controller; 
    private ChessBoard board;
    private MouseHandler mouse;
    private BufferedImage whiteTile;
    private BufferedImage blackTile;
    private BufferedImage[][] highlightMatrix;
    private int remainingFrames = 0;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private ChessPiece aniPiece;
    //////////////////////////////////
    //            init              //
    //////////////////////////////////
    public GamePanel(LogicController controller,ChessBoard board){
        highlightMatrix = new BufferedImage[BOARD_SIZE][BOARD_SIZE];
        this.board = board;
        mouse = new MouseHandler();
        try{
            whiteTile = ImageIO.read(new File(WHITE_TILE));
            blackTile = ImageIO.read(new File(BLACK_TILE));
        } catch(IOException e){
            e.printStackTrace();
        }
        this.controller = controller;
        this.setPreferredSize(new Dimension(WINDOW_X_RESOLUTION,WINDOW_Y_RESOLUTION));
        this.addMouseListener(mouse);
        this.setFocusable(true);
    }
    // Run submethod for readability. Will parse and pass input to logic controller to decide action
    public void update(){
        if(remainingFrames==1){ // if last frame of animation
            completeMove();
        }
        if(remainingFrames>0){ // if in middle of animation 
            remainingFrames--;
        } else if(mouse.hasInput()){ // update controller if no active animations and user has given input
            MouseEvent e = mouse.getClick();
            if(e==null){
                return;
            }
            final int mx = e.getX()/TILE_WIDTH;
            final int my = e.getY()/TILE_HEIGHT;
            if(mx>=BOARD_SIZE || my >=BOARD_SIZE || mx<0 || my <0){
                return;
            }
            controller.update(mx, my);
        }
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
        remainingFrames = ANIMATION_FRAMES;
    }
    //
    private void completeMove(){
        aniPiece = null;
        x1 = -1;
        x2 = -1;
        y1 = -1;
        y2 = -1;
    }
    // JComponent inherited method to render a frame. ORDER OF OPERATIONS MATTERS
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        paintBackground(g);
        paintHighlight(g);
        paintPieces(g);
        paintAnimation(g);
    }

    // <Sub Methods of paintComponent()> 
    private void paintHighlight(Graphics g){
        for(int i =0; i <highlightMatrix[0].length; i++){
            for(int j =0; j <highlightMatrix.length; j++){
                if(highlightMatrix[j][i]!=null){
                    g.drawImage(highlightMatrix[j][i], i*TILE_WIDTH, j*TILE_HEIGHT, null);
                }
        }
    }
    }
    //
    private void paintPieces(Graphics g){ 
        for(int j = 0; j < board.board[0].length;j++){
            for(int i = 0; i < board.board.length;i++){
                try{
                    ChessPiece p = board.board[j][i];
                    if(p!=null){
                        g.drawImage(ImageIO.read(new File(p.getFileName())), i*TILE_WIDTH, j*TILE_HEIGHT, null);   
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
                    g.drawImage(whiteTile, i*TILE_WIDTH, j*TILE_HEIGHT, null);
                }else{
                    g.drawImage(blackTile, i*TILE_WIDTH, j*TILE_HEIGHT, null);
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
        final int dx = LERP(x1*TILE_WIDTH,x2*TILE_WIDTH,ANIMATION_FRAMES-remainingFrames,ANIMATION_FRAMES);
        final int dy = LERP(y1*TILE_HEIGHT,y2*TILE_HEIGHT,ANIMATION_FRAMES-remainingFrames,ANIMATION_FRAMES);
        g.drawImage(image,dx, dy, null);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //   
    private int LERP(int start, int end, int elapsedFrames, int totalFrames){
        return (int)Math.round(start + (end-start) * ((double)elapsedFrames) /totalFrames);
    }
}

