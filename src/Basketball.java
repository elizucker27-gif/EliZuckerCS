import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;




public class Basketball implements Runnable, KeyListener, MouseListener {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;


    Lebron bron;
    MJ mj;
    Kareem kareem;
    Ball ball;

    Image bronImage;
    Image mjImage;
    Image kareemImage;
    Image bgImage;
    Image hoopImage;
    Image ballImage;

    boolean firstCrash;
    boolean newCrash;
    boolean shot;
    int score;
    boolean scored;
    boolean chargingShot;
    int shotPower;
    Rectangle hoopRect;
    Rectangle frontRim;
    Rectangle backRim;
    boolean rimHit;
    int rimFlashTimer;
    int rimCooldown;
    Rectangle rimSwish;



    public static void main(String[] args) {
        Basketball ex = new Basketball();
        new Thread(ex).start();
    }

    public Basketball() {
        setUpGraphics();

        firstCrash = true;
        newCrash = true;
        score = 0;
        shot = false;
        scored = false;
        chargingShot = false;
        shotPower = 0;
        rimHit = false;
        rimFlashTimer = 0;
        rimCooldown = 0;


        bron = new Lebron("Lebron.png", 0, HEIGHT - 120);
        //mj = new MJ("MJ.png", 100, 100);
        //kareem = new Kareem("Kareem.png", 600, 400);
        ball = new Ball("ball.png", WIDTH / 2 - 40, 100);




        bronImage = new ImageIcon("Lebron.png").getImage();
        //mjImage = new ImageIcon("MJ.png").getImage();
        //kareemImage = new ImageIcon("Kareem.png").getImage();
        hoopImage = new ImageIcon("hoop.png").getImage();
        ballImage = new ImageIcon("ball.png").getImage();

        bgImage = new ImageIcon("Space.png").getImage();

        int hoopWidth = 180;
        int hoopHeight = 180;
        hoopRect = new Rectangle(850, 205, 80,15);
        frontRim = new Rectangle(827, 153, 1, 1);
        backRim = new Rectangle(924, 153, 1, 1);
        rimSwish = new Rectangle(860, 150, 30, 20);

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
//        mj.move();
//        kareem.move();
        if (!shot) {
            ball.followPlayer(bron);
        } else {
            ball.move();
        }
        if (chargingShot && !shot) {
            shotPower++;

            if (shotPower > 35) {
                shotPower = 35;
            }
        }

//    crashBronMJ();
//    crashMJKA();
        checkSwish();
        checkRim();
        checkScore();
        resetBallIfNeeded();
    }





//    public void crashBronMJ() {
//        if (bron.rect.intersects(mj.rect) && firstCrash) {
//
//            bron.dx = -bron.dx;
//            bron.dy = -bron.dy;
//
//            mj.dx = -mj.dx;
//            mj.dy = -mj.dy;
//
//            mj.width += 10;
//            mj.height += 10;
//
//            firstCrash = false;
//        }
//
//
//        if (!bron.rect.intersects(mj.rect)) {
//            firstCrash = true;
//        }
//    }
//
//    public void crashMJKA() {
//        if (mj.rect.intersects(kareem.rect) && newCrash) {
//
////            if (Math.random() < 0.5) {
////                kareem.dx = -kareem.dx;
////            } else {
//            kareem.dy = -kareem.dy;
//            kareem.dx = -kareem.dx;
//            mj.dx = -mj.dx;
//            mj.dy = -mj.dy;
//
//            newCrash = false;
////            }
//        }
//        if (!mj.rect.intersects(kareem.rect)) {
//            newCrash = true;
//        }
//
//
//    }

public void checkScore() {

    if (ball.rect.intersects(hoopRect) && !scored) {
        score++;
        scored = true;
        shot = false;

        System.out.println("Score: " + score);

        ball.followPlayer(bron);
    }
}

    public void checkRim() {

        if (rimCooldown > 0) {
            rimCooldown--;
        }

        if ((ball.rect.intersects(frontRim) || ball.rect.intersects(backRim))
                && rimCooldown == 0
                && ball.dy > 0) {

            double randomBounce = Math.random();

            if (ball.rect.intersects(frontRim)) {
                ball.dx = -4 - (int)(randomBounce * 4);
            }

            if (ball.rect.intersects(backRim)) {
                ball.dx = 4 + (int)(randomBounce * 4);
            }

            ball.dy = -6;


            ball.xpos += ball.dx * 2;
            ball.ypos -= 8;
            ball.rect.setLocation(ball.xpos, ball.ypos);

            rimHit = true;
            rimFlashTimer = 10;
            rimCooldown = 12;
        }

        if (rimFlashTimer > 0) {
            rimFlashTimer--;
        } else {
            rimHit = false;
        }
    }


    public void checkSwish() {
        if (ball.rect.intersects(rimSwish) && ball.dy > 0) {

            ball.dx = 0;
            ball.dy = 6;

            rimCooldown = 10;
        }
    }
    public void resetBallIfNeeded() {
        if (ball.ypos > HEIGHT || ball.xpos < 0 || ball.xpos > WIDTH) {
            shot = false;
            scored = false;
            ball.followPlayer(bron);
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);

        g.drawImage(bronImage, bron.xpos, bron.ypos, bron.width, bron.height, null);
        //g.drawImage(mjImage, mj.xpos, mj.ypos, mj.width, mj.height, null);
        //g.drawImage(kareemImage, kareem.xpos, kareem.ypos, kareem.width, kareem.height, null);
        g.drawImage(ballImage, ball.xpos, ball.ypos, ball.width, ball.height, null);

        int hoopWidth = 180;
        int hoopHeight = 180;

        g.drawImage(hoopImage, WIDTH - hoopWidth , 100, hoopWidth, hoopHeight, null);
        if (rimHit) {
            g.setColor(Color.RED);
            g.fillRect(frontRim.x, frontRim.y, 10, 20);
            g.fillRect(backRim.x, backRim.y, 10, 20);
        }
        g.setColor(Color.BLUE);
        g.drawRect(rimSwish.x, rimSwish.y, rimSwish.width, rimSwish.height);

        g.setColor(new Color(255,255,255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Score: " + score, 400, 50);

        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setColor(Color.WHITE);
        g.drawString("Space bar to shoot  Click to reset", 20, 30);
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
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.setFocusable(true);
        canvas.requestFocus();
        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 38) {
            bron.dy = -10;
        }
        if (e.getKeyCode() == 40) {
            bron.dy = 10;
        }
        if (e.getKeyCode() == 37) {
            bron.dx = -10;
        }
        if (e.getKeyCode() == 39) {
            bron.dx = 10;
        }
        if (e.getKeyCode() == 32 && !shot && !chargingShot) {
            chargingShot = true;
            shotPower = 0;
        }




    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 38 || e.getKeyCode() == 40) {
            bron.dy = 0;
        }

        if (e.getKeyCode() == 37 || e.getKeyCode() == 39) {
            bron.dx = 0;
        }
        if(e.getKeyCode()==32&& chargingShot&&!shot){
            chargingShot=false;
            shot=true;
            scored=false;

            ball.xpos = bron.xpos +bron.width -20;
            ball.ypos = ball.ypos +2;

            ball.dx =10 +shotPower/3;
            ball.dy = -12 -shotPower / 2;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        score = 0;
        shot = false;
        scored = false;
        chargingShot = false;
        shotPower = 0;

        bron.xpos = 0;
        bron.ypos = HEIGHT - 120;
        bron.dx = 0;
        bron.dy = 0;

        ball.followPlayer(bron);

        System.out.println("Game reset");
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}