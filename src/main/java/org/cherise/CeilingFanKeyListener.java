package org.cherise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CeilingFanKeyListener implements KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CeilingFanKeyListener.class);

    @Override
    public void keyTyped(KeyEvent e) {
        // No need to track keys typed
    }

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

    @Override
    public void keyReleased(KeyEvent e) {
        // No need to track keys released
    }
}
