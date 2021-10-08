package me.Abhigya.core.util.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

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
    public static void resize( String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight )
            throws IOException {
        // reads input image
        File inputFile = new File( inputImagePath );
        BufferedImage inputImage = ImageIO.read( inputFile );

        // creates output image
        BufferedImage outputImage = new BufferedImage( scaledWidth, scaledHeight, inputImage.getType( ) );

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics( );
        g2d.drawImage( inputImage, 0, 0, scaledWidth, scaledHeight, null );
        g2d.dispose( );

        // extracts extension of output file
        String formatName = outputImagePath.substring( outputImagePath.lastIndexOf( "." ) + 1 );

        // writes to output file
        ImageIO.write( outputImage, formatName, new File( outputImagePath ) );
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
    public static void resize( String inputImagePath, String outputImagePath, double percent ) throws IOException {
        File inputFile = new File( inputImagePath );
        BufferedImage inputImage = ImageIO.read( inputFile );
        int scaledWidth = (int) ( inputImage.getWidth( ) * percent );
        int scaledHeight = (int) ( inputImage.getHeight( ) * percent );
        resize( inputImagePath, outputImagePath, scaledWidth, scaledHeight );
    }

    /**
     * Converts the {@link BufferedImage} type.
     * <p>
     *
     * @param srcImage    {@link BufferedImage} to convert
     * @param destImgType Type to convert into
     * @return Converted image
     */
    public static BufferedImage convert( final BufferedImage srcImage, final int destImgType ) {
        BufferedImage img = new BufferedImage( srcImage.getWidth( ), srcImage.getHeight( ), destImgType );
        Graphics2D g2d = img.createGraphics( );
        g2d.drawImage( srcImage, 0, 0, null );
        g2d.dispose( );
        return img;
    }

    /**
     * Read the image from the specified resource
     *
     * @param image the image resource path
     * @return the image as string
     */
    public static String readImage( InputStream image ) {
        StringBuilder builder = new StringBuilder( );
        try {
            BufferedImage img = ImageIO.read( image );
            for ( int i = 0; i < img.getHeight( ); i++ ) {
                for ( int j = 0; j < img.getWidth( ); j++ ) {
                    Color pixel = new Color( img.getRGB( j, i ) );
                    double pixelValue = ( ( ( pixel.getRed( ) * 0.30 ) + ( pixel.getBlue( ) * 0.59 ) + ( pixel
                            .getGreen( ) * 0.11 ) ) );
                    builder.append( strChar( pixelValue ) );
                }
            }
            image.close( );
        } catch ( Throwable ex ) {
            ex.printStackTrace( );
        }

        return builder.toString( );
    }

    /**
     * Read the image from the specified resource
     *
     * @param image the image resource path
     * @return the image as string
     */
    public static String readImage( File image ) {
        StringBuilder builder = new StringBuilder( );
        try {
            BufferedImage img = ImageIO.read( image );
            for ( int i = 0; i < img.getHeight( ); i++ ) {
                for ( int j = 0; j < img.getWidth( ); j++ ) {
                    Color pixel = new Color( img.getRGB( j, i ) );
                    double pixelValue = ( ( ( pixel.getRed( ) * 0.30 ) + ( pixel.getBlue( ) * 0.59 ) + ( pixel
                            .getGreen( ) * 0.11 ) ) );
                    builder.append( strChar( pixelValue ) );
                }
            }
        } catch ( Throwable ex ) {
            ex.printStackTrace( );
        }

        return builder.toString( );
    }

    /**
     * Read the image from the specified resource
     *
     * @param image the image resource path
     * @return the image as string
     */
    public static String readImage( Path image ) {
        StringBuilder builder = new StringBuilder( );
        try {
            BufferedImage img = ImageIO.read( image.toFile( ) );
            for ( int i = 0; i < img.getHeight( ); i++ ) {
                for ( int j = 0; j < img.getWidth( ); j++ ) {
                    Color pixel = new Color( img.getRGB( j, i ) );
                    double pixelValue = ( ( ( pixel.getRed( ) * 0.30 ) + ( pixel.getBlue( ) * 0.59 ) + ( pixel
                            .getGreen( ) * 0.11 ) ) );
                    builder.append( strChar( pixelValue ) );
                }
            }
        } catch ( Throwable ex ) {
            ex.printStackTrace( );
        }

        return builder.toString( );
    }

    /**
     * Read the image from the specified resource
     *
     * @param imagePath the image resource path
     * @return the image as string
     */
    public static String readImage( String imagePath ) {
        StringBuilder builder = new StringBuilder( );
        try {
            BufferedImage img = ImageIO.read( new File( new File( imagePath ).getAbsolutePath( ).replace( "%20", " " ) ) );
            for ( int i = 0; i < img.getHeight( ); i++ ) {
                for ( int j = 0; j < img.getWidth( ); j++ ) {
                    Color pixel = new Color( img.getRGB( j, i ) );
                    double pixelValue = ( ( ( pixel.getRed( ) * 0.30 ) + ( pixel.getBlue( ) * 0.59 ) + ( pixel
                            .getGreen( ) * 0.11 ) ) );
                    builder.append( strChar( pixelValue ) );
                }
            }
        } catch ( Throwable ex ) {
            ex.printStackTrace( );
        }

        return builder.toString( );
    }

    /**
     * Parse a char value to string
     *
     * @param g the char value
     * @return the char string value
     */
    private static String strChar( double g ) {
        String str;
        if ( g >= 240 ) {
            str = " ";
        } else if ( g >= 210 ) {
            str = ".";
        } else if ( g >= 190 ) {
            str = "*";
        } else if ( g >= 170 ) {
            str = "+";
        } else if ( g >= 120 ) {
            str = "^";
        } else if ( g >= 110 ) {
            str = "&";
        } else if ( g >= 80 ) {
            str = "8";
        } else if ( g >= 60 ) {
            str = "#";
        } else {
            str = "@";
        }

        return str;
    }

}
