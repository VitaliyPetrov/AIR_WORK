package com.vp.airviewer.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Main window of app.
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 2/1/14
 * Time: 7:17 PM
 */
public class AVFrame extends JFrame {
    /**
     * Creates a new, initially invisible <code>Frame</code> with the
     * specified title.
     * <p/>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param title the title for the frame
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
     *                                    returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Component#setSize
     * @see java.awt.Component#setVisible
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public AVFrame(String title) throws HeadlessException {
        super(title);
        // add UI elements
    }
}
