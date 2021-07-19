package me.Abhigya.core.util.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class for dealing with images.
 */
public class ImageUtils {

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional).
     * <p>
     *
     * @param inputImagePath  Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth     Absolute width in pixels
     * @param scaledHeight    Absolute height in pixels
     * @throws IOException thrown while dealing with I/O functions.
     */
    public static void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     * <p>
     *
     * @param inputImagePath  Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent         Double number specifies percentage of the output
     *                        image over the input image
     * @throws IOException thrown while dealing with I/O functions.
     */
    public static void resize(String inputImagePath, String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    /**
     * Converts the {@link BufferedImage} type.
     * <p>
     *
     * @param srcImage    {@link BufferedImage} to convert
     * @param destImgType Type to convert into
     * @return Converted image
     */
    public static BufferedImage convert(final BufferedImage srcImage, final int destImgType) {
        BufferedImage img = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), destImgType);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(srcImage, 0, 0, null);
        g2d.dispose();
        return img;
    }

}
