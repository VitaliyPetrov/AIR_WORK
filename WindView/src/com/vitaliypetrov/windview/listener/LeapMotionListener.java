package com.vitaliypetrov.windview.listener;

import com.leapmotion.leap.*;
import com.vitaliypetrov.windview.ui.WVFrame;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 1/5/14
 * Time: 3:18 PM
 */

public class LeapMotionListener extends Listener {

    private WVFrame view;

    public LeapMotionListener(WVFrame view) {
        this.view = view;
    }

    @Override
    public void onInit(Controller controller) {
       view.writeToEventBox("Initialized");
    }

    @Override
    public void onConnect(Controller controller) {
        view.writeToEventBox("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    @Override
    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        view.writeToEventBox("Disconnected");
    }

    @Override
    public void onExit(Controller controller) {
        view.writeToEventBox("Exited");
    }

    @Override
    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        view.writeToEventBox("\nFrame id: " + frame.id()
                + ", timestamp: " + frame.timestamp()
                + ", hands: " + frame.hands().count()
                + ", fingers: " + frame.fingers().count()
                + ", tools: " + frame.tools().count()
                + ", gestures " + frame.gestures().count());

        if (!frame.hands().isEmpty()) {
            // Get the first hand
            Hand hand = frame.hands().get(0);

            // Check if the hand has any fingers
            FingerList fingers = hand.fingers();
            if (!fingers.isEmpty()) {
                // Calculate the hand's average finger tip position
                Vector avgPos = Vector.zero();
                for (Finger finger : fingers) {
                    avgPos = avgPos.plus(finger.tipPosition());
                }
                avgPos = avgPos.divide(fingers.count());
                view.writeToEventBox("\nHand has " + fingers.count()
                        + " fingers, average finger tip position: " + avgPos);
            }

            // Get the hand's sphere radius and palm position
            view.writeToEventBox("\nHand sphere radius: " + hand.sphereRadius()
                    + " mm, palm position: " + hand.palmPosition());

            // Get the hand's normal vector and direction
            Vector normal = hand.palmNormal();
            Vector direction = hand.direction();

            // Calculate the hand's pitch, roll, and yaw angles
            view.writeToEventBox("\nHand pitch: " + Math.toDegrees(direction.pitch()) + " degrees, "
                    + "roll: " + Math.toDegrees(normal.roll()) + " degrees, "
                    + "yaw: " + Math.toDegrees(direction.yaw()) + " degrees");
        }

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_CIRCLE:
                    CircleGesture circle = new CircleGesture(gesture);

                    // Calculate clock direction using the angle between circle normal and pointable
                    String clockwiseness;
                    if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4) {
                        // Clockwise if angle is less than 90 degrees
                        clockwiseness = "clockwise";
                    } else {
                        clockwiseness = "counterclockwise";
                    }

                    // Calculate angle swept since last frame
                    double sweptAngle = 0;
                    if (circle.state() != Gesture.State.STATE_START) {
                        CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
                        sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
                    }

                    view.writeToEventBox("\nCircle id: " + circle.id()
                            + ", " + circle.state()
                            + ", progress: " + circle.progress()
                            + ", radius: " + circle.radius()
                            + ", angle: " + Math.toDegrees(sweptAngle)
                            + ", " + clockwiseness);
                    break;
                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    view.writeToEventBox("\nSwipe id: " + swipe.id()
                            + ", " + swipe.state()
                            + ", position: " + swipe.position()
                            + ", direction: " + swipe.direction()
                            + ", speed: " + swipe.speed());
                    break;
                case TYPE_SCREEN_TAP:
                    ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
                    view.writeToEventBox("\nScreen Tap id: " + screenTap.id()
                            + ", " + screenTap.state()
                            + ", position: " + screenTap.position()
                            + ", direction: " + screenTap.direction());
                    break;
                case TYPE_KEY_TAP:
                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
                    view.writeToEventBox("\nKey Tap id: " + keyTap.id()
                            + ", " + keyTap.state()
                            + ", position: " + keyTap.position()
                            + ", direction: " + keyTap.direction());
                    break;
                default:
                    view.writeToEventBox("\nUnknown gesture type.");
                    break;
            }
        }

        if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
            view.writeToEventBox("Hands or gestures not detected");
        }
    }
}
