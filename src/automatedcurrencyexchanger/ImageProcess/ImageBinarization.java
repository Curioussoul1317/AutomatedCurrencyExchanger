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

    private static int size = 339;
    private static int pixSize = 1;

    public static String LoadImage(String ToBinary) throws Exception {
      
        String[] BinarryArray = null;
        String output = "";
        try {

            BufferedImage image = ImageIO.read(new File(ToBinary));
            BinarryArray = toArray(image);
  
            int count = 0;
            for (String s : BinarryArray) {
                output = output + s + ",";
            }
        } catch (Exception e) {
            APP_LOG.log(Level.SEVERE, "Binarrazing", e);

        }
        output = output.substring(0, output.length() - 1);
        System.out.println(output);
        ImageResult (BinarryArray);
        return output;
    }

    private static String[] toArray(BufferedImage NumerizedImage) throws Exception {
        String[] arrayRep = new String[size * size];
        try {
            int arrayIndex = 0;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    BufferedImage subImage = NumerizedImage.getSubimage(col, row, pixSize, pixSize);

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
