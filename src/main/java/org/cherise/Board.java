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
    private static final int DELAY = 500;
    private static BufferedImage fan;

    public Board() throws IOException {
        initBoard();
    }

    private void initBoard() throws IOException {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        loadImage();
    }

    private static void loadImage() throws IOException {
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
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawFan(graphics);
    }

    private void drawFan(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        int xValue = (this.getWidth() - fan.getWidth(null)) / 2;
        int yValue = (this.getHeight() - fan.getHeight(null)) / 2;
        graphics2D.drawImage(fan, xValue, yValue, this);
        Toolkit.getDefaultToolkit().sync();
    }

//    private void fanCycle() {
//
//    }

    public static void rotateFan() {
        double radians = Math.toRadians(-10);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int fanWidth = fan.getWidth();
        int fanHeight = fan.getHeight();
        int newFanWidth = (int) Math.floor(fanWidth * cos + fanHeight * sin);
        int newFanHeight = (int) Math.floor(fanHeight * cos + fanWidth * sin);

        GraphicsConfiguration graphicsConfiguration = getDefaultConfiguration();

        BufferedImage newFan = graphicsConfiguration.createCompatibleImage(newFanWidth, newFanHeight, Transparency.TRANSLUCENT);
        Graphics2D graphics2D = newFan.createGraphics();
        graphics2D.translate((newFanWidth - fanWidth) / 2, (newFanHeight - fanHeight) / 2);
        graphics2D.rotate(radians, (double) fanWidth / 2, (double) fanHeight / 2);
        graphics2D.drawRenderedImage(fan, null);
        graphics2D.dispose();

        fan = newFan;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        return graphicsDevice.getDefaultConfiguration();
    }

    @Override
    public void run() {
        long startTime;
        long timeDiff;
        long sleep;

        boolean isRunning = true;

        startTime = System.currentTimeMillis();

        while(isRunning) {
            for (int i = 0; i < 8; i++) {
                rotateFan();
                repaint();

                timeDiff = System.currentTimeMillis() - startTime;
                sleep = DELAY - timeDiff;

                if (sleep < 0) {
                    sleep = 2;
                }

                try {
//                    Thread.sleep(sleep);
                    Thread.sleep(DELAY);
                } catch (InterruptedException ie) {
                    String message = String.format("Thread interrupted: %s", ie.getMessage());
                    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                    isRunning = false;
                    Thread.currentThread().interrupt();
                }

                startTime = System.currentTimeMillis();
            }
            try {
                loadImage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
