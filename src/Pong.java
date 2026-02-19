// Basic Game Application
// Basic Object, Image, Movement
// Threaded

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Pong implements Runnable, KeyListener {

    // Window size
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    // Graphics variables
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    // Game objects
    Lebron bron;
    Image bronImage;

    MJ mj;
    Image mjImage;

    Kareem kareem;       // ★ NEW
    Image kareemImage;   // ★ NEW

    Image bgImage;

    public boolean firstCrash;

    public boolean firstCrash2;


    // Main method
    public static void main(String[] args) {
        Pong ex = new Pong();
        new Thread(ex).start();
    }

    // Constructor
    public Pong() {
        setUpGraphics();
        firstCrash = true;
        firstCrash2 = true;

        // Create Lebron
        bron = new Lebron("Lebron.png", 300, 300);
        bronImage = new ImageIcon("Lebron.png").getImage();

        // Create MJ
        mj = new MJ("MJ.png", 100, 100);
        mjImage = new ImageIcon("MJ.png").getImage();
        mj.dx = 6;
        mj.dy = 4;


        kareem = new Kareem("Kareem", 100, 100);
        kareemImage = new ImageIcon("Kareem.png").getImage();

        // Background
        bgImage = new ImageIcon("Space.jpeg").getImage();

        System.out.println("Setup complete, starting game...");




    }

    // Game loop
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
        kareem.move(); // ★ NEW

        checkCrash();


    }

    public void checkCrash() {

        // Lebron paddle collision
        if (bron.rect.intersects(mj.rect) && firstCrash == true) {
            mj.dx = -mj.dx;
            firstCrash = false;
        }
        // reset lebron crash
        if (!bron.rect.intersects(mj.rect)) {
            firstCrash = true;
        }


        // Kareem paddle collision
        if (kareem.rect.intersects(mj.rect) && firstCrash2 == true) {
            mj.dx = -mj.dx;
            mj.xpos = kareem.xpos - mj.width;

            firstCrash2 = false;
        }

        // reset kareem crash
        if (!kareem.rect.intersects(mj.rect)) {
            firstCrash2 = true;
        }

    }

    private void render() {
        Graphics2D g = (Graphics2D) this.bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);

        g.setColor(Color.RED);
        g.fillRect(800, 20, 15, 100);

        // Draw Lebron
        g.drawImage(bronImage, bron.xpos, bron.ypos, bron.width, bron.height, null);

        // Draw MJ
        g.drawImage(mjImage, mj.xpos, mj.ypos, mj.width, mj.height, null);

        // ★ Draw Kareem
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
        frame = new JFrame("Basic Game App");
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
        canvas.requestFocus();

        canvas.addKeyListener(this);

        System.out.println("Graphics setup complete");
    }


    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 38) {
            kareem.dy = -10;
            kareem.dx = 0;

        }
        if (e.getKeyCode() == 40) {
            kareem.dy = 10;
            kareem.dx = 0;

        }
        if (e.getKeyCode() == 37) {
            kareem.dx = -10;
            kareem.dy = 0;
        }
        if (e.getKeyCode() == 39) {
            kareem.dx = 10;
            kareem.dy = 0;

        }
        if (e.getKeyCode() == 87) {
            bron.dy = -10;
            bron.dx = 0;
        }
        if (e.getKeyCode() == 83) {
            bron.dy = 10;
            bron.dx = 0;
        }
        if (e.getKeyCode() == 65) {
            bron.dx = -10;
            bron.dy = 0;
        }
        if (e.getKeyCode() == 68) {
            bron.dx = 10;
            bron.dy = 0;
        }
    }


    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 38) { //up
            kareem.dy = 0;
            kareem.dx = 0;

        }
        if (e.getKeyCode() == 40) { //down
            kareem.dy = 0;
            kareem.dx = 0;

        }
        if (e.getKeyCode() == 37) {
            kareem.dy = 0;
            kareem.dx = 0;
        }
        if (e.getKeyCode() == 39) {
            kareem.dy = 0;
            kareem.dx = 0;

        }
        if (e.getKeyCode() == 87) { //up
            bron.dy = 0;
            bron.dx = 0;
        }
        if (e.getKeyCode() == 83) { //down
            bron.dy = 0;
            bron.dx = 0;
        }
        if (e.getKeyCode() == 65) {
            bron.dx = 0;
            bron.dy = 0;
        }
        if (e.getKeyCode() == 68) {
            bron.dx = 0;
            bron.dy = 0;
        }

    }
}