package org.cherise;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CeilingFanAnimation extends JFrame {

    public CeilingFanAnimation() throws IOException {
        initUI();
    }

    private void initUI() throws IOException {
        add(new CeilingFan());
        setResizable(false);
        pack();
        setTitle("Ceiling Fan");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ceilingFanAnimationJFrame = null;
            try {
                ceilingFanAnimationJFrame = new CeilingFanAnimation();
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            ceilingFanAnimationJFrame.setVisible(true);
        });
    }

}