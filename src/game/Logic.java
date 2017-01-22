package game;

import java.util.ArrayList;

public class Logic {

    private final Game game;
    private final int SIZEOFBOARD;
    private final int NUMBERSOFROWSPLAYING;
    private int[][] board;
    private int white; //player
    private int black; //computer
    private int whiteQueens; //player
    private int blackQueens;//computer
    

    public Logic(Game game, int sizeOfBoard, int numberOfRowsPlaying) {
        this.game = game;
        this.SIZEOFBOARD = sizeOfBoard;
        this.NUMBERSOFROWSPLAYING = numberOfRowsPlaying;
        this.board = new int[SIZEOFBOARD][SIZEOFBOARD];
        setUpNewBoard();
    }

    private void setUpNewBoard() {
        for (int i = 0; i < SIZEOFBOARD; i++) {
            for (int j = 0; j < SIZEOFBOARD; j++) {
                this.board[i][j] = 0;
            }
        }
        for (int i = 0; i < NUMBERSOFROWSPLAYING; i++) {
            for (int j = 0; j < SIZEOFBOARD; j += 2) {
                int tmp = j;
                if (i % 2 == 0) {
                    tmp++;
                    if(tmp == SIZEOFBOARD){
                        continue;
                    }
                }
                board[i][tmp] = 1;
            }
        }
        for (int i = SIZEOFBOARD - 1; i > SIZEOFBOARD - 1 - NUMBERSOFROWSPLAYING; i--) {
            for (int j = 0; j < SIZEOFBOARD; j += 2) {
                int tmp = j;
                if (i % 2 == 0) {
                    tmp++;
                    if(tmp == SIZEOFBOARD){
                        continue;
                    }
                }
                board[i][tmp] = -1;
            }
        }
        Data data = new Data(SIZEOFBOARD);
        data.setBoard(board);
        sendData(data);
    }

    public void sendData(Data data) {
        game.passToVisual(data);
    }

    public void getData(Data data) {
        //Jeżeli ta metoda jest wywołana to znaczy, że użytkownik wcisnął przycisk a wewnątrz obiektu data jest wszytko to co potrzebujesz
        dataState (data);
    }
    
