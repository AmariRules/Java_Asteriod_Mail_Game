
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Asteroid extends GameObject {

    private double speed, rotSpeed; // Asteroids have a rotation speed and movement speed
    private double x, y, vx, vy, angle, shootCoolDown =0, shootRate;
    private final int R = 2;
   // int imgW, imgH;
    public Asteroid(BufferedImage img, double myX, double myY) {

        this.img = img;
        this.x = myX;
        this.y = myY;


        if (Math.random() < .5) {
            speed = (2 * Math.random() + .5);
        } else {
            speed = (-2 * Math.random() - .5);
        }
        centerX = myX + (img.getWidth() / 2);
        centerY = myY + (img.getHeight() / 2);
        direction = (Math.random());

        if (Math.random() < .5) {
            rotSpeed = 2 * Math.random(); // sets speed of asteroids and rotation
        } else {
            rotSpeed = (-2) * Math.random();
        }
        collision = new Rectangle2D.Double(myX + (img.getWidth() * .2), myY + (img.getHeight() * .2), (img.getWidth() * .6), (img.getHeight()) * .6);
        movement = AffineTransform.getTranslateInstance(myY, myY);

    }


    public void checkBorder(){
        if (x > 1200) {
            x = -img.getWidth() + 1;
            centerX = x + (img.getWidth() / 2);
            collision = new Rectangle2D.Double(x + (img.getWidth() * .2), y + (img.getHeight() * .2), (img.getWidth() * .6), (img.getHeight()) * .6);
            movement = AffineTransform.getTranslateInstance(y, y);
        } else if (y > 650) {
            y = -img.getHeight() + 1;
            centerY = y + (img.getHeight() / 2);
            collision = new Rectangle2D.Double(x + (img.getWidth() * .2), y + (img.getHeight() * .2), (img.getWidth() * .6), (img.getHeight()) * .6);
            movement = AffineTransform.getTranslateInstance(y, y);
        } else if (x < -img.getWidth()) {
            x = 1199;
            centerX = x + (img.getWidth() / 2);
            collision = new Rectangle2D.Double(x + (img.getWidth() * .2), y + (img.getHeight() * .2), (img.getWidth() * .6), (img.getHeight()) * .6);
            movement = AffineTransform.getTranslateInstance(y, y);
        } else if (y < -img.getHeight()) {
            y = 649;
            centerY = y + (img.getHeight() / 2);
            collision = new Rectangle2D.Double(x + (img.getWidth() * .2), y + (img.getHeight() * .2), (img.getWidth() * .6), (img.getHeight()) * .6);
            movement = AffineTransform.getTranslateInstance(y, y);
        }
        //movement of asteroid
        x = x + speed;
        y = y + speed;
        centerX = centerX + speed;
        centerY = centerY + speed;
        direction = direction + rotSpeed;
        movement = AffineTransform.getTranslateInstance(speed, speed);
        collision = movement.createTransformedShape(collision);
    }

    public Shape getRec() {
        return collision;
    }


    protected void draw(Graphics2D g) {
        //checkBorder();
        g.setTransform(original);
        g.rotate(Math.toRadians(direction), centerX, centerY);
        g.drawImage(img, (int) x, (int) y, null); // draw the image.
        g.setTransform(original);
    }
}
