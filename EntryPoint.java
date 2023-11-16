public class EntryPoint { // Entry point. Right now does nothing but call non-static in a static context. May do something in the future.
    public static void main(final String[] args){
        GameFrame mainWindow = new GameFrame("Chess");
        ChessBoard chessBoard = new ChessBoard();
        LogicController logicController = new LogicController();
        mainWindow.run(logicController, chessBoard);
    }
}
