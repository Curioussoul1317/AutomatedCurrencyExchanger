/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger.ImageProcess;

import static CurrencyRecognition.RecognitionProcess.ImageResult;
import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author CouriousSoul
 */

public class ImageBinarization {

    private static final Logger APP_LOG = automatedcurrencyexchanger.ErrorLogs.ApplicationLogger.getNewLogger(ImageProcessing.class.getName());
    private static final PathConfiguration IMAGE_PATH = new PathConfiguration();
    private static final String SUB_PATH = (IMAGE_PATH.GetImagePaths("croped", null));
//    private static int size = 150;
//    private static int pixSize = 1;
    
      private static int size =28;
    private static int pixSize=1;

    public static String[] LoadImage() {
        String[] numberArray = null;
        try {

            BufferedImage image = ImageIO.read(new File(SUB_PATH + "A.png"));
            numberArray = toStringArray(image);
            int count = 0;
            for (String s : numberArray) {
                System.out.print(s + ",");
                count++;

                if (count == size) {
                    System.out.println("");
                    count = 0;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ImageBinarization.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numberArray;
   
    
    }

    private static String[] toStringArray(BufferedImage numberImage) throws Exception {
        String[] arrayRep = new String[size * size];
        try {
            int arrayIndex = 0;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    BufferedImage subImage = numberImage.getSubimage(col, row, pixSize, pixSize);

                    int num = subImage.getRGB(0, 0);
                    //System.out.println(num);
                    if (num == -1) {
                        arrayRep[arrayIndex] = "0";
                    } else {
                        arrayRep[arrayIndex] = "1";
                    }
                    arrayIndex++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return arrayRep;
    }

}
