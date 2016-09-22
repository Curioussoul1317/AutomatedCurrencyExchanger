/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import automatedcurrencyexchanger.ImageProcess.ImageProcessing;
import automatedcurrencyexchanger.ImageProcess.ImageBinarization;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CouriousSoul
 */
public class JUnitTest {

    public JUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
    }

    @Test
    public void ImageConfrimation() {
        String testImagePath = "C:\\Users\\CouriousSoul\\Desktop\\blank.jpg";
        int originalColorValue = 0;
        int methodColorValue = ImageProcessing.ImageConfrimation(testImagePath);
        assertEquals(methodColorValue, originalColorValue);

    }

    @Test
    public void ImageBinaraizationBlack() throws Exception {
        String imagePath = "C:\\Users\\CouriousSoul\\Desktop\\sanpleImagesForTest\\blackImage.jpg";
        BufferedImage image = ImageIO.read(new File(imagePath));
        String[] binaryArray = ImageBinarization.toArray(image);

        int totalSum = 0;
        int expectedValue = 339*339;
        for (String s : binaryArray) {
            totalSum += Integer.parseInt(s);
        }
        assertEquals(expectedValue, totalSum);
    }

    @Test
    public void ImageBinaraizationWhite() throws Exception {
        String imagePath = "C:\\Users\\CouriousSoul\\Desktop\\sanpleImagesForTest\\whiteImage.jpg";
        BufferedImage image = ImageIO.read(new File(imagePath));
        String[] binaryArray = ImageBinarization.toArray(image);

        int totalSum = 0;
        int expectedValue = 0;
        for (String s : binaryArray) {
            totalSum += Integer.parseInt(s);
        }
        assertEquals(expectedValue, totalSum);

    }
}
