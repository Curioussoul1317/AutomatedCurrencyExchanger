/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedcurrencyexchanger.ErrorLogs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author CouriousSoul
 */
public class PathConfiguration {

    public static Properties ImagePaths = new Properties();

    public String GetImagePaths(String original, String croped) {
        String value = "";
        try {
            ImagePaths.load(new FileInputStream("ImagePaths.tut"));
            value = ImagePaths.getProperty(original, croped);
        } catch (IOException e) {

        }
        return value;
    }
}
