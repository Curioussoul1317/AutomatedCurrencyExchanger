/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger;
    import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
/**
 *
 * @author CouriousSoul
 */


public class NewMain2 {

    /**
     * @param args the command line arguments
     */
        /**
 * Writes audio data in player audio buffer.
 * @param timestamp Timestamp of the received audio package.
 * @param data Audio data.
 * @param absolute if the timestamp absolute or not
 */
   
    public static void main(String[] args) {
 
    
    }
    

public void write(long timestamp, byte[] audioData, boolean absolute) {
    try {

        InputStream byteArrayInputStream = new ByteArrayInputStream(
                audioData);
        AudioFormat ulawFormat = null;
        AudioInputStream realNetStream = new AudioInputStream(
                byteArrayInputStream, ulawFormat, AudioSystem.NOT_SPECIFIED);

        synchronized (realNetStream) {
            //int len = 128000;
            int len = 160;
            int cnt;
            byte tempBuffer[] = new byte[len];

            while ((cnt = realNetStream.read(tempBuffer,
                                             0, tempBuffer.length)) != -1) {
                if (cnt > 0) {
                    /* Write data to the internal buffer of the data line where
                       it will be delivered
                       to the speaker. */
                 //   outStream.write(tempBuffer, 0, cnt);
                    System.out.println("wrote to phone " + cnt + " bytes");
                    //System.out.print(". ");

                    //Testing Purposes
                    //fos.write(tempBuffer, 0, cnt);
                } else if (cnt == 0) {
                   System.out.println("nothing to write");
                    Thread.sleep(15);
                } else
                    break;
            }
        }

        /* Create a thread to play back the data and start it running.
           It will run until all the data has been played back.*/

        //playThread = new Thread(new PlayThread());
        //playThread.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
