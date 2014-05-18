package com.vp.airviewer.fileutils;

import com.vp.airviewer.ui.AVFrame;

import javax.imageio.ImageIO;
import java.awt.*;
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
    private AVFrame frame;

    public FileOperations(ArrayList imagesList) {
        this.imageList = imagesList;
        this.IMAGE_LIST_LENGTH = imagesList.size();
        this.currentPosition = 0;
    }

    public BufferedImage nextImage() {
        BufferedImage bufferedImage = getPreparedImage(currentPosition + 1);
        if (bufferedImage != null) {
            currentPosition++;
        }
        return bufferedImage;
    }

    public BufferedImage previousImage() {
        BufferedImage bufferedImage = getPreparedImage(currentPosition - 1);
        if (bufferedImage != null) {
            currentPosition--;
        }
        return bufferedImage;
    }

    public BufferedImage firstImage() {
        return getPreparedImage(0);
    }

    private BufferedImage getPreparedImage(int index) {
        BufferedImage bufferedImage = getImageAt(index);
        Dimension resizeDimension = prepareResizeDimension(bufferedImage);
        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
        return resizeImage(bufferedImage, resizeDimension, type);
    }

    //TODO: Add checking for null
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

    public void setFrame(AVFrame frame) {
        this.frame = frame;
    }

    private Dimension prepareResizeDimension(BufferedImage bufferedImage) {
        Dimension imageDimension = new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
        return getScaledDimension(imageDimension, frame.getBounds().getSize());
    }

    private Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    public BufferedImage resizeImage(BufferedImage originalImage, Dimension resizeDimension, int type) {
        BufferedImage resizedImage = new BufferedImage(resizeDimension.width, resizeDimension.height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, resizeDimension.width, resizeDimension.height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }
}
