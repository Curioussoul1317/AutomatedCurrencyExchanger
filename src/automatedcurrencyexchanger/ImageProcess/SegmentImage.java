/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger.ImageProcess;

import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
import static automatedcurrencyexchanger.ImageProcess.MergeImage.Mergeimage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;

/**
 *
 * @author CouriousSoul
 */
public class SegmentImage {

    private static final Logger appLog = automatedcurrencyexchanger.ErrorLogs.ApplicationLogger.getNewLogger(SegmentImage.class.getName());
    private static final PathConfiguration ImPath = new PathConfiguration();
    private static final String masterPath = (ImPath.GetImagePaths("original", null));
    private static final String Subpath = (ImPath.GetImagePaths("croped", null));

    public static void SegmentSize(int SegmentType) {

        switch (SegmentType) {

            case 0:
                // Confirm the dollar direction
                SegmentImage(masterPath, Subpath + "GreanLogo.jpg", 5050, 1100, 770, 720);
                GetPixelColor();
                break;
            case 1:
                // parameters to check dollar
                SegmentImage(masterPath, Subpath + "image0.jpg", 100, 100, 100, 100);
                SegmentImage(masterPath, Subpath + "image2.jpg", 200, 200, 100, 100);
                SegmentImage(masterPath, Subpath + "image1.jpg", 150, 150, 100, 100);
                SegmentImage(masterPath, Subpath + "image3.jpg", 250, 250, 100, 100);
                Mergeimage();
                break;
            case 2:
                //Dollar one security ribbon
                SegmentImage(masterPath, Subpath + "image0.jpg", 100, 100, 100, 100);
                break;
            case 3:
                //Dollar two security ribbon
                SegmentImage(masterPath, Subpath + "image0.jpg", 100, 100, 100, 100);
                break;
            case 4:
                //Dollar five security ribbon
                SegmentImage(masterPath, Subpath + "image0.jpg", 100, 100, 100, 100);
                break;
            case 5:
                //Dollar Ten security ribbon
                SegmentImage(masterPath, Subpath + "image0.jpg", 100, 100, 100, 100);
                break;
            case 6:
                //Dollar Twentty security ribbon
                SegmentImage(masterPath, Subpath + "image0.jpg", 100, 100, 100, 100);
                break;
            case 7:
                //Dollar Fifitty security ribbon
                SegmentImage(masterPath, Subpath + "image0.jpg", 100, 100, 100, 100);
                break;
            case 8:
                //Dollar Fifitty security ribbon
                SegmentImage(masterPath, Subpath + "image0.jpg", 100, 100, 100, 100);
                break;
            default:
                // Confirm the dollar direction
                SegmentImage(masterPath, Subpath + "GreanLogo.jpg", 5050, 1100, 770, 720);
                GetPixelColor();
                break;

        }

    }

    public static void SegmentImage(String fileIn, String fileOut, int x, int y, int x1, int y1) {
        BufferedImage originalImgage;
        try {
            originalImgage = ImageIO.read(new File(fileIn));
            BufferedImage SubImgage = originalImgage.getSubimage(x, y, x1, y1);
            File out = new File(fileOut);
            ImageIO.write(SubImgage, "jpg", out);
        } catch (IOException e) {
            appLog.log(Level.SEVERE, "Croping failed", e);
        }
    }

    public static void GetPixelColor() {

        try {
            Set<Integer> colors = new HashSet<Integer>();
            BufferedImage image = ImageIO.read(new File(Subpath + "GreanLogo.jpg"));
            int w = image.getWidth();
            int h = image.getHeight();
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int pixel = image.getRGB(x, y);
                    colors.add(pixel);
                }
            }
            System.out.println("There are " + colors.size() + " colors");

            if (colors.size() < 60000) {
                for (int a = 0; a < 2; a++) {
                    RotateImage();
                }
                int SegmentType = 0;
                SegmentSize(SegmentType);
            } else {
                int SegmentType = 1;
                SegmentSize(SegmentType);

            }
        } catch (IOException e) {
            appLog.log(Level.SEVERE, "ColorSize ", e);
        }

    }

    public static BufferedImage RotateImage() {

        try {
            BufferedImage img = ImageIO.read(new File(masterPath));
            int width = img.getWidth();
            int height = img.getHeight();
            BufferedImage newImage = new BufferedImage(height, width, img.getType());

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    newImage.setRGB(height - 1 - j, i, img.getRGB(i, j));
                }
            }
            ImageIO.write(newImage, "jpg", new File(masterPath));

            return newImage;
        } catch (IOException e) {
            appLog.log(Level.SEVERE, "Image Unable rotate", e);

        }
        return null;

    }

}
