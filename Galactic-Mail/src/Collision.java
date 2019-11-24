import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Collision {

    BufferedImage img;
    public boolean playerVroid(spaceShip p1, ArrayList<Asteroid> asteroids, ArrayList<Explosion> x) {
    this.img = img;
       for (int i = 0; i < asteroids.size(); i++) {
            if (asteroids.get(i).getRec().getBounds2D().intersects(p1.getRec().getBounds2D())
                    && !p1.getOnMoon()) {
                x.add(new Explosion(p1.getCenterX() - 25, p1.getCenterY() - 25, 100, 100,"explosion1_"));
                asteroids.remove(i);
                p1.setHealth();

                return true;
            }
        }
        return false;
    }

    public void playerVmoon(spaceShip p1, ArrayList<Moon> x) {
        for (int i = 0; i < x.size(); i++) {
            if (p1.getRec().getBounds2D().intersects(x.get(i).getRec().getBounds2D()) && p1.getRec().getBounds2D().getMinX() - 5 > x.get(i).getRec().getBounds2D().getMinX()
                    && p1.getRec().getBounds2D().getMaxX() + 5 < x.get(i).getRec().getBounds2D().getMaxX()
                    && p1.getRec().getBounds2D().getMinY() - 5  > x.get(i).getRec().getBounds2D().getMinY()
                    && p1.getRec().getBounds2D().getMaxY() + 5 < x.get(i).getRec().getBounds2D().getMaxY()) {
                if (!x.get(i).getOnMoon()) {
                    p1.setIsShot(false);
                    if (!x.get(i).getFirstMoon()) {
                        p1.setScore(p1.getScore() + 1000);
                    }
                    p1.setImage("-off");
                    p1.setSpeed((4 * Math.random() + 2));
                    p1.setOnMoon(true);
                   // p1.isShot=false;
                }
                x.get(i).setOnMoon(true);
                if (p1.getScore() > 0 && !x.get(i).getFirstMoon()) {
                    p1.setScore(p1.getScore() - 10);
                }
            }
            if (x.get(i).getOnMoon() && !p1.getRec().getBounds2D().intersects(x.get(i).getRec().getBounds2D())) {
                p1.setOnMoon(false);
                x.remove(i);
                i--;
            }
        }
    }

}
