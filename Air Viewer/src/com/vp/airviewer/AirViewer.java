package com.vp.airviewer;

import com.vp.airviewer.fileutils.FileFinder;
import com.vp.airviewer.ui.AVFrame;

import java.util.ArrayList;

/**
 * Class for start the app.
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 2/1/14
 * Time: 7:10 PM
 */
public class AirViewer {
    public static final String IMAGE_FORMATS = "([^\\s]+(\\.(?i)(jpg|png|bmp))$)";
    public static final String START_PATH = "D:\\SkyDrive\\Pictures\\";

    public static void main(String[] args) {
        FileFinder imageFinder = new FileFinder();
        ArrayList<String> imageList = null;
        try {
            imageList = (ArrayList<String>) imageFinder.findAll(START_PATH, IMAGE_FORMATS);
            System.out.println(imageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AVFrame("Air Viewer", imageList);
    }
}
