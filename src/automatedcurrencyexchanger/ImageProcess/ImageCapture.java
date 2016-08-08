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
public class ImageCapture {

    private static LightControlls lcObject = LightControlls.lightControll();
    private static final Logger appLog
            = automatedcurrencyexchanger.ErrorLogs.ApplicationLogger.getNewLogger(ImageCapture.class.getName());
    
   public static void captureImage(String path) {
       int lightValue = 0;

       if (lightValue == 0) {
        // lcObject.onCapKeys();
        AccessCam(path);
        // lcObject.OffCapKeys();
       } else {
            //lcObject.OnscrollLock();
           AccessCam(path);
            //lcObject.OffscrollLock();
       }
       
 
   } 

    public static void AccessCam(String path) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture camera = new VideoCapture(0);

        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
        Mat mr1 = m.row(1);
        mr1.setTo(new Scalar(1));
        Mat mc5 = m.col(5);
        mc5.setTo(new Scalar(5));
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

    private LightControlls() {
    }

    public static LightControlls lightControll() {
        if (lc == null) {
            lc = new LightControlls();
        }
        return lc;
    }

    public boolean capsLock(boolean cp) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, cp);
        return true;
    }

    public boolean scrollLock(boolean sp) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.setLockingKeyState(KeyEvent.VK_SCROLL_LOCK, sp);
        return true;
    }

    public boolean onCapKeys() {
        return capsLock(true);
    }

    public boolean OffCapKeys() {
        return capsLock(false);
    }

    public boolean OnscrollLock() {
        return scrollLock(true);
    }

    public boolean OffscrollLock() {
        return scrollLock(false);
    }
}
