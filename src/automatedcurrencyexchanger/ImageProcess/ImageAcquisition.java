/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger.ImageProcess;

import automatedcurrencyexchanger.ErrorLogs.ApplicationLogger;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 *
 * @author CouriousSoul
 */
public class ImageAcquisition {

    private static LightControlls lcObject = LightControlls.lightControll();
    private static final Logger appLog
            = automatedcurrencyexchanger.ErrorLogs.ApplicationLogger.getNewLogger(ImageAcquisition.class.getName());
    
   public static void captureImage(String path ) {
       int lightValue=0;
       if (lightValue == 0) {
         lcObject.OnWhiteLight();
          AccessCam(path);
        lcObject.OffWhiteLight();
       
       } else {
            lcObject.OnUvLight();
           AccessCam(path);
           lcObject.OffUvLight();
       
            
       }
       
 
   } 

    public static void AccessCam(String path) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture camera = new VideoCapture(0);
 boolean wset = camera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, 7376);
    boolean hset = camera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, 3120);
        if (!camera.isOpened()) {
            String errorMessage = "Input Device not found";
            appLog.log(Level.SEVERE, errorMessage);
        } else {
            Mat frame = new Mat();
            while (true) {
                if (camera.read(frame)) {

                    Highgui.imwrite(path, frame);

                    break;
                }
            }
        }
        camera.release();
         

    }

}

class LightControlls {

    private static LightControlls lc;

//    private LightControlls() {
//    }

    public static LightControlls lightControll() {
        if (lc == null) {
            lc = new LightControlls();
        }
        return lc;
    }

    public boolean WhiteLight(boolean cp) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, cp);
        return true;
    }

    public boolean UvLight(boolean sp) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.setLockingKeyState(KeyEvent.VK_SCROLL_LOCK, sp);
        return true;
    }

    public boolean OnWhiteLight() {
        return WhiteLight(true);
    }

    public boolean OffWhiteLight() {
        return WhiteLight(false);
    }

    public boolean OnUvLight() {
        return UvLight(true);
    }

    public boolean OffUvLight() {
        return UvLight(false);
    }
}
