package ThomasMills.MajorProject.BlinkingReminder.blinkcheckertest;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import ThomasMills.MajorProject.BlinkingReminder.blinkchecker.EyelidPixelCounter;
import ThomasMills.MajorProject.BlinkingReminder.runner.Run;

/**
 * EyelidPixelCounterTest is a file that tests
 * @author Thomas Andrew Mills
 *
 */
public class EyeLidPixelCounterTest {
	@SuppressWarnings("unused")
	private Run runner;
	private EyelidPixelCounter eyeLidPixelCounter;
	
	/**
	 * loads opencv 
	 */
	@Before
	public void setUp(){
		runner = new Run();
		eyeLidPixelCounter = new EyelidPixelCounter();
	}

	/**
	 * This is a method to loop through evey image in the test pictures "open eyes"
	 * folder. it gets the byte data of the image, creates an OpenCV Mat
	 * and attempts to classify the image,.
	 */
	@Test
	public void testCheckForOpenEyes() {
		//open directory
			  File directory= new File("TestPictures/open");
			  File[] directoryListing= directory.listFiles();	
			//as long as there were files in the directory
			  if (directoryListing != null) {
				    for (File f : directoryListing) {	 
				    	// for every file int he directory 
				    	BufferedImage img = new BufferedImage(45,45,BufferedImage.TYPE_BYTE_GRAY);
				    	try {	    			
				    	//attempt to read the file 
				    	    img = ImageIO.read(f);
				    	    // gets immages data buffer
				    	    DataBufferByte dataBuffer = (DataBufferByte)img.getRaster().getDataBuffer();
				    	    // gets images byte data array 
				    	    byte[] imageByteData =  dataBuffer.getData();
				    	    // initialises a new mat
				    	    Mat croppedEye = new Mat(45,45,0);
				    	    //copies images byte data direcly into the OpenCV matrix
				    	    croppedEye.put(0, 0, imageByteData);
				    	    if(croppedEye!=null){    	 		    	       
					    	   assertFalse(eyeLidPixelCounter.classify(croppedEye));
				    	    }		    	    
				    	} catch (IOException e) {
				    		e.printStackTrace();
				    	}		
			  }
			  }
			  
		}
	
	/**
	 * This is a method to loop through evey image in the test pictures "closed eyes"
	 * folder. it gets the byte data of the image, creates an OpenCV Mat
	 * and attempts to classify the image,.
	 */
	@Test
	public void testCheckForClosedEyes() {
		      //closed directory
			  File directory= new File("TestPictures/closed");
			  File[] directoryListing= directory.listFiles();	
			  //as long as there were files in the directory
			  if (directoryListing != null) {
				  // for every file int he directory 
				    for (File f : directoryListing) {	 	
				    	// new buffered image the size of the test pictures
				    	BufferedImage img = new BufferedImage(45,45,BufferedImage.TYPE_BYTE_GRAY);
				    	try {	    			
				    		//attempts to read the image from the directoryListing
				    	    img = ImageIO.read(f);
				    	    //gets the images databuffer
				    	    DataBufferByte dataBuffer = (DataBufferByte)img.getRaster().getDataBuffer();
				    	    //gets the images byte data array
				    	    byte[] imageByteData =  dataBuffer.getData();
				    	    //initialises a new eye
				    	    Mat croppedEye = new Mat(45,45,0);
				    	  //copies the byte array data direcly into the OpenCV Mat
				    	    croppedEye.put(0, 0, imageByteData);
				    	    if(croppedEye!=null){    	 		
				               //tests the classifier method with the picture from the file
					    	   assertTrue(eyeLidPixelCounter.classify(croppedEye));
				    	    }		    	    
				    	} catch (IOException e) {
				    		e.printStackTrace();
				    	}		
			  }
			  }
			  
		}
	
	/**
	 * test resize mat tests to see if the resizeMat()
	 * method converts it to the right size
	 */
	@Test 
	public void testResizeMat(){
		// new opencv matrix
		Mat mat = new Mat(1,1,CvType.CV_8UC1);
		// mat to store the resized picture
		Mat resized = new Mat();
		//calls resize
		resized = eyeLidPixelCounter.resizeEyeMat(mat);
		//checks height is correct
		assertEquals(45, resized.height());
		//checks if width is correct 
		assertEquals(45,resized.width());
	}
	
	/**
	 * testMedianBlurNotNull() makes sure that the median blur does
	 * not cause any errors. 
	 */
	@Test
	public void testMedianBlurNotNull(){
		// new mat the size of an eye
		Mat mat = new Mat(45,45, CvType.CV_8UC1);
		// mat to store the blurred image
		Mat blurred = new Mat();
		//performs a median blur
		blurred = eyeLidPixelCounter.blurEye(mat);
		//checks to see the image is not null
		assertNotNull(blurred);
	}
	
	/**
	 * this is a method to test to see if the opencv's adaptive threshold
	 * returns a binary image (a binary image either contains the value 0 or -1)
	 */
	@Test
	public void testAdaptiveThresholdReturnsBinaryImage(){
		// new mat the size of an eye
		Mat mat = new Mat(45,45,CvType.CV_8UC1);
		// matrix to store the threshold
		Mat threshold = new Mat();
		// attempts to do an adaptive threshold
		threshold = eyeLidPixelCounter.adaptiveThreshold(mat);
		// declares a new byte data array the with the amount of pixels threshold has
		byte[] imageByteData = new byte[mat.height()*mat.width()];
		// gets the byte data from threshold and puts it into imageByteData
		threshold.get(0, 0,imageByteData);
		// loops through every byte in imageByteDta
		for(int i: imageByteData){
			// checks to see if it is 0 or -1
			if(i==0 ||i==-1){	
			}
			else{
				//fails if another value is found. 
				fail();
			}
		}
	}
	
	/**
	 * this method tests to see if the cropping feature crops to the right size
	 */
	@Test
	public void testCropAtEyeLidRightSize(){
		// mat the size of an eye
		Mat uncroppedEye = new Mat(45,45,CvType.CV_16UC1);
		//cropps the mat
		Mat cropped = eyeLidPixelCounter.cropAtEyelid(uncroppedEye);
		//checks height and width 
		if(cropped.height()!=5){
			fail();
		}
		if(cropped.width()!=20){
			fail();
		}
	}
	
	/**
	 * checks to see if the pixel counter works
	 */
	@Test
	public void testPixelCounterReturnsOpen(){
		// new byte aarray
		byte[] imageByteData = new byte[100];
		//fills 70% with 0
		for(int i = 0; i < 70; i++){
			imageByteData[i] = 0;
		}
		//fills the remaining 30 with -1
		for(int i = 70; i < 100; i++){
			imageByteData[i] = -1;
		}
		//checks to see if it returns false (open eye)
		assertFalse(eyeLidPixelCounter.countPixels(imageByteData));
	}
	
	
	/**
	 * checks to see if the pixel counter works
	 */
	
	@Test
	public void testPixelCounterReturnsClosed(){
		// new byte aarray
		byte[] imageByteData = new byte[100];
		//fills 70% with -1
		for(int i = 0; i < 70; i++){
			imageByteData[i] = -1;
		}
		//fills the remaining 30 with 0
		for(int i = 70; i < 100; i++){
			imageByteData[i] = 0;
		}
		//checks to see if it returns true (closed eye)
		assertTrue(eyeLidPixelCounter.countPixels(imageByteData));
	}
	
}
