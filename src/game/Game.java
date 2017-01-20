
package game;

public class Game {

    private final Visual visual;
    private final Logic logic;
    
    public Game(int sizeOfBoard, int numberOfRowsPlaying){
        this.visual = new Visual(this, sizeOfBoard);
        this.logic = new Logic(this, sizeOfBoard, numberOfRowsPlaying);
    }
    
    
    public static void main(String[] args) {
        Game game = new Game(9,3);
    }
    
    public void passToVisual(Data data){
        visual.getData(data);
    }
    
    public void passToLogic(Data data){
        logic.getData(data);
    }
    
}
