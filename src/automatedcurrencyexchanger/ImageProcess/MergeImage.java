/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger.ImageProcess;

import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author CouriousSoul
 */
public class MergeImage {
    private static final Logger appLog = automatedcurrencyexchanger.ErrorLogs.ApplicationLogger.getNewLogger(SegmentImage.class.getName());
    private static final PathConfiguration ImPath = new PathConfiguration();
    private static final String masterPath = (ImPath.GetImagePaths("original", null));
    private static final String Subpath = (ImPath.GetImagePaths("croped", null));

    public static void Mergeimage() {

        int rows = 2;   //we assume the no. of rows and cols are known and each SubPiece has equal width and height
        int cols = 2;
        int SubPieces = rows * cols;

        int SubPieceWidth, SubPieceHeight;
        int type;
        //fetching image files
        File[] imgFiles = new File[SubPieces];
        for (int i = 0; i < SubPieces; i++) {

            imgFiles[i] = new File(Subpath + "image" + i + ".jpg");
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
            ImageIO.write(finalImg, "jpg", new File(Subpath + "MergedImg.jpg"));

        } catch (IOException e) {
            appLog.log(Level.SEVERE, "Merging failed", e);
        }
    }

}
