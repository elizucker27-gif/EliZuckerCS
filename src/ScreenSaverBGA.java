import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class ScreenSaverBGA implements Runnable {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    // Characters
    Lebron bron;
    MJ mj;
    Kareem kareem;

    Image bronImage;
    Image mjImage;
    Image kareemImage;
    Image bgImage;

    boolean firstCrash;

    public static void main(String[] args) {
        ScreenSaverBGA ex = new ScreenSaverBGA();
        new Thread(ex).start();
    }

    public ScreenSaverBGA() {
        setUpGraphics();

        firstCrash = true;

        bron = new Lebron("Lebron.png", 300, 300);
        mj = new MJ("MJ.png", 100, 100);
        kareem = new Kareem("Kareem.png", 600, 400);

        bronImage = new ImageIcon("Lebron.png").getImage();
        mjImage = new ImageIcon("MJ.png").getImage();
        kareemImage = new ImageIcon("Kareem.png").getImage();

        bgImage = new ImageIcon("Space.jpeg").getImage();
    }

    public void run() {
        while (true) {
            moveThings();
            render();
            pause(30);
        }
    }

    public void moveThings() {
        bron.move();
        mj.move();
        kareem.move();

        crashBronMJ();
        crashMJKA();
    }

    // CRASH METHOD

    public void crashBronMJ() {
        if (bron.rect.intersects(mj.rect) && firstCrash) {

            bron.dx = -bron.dx;
            bron.dy = -bron.dy;

            mj.dx = -mj.dx;
            mj.dy = -mj.dy;

            mj.width += 10;
            mj.height += 10;

            firstCrash = false;
        }


        if (!bron.rect.intersects(mj.rect)) {
            firstCrash = true;
        }
    }

    //  CRASH METHOD

    public void crashMJKA() {
        if (mj.rect.intersects(kareem.rect)) {

            if (Math.random() < 0.5) {
                kareem.dx = -kareem.dx;
            } else {
                kareem.dy = -kareem.dy;
            }
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);

        g.drawImage(bronImage, bron.xpos, bron.ypos, bron.width, bron.height, null);
        g.drawImage(mjImage, mj.xpos, mj.ypos, mj.width, mj.height, null);
        g.drawImage(kareemImage, kareem.xpos, kareem.ypos, kareem.width, kareem.height, null);

        g.dispose();
        bufferStrategy.show();
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Basketball Screensaver");
        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    }
}