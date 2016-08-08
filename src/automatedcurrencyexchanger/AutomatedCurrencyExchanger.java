/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger;

import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
import automatedcurrencyexchanger.ImageProcess.ImageCapture;
import static automatedcurrencyexchanger.ImageProcess.SegmentImage.SegmentSize;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author CouriousSoul
 */
public class AutomatedCurrencyExchanger {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        
        PathConfiguration ImPath = new PathConfiguration();
        
     
     String masterPath = (ImPath.GetImagePaths("original", null));
     ImageCapture.captureImage(masterPath);
          int SegmentType = 0;
        SegmentSize(SegmentType);
        
        

     
    }
    


    
}
