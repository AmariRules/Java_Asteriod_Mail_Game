import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Background {
    private BufferedImage img;

    Background(BufferedImage img){
        this.img = img;
    }


    protected void draw(ImageObserver obj, Graphics g) {
        int width = spaceShipGame.getTankGame().getWidth();
        int height = spaceShipGame.getTankGame().getHeight();



            g.drawImage(img,0,0, width,height, null);

    }
}


