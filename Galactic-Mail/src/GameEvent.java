import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;

public class GameEvent extends Observable {
    public int type;
    public Object event;
    public boolean gameOver;

    public void setValue(KeyEvent e){
        type = 1;
        event = e;
        setChanged();
        notifyObservers(this);
    }
    public void setValue(String msg){
        type = 2;
        event = msg;
        setChanged();
        notifyObservers(this);
    }
public void setGameOver(boolean value){
        this.gameOver = value;
}
    //@Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font f2 = new Font("arial", Font.BOLD, 30);
        g2d.setFont(f2); // Font is set

    }
}
