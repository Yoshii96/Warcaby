package game;

public class Data {

    private final int sizeOfBoard;
    //rozmiar planszy
    private int[][] board;
    //cała tablica, jeżeli 0-puste, 1-nasze, -1-przeciwnika
    //wysyla to zazwyczaj logic do visuala
    //na poczatek wszędzie 0;
    private int[] clicked;
    //tablica, która trzyma x i y, współrzędne kliknięcia, przy inicjalizacji null
    private int[][] moves;
    //tablica możliwch ruchów, maksymalnie 4 możliwe ruchy, współrzędne x i y mówią jakie pole możemy wybrać, na początek nulll
    private int[] chosenMove;
    //tablica, króra trzyma x i y, wybrany ruch, jeden z możliwych, na początek null
    private int[][] lastMove;
    //ostatni ruch komputera, pierwszy wiersz tablicy = gdzie stal wczesniej, drugi wiersz = gdzie teraz, na początek null

    public Data(int sizeOfB) {
        sizeOfBoard = sizeOfB;
        board = new int[sizeOfBoard][sizeOfBoard];
        for (int i = 0; i < sizeOfBoard; i++) {
            for (int j = 0; j < sizeOfBoard; j++) {
                board[i][j] = 0;
            }
        }
        clicked = null;
//        clicked = new int[2];
//        clicked[0] = -1;
//        clicked[1] = -1;
        moves = null;
//        moves = new int[4][2];
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 2; j++) {
//                moves[i][j] = -1;
//            }
//        }
        chosenMove = null;
//        chosenMove = new int[2];
//        chosenMove[0] = -1;
//        chosenMove[1] = -1;
        lastMove = null;
//        lastMove = new int[2][2];
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                lastMove[i][j] = -1;
//            }
//        }
    }

    public int getSizeOfBoard() {
        return sizeOfBoard;
    }

    public int[][] getBoard() {
        return board;
    }

    public int[] getClicked() {
        return clicked;
    }

    public int[][] getMoves() {
        return moves;
    }

    public int[] getChosenMove() {
        return chosenMove;
    }

    public int[][] getLastMove() {
        return lastMove;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setClicked(int[] clicked) {
        this.clicked = clicked;
    }

    public void setMoves(int[][] moves) {
        this.moves = moves;
    }

    public void setChosenMove(int[] chosenMove) {
        this.chosenMove = chosenMove;
    }

    public void setLastMove(int[][] lastMove) {
        this.lastMove = lastMove;
    }

    public void printData() {
        System.out.println("Plansza ma wymiary " + sizeOfBoard + " x " + sizeOfBoard);
        System.out.println("Plansza wygląda tak:");
        for (int i = 0; i < sizeOfBoard; i++) {
            for (int j = 0; j < sizeOfBoard; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
        if (clicked != null) {
            System.out.println("Użytkownik kliknął w pole o współrzędnych = " + clicked[0] + "," + clicked[1]);
        }
        if (moves != null) {
            System.out.println("Możliwe ruchy to");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 2; j++) {
                    System.out.print(moves[i][j] + " ");
                }
                System.out.println("");
            }
        }
        if (chosenMove != null) {
            System.out.println("Użytkownik wybrał ruch na pole o wspórzędnych = " + chosenMove[0] + "," + chosenMove[1]);
        }
        if (lastMove != null) {
            System.out.println("Komputer ruszył się z pola " + lastMove[0][0] + "," + lastMove[0][1] + " ,na pole " + lastMove[1][0] + "," + lastMove[1][1]);
        }
    }
}
