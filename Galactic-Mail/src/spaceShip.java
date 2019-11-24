import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class spaceShip extends GameObject {

    private double speed;
    private double x, y, vx, vy, angle;
    private final int R = 2;
    private final int ROTATIONSPEED = 4;
    protected int coolDown = 0, score = 0, health = 100, life = 3, spawnX,spawnY;
    protected boolean powerUp;
    private BufferedImage img;
    private boolean restartPressed, DownPressed, RightPressed, LeftPressed;//, shootPressed;
    private AffineTransform movement;
    boolean onMoon, isShot;
    int counter = 0;
    boolean isShootPressed = false;
    public boolean shootPressed;


    spaceShip(double x, double y, int vx, int vy, int angle, BufferedImage img, int speed, double direction) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        centerX = x+ (img.getWidth()/2);
        centerY=y+(img.getHeight()/2);
        this.speed = 5;
        onMoon = true;
        isShot = false;
        score = 0;
        this.direction = direction;
        collision = new Rectangle2D.Double(x, y, img.getWidth(), img.getHeight());
    }
    public int getLife(){
        return life;
    }

    void toggleUpPressed() {
        this.restartPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {this.shootPressed = true;}
    

    void unToggleUpPressed() {
        this.restartPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {this.shootPressed = false;}
    public boolean getRestartPressed(){
        return restartPressed;
    }

    public void update() {
        if (this.LeftPressed) {
            direction = direction - speed;
            if (direction < -360) { // If it becomes less than -360 set it back to 0.
                direction = direction + 360;
            }
            movement = AffineTransform.getRotateInstance(Math.toRadians(-speed), centerX, centerY);
            this.collision = movement.createTransformedShape(collision);
        }
        if (this.RightPressed) {
            direction = direction + speed;
            if (direction > 360) {
                direction = direction - 360;
            }
            movement = AffineTransform.getRotateInstance(Math.toRadians(speed), centerX, centerY);
            this.collision = movement.createTransformedShape(collision);
        }
        if (isShot) {
        //    launch();
            move();
            if(this.shootPressed){
            }
        }
    }
    public void move(){
        this.x= x + (speed * Math.cos(Math.toRadians(direction)));
        this.y = y + (speed * Math.sin(Math.toRadians(direction)));
        this.centerX = centerX + speed * Math.cos(Math.toRadians(direction));
        this.centerY = centerY + speed * Math.sin(Math.toRadians(direction));
        this.movement = AffineTransform.getTranslateInstance(speed * Math.cos(Math.toRadians(direction)), speed * Math.sin(Math.toRadians(direction)));
        this.collision = movement.createTransformedShape(collision);

            if (x > 1200) {
                x = 0;
                collision = new Rectangle2D.Double(x, y, img.getWidth(), img.getHeight());
            } else if (y > 750) {
                y = 0;
                collision = new Rectangle2D.Double(x, y, img.getWidth(), img.getHeight());
            } else if (x < 0) {
                x = 1199;
                collision = new Rectangle2D.Double(x, y, img.getWidth(), img.getHeight());
            } else if (y < 0) {
                y = 749;
                collision = new Rectangle2D.Double(x, y, img.getWidth(), img.getHeight());
            }

            centerX = x + (img.getWidth() / 2);
            centerY = y + (img.getHeight() / 2);
            this.setImage("");




    }

    public void setOnMoon(boolean onMoon) {
        this.onMoon = onMoon;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }
    public int getHealth(){
        return health;
    }
    public int getAngle(){
        return (int)angle;
    }
    public int getTankCenterX(){
        return (int)x+((img.getWidth(null)/60)/2);
    }
    public int getTankCenterY(){
        return (int)y+(img.getHeight(null)/2);
    }

    public Shape getRec() {
        return collision;
    }

    public void nextLevel(int myX, int myY, double direction) {
        this.x = myX;
        this.y = myY;
        this.speed = 3;
        this.centerX = myX + (img.getWidth() / 2);
        this.centerY = myY + (img.getHeight() / 2);
        this.direction = direction;
        collision = new Rectangle2D.Double(myX, myY, img.getWidth(), img.getHeight());
        isShot = false;
        onMoon = true;
    }

    void drawImage( Graphics2D g) {
        System.out.println(life);
        g.setTransform(original);
        g.rotate(Math.toRadians(direction), centerX, centerY);
        g.drawImage(img, (int) x, (int) y, null);
        g.setTransform(original);
        System.out.println("Ship Location X: " + this.x + "Y:" + this.y);
    }

   public int getX(){
        return (int)x;
    }
    public void setX(int x) {
    this.x = x;
    }
    public boolean getOnMoon() {
        return onMoon;
    }
    public void setIsShot(boolean isShot) {
        this.isShot = isShot;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCenterX() {
        return centerX;
    }
    public double getCenterY(){
        return centerY;
    }
    public void setImage(String attribute) {
        img = loadImage("Resources/spaceship" + attribute + ".png");
    }
    protected final BufferedImage loadImage(String pathName) {
        try {
            return ImageIO.read(new File(pathName));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null; // Returns null if error occurred
    }

    public void setLife() {
    this.life--;
    }

    public void setHealth() {
        this.health-=50;
        if(this.health==0) {
            life--;
            health = 100;
        }
    }
}
