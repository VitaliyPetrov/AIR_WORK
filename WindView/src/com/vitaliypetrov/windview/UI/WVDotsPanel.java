package com.vitaliypetrov.windview.ui;

import com.leapmotion.leap.Controller;
import com.vitaliypetrov.windview.listener.LeapMotionDotsListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 1/16/14
 * Time: 8:34 PM
 */
public class WVDotsPanel extends JPanel {

    private static final int MAXPOINTS = 5000;

    // dimensions of the panel, which are fixed
    private static final int PWIDTH = 800;
    private static final int PHEIGHT = 500;


    // for storing the drawable dots and their colors
    private Point[] points = new Point[MAXPOINTS];
    private Color[] pointsColor = new Color[MAXPOINTS];
    private int nPoints = 0;   // index into the two arrays

    // range of colors that can be cycled through
    private Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.BLACK, Color.GRAY};
    private int currColor = 0;   // starting color is blue

    private LeapMotionDotsListener listener;
    private Controller controller;

    public WVDotsPanel(){
        setBackground(Color.WHITE);

        // the first dot starts in the center of the panel, and is blue
        points[nPoints] = new Point(PWIDTH/2,PHEIGHT/2);
        pointsColor[nPoints] = colors[currColor];
        nPoints++;

        listener = new LeapMotionDotsListener(this);
        controller = new Controller();
        controller.addListener(listener);

    }

    public Dimension getPreferredSize(){
        return new Dimension(PWIDTH,PHEIGHT);
    }


    /**
     * repaint the panel by redrawing all the colored dots
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponents(g);  // repaint standard stuff first
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); // so dots are round

        for(int i=0; i< nPoints; i++){
            g2.setColor(pointsColor[i]);
            g2.fillOval(points[i].x,points[i].y, 12, 12);
        }
    }

    /**
     * Record a dot at the specified position, then request a repaint.
     * The normPt point is an offset, with the x- and y- values in the range -1 to 1
     * called by the listener.
     * @param normPt
     */
    public void addPoint(Point2D.Float normPt){

        if(normPt == null){
            return;
        }

        if((normPt.x == 0) && normPt.y == 0){
            return; // do nothing since offset is zero
        }

        if(nPoints < MAXPOINTS){ // add a new dot
            points[nPoints] = toScrCoords(normPt.x,normPt.y);
            pointsColor[nPoints] = colors[currColor];
            nPoints++;
            repaint(); //the repaint will call paintComponent() method
        }
    }

    /**
     * The norm values are offsets, in the range -1 to 1.
     * Add the offset (multiplied by 12 to make it bigger) to the previous point's position to get a new (x, y) position.
     * Don't allow the new dot to be located outside the panel.
     * @param xNorm
     * @param yNorm
     * @return
     */
    private Point toScrCoords(float xNorm, float yNorm){
        int x = (int)Math.round( (float)points[nPoints-1].x + xNorm*12);
        int y = (int)Math.round( (float)points[nPoints-1].y + yNorm*12);

        // restrict the new dot to be visible on the panel
        if(x < 0){
            x = 0;
        } else if (x > PWIDTH-1){
            x = PWIDTH-1;
        }

        if(y < 0){
            y = 0;
        } else if (y > PHEIGHT-1){
            y = PHEIGHT-1;
        }
        return new Point(x,y);
    }

    /**
     * Move the current dot by calculating a new (x,y) position using toScrCoords(), but assign that value to the current dot rather
     * than create a new one.
     * Then request a repaint.
     * Called by the listener
     * @param normPt
     */
    public void movePoint(Point2D.Float normPt){
        if (normPt == null){
            return;
        }
        if ((normPt.x == 0) && (normPt.y == 0)){
            return;     // do nothing since offset is zero
        }
        points[nPoints-1] = toScrCoords(normPt.x, normPt.y);  // change current dot
        repaint();
    }

    /**
     * Cycle through the colors by incrementing (or decrementing) the currColor integer, modulo the size of colors[].
     * Then request a repaint.
     * Called by the listener
     * @param isClockWise
     */
    public void cycleColor(boolean isClockWise){
        if (isClockWise)    // increment
            currColor = (currColor + 1) % colors.length;
        else {   // decrement
            currColor--;
            if (currColor < 0)
                currColor = colors.length-1;
        }
        pointsColor[nPoints-1] = colors[currColor];
        repaint();
    }

    /**
     * Delete a point by decrementing the nPoints value which indexes into the points[] and pointsColor[] arrays.
     * However, do not delete the first point, since this is needed to calculate the second point by adding an offset to it.
     * Then request a repaint.
     * Called by the listener
     */
    public void undo(){
        if (nPoints == 1)    // don't delete the first point
            return;
        nPoints--;
        repaint();
    }

    /**
     * Remove the listener;
     * Called from top-level at termination
     */
    public void stopListening(){
        controller.removeListener(listener);
    }

}
