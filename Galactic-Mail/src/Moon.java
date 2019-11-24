
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Moon extends GameObject {

    private boolean onMoon;
    private final boolean firstMoon;
    //all moons
     public Moon(BufferedImage img) {
      this.img = img;
        x = 0;
        y = 0;
        centerX = x + (img.getWidth() / 2);
        centerY = y + (img.getHeight() / 2);
        onMoon = false;
        firstMoon = false;
        direction = 0;
    }//starting moon
    public Moon(BufferedImage img, double myX, double myY) {
        this.img = img;
        this.x = myX;
        this.y = myY;
        centerX = myX + (img.getWidth() / 2);
        centerY = myY + (img.getHeight() / 2);
        direction = 0;
        collision = new Rectangle2D.Double(myX, myY, img.getWidth(), img.getHeight());
        movement = AffineTransform.getTranslateInstance(myY, myY);
        onMoon = true;
        firstMoon = true;
    }

    public static void setMoons(ArrayList<Moon> x) {
        double xPlacement = 200, yPlacement;
        for (int i = 0; i < x.size(); i++) {
            yPlacement = (  600 * Math.random());
            x.get(i).setX(xPlacement * (i + 1 ));
            x.get(i).setY(yPlacement);
        }

    }
    private void setX(double myX) {
        this.x = myX;
        collision = new Rectangle2D.Double(this.x, this.y, img.getWidth(), img.getHeight());
        movement = AffineTransform.getTranslateInstance(this.y, this.y);
    }

    private void setY(double myY) {
        this.y = myY;
        collision = new Rectangle2D.Double(this.x, this.y, img.getWidth(), img.getHeight());
        movement = AffineTransform.getTranslateInstance(myY, myY);
    }
    public Shape getRec() {
        return collision;
    }
    public boolean getOnMoon() {
        return onMoon;
    }
    public void setOnMoon(boolean onMoon) {
        this.onMoon = onMoon;
    }
    public boolean getFirstMoon() {
        return firstMoon;
    }
    protected void draw(Graphics2D g) {
        g.setTransform(original);
        g.rotate(Math.toRadians(direction), centerX, centerY);
        g.drawImage(img, (int) this.x, (int) this.y, null); // draw the image.
        g.setTransform(original);
    System.out.println("Moon Location X"+ this.x +  " Y: " + this.y );
     }
}
