/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger;

import static CurrencyRecognition.DollarColorValidation.ImageInput;
import java.util.logging.Level;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 *
 * @author CouriousSoul
 */
public class NewMain1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String N = "C:\\Users\\CouriousSoul\\Desktop\\AutomatedCurrencyExchanger\\AutomatedCurrencyExchanger\\captured\\SubImages\\Color.jpg";
 
                ImageInput(N);
    }
    
    
      
}
