/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger.ImageProcess;

import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    private static int segmentType=0;
    
     public static void SegmentSize() {

        switch (segmentType) {

            case 0:
                // Confirm the dollar direction
                SegmentImage(MASTER_PATH, SUB_PATH + "GreanLogo.jpg", 5050, 1100, 770, 720);
                GetPixelColor();
                break;
            case 1:
                // parameters to check dollar
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                SegmentImage(MASTER_PATH, SUB_PATH + "image2.jpg", 200, 200, 100, 100);
                SegmentImage(MASTER_PATH, SUB_PATH + "image1.jpg", 150, 150, 100, 100);
                SegmentImage(MASTER_PATH, SUB_PATH + "image3.jpg", 250, 250, 100, 100);
                Mergeimage();
                break;
            case 2:
                //Dollar one security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 3:
                //Dollar two security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 4:
                //Dollar five security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 5:
                //Dollar Ten security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 6:
                //Dollar Twentty security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 7:
                //Dollar Fifitty security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            case 8:
                //Dollar Fifitty security ribbon
                SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 100, 100, 100);
                break;
            default:
                // Confirm the dollar direction
                SegmentImage(MASTER_PATH, SUB_PATH + "GreanLogo.jpg", 5050, 1100, 770, 720);
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
            APP_LOG.log(Level.SEVERE, "Croping failed", e);
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
                RotateImage( );
                segmentType = 0;
                SegmentSize();
            } else {
                segmentType = 1;
                SegmentSize();

            }
        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "ColorSize ", e);
        }

    }
    
        
      public static void RotateImage( ) {
         
        BufferedImage img;
        try {
            img = ImageIO.read(new File(MASTER_PATH));
            BufferedImage newImage = rotate(img, 180);
            ImageIO.write(newImage, "jpg", new File(MASTER_PATH));
        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "Image Unable rotate", e);
        }

    }

    public static BufferedImage rotate(BufferedImage image, int angle) throws IOException {

        angle -= 90;
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(height, width, image.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newImage.setRGB(height - 1 - j, i, image.getRGB(i, j));
            }
        }
        if (angle > 0) {
            return rotate(newImage, angle);
        }
        return newImage;

    }
    
    
    
    
    public static void Mergeimage() {

        int rows = 2;   //we assume the no. of rows and cols are known and each SubPiece has equal width and height
        int cols = 2;
        int SubPieces = rows * cols;

        int SubPieceWidth, SubPieceHeight;
        int type;
        //fetching image files
        File[] imgFiles = new File[SubPieces];
        for (int i = 0; i < SubPieces; i++) {

            imgFiles[i] = new File(SUB_PATH + "image" + i + ".jpg");
        }
        try {
            //creating a bufferd image array from image files
            BufferedImage[] buffImages = new BufferedImage[SubPieces];
            for (int i = 0; i < SubPieces; i++) {
                buffImages[i] = ImageIO.read(imgFiles[i]);
            }
            type = buffImages[0].getType();
            SubPieceWidth = buffImages[0].getWidth();
            SubPieceHeight = buffImages[0].getHeight();

            //Initializing the final image
            BufferedImage finalImg = new BufferedImage(SubPieceWidth * cols, SubPieceHeight * rows, type);

            int num = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    finalImg.createGraphics().drawImage(buffImages[num], SubPieceWidth * j, SubPieceHeight * i, null);
                    num++;
                }
            }
            System.out.println("Image concatenated.....");
            ImageIO.write(finalImg, "jpg", new File(SUB_PATH + "MergedImg.jpg"));

        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "Merging failed", e);
        }
        Grayscale();
    }
    
    
    public static void Grayscale() {

    try {
            //colored image path
            BufferedImage image = ImageIO.read(new File(SUB_PATH + "MergedImg.jpg"));
                   
            //getting width and height of image
            double image_width = image.getWidth();
            double image_height = image.getHeight();

            BufferedImage bimg = null;
            BufferedImage img = image;

            //drawing a new image      
            bimg = new BufferedImage((int) image_width, (int) image_height,
                    BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D gg = bimg.createGraphics();
            gg.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);

                             //saving black and white image onto drive
            //String temp = "blackAndwhite.jpeg";
             ImageIO.write(bimg, "jpg", new File(SUB_PATH + "MergedImg.jpg"));
            
        } catch (Exception e) {
            System.out.println(e);
        }

}
        
  
    
}
