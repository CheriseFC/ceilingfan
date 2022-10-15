package org.cherise;

import javax.swing.*;
import java.awt.*;

/**
 * @author Cherise Franklin-Clarke
 *
 * main class
 */
public class CeilingFanAnimation extends JFrame {

    /**
     * Method used to initialize the UI
     */
    public CeilingFanAnimation() {
        initUI();
    }

    private void initUI() {
        add(new CeilingFan());
        setResizable(false);
        pack();
        setTitle("Ceiling Fan");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(new CeilingFanKeyListener());
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ceilingFanAnimationJFrame = null;
            ceilingFanAnimationJFrame = new CeilingFanAnimation();
            ceilingFanAnimationJFrame.setVisible(true);
        });
    }

}