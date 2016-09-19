/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrencyRecognition;

import static CurrencyRecognition.CounterfeitValidation.ImageInput;
import java.io.IOException;
import java.io.File;
import javax.swing.JFileChooser;

import java.util.ArrayList;
import weka.core.Instance;
import weka.core.Instances;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import NuralNet.PatternNeuralNet;
import automatedcurrencyexchanger.ErrorLogs.PathConfiguration;
import automatedcurrencyexchanger.ImageProcess.ImageAcquisition;
import automatedcurrencyexchanger.ImageProcess.ImageBinarization;
import static automatedcurrencyexchanger.ImageProcess.ImageProcessing.SegmentSize;

/**
 *
 * @author CouriousSoul
 */
public class RecognitionProcess {
 
    
    
    public static void ImageResult (String[] numberArray) throws Exception{
        
        //  String[] numberArray = null;
        //  numberArray = ImageBinarization.LoadImage();
                 
    
              ArrayList<Double> results = predictNumber(numberArray);
                String value ="";
                for(int index=0;index<(results.size()-1);index++)
                {
                 value += (("Number "+index+" : "+results.get(index))+"\n");

                }
                // jTextPane1.setText(value);
                 System.out.println(value);
                                
                  double LastResult = (results.get(results.size()-1));
//                  String aString = Double.toString(LastResult);
//                 ///
//                  System.out.println("Number : "+aString.substring(0, 1) );
//                  String DValue=(aString.substring(0, 1));
//                   System.out.println("Number u: "+aString);
//                   /////
                  
                  Double d = LastResult ;
                  Integer i = d.intValue(); // i becomes 5  
                  PathConfiguration ImPath = new PathConfiguration();
                  String masterPath = (ImPath.GetImagePaths("original", null));
                 // ImageAcquisition.captureImage(masterPath,i);
//                  int segmentType = i;
//               //   SegmentSize();
//                  ImageInput(i);
                
    
    
    
    }
    
    public static ArrayList<Double> predictNumber(String[] instanceData) {
        
        ArrayList<Double> predictions = new ArrayList<>();
        if (!PatternNeuralNet.trained) {
            System.err.println("Neural netowrk is not trained....");
        } else {
            Instance temp = toInstance(instanceData);
            try {
                temp.setClassValue(PatternNeuralNet.nN.classifyInstance(temp));
                for(double d:PatternNeuralNet.nN.distributionForInstance(temp)){
                    // classify all the instance in array
                    predictions.add(d);
                }// giving a class value to the instance of teh image 
                // listing all the index
                predictions.add(temp.classValue());
                // adding the closes value to last with its class value
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        return predictions;
    }
    
    private static Instance toInstance(String[] instanceData){
        try{
            PatternNeuralNet.loadDataTemplate();
            Instance tempInstance = new Instance(PatternNeuralNet.template.numAttributes());
            tempInstance.setDataset(PatternNeuralNet.template);
            
            for(int index=0; index<instanceData.length;index++){
                tempInstance.setValue(index, instanceData[index]);
            }
            PatternNeuralNet.template.add(tempInstance);
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
        return PatternNeuralNet.template.lastInstance();
    }
    
    
    

    
}
