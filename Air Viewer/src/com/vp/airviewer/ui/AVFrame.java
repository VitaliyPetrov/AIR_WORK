package com.vp.airviewer.ui;

import com.vp.airviewer.fileutils.FileOperations;

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

    private static final String LOGO_PATH = "/logo.png";

    private RootPanel rp;
    private FileOperations fileOperations;

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
    public AVFrame(String title, FileOperations fileOperations) throws HeadlessException {
        super(title);
        this.fileOperations = fileOperations;

        AddRootPanel(fileOperations);

        addWindowListener(new WindowAdapter() {
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


        //Config frame
        //TODO: Set absolute path
        this.setIconImage(new ImageIcon(LOGO_PATH).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // center the window
        setVisible(true);
    }

    /**
     * Method add root panel into frame
     */
    private void AddRootPanel(FileOperations fileOperations) {
        rp = new RootPanel(fileOperations);
        Container container = getContentPane();
        container.add(rp.getScrollPane());
    }
}
