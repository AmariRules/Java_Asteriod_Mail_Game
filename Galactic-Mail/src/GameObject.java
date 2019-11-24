import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public abstract class GameObject{
    public double x, y, height, width, planeLooop = 0, speed;
    Rectangle bbox;
    protected boolean boom;
    protected Image[] imgs = new Image[3];
    protected BufferedImage img;
    protected Shape collision;
    protected double centerX, centerY, direction;
    protected AffineTransform original = new AffineTransform();
    protected AffineTransform movement;


    // Construct for object without array
    public GameObject(BufferedImage img, double x, double y, int speed){
        this.img = img;
        this.x = x;
        this.y = y;
        height = img.getHeight(null);
        width = img.getWidth(null);

    }

    protected GameObject() {
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }
    public void update(Observable obj, Object arg){}
    public void draw(ImageObserver imgObs, Graphics2D g){}

}