
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Explosion {

    private final ArrayList<BufferedImage> images;
    private final double myX, myY;
    private final int sizeX, sizeY;
    private int counter;
    public Explosion(double myX, double myY, int sizeX, int sizeY, String image) {
        this.myX = myX;
        this.myY = myY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        counter = 0;
        images = new ArrayList(6);
        for (int i = 0; i < 6; i++) {
            images.add(loadImage("Resources/" + image + (i + 1) + ".png"));
        }
    }
    private BufferedImage loadImage(String pathName) {
        try {
            return ImageIO.read(new File(pathName)); // Return image if it is  created without error
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null; // Return null if error occurred
    }
    protected void draw(Graphics2D g) {
        g.drawImage(images.get(counter), (int) (myX - images.get(counter).getWidth() / 2), (int) (myY - images.get(counter).getHeight() / 2), sizeX, sizeY, null);
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }
    public int getCounter() {
        return counter;
    }
}
