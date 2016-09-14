/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger.ImageProcess;

import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
import static automatedcurrencyexchanger.ImageProcess.ImageBinarization.LoadImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author CouriousSoul
 */
public class ImageProcessing {

    private static final Logger APP_LOG = automatedcurrencyexchanger.ErrorLogs.ApplicationLogger.getNewLogger(ImageProcessing.class.getName());
    private static final PathConfiguration IMAGE_PATH = new PathConfiguration();
    private static final String MASTER_PATH = (IMAGE_PATH.GetImagePaths("original", null));
    private static final String SUB_PATH = (IMAGE_PATH.GetImagePaths("croped", null));
    private static int segmentType = 0;

    public static void SegmentSize() {

        switch (segmentType) {

            case 0:
                // Extract only the dollar ColouImage
                // L,     T,     R,  B
                // SegmentImage(MASTER_PATH, MASTER_PATH, 131, 2, 2, 2);
                 SegmentImage(MASTER_PATH, MASTER_PATH, 35, 425, 2160, 960);
               //  SegmentImage(MASTER_PATH, SUB_PATH + "Red.jpg", 50, 20, 1000, 3010);
                 Grayscale();
                //ImageConfrimation();
                
                break;
            case 1:
                // Confirm the dollar direction
               SegmentImage(MASTER_PATH, SUB_PATH + "GreanLogo.jpg", 5050, 1100, 770, 720);
                
               // GetPixelColor();

                break;
            case 2:
//           
                // parameters to check dollar
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 50, 20, 1000, 3010);
                SegmentImage(MASTER_PATH, SUB_PATH + "image1.jpg", 2000, 20, 2050, 3010);
                SegmentImage(MASTER_PATH, SUB_PATH + "image2.jpg", 2000, 20, 2050, 3010);
                SegmentImage(MASTER_PATH, SUB_PATH + "image3.jpg", 1000, 20, 50, 3010);

                Mergeimage();
                break;
//            case 3:
//                //Dollar one security ribbon
//                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
//                break;
//            case 4:
//                //Dollar two security ribbon
//                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
//                break;
            case 5:
                //Dollar five security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                
                break;
            case 10:
                //Dollar Ten security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 20:
                //Dollar Twentty security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 50:
                //Dollar Fifitty security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 100:
                //Dollar Fifitty security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            default:

                // Confirm the dollar direction
//                SegmentImage(MASTER_PATH, SUB_PATH + "GreanLogo.jpg", 5050, 1100, 770, 720);
//                GetPixelColor();
                break;

        }

    }

    public static void SegmentImage(String ImageIn, String ImageOut, int x, int y, int x1, int y1) {
        BufferedImage originalImgage;
        try {
            originalImgage = ImageIO.read(new File(ImageIn));
            BufferedImage SubImgage = originalImgage.getSubimage(x, y, x1, y1);
            File out = new File(ImageOut);
            ImageIO.write(SubImgage, "jpg", out);
        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "Croping failed", e);
        }
    }
    public static void ImageConfrimation() {

        try {
            Set<Integer> colors = new HashSet<Integer>();
            BufferedImage image = ImageIO.read(new File(MASTER_PATH));
            int w = image.getWidth();
            int h = image.getHeight();
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int pixel = image.getRGB(x, y);
                    colors.add(pixel);
                }
            }
            System.out.println("There are full image " + colors.size() + " colors");

            if (colors.size() <  150000) {
                System.out.println("Please input dolaa");
            } else {
               segmentType = 1;
                SegmentSize();

            }
        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "ColorSize ", e);
        }

    }
    public static void GetPixelColor() {

        try {
            Set<Integer> colors = new HashSet<Integer>();
            BufferedImage image = ImageIO.read(new File(SUB_PATH + "GreanLogo.jpg"));
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
                RotateImage();
                segmentType = 1;
                SegmentSize();
            } else {
                segmentType = 2;
                SegmentSize();

            }
        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "ColorSize ", e);
        }

    }

    public static void RotateImage() {

        BufferedImage ImageIn;
        try {
            ImageIn = ImageIO.read(new File(MASTER_PATH));
            BufferedImage ImageOut = Rotate(ImageIn, 180);
            ImageIO.write(ImageOut, "jpg", new File(MASTER_PATH));
        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "Image Unable rotate", e);
        }

    }

    public static BufferedImage Rotate(BufferedImage Imagetorotate, int angle) throws IOException {

        angle -= 90;
        int width = Imagetorotate.getWidth();
        int height = Imagetorotate.getHeight();
        BufferedImage Rotatedimage = new BufferedImage(height, width, Imagetorotate.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Rotatedimage.setRGB(height - 1 - j, i, Imagetorotate.getRGB(i, j));
            }
        }
        if (angle > 0) {
            return Rotate(Rotatedimage, angle);
        }
        return Rotatedimage;

    }

    public static void Mergeimage() {

        int rows = 1;
        int cols = 4;
        int SubPieces = rows * cols;

        int SubPieceWidth, SubPieceHeight;
        int type;

        File[] imgFiles = new File[SubPieces];
        for (int i = 0; i < SubPieces; i++) {

            imgFiles[i] = new File(SUB_PATH + "image" + i + ".jpg");
        }
        try {

            BufferedImage[] buffImages = new BufferedImage[SubPieces];
            for (int i = 0; i < SubPieces; i++) {
                buffImages[i] = ImageIO.read(imgFiles[i]);
            }
            type = buffImages[0].getType();
            SubPieceWidth = buffImages[0].getWidth();
            SubPieceHeight = buffImages[0].getHeight();

            BufferedImage MergedImage = new BufferedImage(SubPieceWidth * cols, SubPieceHeight * rows, type);

            int num = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    MergedImage.createGraphics().drawImage(buffImages[num], SubPieceWidth * j, SubPieceHeight * i, null);
                    num++;
                }
            }
            System.out.println("Image concatenated.....");
            ImageIO.write(MergedImage, "jpg", new File(SUB_PATH + "MergedImg.jpg"));

        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "Merging failed", e);
        }
        Grayscale();
    }

    public static void Grayscale() {

        try {

            BufferedImage ColouImage = ImageIO.read(new File(SUB_PATH + "MergedImg.jpg"));

            double image_width = ColouImage.getWidth();
            double image_height = ColouImage.getHeight();

            BufferedImage BalckandwhiteImage = null;
            BufferedImage img = ColouImage;

            BalckandwhiteImage = new BufferedImage((int) image_width, (int) image_height,
                    BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D gg = BalckandwhiteImage.createGraphics();
            gg.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);

            ImageIO.write(BalckandwhiteImage, "jpg", new File(SUB_PATH + "MergedImg.png"));

        } catch (Exception e) {
            System.out.println(e);
        }
 LoadImage();
    }

}