    public void dataState (Data data) {
        if (data.getMoves()==null) {
            int[] clicked = new int[2];
            clicked = data.getClicked();
            data.setMoves(getPlayerMoves(getMovesForOnePiece(clicked)));
        }
        else if (data.getLastMove()==null) {
            ArrayList<Move> moves = getMovesForOnePiece(data.getClicked());
            makeMove(moves.get(data.getChosenMove()), -1);
            data.setBoard(board);
            sendData(data);
            Move move = computerMove();
            int[][] compLastMove = new int[2][2];
            compLastMove[0] = move.getFromPiece();
            compLastMove[1] = move.getToPiece();
            data.setClicked(null);
            data.setMoves(null);
            data.setChosenMove(-1);
            data.setLastMove(compLastMove);
            data.setBoard(board);
        }
        sendData(data);
    }

        
        
        
    public ArrayList<Move> getMoves (int player) { //zwraca liste mozliwych ruchow dla danego gracza
        ArrayList<Move> movesAll = new ArrayList<Move>();
        ArrayList<Move> moves;
        Move move;
        for (int i = 0; i < 8; i++) { 
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == player) {
                    if ((moves=checkMove(i, j, player))!=null) //sprawdza ruchy dla danego piona
                        movesAll.addAll(checkMove(i, j, player));
                }
                else if (board[i][j] == (player*2)) {
                    if ((moves=checkMoveForQueen(i, j, player))!=null) //sprawdza ruchy dla danej damki
                        movesAll.addAll(checkMoveForQueen(i, j, player));
                }
            }
        }
        return movesAll;
    }
    
    public ArrayList<Move> getMovesForOnePiece (int[] clicked) {
        if (board[clicked[0]][clicked[1]]==-1)
            return checkMove(clicked[0], clicked[1], -1);
        else 
            return checkMoveForQueen(clicked[0], clicked[1], -1);
    }
    
    public int[][] getPlayerMoves (ArrayList<Move> movesList) {
        if (movesList == null)
            return null;
        int[][] movesArray = new int[20][2];
        int size = movesList.size();
        for (int i = 0; i < 20; i++) {
            if (i<size)
                movesArray[i] = movesList.get(i).getToPiece();
            else {
                movesArray[i][0] = -1;
                movesArray[i][1] = -1;
            }
        }
        return movesArray;
    }
    
    public ArrayList<Move> checkMove (int fromY, int fromX, int player) { //sprawdza ruchy dla danego piona
        ArrayList<Move> moves = new ArrayList<Move>();
        if (player == -1) {
            if ((fromY-1)>=0 && (fromX-1)>=0 && board[fromY-1][fromX-1]==0)
                moves.add(new Move (fromY, fromX, fromY-1, fromX-1, false, 0));
            else if ((fromY-2)>=0 && (fromX-2)>=0 && (board[fromY-1][fromX-1]==(-player) || board[fromY-1][fromX-1]==(2*(-player))) && board[fromY-2][fromX-2]==0)
                moves.add(0, new Move (fromY, fromX, fromY-2, fromX-2, true, board[fromY-1][fromX-1]));
            if ((fromY-1)>=0 && (fromX+1)<=7 && board[fromY-1][fromX+1]==0)
                moves.add(new Move (fromY, fromX, fromY-1, fromX+1, false, 0));
            else if ((fromY-2)>=0 && (fromX+2)<=7 && (board[fromY-1][fromX+1]==(-player) || board[fromY-1][fromX+1]==(2*(-player))) && board[fromY-2][fromX+2]==0)
                moves.add(0, new Move (fromY, fromX, fromY-2, fromX+2, true, board[fromY-1][fromX+1]));
            if ((fromY+2)<=7 && (fromX+2)<=7 && (board[fromY+1][fromX+1]==(-player) || board[fromY+1][fromX+1]==(2*(-player))) && board[fromY+2][fromX+2]==0)
                moves.add(0, new Move (fromY, fromX, fromY+2, fromX+2, true, board[fromY+1][fromX+1]));
            if ((fromY+2)<=7 && (fromX-2)>=0 && (board[fromY+1][fromX-1]==(-player) || board[fromY+1][fromX-1]==(2*(-player))) && board[fromY+2][fromX-2]==0)
                moves.add(0, new Move (fromY, fromX, fromY+2, fromX-2, true, board[fromY+1][fromX-1]));
        }
        else if (player == 1) {               
            if ((fromY-2)>=0 && (fromX-2)>=0 && (board[fromY-1][fromX-1]==(-player) || board[fromY-1][fromX-1]==(2*(-player))) && board[fromY-2][fromX-2]==0)
                moves.add(0, new Move (fromY, fromX, fromY-2, fromX-2, true, board[fromY-1][fromX-1]));
            if ((fromY-2)>=0 && (fromX+2)<=7 && (board[fromY-1][fromX+1]==(-player) || board[fromY-1][fromX+1]==(2*(-player))) && board[fromY-2][fromX+2]==0)
                moves.add(0, new Move (fromY, fromX, fromY-2, fromX+2, true, board[fromY-1][fromX+1]));
            if ((fromY+1)<=7 && (fromX+1)<=7 && board[fromY+1][fromX+1]==0)
                moves.add(new Move (fromY, fromX, fromY+1, fromX+1, false, 0));
            else if ((fromY+2)<=7 && (fromX+2)<=7 && (board[fromY+1][fromX+1]==(-player) || board[fromY+1][fromX+1]==(2*(-player))) && board[fromY+2][fromX+2]==0)
                moves.add(0, new Move (fromY, fromX, fromY+2, fromX+2, true, board[fromY+1][fromX+1]));
            if ((fromY+1)<=7 && (fromX-1)>=0 && board[fromY+1][fromX-1]==0)
                moves.add(new Move (fromY, fromX, fromY+1, fromX-1, false, 0));
            else if ((fromY+2)<=7 && (fromX-2)>=0 && (board[fromY+1][fromX-1]==(-player) || board[fromY+1][fromX-1]==(2*(-player))) && board[fromY+2][fromX-2]==0)
                moves.add(0, new Move (fromY, fromX, fromY+2, fromX-2, true, board[fromY+1][fromX-1]));
        }
        if (moves.size()==0)
            return null;
        return moves;
    }
    
    public ArrayList<Move> checkMoveForQueen (int fromY, int fromX, int player) { //sprawdza ruchy dla danej damki
        ArrayList<Move> moves = new ArrayList<Move>();
        int i = fromY;
        int j= fromX;
        while ((i-1)>=0 && (j-1)>=0 && board[i-1][j-1]==0) {
            moves.add(new Move (fromY, fromX, i-1, j-1, false, 0));
            --i;
            --j;
        }
        --i;
        --j;
        if ((i-1)>=0 && (j-1)>=0 && (board[i][j]==(-player) || board[i][j]==(2*(-player))) && board[i-1][j-1]==0)
            moves.add(new Move (fromY, fromX, i-1, j-1, true, board[i][j]));
        
        i = fromY;
        j= fromX;
        while ((i-1)>=0 && (j+1)<=7 && board[i-1][j+1]==0) {
            moves.add(new Move (fromY, fromX, i-1, j+1, false, 0));
            --i;
            ++j;
        }
        --i;
        ++j;
        if ((i-1)>=0 && (j+1)<=7 && (board[i][j]==(-player) || board[i][j]==(2*(-player))) && board[i-1][j+1]==0)
            moves.add(new Move (fromY, fromX, i-1, j+1, true, board[i][j]));
    
        i = fromY;
        j= fromX;
        while ((i+1)<=7 && (j+1)<=7 && board[i+1][j+1]==0) {
            moves.add(new Move (fromY, fromX, i+1, j+1, false, 0));
            ++i;
            ++j;
        }
        ++i;
        ++j;
        if ((i+1)<=7 && (j+1)<=7 && (board[i][j]==(-player) || board[i][j]==(2*(-player))) && board[i+1][j+1]==0)
            moves.add(new Move (fromY, fromX, i+1, j+1, true, board[i][j]));

        i = fromY;
        j= fromX;
        while ((i+1)<=7 && (j-1)>=0 && board[i+1][j-1]==0) {
            moves.add(new Move (fromY, fromX, i+1, j-1, false, 0));
            ++i;
            --j;
        }
        ++i;
        --j;
        if ((i+1)<=7 && (j-1)>=0 && (board[i][j]==(-player) || board[i][j]==(2*(-player))) && board[i+1][j-1]==0) {
            moves.add(new Move (fromY, fromX, i+1, j-1, true, board[i][j]));
        }
        if (moves.size()==0)
            return null;
        return moves;
    }
    
    public int minimax (int player, int depth) {//liczy wartosc dnego ruchu komputera
        int winner, value, checkValue;
        Move move;
        if (depth == 2)
            return -player * gameState ();
        player = (player == 1) ? -1 : 1;
        checkValue = (player == 1) ? 1000 : -1000;
        ArrayList<Move> moves = getMoves(player);
        for(int i=0; i<moves.size(); i++) {
            move = moves.get(i);
            makeMove (move, player);
            System.out.println(toString ());
            value = minimax(player, depth + 1);
            undoMove (move, player);
            System.out.println(toString ());
            if(((player == 1) && (value < checkValue)) || ((player == -1) && (value > checkValue)))
                checkValue = value;
            }
        return checkValue;
    }

    public Move computerMove () { //wykonuje ruch komputera
        int value, checkValue;
        Move move, newMove = null;
        checkValue = -1000;
        ArrayList<Move> moves = getMoves(1);
        newMove = moves.get(0);
        for(int i=0; i<moves.size(); i++) {
            move = moves.get(i);
            if (move.getMustBeat() == true) {
                newMove = move;
                break;
            }
            move.printMove();
            makeMove (move, 1);
            value = minimax(1, 0);
            undoMove (move, 1);
            if(value > checkValue) {
                checkValue = value;
                newMove = move;
            }
        }
        makeMove (newMove, 1);
        return newMove;
    }
    
    public int gameState () { //zwraca stan gry
        return black - white + 2*(blackQueens - whiteQueens);
    }
    
    public void makeMove (Move move, int player) { //robi ruch na planszy
        int[] from = move.getFromPiece();
        int[] to = move.getToPiece();
        boolean beat = move.getMustBeat();
        //if (board[from[0]][from[1]] == 2);
            //move.setWasQuenn(true);
        board[from[0]][from[1]] = 0;
        if ((player == 1 && to[0] == 7) || (player == -1 && to[0] == 0)) {
            board[to[0]][to[1]] = 2*player;
            if (!move.getWasQuenn()) {
                if (player == -1) {
                --black;
                ++blackQueens;
                }
                else {
                --white;
                ++whiteQueens;
                }
            }
        }
        else
            board[to[0]][to[1]] = player;
        if (beat == true) {
            board[to[0]-((to[0]-from[0])/Math.abs(to[0]-from[0]))][to[1]-((to[1]-from[1])/Math.abs(to[1]-from[1]))] = 0;
            if (player == -1 && (move.getBeaten()==(-2*player)))
                --whiteQueens;
            else if (player == -1 && (move.getBeaten()==0))
                --white;
            else if (player == 1 && (move.getBeaten()==(-2*player)))
                --blackQueens;
            else if (player == 1 && (move.getBeaten()==0))
                --black;
        }
    }
    
    public void undoMove (Move move, int player) { //cofa wczesniej zrobiony ruch
        int[] from = move.getFromPiece();
        int[] to = move.getToPiece();
        boolean beat = move.getMustBeat();
        board[to[0]][to[1]] = 0;
        //if ()
        //if (move.getWasQuenn())
            //board[from[0]][from[1]] = 2*player;
        //else {
            board[from[0]][from[1]] = player;
            //if (player == 1)
                //--blackQueens;
                
            //}
        if (beat == true) {
            board[to[0]-((to[0]-from[0])/Math.abs(to[0]-from[0]))][to[1]-((to[1]-from[1])/Math.abs(to[1]-from[1]))] = move.getBeaten();
            if (player == -1)
                ++white;
            else
                ++black;
        }
    }
    
    public String toString(){
        String s = "board: ";
        for(int a = 0; a < board.length; a++){
            s += "\n";
            for(int b = 0; b < board[0].length; b++){
                switch(board[a][b]){
                    case(-2): s += 'B'; break;
                    case(-1): s += 'b'; break;
                    case(0): s += '_'; break;
                    case(1): s += 'r'; break;
                    case(2): s += 'R'; break;
                    default: break;
                }
                s += " ";
            }
        }
        return s;
    }
}

