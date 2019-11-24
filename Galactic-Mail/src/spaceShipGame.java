import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class spaceShipGame extends JApplet implements Runnable {
    public static GameEvent gameEvents;
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 650;
    private BufferedImage world, subIm, bimg, bimg2, menuBackground, roids, moonPic;
    private JFrame jf;
    public spaceShip t1;
    private Background backG;
    private ArrayList<Moon> moons;
    private ArrayList<Asteroid> steroids;
    private ArrayList<Moon> startingMoon;
    private ArrayList<Explosion> splosions;
    private Collision collide;
    private int level = 1;
    private boolean newLevel = false;



    private static final spaceShipGame SHIP_GAME = new spaceShipGame();
    private int w = SHIP_GAME.SCREEN_WIDTH, h = SHIP_GAME.SCREEN_HEIGHT;
    private int battleFieldX = 1024, battleFieldY = 768;
    boolean gameDone = false;
    JPanel p = new JPanel();
    String win, lose;
    private Thread thread;
    public InputStream map;
    Graphics2D g2;


    private Image sWall, weakW, life, health;
    private Dimension windowSize;

    public int getBattleFieldX() {
        return battleFieldX;
    }
    public int getBattleFieldY(){
        return battleFieldY;
    }

    BufferedImage bullet = null, t1img = null, wor = null, powerUpBullet = null, background = null,player1View = null, player2View = null;


    public static void main(String[] args) {

        SHIP_GAME.init();
        SHIP_GAME.start();

        try {

            while (true) {
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }

    @Override
    public void init() {
        level = 0;// first level
        this.jf = new JFrame("Galactic Mail");
        this.world = new BufferedImage(SHIP_GAME.SCREEN_WIDTH, SHIP_GAME.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("BeFunky-design.png"));
            t1img = ImageIO.read(this.getClass().getClassLoader().getResource("spaceship.png"));
            background = ImageIO.read(this.getClass().getClassLoader().getResource("background.png"));
           life = ImageIO.read(this.getClass().getClassLoader().getResource("spaceship.png"));
            roids = ImageIO.read(this.getClass().getClassLoader().getResource("asteroid.png"));
            moonPic = ImageIO.read(this.getClass().getClassLoader().getResource("moon_1.png"));
            collide = new Collision();
            gameEvents = new GameEvent();
            t1 = new spaceShip(100, 300, 0, 0, 0, t1img, 3, 0); // Needs initial value for the initial size, bound to map
            startingMoon = new ArrayList();
            startingMoon.add(new Moon(moonPic, 80, 280));
            splosions = new ArrayList();
            steroids = new ArrayList();
            for (int i = 0; i< 10;i++){
                steroids.add(new Asteroid(roids, (1000*Math.random()+100),  (500*Math.random()+50)));
            }
            moons = new ArrayList();
            for(int i = 0; i< 5;i++){
                moons.add(new Moon(moonPic));
            }
            Moon.setMoons(moons);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        backG = new Background(background); //background image

        ShipControl tc2 = new ShipControl(t1, KeyEvent.VK_SPACE, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);
       this.jf.addKeyListener(tc2); //Key listener for ship


        this.jf.setSize(new Dimension(SHIP_GAME.w, SHIP_GAME.h));
        this.jf.setResizable(false); // Allow the JFrame to be resized
        this.jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
        SHIP_GAME.windowSize = jf.getContentPane().getSize();
        System.out.println(SHIP_GAME.windowSize.height + "   " + SHIP_GAME.windowSize.width);
        //mapPrint();

    }


public static spaceShipGame getTankGame(){
        return SHIP_GAME;
}


    public void drawBuffered(){
        backG.draw(this, g2); //draw background

    }
    @Override
    public void paint(Graphics g) {

        bimg = (BufferedImage) createImage(w, h);
        g2 = bimg.createGraphics();

//        if (!gameDone) {

            drawBuffered();
            g.drawImage(bimg2, 0, 0, this);

//        if (!gameDone) {
            for (int i = 0; i < steroids.size(); i++) {
                steroids.get(i).draw(g2);
            }
            if (startingMoon.size() > 0) {
                startingMoon.get(0).draw(g2);
            }

            for (int i = 0; i < moons.size(); i++) {
                moons.get(i).draw(g2);
            }
        if (!gameDone) {
            t1.drawImage(g2);
        }
            for (int i = 0; i < splosions.size(); i++) {
                if (splosions.get(i).getCounter() < 6) {
                    splosions.get(i).draw(g2);
                    splosions.get(i).setCounter(splosions.get(i).getCounter() + 1);
                } else {
                    splosions.remove(i);
                    i--;
                }
            }

            bimg2 = (BufferedImage) createImage(windowSize.width, windowSize.height);
            g2 = bimg2.createGraphics();
            player1View = bimg.getSubimage(0, 0, windowSize.width, windowSize.height);
            g2.drawImage(player1View, 0, 0, this);

            //Displays life count
            if (t1.getLife() != 0) {
                for (int i = 0; i < t1.getLife(); i++) {
                    g2.drawImage(life, i * 32, windowSize.height - 20, 20, 20, this);
                }
//            }
            if (t1.getHealth() != 0) {
                for (int i = 0; i < t1.getHealth(); i++) {
                    g2.setColor(Color.white);
                    g2.drawString("Health" + t1.getHealth(), windowSize.height - 100,30 );
                    g2.drawString("Current Level: " + this.level , 50, 20);
                }
            }
                g2.setColor(Color.white);
                g2.drawString("SCORE: " + t1.getScore(), w-100, 10);
        }
        if(gameDone){
            g2.setColor(Color.white);
            System.out.println("gameover");
            //g2.setColor(Color.white);
            g2.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 150));
            g2.drawString("GAMEOVER", 140, 250);
            g2.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 50));
            g2.drawString("SCORE: " + t1.getScore(), 460, 350);
            g2.drawString("Nice Try! You made it to Level: "+ this.level, 200, 450);
            g2.drawString("Press Space Bar to Replay", 450, 550);
            gameEvents.draw(g2);
        }
    g2.dispose();


    }
    private void nextLevel(){
        t1.nextLevel(100, 300, 0);
        startingMoon = new ArrayList();
        startingMoon.add(new Moon(moonPic,80, 280));
        steroids = new ArrayList();
        for (int i = 0; i < 11 + 2 * level; i++) {
            steroids.add(new Asteroid(roids,1200 * Math.random(), 650 * Math.random()));
        }
        moons = new ArrayList();
        for (int i = 0; i < 5; i++) {
            moons.add(new Moon(moonPic));
        }
        splosions = new ArrayList();
        Moon.setMoons(moons);
    }
    private void update(){
        if(!gameDone){
            t1.update();
            if(moons.size()==0){
                level++;

                t1.setScore(t1.getScore()+1000);
                nextLevel();
                newLevel = true;
            }
            collide.playerVmoon(t1, startingMoon);
            collide.playerVmoon(t1, moons);
            if(collide.playerVroid(t1, steroids, splosions)&& t1.getLife()==0){
                gameDone = true;
            }

            if(!gameDone&&t1.shootPressed){
                t1.setIsShot(true);
                newLevel = false;
                t1.update();

            }
            if(!gameDone&&steroids.size()<5){
                for (int i = 0; i < 7 + 2 * level; i++) {
                    steroids.add(new Asteroid(roids,1200 * Math.random(), 650 * Math.random()));
                }
            }
        }
        for(int i = 0; i < steroids.size(); i++) {
            steroids.get(i).checkBorder();
        }
        if(gameDone) {  //method to restart game/
            if (t1.getRestartPressed()) {

                this.gameDone = false;
                this.level = 1;
                t1 = new spaceShip(100, 300, 0, 0, 0, t1img, 3, 0); // Needs initial value for the initial size, bound to map
                startingMoon = new ArrayList();
                startingMoon.add(new Moon(moonPic, 80, 280));
                splosions = new ArrayList();
                steroids = new ArrayList();
                for (int i = 0; i< 10;i++){
                    steroids.add(new Asteroid(roids, (1000*Math.random()+100),  (500*Math.random()+50)));
                }
                moons = new ArrayList();
                for(int i = 0; i< 5;i++){
                    moons.add(new Moon(moonPic));
                }
                Moon.setMoons(moons);
                ShipControl tc2 = new ShipControl(t1, KeyEvent.VK_SPACE, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
                this.jf.addKeyListener(tc2); //Key listener for ship


            }
        }
    }


    public void start(){
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            SHIP_GAME.update();
            repaint();

           // SHIP_GAME.update();

            try {
                thread.sleep(35);

            } catch (InterruptedException e) {
                break;
            }
        }
    }


    }
