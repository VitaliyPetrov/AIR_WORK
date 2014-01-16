package com.vitaliypetrov.windview.listener;

import com.leapmotion.leap.*;
import com.vitaliypetrov.windview.ui.WVDotsPanel;

import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 1/16/14
 * Time: 9:40 PM
 */
public class LeapMotionDotsListener extends Listener {
    private static final int CHANGE_INTERVAL = 100; // (ms) minimum time interval between listener's changes to the dots
    private static final int TICK_COLOR = 4; // number of circle gestures required before a color change

    private WVDotsPanel dotsPanel; // reference back to drawing area
    private long startTime;       // used to control the frequency of listener changes
    private int cycleCounter = 0; // used to control the frequency of color changes

    public LeapMotionDotsListener(WVDotsPanel dp){
        super();
        dotsPanel = dp;
        startTime = System.currentTimeMillis();
    }

    public void onConnect(Controller controller){
        System.out.println("Controller has been connected");
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
    }


    public void onExit(Controller controller){
        System.out.println("Exited");  }


    public void onFrame(Controller controller)
    {
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

        // slow down processing rate to be every CHANGE_INTERVAL ms
        long currTime = System.currentTimeMillis();
        if (currTime - startTime < CHANGE_INTERVAL)
            return;     // don't do anything until CHANGE_INTERVAL time has passed

        int handsCount = frame.hands().count();
        if (handsCount == 1) {      // one hand
            Hand hand =  frame.hands().get(0);
            int fingersCount =  hand.fingers().count();
            Point2D.Float normPt = calcScreenNorm(hand, screen);
            if (fingersCount == 1)   // one finger == move point
                dotsPanel.movePoint(normPt);
            else if (fingersCount == 2)   // two fingers == add point
                dotsPanel.addPoint(normPt);
            else if (fingersCount == 3)   // 3 fingers == undo
                dotsPanel.undo();

        }
        else if (handsCount == 2)  // two hands == circle gestures with left
            processCircle(frame);

        startTime = System.currentTimeMillis();    // reset start time
    }  // end of onFrame()




    private Point2D.Float calcScreenNorm(Hand hand, Screen screen)
  /* The dot position is calculated using the screen position that the
     user's hand is pointing at, which is then normalized to an (x,y)
     value between -1 and 1, where (0,0) is the center of the screen.
  */
    {
        Vector palm = hand.palmPosition();
        Vector direction = hand.direction();
        Vector intersect = screen.intersect(palm, direction, true);
        // intersection is in screen coordinates

        // test for NaN (not-a-number) result of intersection
        if (Float.isNaN(intersect.getX()) || Float.isNaN(intersect.getY()))
            return null;

        float xNorm = (Math.min(1, Math.max(0, intersect.getX())) - 0.5f)*2;      // constrain to -1 -- 1
        float yNorm = (Math.min(1, Math.max(0, (1-intersect.getY()))) - 0.5f)*2;

        return new Point2D.Float(xNorm, yNorm);
    }  // end of calcScreenNorm()





    private void processCircle(Frame frame)
  /* If the left hand is carrying out a circling gesture then cycle the
     colors in the panel. Interpret the gesture as either a clockwise
     or counter-clockwise turn; do not use pass the angle to the panel.
     These circle gestures occur too quickly for the color change to be
     easy to control, and so a counter is used to slow down the rate that
     the panel's cycleColor() is called.
  */
    {
        Hand leftHand = frame.hands().leftmost();
        int leftHandID = leftHand.id();

        GestureList gestures = frame.gestures();
        for (Gesture gesture : gestures) {
            if (isGestureHand(gesture, leftHandID) &&
                    (gesture.type() == Gesture.Type.TYPE_CIRCLE)) {     // only process circles from left hand
                CircleGesture circle = new CircleGesture(gesture);

                // Calculate clock direction using the angle between circle normal and pointable
                boolean isClockwise =
                        (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4);
                if (cycleCounter == TICK_COLOR) {
                    dotsPanel.cycleColor(isClockwise);
                    cycleCounter = 0;
                }
                else
                    cycleCounter++;
                break;
            }
        }
    }  // end of processCircle()



    private boolean isGestureHand(Gesture gesture, int handID)
    // does gesture originate from the hand with the specified ID?
    {
        HandList gestHands = gesture.hands();
        for(Hand h : gestHands)
            if (h.id() == handID)
                return true;
        return false;
    }  // end of isGestureHand()



    private Vector round1dp(Vector v)
    // round the x,y,z values to 1 dp
    {
        v.setX( (float)Math.round(v.getX()*10)/10 );
        v.setY( (float)Math.round(v.getY()*10)/10 );
        v.setZ( (float)Math.round(v.getZ()*10)/10 );
        return v;
    }  // end of round1dp()


    private float round1dp(float f)
    // round to 1 dp
    {  return (float)Math.round(f*10)/10;  }



}
