package com.vitaliypetrov.windview.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 1/16/14
 * Time: 8:18 PM
 */
public class WVDotsFrame extends JFrame {

    private WVDotsPanel dp;

    public WVDotsFrame(){
        super("Wind View Dots example");
        Container container = getContentPane();
        dp = new WVDotsPanel();
        container.add(dp);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener( new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                dp.stopListening();
                System.exit(0);
            }
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(null); // center the window
        setVisible(true);
    }
}
