package org.cherise;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Board extends JPanel implements Runnable {

    private static final int BOARD_WIDTH = 250;
    private static final int BOARD_HEIGHT = 250;
    private static final int DELAY = 25;
//    private transient Image fan;
    private transient BufferedImage fan;

    public Board() throws IOException {
        initBoard();
    }

    private void initBoard() throws IOException {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        loadImage();
    }

    private void loadImage() throws IOException {
        Path imagePath = Paths.get("src", "resources", "fan4.png");
        fan = ImageIO.read(new File(imagePath.toString()));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        Thread animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFan(g);
    }

    private void drawFan(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int xValue = (this.getWidth() - fan.getWidth(null)) / 2;
        int yValue = (this.getHeight() - fan.getHeight(null)) / 2;
        g2d.drawImage(fan, xValue, yValue, this);
        Toolkit.getDefaultToolkit().sync();
    }

//    private void cycle() {
//        xValue += 1;
//        yValue += 1;
//
//        if (yValue > BOARD_HEIGHT) {
//            yValue = INIT_Y;
//            xValue = INIT_X;
//        }
//    }

    @Override
    public void run() {
        long startTime;
        long timeDiff;
        long sleep;

        boolean isRunning = true;

        startTime = System.currentTimeMillis();

        while(isRunning) {
//            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - startTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ie) {
                String message = String.format("Thread interrupted: %s", ie.getMessage());
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                isRunning = false;
                Thread.currentThread().interrupt();
            }

            startTime = System.currentTimeMillis();
        }
    }
}
