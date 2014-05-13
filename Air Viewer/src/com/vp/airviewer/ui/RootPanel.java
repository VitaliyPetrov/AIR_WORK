package com.vp.airviewer.ui;

import com.leapmotion.leap.Controller;
import com.vp.airviewer.listener.AVListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    private JScrollPane scrollPane;


    private ArrayList imageList = null;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public RootPanel(ArrayList imageList) {

        this.imageList = imageList;
        listener = new AVListener(this);
        controller = new Controller();
        controller.addListener(listener);

        addImageLabel();
        showImage((File) this.imageList.get(0));
    }

    private void addImageLabel() {
        imgLabel = new JLabel();
        scrollPane = new JScrollPane(imgLabel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * Remove the listener;
     * Called from top-level at termination
     */
    public void stopListening() {
        controller.removeListener(listener);
    }

    public void showImage(File imagePath) {
        try {
            bufferedImage = ImageIO.read(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(bufferedImage);
        imgLabel.setIcon(icon);
    }

    public void nextImage() {
        showImage((File) this.imageList.get(1));
    }

    public void previosImage() {
        showImage((File) this.imageList.get(0));
    }
}
