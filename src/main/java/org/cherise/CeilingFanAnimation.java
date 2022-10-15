package org.cherise;

import javax.swing.*;
import java.awt.*;

public class CeilingFanAnimation extends JFrame {

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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ceilingFanAnimationJFrame = null;
            ceilingFanAnimationJFrame = new CeilingFanAnimation();
            ceilingFanAnimationJFrame.setVisible(true);
        });
    }

}