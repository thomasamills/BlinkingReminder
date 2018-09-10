package ThomasMills.MajorProject.BlinkingReminder.eyedetectortest;

import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import ThomasMills.MajorProject.BlinkingReminder.eyedetector.EyeDetector;
import ThomasMills.MajorProject.BlinkingReminder.runner.Run;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.junit.Before;


/**
 * Testing features of the EyeDetector Class
 * Must have a webcam attached for this. 
 * @author Thomas Andrew Mills
 *
 */

public class EyeDetectorTest {
	
   private EyeDetector eyeD;
   private Mat testMat, testMat2; 
   private BufferedImage testImage, testImage2;

   
  
   @Before
   public void setUp(){  
	       //loads OpenCV library
		   @SuppressWarnings("unused")
		   Run runner = new Run();
		   //Initialises a new EyeDetector class;
		   eyeD = new EyeDetector();
		   //creates a new buffered image 
		   testImage = new BufferedImage(480, 720, BufferedImage.TYPE_3BYTE_BGR);
		   //loads TestImage (Picture of a face with face clearly showing)
		   try {
			testImage = ImageIO.read(new File("TestPictures/TestImage.png"));
		   } catch (IOException e) {
			  e.printStackTrace();
		   }
		   //gets the byte data from the buffered image
		   byte[] imgData = ((DataBufferByte) testImage.getRaster().getDataBuffer()).getData();
		   //creates a new mat the size of the image
		   testMat = new Mat(testImage.getHeight(),testImage.getWidth(),16);
		   //fills the mat with the byte data from the buffered image
		   testMat.put(0, 0, imgData);    
		   testImage2 = new BufferedImage(278, 278, BufferedImage.TYPE_BYTE_GRAY);
		   //loads TestImage (Picture of a face with face clearly showing)
		   try {
			testImage2 = ImageIO.read(new File("TestPictures/TestImage2.png"));
		   } catch (IOException e) {
			  e.printStackTrace();
		   }
		   //gets the byte data from the buffered image
		   byte[] img2Data = ((DataBufferByte) testImage2.getRaster().getDataBuffer()).getData();
		   //creates a new mat the size of the image
		   testMat2 = new Mat(testImage2.getHeight(),testImage2.getWidth(),0);
		   //fills the mat with the byte data from the buffered image
		   testMat2.put(0, 0, img2Data);
	       
   }
     
   /**
    * testGrabFrameAsMat()
    * used to test to see if the frame gathered is not null. 
    */
   
   @Test
   public void testGrabFrameAsMat(){  
	   //grabs a frame
      Mat testGrab = eyeD.grabFrameAsMat();    
      //checks to see if testGrab is not null (if it isn't the method has worked).
      assertNotNull(testGrab);
   }
   
   /**
    * testConvertMatToImage(){
    * converts the testMat to an image and checks to see if the image is not null.
    */
   
   @Test
   public void testConvertMatToImage(){
      //buffered image
	  BufferedImage image = null;
	  if(testMat!=null){
		  //gets height and width of the test matrix
	      int testMatWidth = testMat.width();
	      int testMatHeight = testMat.height();
	      //attempts conversion   
	      image = eyeD.convertMatToImage(testMat, testMatWidth, testMatHeight);
	      //checks if image is not null.
	  }
	  assertNotNull(image);
   }
   
   
   /**
    * testConvertBGR2GRAY()
    * gets a mat from the webcam (possible fail if testGrabFrameAsMat also fails)
    * converts it from BGR to grey and checks to see if the number of color channels 
    * is equal to 1. 
    */
   
   @Test
   public void testConvertBGR2GRAY(){
	  //converts the mat to gray
	  Mat m = eyeD.convertBGR2Gray(testMat);
	  //checks the number of channels
	  assertEquals(1,m.channels());
   }
   
   /**
    * testDetectFace()
    * uses the Mat from setUp method, converts it to gray.
    * It then creates a new rect and sets it to null,then 
    * sets the rect equal to EyeDetector.detectFace(Mat m) 
    * method, if r is still null, a face was not detected in
    * the picture. 
    */
   
   @Test 
   public void testDetectFace(){
	   //converts the mat to grayscale
	   Mat m = eyeD.convertBGR2Gray(testMat);
	   //creates a new rect
	   Rect r = null;	   
	   //attempts to detect face
	   r = eyeD.detectFace(m);
	   //test fails if r is null
	   assertNotNull(r);
   }

   @Test
   public void testDetectEyeHasBounds(){
	   Rect r = null;
	   r = eyeD.detectEyesFromFace(testMat2);
	   
	   assertNotNull(r);
   }
   
   
}