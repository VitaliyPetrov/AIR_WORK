package com.vitaliypetrov.windview.ui;

import com.leapmotion.leap.Controller;
import com.vitaliypetrov.windview.listener.LeapMotionListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy Petrov
 * Date: 10/27/13
 * Time: 1:56 PM
 */

public class WVFrame extends JFrame {
    private final int FRAME_WIDTH = 500;
    private final int FRAME_HEIGHT = 500;
    private final String PROJECT_NAME = "WV";

    private JTextArea eventBox = null;
    private LeapMotionListener listener;
    private Controller controller;

    /**
     * Constructor for main application window
     * @throws HeadlessException
     */
    public WVFrame() throws HeadlessException {
        super();
        setTitle(PROJECT_NAME);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createGUI();

        listener = new LeapMotionListener(this);
        controller = new Controller();
        controller.addListener(listener);


        addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                controller.removeListener(listener);    // Remove the listener when Window close
                System.exit(0);
            }
        });

        setLocationRelativeTo(null); // center the window
        setVisible(true);

       /*
         Additional device connection check after 5 seconds
         Note: It is not  necessary.
       */
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException e) {
            //ignore
        }

        if (!controller.isConnected()) {
            writeToEventBox("Cannot connect to Leap");
        }
    }

    /**
     * Create and add JPanel with JTextArea for event log
     */
    private void createGUI() {
        Container container = getContentPane();

        JPanel eventPanel = new JPanel(new GridLayout(1,1));

        eventBox = new JTextArea();
        eventBox.setEditable(false);
        eventBox.setLineWrap(true);
        eventBox.setWrapStyleWord(true); // lines will be wrapped by word

        DefaultCaret caret = (DefaultCaret)eventBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); //caret always will be in end of text

        JScrollPane scrollPane = new JScrollPane(eventBox);// add scroll for JTextArea
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        eventPanel.add(scrollPane);
        container.add(eventPanel);
    }

    /**
     * Method appending new information to event log element
     * @param msg String - message or information
     */
    public void writeToEventBox(String msg){
        eventBox.append("\n");
        eventBox.append(msg);
    }


}
