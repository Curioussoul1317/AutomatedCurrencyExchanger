/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger.ImageProcess;

import static automatedcurrencyexchanger.CurrencyExchanger.getImages;
import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
//import static automatedcurrencyexchanger.ImageProcess.DirectionValidation.ImageInput;
import static automatedcurrencyexchanger.ImageProcess.ImageBinarization.LoadImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import weka.gui.ComponentHelper;

/**
 *
 * @author CouriousSoul
 */
public class ImageProcessing {

    private static final Logger APP_LOG = automatedcurrencyexchanger.ErrorLogs.ApplicationLogger.getNewLogger(ImageProcessing.class.getName());
    private static final PathConfiguration IMAGE_PATH = new PathConfiguration();
    private static final String MASTER_PATH = (IMAGE_PATH.GetImagePaths("original", null));
    private static final String SUB_PATH = (IMAGE_PATH.GetImagePaths("croped", null));

   

    public static void SegmentSize(int segmentType)  {

        switch (segmentType) {

            case 0:
               
                // L,     -T,     R,  B
               SegmentImage(MASTER_PATH, MASTER_PATH, 355, 250, 1640, 690);
               ImageConfrimation(MASTER_PATH);
              
               break;
            case 1:
                  SegmentImage(MASTER_PATH, SUB_PATH + "GreenSeal.jpg", 1085, 240, 250, 210);
                  GetPixelColor(SUB_PATH + "GreenSeal.jpg");
               
                break;
            case 2:
             
                   SegmentImage(MASTER_PATH, SUB_PATH + "image0.jpg", 100, 200, 340, 220);
                   SegmentImage(MASTER_PATH, SUB_PATH + "image1.jpg", 1300, 40, 340, 120);
                   Mergeimage();
                break;
            case 3:
                   SegmentImage(SUB_PATH + "MergedImg.jpg",SUB_PATH + "MergedImg.jpg", 1,1 , 339, 339);
                   Grayscale(SUB_PATH + "MergedImg.jpg");
                    getImages();
                break;
         
            default:
                  // Confirm the dollar direction
//                SegmentImage(MASTER_PATH, SUB_PATH + "GreenSeal.jpg", 5050, 1100, 770, 720);
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
    
    
    
    public static int ImageConfrimation(String MasterImage) {
        int pixel =0;
        try {
            
            BufferedImage image = ImageIO.read(new File(MasterImage));
            Set<Integer> colors = new HashSet<Integer>();
            int w = image.getWidth();
            int h = image.getHeight();
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    pixel = image.getRGB(x, y);
                    colors.add(pixel);
                }
            }
            System.out.println("There are full image " + colors.size() + " colors");

            if (colors.size() <  30000) {
                System.out.println("Please input dolaa");
            } else {
             
                SegmentSize(1);
               
            }
        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "ColorSize ", e);
        }
    return pixel;
    }
    
 
     public static void GetPixelColor(String Logo) {

        try {
            Set<Integer> colors = new HashSet<Integer>();
            BufferedImage image = ImageIO.read(new File(Logo));
            int w = image.getWidth();
            int h = image.getHeight();
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int pixel = image.getRGB(x, y);
                    colors.add(pixel);
                }
            }
            System.out.println("There are " + colors.size() + " colors");

            if (colors.size() < 11111) {
                RotateImage(MASTER_PATH);
               
                SegmentSize(1);
            } else {
              
                SegmentSize(2);

            }
        } catch (IOException e) {
            APP_LOG.log(Level.SEVERE, "ColorSize ", e);
        }

    }
 

    public static void RotateImage(String Image) {

        
        try {
            BufferedImage ImageIn = ImageIO.read(new File(Image));
            BufferedImage ImageOut = Rotate(ImageIn, 180);
            ImageIO.write(ImageOut, "jpg", new File(Image));
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

    public static void Mergeimage()  {

        int rows = 2;
        int cols = 1;
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
         SegmentSize(3);
       
    }

    public static void Grayscale(String ToGray)  {

       try  {
       
         BufferedImage original = ImageIO.read(new File(SUB_PATH + "MergedImg.jpg"));
         BufferedImage binarized = new BufferedImage(original.getWidth(), original.getHeight(),BufferedImage.TYPE_BYTE_BINARY);

         int red;
         int newPixel;
         int threshold =128;

            for(int i=0; i<original.getWidth(); i++) 
            {
                for(int j=0; j<original.getHeight(); j++)
                {

                    // Get pixels
                  red = new Color(original.getRGB(i, j)).getRed();

                  int alpha = new Color(original.getRGB(i, j)).getAlpha();

                  if(red > threshold)
                    {
                        newPixel = 0;
                    }
                    else
                    {
                        newPixel = 255;
                    }
                    newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                    binarized.setRGB(i, j, newPixel);

                }
            } 
            ImageIO.write(binarized, "jpg",new File(SUB_PATH + "GrayScal.jpg") );
             LoadImage(SUB_PATH + "GrayScal.jpg");
         }
        catch (Exception e) 
        {
                APP_LOG.log(Level.SEVERE, "GrayScale failed", e);
                
        }

    }
    
     private static int colorToRGB(int alpha, int red, int green, int blue) {
            int newPixel = 0;
            newPixel += alpha;
            newPixel = newPixel << 8;
            newPixel += red; newPixel = newPixel << 8;
            newPixel += green; newPixel = newPixel << 8;
            newPixel += blue;

            return newPixel;
        }
}
