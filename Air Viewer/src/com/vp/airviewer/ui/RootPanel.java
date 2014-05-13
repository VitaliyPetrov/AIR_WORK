package com.vp.airviewer.ui;

import com.leapmotion.leap.Controller;
import com.vp.airviewer.fileutils.FileOperations;
import com.vp.airviewer.listener.AVListener;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 5/4/14
 * Time: 4:22 PM
 */
public class RootPanel extends JPanel {

    private AVListener listener;
    private Controller controller;

    private JLabel imgLabel;
    private JScrollPane scrollPane;


    private FileOperations fileOperations = null;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public RootPanel(FileOperations fileOperations) {

        this.fileOperations = fileOperations;
        listener = new AVListener(this);
        controller = new Controller();
        controller.addListener(listener);

        addImageLabel();
        showImage(this.fileOperations.firstImage());
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

    public void showImage(BufferedImage bufferedImage) {
        ImageIcon icon = new ImageIcon(bufferedImage);
        imgLabel.setIcon(icon);
    }

    public void nextImage() {
        showImage(this.fileOperations.nextImage());
    }

    public void previosImage() {
        showImage(this.fileOperations.previousImage());
    }
}
