package ThomasMills.MajorProject.BlinkingReminder.eyedetectortest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import ThomasMills.MajorProject.BlinkingReminder.eyedetector.FalsePositiveReducer;
import ThomasMills.MajorProject.BlinkingReminder.runner.Run;


public class FalsePositiveReducerTest {
	private FalsePositiveReducer fpReducer;
	
	
	
	@Before
	public void setUp(){
		/*This will load OpenCV Library
		  called in Runs constructor*/ 
		@SuppressWarnings("unused")
		Run runner = new Run();
		fpReducer = new FalsePositiveReducer();
	}

	/**
	 * This test is used to run the fp reducer 
	 * findCorrectFace method. It constructs an 
	 * array of OpenCV Rect objects (one larger 
	 * than the other), calls the findCorrectFace
	 * method with the array and checks to see 
	 * if it returns the biggest one. 
	 */
	@Test
	public void testMultipleFaces() {
		Rect[] multipleFaces;		
	    //largest
		Rect largestFace = new Rect(10,10,150,150);
		//smaller
		Rect face1 = new Rect(10,10,100,100);
		//smaller
		Rect face2 = new Rect(10,10,75,75);
		//Initialises array to be of length 3
		multipleFaces = new Rect[3];
		/*Fills the array of Rects array with multiple 
		 * faces of different sizes
		 */	
		multipleFaces[0] = largestFace;
		multipleFaces[1] = face1;
		multipleFaces[2] = face2;
		//checks if the findCorrectFace method returns the largest face
		assertEquals(largestFace,fpReducer.findCorrectFace(multipleFaces));
	}
	
	/**
	 * This test is used to test the false 
	 * positive reducers findCorrectFace
	 * method but passing in only one face. 
	 * If the result is not null the face 
	 * has been found. 
	 */
	@Test
	public void testSingleFace(){
		//new Rect[1] to contain the single face size/position
		Rect[] singleFace = new Rect[1];
		//initialises a new face. 
		Rect face = new Rect(10,10,150,150);
		//adds face to the Rect array
		singleFace[0] = face;
		//attempts to find correct face (null if non found)
		assertNotNull(fpReducer.findCorrectFace(singleFace));
	}
	
	
	@Test
	public void testMultipleEyes(){
		//new mat representing the face (size 100*100)
		Mat croppedFace = new Mat(100,100,1);
		Rect[] multipleEyes = new Rect[3];
		//top left of photo and reasonable size
		Rect bestPositionedEye = new Rect(20,20, 45,45);
		//top left of photo but smaller
		Rect eye1 = new Rect (20,20,30,30);
		//bottom right of the photo
		Rect eye2 = new Rect (75,75,45,45);
		//adds eyes to rect array
		multipleEyes[0] = bestPositionedEye;
		multipleEyes[1] = eye1;
		multipleEyes[2] = eye2; 
		assertEquals(bestPositionedEye, fpReducer.findCorrectEye(multipleEyes, croppedFace));	
	}
	
	@Test 
	public void testSingleEye(){
		//new mat representing the face (size 100*100)
		Mat croppedFace = new Mat(100,100,1);
		//new Rect[1] to contain the single face size/position
		Rect[] singleEye = new Rect[1];
		//initialises a new face. 
		Rect eye = new Rect(10,10,10,10);
		//adds face to the Rect array
		singleEye[0] = eye;
		//attempts to find correct face (null if non found)
		assertNotNull(fpReducer.findCorrectEye(singleEye,croppedFace));
	}
	

}
