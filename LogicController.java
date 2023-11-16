public class LogicController { // TODO @TARIQ
    // notes for tariq from daniel:
    // the only IO methods you should need are movePiece() and highlight()
    // please do not call IO.update();
    // if you need any more IO methods lmk
    // 
    // the only methods you should need from braden are chessPieces.moveList()
    // the only methods you should need frokm ruth are isCheck(), isCheckmate(), isStalemate()
    // any other model methods you need ask one of them 
    public void update(int x, int y, GamePanel IO, ChessBoard board){
        // Test1:
        board.board[0][0] = new Pawn();
        System.out.printf("x: %d y: %d %n",x,y); 
        IO.movePiece(0, 0, x, y);
        IO.highlight(x, y, "assets\\basichighlight.png");
    }
}
