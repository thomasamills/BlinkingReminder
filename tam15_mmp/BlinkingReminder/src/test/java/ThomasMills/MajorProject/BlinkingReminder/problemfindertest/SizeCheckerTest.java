package ThomasMills.MajorProject.BlinkingReminder.problemfindertest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;

import ThomasMills.MajorProject.BlinkingReminder.problemfinder.SizeChecker;
import ThomasMills.MajorProject.BlinkingReminder.runner.Run;

/**
 * Class to test the methods in the SizeChecker class
 * @author Thomas Mills
 *
 */
public class SizeCheckerTest {
	
	private SizeChecker sizeChecker;
	
	@Before
	public void setUp(){
		/*
		 * Loads OpenCV libraries 
		 */
		@SuppressWarnings("unused")
		Run run = new Run();
		sizeChecker = new SizeChecker();
	}

	
	/**
	 * tests to see if checksize returns true when a 
	 * reasonabe sized face is passed in.
	 */
	@Test
	public void testCheckSizeFaceOk() {
		Mat croppedFace = new Mat(80,80,1);
		int totalArea = 1000;
		assertTrue(sizeChecker.checkSize(croppedFace, totalArea));
	}
	
	/**
	 * tests to see if checksize returns true when an 
	 * un-reasonabe sized face is passed in.
	 */
	@Test
	public void testCheckSizeFaceSmall(){
		Mat croppedFace = new Mat(5,5,1);
		int totalArea = 1000;
		assertFalse(sizeChecker.checkSize(croppedFace, totalArea));
	}

}
