package org.cherise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Cherise Franklin-Clarke
 *
 * JPanel class
 */
public class CeilingFan extends JPanel implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CeilingFan.class);

    private static final int CEILING_WIDTH = 250;
    private static final int CEILING_HEIGHT = 250;
    private static int delay = 500;
    private static int fanSetting = 0;
    private static int angle = 10;
    private static boolean isFanRunning = false;
    private static BufferedImage fan;

    /**
     * Method to initialize the fan
     */
    public CeilingFan() {
        initCeilingFan();
    }

    private void initCeilingFan() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(CEILING_WIDTH, CEILING_HEIGHT));
        loadImage();
    }

    private static void loadImage() {
        Path imagePath = Paths.get("src", "resources", "fan4.png");
        try {
            fan = ImageIO.read(new File(imagePath.toString()));
        } catch (IOException ioe) {
            String msg = String.format("Unable to load image: %s%n%s", imagePath, ioe);
            LOGGER.error(msg);
        }
    }

    /**
     * Method used for initialization tasks
     */
    @Override
    public void addNotify() {
        super.addNotify();
        Thread animator = new Thread(this);
        animator.start();
    }

    /**
     * Method for custom painting
     * @param graphics the <code>Graphics</code> object to protect
     */
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

    private static void rotateFan() {
        double radians = Math.toRadians(angle);
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

    /**
     * Method used to increase the fan speed
     */
    public static void increaseSpeed() {
        if (fanSetting == 0) {
            fanSetting++;
            isFanRunning = true;
        } else if (fanSetting < 3) {
            delay = delay / 2;
            fanSetting++;
        } else {
            stopFan();
            fanSetting = 0;
            resetDelay();
        }

        String msg = String.format("Fan speed: %s", fanSetting);
        LOGGER.debug(msg);
    }

    /**
     * Method used to reverse the direction of the fan's rotation
     */
    public static void reverseDirection() {
        angle = angle * -1;
        fanSetting = 0;
        resetDelay();

        LOGGER.debug("Direction reversed");
    }

    private static void stopFan() {
        isFanRunning = false;
        LOGGER.debug("The fan has stopped");
    }

    private static void resetDelay() {
        delay = 500;
    }

    /**
     * Method used to animate the fan's rotation using a Thread
     */
    @Override
    public void run() {
        boolean loopEnabled = true;

        while(loopEnabled) {
            for (int i = 0; i < 9; i++) {
                if(isFanRunning) {
                    rotateFan();
                    if (i == 8) {
                        loadImage();
                    }
                }

                repaint();

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    String message = String.format("Thread interrupted: %s", ie.getMessage());
                    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                    loopEnabled = false;
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
