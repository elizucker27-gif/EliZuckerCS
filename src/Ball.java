import java.awt.*;

public class Ball {

    public int xpos;
    public int ypos;
    public String name;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public Rectangle rect;
    boolean isShot;

    public Ball(String pName, int pXpos, int pYpos) {
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = 0;
        dy = 0;
        isShot = false;
        width = 80;
        height = 80;
        rect = new Rectangle(xpos, ypos, width, height);
    }

    public void move() {
        xpos += dx;
        ypos += dy;

        dy += 1;

        rect = new Rectangle(xpos, ypos, width, height);
    }


    public void followPlayer(Lebron bron) {
        xpos = bron.xpos + bron.width - 30;
        ypos = bron.ypos + 20;
        dx = 0;
        dy = 0;
        rect = new Rectangle(xpos, ypos, width, height);
    }
}