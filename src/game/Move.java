
package game;
import java.util.*;

public class Move {
   private int[] fromPiece; //przesuwany pion
   private int[] toPiece; //gdzie przesuwany
   private boolean mustBeat; //czy dany ruch musi bić
   int beaten; //co zbił, potrzebne do odtworzenia stanu gry. jesli nic to 0
   private boolean wasQueen;  //sprawdza czy pion byl juz damka przed ruchem (do odtworzenia ruchu, gdy pion wejdzie na koniec planszy)
   
  public Move (int fromY, int fromX, int toY, int toX, boolean mBeat, int mBeaten) {
      fromPiece = new int[2];
      toPiece = new int[2];
      fromPiece[0] = fromY;
      fromPiece[1] = fromX;
      toPiece[0] = toY;
      toPiece[1] = toX;
      mustBeat = mBeat;
      beaten = mBeaten;
      wasQueen = false;
  }
  
  public int[] getFromPiece () {
      return fromPiece;
  }
  
  public int[] getToPiece () {
      return toPiece;
  }
  
  public boolean getMustBeat () {
      return mustBeat;
  }
  
  public int getBeaten () {
      return beaten;
  }
  
  public boolean getWasQuenn () {
      return wasQueen;
  }
  
  public void setWasQuenn (boolean pOq) {
      wasQueen = pOq;
  }
  
  public void printMove () {
      System.out.println("from: " + Arrays.toString(fromPiece) + ", to: " + Arrays.toString(toPiece) + ", " + mustBeat + ", " + beaten);
  }
}
