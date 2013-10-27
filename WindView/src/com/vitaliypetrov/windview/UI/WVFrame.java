package com.vitaliypetrov.windview.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy Petrov
 * Date: 10/27/13
 * Time: 1:56 PM
 */

public class WVFrame extends JFrame {
    private final int FRAME_WIDTH = 500;
    private final int FRAME_HEIGHT = 500;
    private final String PROJECT_NAME = "WV" ;

    /**
     * Constructor for main application window
     * @throws HeadlessException
     */
    public WVFrame() throws HeadlessException {
        super();
        setTitle(PROJECT_NAME);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);

        //Creating root panel without any layout
        JPanel rootPanel =new JPanel();
        rootPanel.setLayout(null);

        //Create and add JPanel with JTextArea for even log
        WVPanel eventPanel = new WVPanel();

        rootPanel.add(eventPanel);
        this.add(rootPanel);

    }
}
