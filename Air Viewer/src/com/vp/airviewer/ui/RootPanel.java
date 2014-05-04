package com.vp.airviewer.ui;

import com.leapmotion.leap.Controller;
import com.vp.airviewer.listener.AVListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 5/4/14
 * Time: 4:22 PM
 */
public class RootPanel extends JPanel {

    private AVListener listener;
    private Controller controller;

    private BufferedImage bufferedImage;
    private JLabel imgLabel;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public RootPanel() {


        listener = new AVListener(this);
        controller = new Controller();
        controller.addListener(listener);

        addImageLabel();


    }

    private void addImageLabel() {
        try {
            bufferedImage = ImageIO.read(new File(""));
            ImageIcon icon = new ImageIcon(bufferedImage);
            imgLabel = new JLabel();
            imgLabel.setIcon(icon);
            add(imgLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove the listener;
     * Called from top-level at termination
     */
    public void stopListening() {
        controller.removeListener(listener);
    }
}
