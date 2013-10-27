package com.vitaliypetrov.windview.Start;

import com.vitaliypetrov.windview.UI.WVFrame;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy Petov
 * Date: 10/27/13
 * Time: 2:43 PM
 */
public class Start {
    public static void main(String[] args){
        WVFrame mainWindow = new WVFrame();
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
