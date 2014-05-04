package com.vp.airviewer.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main window of app.
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 2/1/14
 * Time: 7:17 PM
 */
public class AVFrame extends JFrame {

    private RootPanel rp;
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

        AddRootPanel();

        //Config frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener( new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                rp.stopListening();
                System.exit(0);
            }
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(null); // center the window
        setVisible(true);
    }

    /**
     * Method add root panel into frame
     */
    private void AddRootPanel() {
        rp = new RootPanel();
        Container container = getContentPane();
        container.add(rp);
    }
}
