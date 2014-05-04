package com.vp.airviewer.listener;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;
import com.vp.airviewer.ui.RootPanel;

/**
 * Class lister using for processing gesture.
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 2/1/14
 * Time: 7:21 PM
 */
public class AVListener extends Listener {

    private RootPanel rp;

    public AVListener(RootPanel rp) {
        super();
        this.rp = rp;
    }

    @Override
    public void onInit(Controller controller) {
        //
    }

    @Override
    public void onConnect(Controller controller) {
        //
    }

    @Override
    public void onDisconnect(Controller controller) {
        //
    }

    @Override
    public void onExit(Controller controller) {
        //
    }

    @Override
    public void onFrame(Controller controller) {
        //
    }
}
