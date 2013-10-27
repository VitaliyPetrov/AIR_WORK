package com.vitaliypetrov.windview.UI;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy Petrov
 * Date: 10/27/13
 * Time: 2:14 PM
 */
public class WVPanel extends JPanel {
    /**
     * Constructor, create JPanel with scrolled JTextArea for showing event
     */
    public WVPanel() {
        super();

        JTextArea eventBox = new JTextArea();
        eventBox.setEditable(false);
        eventBox.setLineWrap(true);
        eventBox.setWrapStyleWord(true); // lines will be wrapped by word

        JScrollPane scrollPane = new JScrollPane(eventBox);// add scroll for JTextArea
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);
    }
}
