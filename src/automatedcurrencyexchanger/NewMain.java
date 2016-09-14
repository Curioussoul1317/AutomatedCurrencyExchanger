/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger;

import static CurrencyRecognition.RecognitionProcess.ImageResult;
import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
import automatedcurrencyexchanger.ImageProcess.ImageAcquisition;
import static automatedcurrencyexchanger.ImageProcess.ImageProcessing.SegmentSize;

/**
 *
 * @author CouriousSoul
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, Exception {
       //  TODO code application logic here
                     PathConfiguration ImPath = new PathConfiguration();
////        
//     
     String masterPath = (ImPath.GetImagePaths("original", null));
        ImageAcquisition.captureImage(masterPath);
////        
//      int segmentType = 1;
//                SegmentSize();
                     
       int segmentType = 1;
      SegmentSize();
       ImageResult ();
    }
    
}
