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
        boolean isOutOfBounds = checkBounds(currentPosition);
        BufferedImage bufferedImage = null;
        if (!isOutOfBounds) {
            try {
                bufferedImage = ImageIO.read((File) imageList.get(currentPosition + 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentPosition++;
        }
        return bufferedImage;
    }

    public BufferedImage previousImage() {
        boolean isOutOfBounds = checkBounds(currentPosition);
        BufferedImage bufferedImage = null;
        if (!isOutOfBounds) {
            try {
                bufferedImage = ImageIO.read((File) imageList.get(currentPosition - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentPosition--;
        }
        return bufferedImage;
    }

    private boolean checkBounds(int currentPosition) {
        return currentPosition < 0 || currentPosition > IMAGE_LIST_LENGTH;
    }

    public BufferedImage firstImage() {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read((File) imageList.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }
}
