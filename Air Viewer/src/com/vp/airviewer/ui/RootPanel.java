package com.vp.airviewer.ui;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 5/4/14
 * Time: 4:22 PM
 */
public class RootPanel extends JPanel {

    /**
     * Remove the listener;
     * Called from top-level at termination
     */
    public void stopListening(){
        controller.removeListener(listener);
    }
}
