
import java.awt.*;

    public class Kareem {

        // Variables
        public String name;
        public int xpos;
        public int ypos;
        public int dx;
        public int dy;
        public int width;
        public int height;
        public boolean isAlive;
        public Rectangle rect;

        // Constructor
        public Kareem(String pName, int pXpos, int pYpos) {
            name = pName;
            xpos = pXpos;
            ypos = pYpos;
            dx = 5;   // movement speed X
            dy = 3;   // movement speed Y
            width = 200;
            height = 200;
            isAlive = true;
            rect = new Rectangle(xpos, ypos, width, height);
        }

        // Move Kareem
        public void move() {
            xpos += dx;
            ypos += dy;
            rect = new Rectangle(xpos, ypos, width, height);

            bounce();
            wrap();
        }

        public void bounce() {
            rect = new Rectangle(xpos, ypos, width, height);

            if (xpos >= 1000 - width) {
                dx = -dx;
            }

            if (ypos >= 700 - height) {
                dy = -dy;
            }

            if (xpos <= 0) {
                dx = -dx;
            }

            if (ypos <= 0) {
                dy = -dy;
            }
        }

        public void wrap() {
            rect = new Rectangle(xpos, ypos, width, height);

            if (xpos > 1000) {
                xpos = 0 - width;
            }

            if (xpos + width < 0) {
                xpos = 1000;
            }

            if (ypos > 700) {
                ypos = 0 - height;
            }

            if (ypos + height < 0) {
                ypos = 700;
            }
        }
    }

