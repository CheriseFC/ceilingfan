package org.cherise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Cherise Franklin-Clarke
 *
 * KeyListener class
 */
public class CeilingFanKeyListener implements KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CeilingFanKeyListener.class);

    /**
     * This program is not listening to typed key events
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // No need to track keys typed
    }

    /**
     * This method is used to listen for key press events on the up arrow and 'R'
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            LOGGER.debug("UP key pressed");
            CeilingFan.increaseSpeed();
        }

        if (key == KeyEvent.VK_R) {
            LOGGER.debug("R pressed");
            CeilingFan.reverseDirection();
        }
    }

    /**
     * This program is not listening to key release events
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // No need to track keys released
    }
}
