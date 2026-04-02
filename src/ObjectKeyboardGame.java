import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class ObjectKeyboardGame implements Runnable, KeyListener {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    // Characters
    Lebron bron;
    MJ mj;

    Image bronImage;
    Image mjImage;
    Image bgImage;

    boolean firstCrash;

    // key booleans for 10/10 diagonal movement
    boolean upPressed;
    boolean downPressed;
    boolean leftPressed;
    boolean rightPressed;

    boolean aPressed;
    boolean dPressed;

    public static void main(String[] args) {
        ObjectKeyboardGame ex = new ObjectKeyboardGame();
        new Thread(ex).start();
    }

    public ObjectKeyboardGame() {
        setUpGraphics();

        firstCrash = true;

        bron = new Lebron("Lebron.png", 300, 300);
        bron.dx = 0;
        bron.dy = 0;

        mj = new MJ("MJ.png", 100, 100);
        mj.dx = 0;
        mj.dy = 0;

        bronImage = new ImageIcon("Lebron.png").getImage();
        mjImage = new ImageIcon("MJ.png").getImage();

        bgImage = new ImageIcon("Space.png").getImage();
    }

    public void run() {
        while (true) {
            moveThings();
            render();
            pause(30);
        }
    }

    public void moveThings() {

        // Lebron movement
        bron.dx = 0;
        bron.dy = 0;

        if (leftPressed) {
            bron.dx = -10;
        }
        if (rightPressed) {
            bron.dx = 10;
        }
        if (upPressed) {
            bron.dy = -10;
        }
        if (downPressed) {
            bron.dy = 10;
        }

        // MJ movement
        mj.dx = 0;

        if (aPressed) {
            mj.dx = -10;
        }
        if (dPressed) {
            mj.dx = 10;
        }

        bron.move();
        mj.move();

        crashBronMJ();
    }

    // CRASH METHOD
    public void crashBronMJ() {
        if (bron.rect.intersects(mj.rect) && firstCrash) {

            if (Math.random() < 0.5) {
                bron.width += 10;
                bron.height += 10;
            } else {
                mj.width += 10;
                mj.height += 10;
            }

            firstCrash = false;
        }

        if (!bron.rect.intersects(mj.rect)) {
            firstCrash = true;
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);

        g.drawImage(bronImage, bron.xpos, bron.ypos, bron.width, bron.height, null);
        g.drawImage(mjImage, mj.xpos, mj.ypos, mj.width, mj.height, null);

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
        canvas.addKeyListener(this);
        canvas.setFocusable(true);
        canvas.requestFocus();
        bufferStrategy = canvas.getBufferStrategy();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == 38) {
            upPressed = true;
        }
        if (e.getKeyCode() == 40) {
            downPressed = true;
        }
        if (e.getKeyCode() == 37) {
            leftPressed = true;
        }
        if (e.getKeyCode() == 39) {
            rightPressed = true;
        }

        if (e.getKeyCode() == 65) {
            aPressed = true;
        }
        if (e.getKeyCode() == 68) {
            dPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 38) {
            upPressed = false;
        }
        if (e.getKeyCode() == 40) {
            downPressed = false;
        }
        if (e.getKeyCode() == 37) {
            leftPressed = false;
        }
        if (e.getKeyCode() == 39) {
            rightPressed = false;
        }

        if (e.getKeyCode() == 65) {
            aPressed = false;
        }
        if (e.getKeyCode() == 68) {
            dPressed = false;
        }
    }
}