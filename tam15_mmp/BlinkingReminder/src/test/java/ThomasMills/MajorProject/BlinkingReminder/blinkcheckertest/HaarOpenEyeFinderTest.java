package ThomasMills.MajorProject.BlinkingReminder.blinkcheckertest;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import ThomasMills.MajorProject.BlinkingReminder.blinkchecker.HaarOpenEyeFinder;
import ThomasMills.MajorProject.BlinkingReminder.runner.Run;

public class HaarOpenEyeFinderTest {
	
	@SuppressWarnings("unused")
	private Run runner;
	private HaarOpenEyeFinder openEyeFinder;
	
	
	@Before
	public void setUp(){
		runner = new Run();
		openEyeFinder = new HaarOpenEyeFinder();
	}
	
	
	/**
	 * This is a method to loop through evey image in the test pictures "open eyes"
	 * folder. it gets the byte data of the image, creates an OpenCV Mat
	 * and attempts to classify the image,.
	 */
	
	//should fail as there is a bug but passes for some reason
	@Test
	public void testCheckForClosedEyes() {
		//closed directory
			  File directory= new File("TestPictures/closed");
			  File[] directoryListing= directory.listFiles();		
			  //if directory not emtpy
			  if (directoryListing != null) {
				  // loop through every file in the directory
				    for (File f : directoryListing) {	
				    	// new buffered image the size of the test images
				    	BufferedImage img = new BufferedImage(45,45,BufferedImage.TYPE_BYTE_GRAY);
				    	try {	    			
				    		// attempts to read the image
				    	    img = ImageIO.read(f);
				    	    // gets the images data buffere
				    	    DataBufferByte dataBuffer = (DataBufferByte)img.getRaster().getDataBuffer();
				    	    // gets the data images byte data 
				    	    byte[] imageByteData =  dataBuffer.getData();
				    	    // mat to store the eye image
				    	    Mat croppedEye = new Mat(45,45,0);
				    	    //copies images byte data direcly into the OpenCV matrix
				    	    croppedEye.put(0, 0, imageByteData);
				    
				    	    if(croppedEye!=null){
				    	    	//attempts to check for an open eye
				    	    	assertTrue(openEyeFinder.checkForBlink(croppedEye));
				    	    }
				    	} catch (IOException e) {
				    		e.printStackTrace();
				    	}		
			  }
			  }
			  
		}

	@Test
	public void testCheckForOpenEyes() {
		//open directory
		  File directory= new File("TestPictures/open");
		  File[] directoryListing= directory.listFiles();
		  // if directory isnt empty
		  if (directoryListing != null) {
			  // loop through every file in the directory 
			    for (File f : directoryListing) {	 
			    	// creates new buffered image the size of the test images 
			    	BufferedImage img = new BufferedImage(45,45,BufferedImage.TYPE_BYTE_GRAY);
			    	try {	    			
			    		// attempts to read an image from the file 
			    	    img = ImageIO.read(f);
			    	    //gets the images data buffer
			    	    DataBufferByte dataBuffer = (DataBufferByte)img.getRaster().getDataBuffer();
			    	    //gets the images byte data
			    	    byte[] imageByteData =  dataBuffer.getData();
			    	    // new mat to store the image of the eye
			    	    Mat croppedEye = new Mat(45,45,0);
			    	 // puts the imageByteData directly into the Mat
			    	    croppedEye.put(0, 0, imageByteData);
			    	    if(croppedEye!=null){
			    	    	//attempts to check for an open eye
			    	    	assertFalse(openEyeFinder.checkForBlink(croppedEye));
			    	    }
			    	} catch (IOException e) {
			    		e.printStackTrace();
			    	}		
		  }
		  }
		  
	}
	
	/**
	 * testCheckEyeIsReasonableSize
	 * this method tests to see if the checkEyeIsReasonableSize 
	 * method returns true when the eye is of reasonable size
	 */
	
	@Test
	public void testCheckEyeIsReasonableSize(){
		// sets eye to be 90% of the maximum area
		int maxArea = 100;
		int eyeArea = 90;	
		//checks if eye is reasonable size
		assertFalse(openEyeFinder.checkEyeReasonableSize(maxArea, eyeArea));
	}
	/**
	 * testCheckEyeIsntReasonableSize
	 * this method tests to see if the checkEyeIsReasonableSize 
	 * method returns true when the eye is not reasonable size
	 */
	@Test 
	public void testEyeIsntReasonableSize(){
		//sets eye to be 30% of the maximum area 
		int maxArea = 100;
		int eyeArea = 30;
		//checks if eye is reasonable size. 
		assertTrue(openEyeFinder.checkEyeReasonableSize(maxArea, eyeArea));
	}
	
	/**
	 * tets getlargest eye tries to find the largest eye out of three sizes. 
	 * 
	 */
	@Test
	public void testGetLargestEye(){
		//new Rect[3] to store multiple eye positions and sizes
		Rect[] multipleEyes = new Rect[3];
		//Largest eye. 
		Rect eye = new Rect(10,10,150,150);
		//smaller eye
		Rect eye1 = new Rect(10,10,130,130);
		//even smaller eye
		Rect eye2 = new Rect(10,10,100,100);
		//adds eyes to the array
		multipleEyes[0] = eye;
		multipleEyes[1] = eye1;
		multipleEyes[2] = eye2;
		//attempts to find correct face (null if non found)
		assertEquals(eye,openEyeFinder.getLargestEye(multipleEyes));
	}
}
