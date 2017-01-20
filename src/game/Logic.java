package game;

public class Logic {

    private final Game game;
    private final int SIZEOFBOARD;
    private final int NUMBERSOFROWSPLAYING;
    private final int[][] board;

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
                board[i][tmp] = 2;
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
                board[i][tmp] = 1;
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
        data.printData();
    }

}
