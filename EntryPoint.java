public class EntryPoint { // Entry point. Right now does nothing but call non-static in a static context. May do something in the future.
    public static void main(String[] args){
        Game game = new Game();
        game.run();
    }
}
