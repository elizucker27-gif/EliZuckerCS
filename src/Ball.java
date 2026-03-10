import java.awt.*;

public class Ball {

        public int xpos;
        public int ypos;
        public int dx;
        public int dy;
        public int width;
        public int height;
        public Rectangle rect;

        public Ball(int x, int y) {
            xpos = x;
            ypos = y;
            dx = 0;
            dy = 0;
            width = 60;
            height = 60;
            rect = new Rectangle(xpos, ypos, width, height);
        }

        public void move() {
            dy += 1;   // gravity

            xpos += dx;
            ypos += dy;

            // floor bounce
            if (ypos >= 700 - height) {
                ypos = 700 - height;
                dy = -dy / 2;   // bounce smaller each time
            }

            // left wall
            if (xpos <= 0) {
                xpos = 0;
                dx = -dx;
            }

            // right wall
            if (xpos >= 1000 - width) {
                xpos = 1000 - width;
                dx = -dx;
            }

            rect = new Rectangle(xpos, ypos, width, height);
        }
    }

