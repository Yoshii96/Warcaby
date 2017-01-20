package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Visual {

    private final Game game;
    private final JFrame frame;
    private final JPanel boardPanel;
    private int SIZEOFBOARD = 8;
    private final JButton[][] buttons;
    private Data data;

    public Visual(Game game, int sizeOfBoard) {
        this.SIZEOFBOARD = sizeOfBoard;
        this.game = game;
        boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(500, 500));
        boardPanel.setLayout(new GridLayout(SIZEOFBOARD, SIZEOFBOARD));
        buttons = new JButton[SIZEOFBOARD][SIZEOFBOARD];
        for (int i = 0; i < SIZEOFBOARD; i++) {
            for (int j = 0; j < SIZEOFBOARD; j++) {
                JButton tmpButton = new JButton("");
                if (i % 2 != 0) {
                    if (j % 2 == 0) {
                        tmpButton.setBackground(Color.black);
                    } else {
                        tmpButton.setBackground(Color.white);
                    }
                } else if (j % 2 != 0) {
                    tmpButton.setBackground(Color.black);
                } else {
                    tmpButton.setBackground(Color.white);
                }
                tmpButton.setBorder(BorderFactory.createEmptyBorder());
                tmpButton.addActionListener(new Click());
                buttons[i][j] = tmpButton;
                boardPanel.add(tmpButton);

            }
        }
        frame = new JFrame("Warcaby");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(boardPanel);
        //frame.setResizable(true);
        //frame.setPreferredSize(new Dimension(300, 300));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();

        this.data = null;
    }

    public class Click implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev2) {
            int x = -1;
            int y = -1;
            for (int i = 0; i < SIZEOFBOARD; i++) {
                for (int j = 0; j < SIZEOFBOARD; j++) {
                    if (ev2.getSource() == buttons[i][j]) {
                        x = i;
                        y = j;
                        int[][] board = data.getBoard();
                        if(board[x][y] == 2){
                            return;
                        }
                        break;
                    }
                }
                if (x != -1) {
                    break;
                }
            }
            int[] clicked = new int[2];
            clicked[0] = x;
            clicked[1] = y;
            data.setClicked(clicked);
            sendData(data);
            //tutaj wysyłanie damych do Logic
        }
    }

    private void updateBoard(Data data) {
        if (SIZEOFBOARD != data.getSizeOfBoard()) {
            System.err.println("Nieprawidłowy rozmiar!");
            return;
        }
        try {
            Image imgRED = ImageIO.read(new File("src/resources/RED.png"));
            Image imgWHITE = ImageIO.read(new File("src/resources/WHITE.png"));
            Dimension buttonDimension = buttons[0][0].getSize();
            imgRED = imgRED.getScaledInstance(buttonDimension.width, buttonDimension.height, Image.SCALE_DEFAULT);
            imgWHITE = imgWHITE.getScaledInstance(buttonDimension.width, buttonDimension.height, Image.SCALE_DEFAULT);

            for (int i = 0; i < SIZEOFBOARD; i++) {
                for (int j = 0; j < SIZEOFBOARD; j++) {
                    if (i % 2 != 0) {
                        if (j % 2 == 0) {
                            buttons[i][j].setBackground(Color.black);
                        } else {
                            buttons[i][j].setBackground(Color.white);
                        }
                    } else if (j % 2 != 0) {
                        buttons[i][j].setBackground(Color.black);
                    } else {
                        buttons[i][j].setBackground(Color.white);
                    }
                }
            }

            int[][] board = data.getBoard();
            for (int i = 0; i < SIZEOFBOARD; i++) {
                for (int j = 0; j < SIZEOFBOARD; j++) {
                    if (board[i][j] == 0) {
                        buttons[i][j].setEnabled(false);
                    } else if (board[i][j] == 1) {
                        buttons[i][j].setEnabled(true);
                        buttons[i][j].setIcon(new ImageIcon(imgWHITE));
                    } else if (board[i][j] == 2) {
                        buttons[i][j].setEnabled(true);
                        buttons[i][j].setIcon(new ImageIcon(imgRED));
                    } else {
                        System.out.println("Błąd. Nieprawidłowa wartość w tablicy");
                    }

                }
            }

            int[] clicked = data.getClicked();
            if (clicked[0] != -1 && clicked[1] != -1) {
                buttons[clicked[0]][clicked[1]].setBackground(Color.LIGHT_GRAY);
            }

            int[][] moves = data.getMoves();
            for (int i = 0; i < 4; i++) {
                if (moves[i][0] != -1 && moves[i][1] != -1) {
                    buttons[moves[i][0]][moves[i][1]].setEnabled(true);
                    buttons[moves[i][0]][moves[i][1]].setBackground(Color.LIGHT_GRAY);
                }
            }

            int[][] lastmove = data.getLastMove();
            if (lastmove[0][0] != -1 && lastmove[0][1] != -1 && lastmove[1][0] != -1 && lastmove[1][1] != -1) {
                buttons[lastmove[0][0]][lastmove[0][1]].setBackground(Color.LIGHT_GRAY);
                buttons[lastmove[1][0]][lastmove[1][1]].setBackground(Color.LIGHT_GRAY);
            }

        } catch (IOException e1) {
            System.err.println("Nie odnaleziono pliku z grafiką");
        }
    }

    public void getData(Data data) {
        this.data = data;
        updateBoard(data);
    }

    public void sendData(Data data) {
        game.passToLogic(data);
    }

}
