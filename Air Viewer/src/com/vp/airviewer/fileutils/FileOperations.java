package com.vp.airviewer.fileutils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 5/13/14
 * Time: 9:23 PM
 */
public class FileOperations {
    private ArrayList imageList;
    private int IMAGE_LIST_LENGTH;
    private int currentPosition;

    public FileOperations(ArrayList imagesList) {
        this.imageList = imagesList;
        this.IMAGE_LIST_LENGTH = imagesList.size();
        this.currentPosition = 0;
    }

    public BufferedImage nextImage() {
        BufferedImage bufferedImage = getImageAt(currentPosition + 1);
        if (bufferedImage != null) {
            currentPosition++;
        }
        return bufferedImage;
    }

    public BufferedImage previousImage() {
        BufferedImage bufferedImage = getImageAt(currentPosition - 1);
        if (bufferedImage != null) {
            currentPosition--;
        }
        return bufferedImage;
    }

    public BufferedImage firstImage() {
        return getImageAt(0);
    }

    private BufferedImage getImageAt(int index) {
        boolean isOutOfBounds = checkBounds(index);
        BufferedImage bufferedImage = null;
        if (!isOutOfBounds) {
            try {
                bufferedImage = ImageIO.read((File) imageList.get(index));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferedImage;
    }

    private boolean checkBounds(int currentPosition) {
        return currentPosition < 0 || currentPosition > IMAGE_LIST_LENGTH;
    }

}
