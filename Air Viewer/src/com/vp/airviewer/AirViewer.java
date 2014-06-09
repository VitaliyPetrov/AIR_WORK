package com.vp.airviewer;

import com.vp.airviewer.fileutils.FileFinder;
import com.vp.airviewer.fileutils.FileOperations;
import com.vp.airviewer.ui.AVFrame;

import java.io.File;
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
    public static String START_PATH = "D:\\";

    public static void main(String[] args) {
        START_PATH = new File(".").getAbsolutePath();
        FileFinder imageFinder = new FileFinder();
        ArrayList<String> imageList = null;
        try {
            imageList = (ArrayList<String>) imageFinder.findAll(START_PATH, IMAGE_FORMATS);
            System.out.println(imageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOperations fileOperations = new FileOperations(imageList);
        new AVFrame("Air Viewer", fileOperations);
    }
}
