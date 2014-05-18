package com.vp.airviewer.listener;

import com.leapmotion.leap.*;
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
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
    }

    @Override
    public void onDisconnect(Controller controller) {
        //
    }

    @Override
    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    @Override
    public void onFrame(Controller controller) {
        Frame frame = controller.frame();

        // if no hand detected, give up
        if (frame.hands().isEmpty())
            return;

        // if the screen isn't available give up
        Screen screen = controller.locatedScreens().get(0);
        if (screen == null) {
            System.out.println("No screen found");
            return;
        }
        if (!screen.isValid()) {
            System.out.println("Screen not valid");
            return;
        }

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);
            switch (gesture.type()) {
                case TYPE_SWIPE:
                    SwipeGesture swipeGesture = new SwipeGesture(gesture);
                    Vector direction = swipeGesture.direction();
                    if (direction.getX() > 0) {
                        rp.nextImage();  //right direction
                    } else {
                        rp.previousImage(); //left direction
                    }
                    break;
                case TYPE_CIRCLE:
                    CircleGesture circleGesture = new CircleGesture(gesture);
                    //to do
            }
        }
    }
}
