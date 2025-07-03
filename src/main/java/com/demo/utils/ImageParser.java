package com.demo.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

public class ImageParser {

    private static BufferedImage getBufferedImage(String imageSource, int index) {
        try {
            index++;
            String[] split = imageSource.split(",");
            String base64Image;
            if (split.length<3){
                base64Image = split[1];
            } else {
                base64Image = split[index];
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeBufferedImage(BufferedImage img, String imagePath) {
        try {
            ImageIO.write(img, "png", new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeBufferedImage(BufferedImage img, String formatName, String imagePath) {
        try {
            ImageIO.write(img, formatName, new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadImage(String imageSource, String filename, int index) {
        writeBufferedImage(getBufferedImage(imageSource, index), filename);
    }

    public static void loadImage(String imageSource, String formatName, String filename) {
        writeBufferedImage(getBufferedImage(imageSource, 1), formatName, filename);
    }

    private static BufferedImage getBufferedImageFromUrl(String url) {
        try {
            URL urlFromString = new URL(url);
            System.out.println(urlFromString.getPath());
            return ImageIO.read(urlFromString);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void loadImageFromUrl(String url, String filename) {
        writeBufferedImage(getBufferedImageFromUrl(url), filename);
    }
}