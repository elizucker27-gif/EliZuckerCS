import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 * Edits by mblair on 10/27/2025
 */
public class Lebron {

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
    public Lebron(String pName, int pXpos, int pYpos) {
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = 5;   // speed in x
        dy = 3;   // speed in y
        width = 200;
        height = 200;
        isAlive = true;
        rect = new Rectangle(xpos, ypos, width, height);
    }

    // Move Lebron
    public void move() {
        xpos += dx;
        ypos += dy;
        rect = new Rectangle(xpos, ypos, width, height);
        wrap();
        wrap();
    }

        public void bounce() {

            if (xpos >= 1000 - width) {
                dx = -dx;
                rect = new Rectangle(xpos, ypos, width, height);
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




        public void wrap(){
        xpos =xpos +dx;
                ypos=ypos +dy;
            rect = new Rectangle(xpos, ypos, width, height);


            if (xpos <=0) {
                xpos = 1000;
            }else if (xpos>=1000){
                xpos=0;
            }
            if (ypos <=0) {
                ypos = 700;
            }else if (ypos>=700){
                ypos=0;
            }



}
}





