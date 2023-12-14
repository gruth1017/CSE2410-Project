public class EntryPoint { 
    public final static int FPS = 240; // FPS CAP    
    public static void main(String[] args){
        while (true) {
            Game game = new Game();
            run(game);   
        }
    }
        // MAIN GAME LOOP DO NOT TOUCH
        public static void run(Game game){
            long frameDelay = 1000000000/FPS;
            long nextUpdate = System.nanoTime()+(long)frameDelay;
             while(true){
                if(nextUpdate<System.nanoTime()){
                    nextUpdate+=frameDelay;
                    if(game.update()){
                        return;
                    };
                }
             }   
        }
}
