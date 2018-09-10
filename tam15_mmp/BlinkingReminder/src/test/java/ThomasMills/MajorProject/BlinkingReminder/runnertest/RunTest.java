package ThomasMills.MajorProject.BlinkingReminder.runnertest;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import ThomasMills.MajorProject.BlinkingReminder.runner.Run;

public class RunTest {
	
	private Run runner;
	
	@Before
	public void setUp(){
		runner = new Run();
	}

	
	/**
	 * tests to see if opencv has loaded by checking to see if it can create a new OpenCV
	 * Mat object
	 */
	@Test
	public void testHasLoadedOpenCV() {
		Mat mat = new Mat(5,5,1);
		assertNotNull(mat);
	}
	/**
	 * checks to see if checkGaze increments the gaze duration
	 */
	@Test
	public void testCheckGazeDurationIncrement(){
        runner.setGazeDuration(0); 
        //noFace is default false
        runner.checkGazeDuration();
        assertEquals(1, runner.getGazeDuration());
	}
	
	/**
	 * tests to see if the gaze duration is broken after
	 * not looking at the screen for more than 20 seconds
	 */
    @Test
    public void TestResetAfterGazeBroken20Seconds(){
    	runner.setGazeBreakDuration(200);
    	//sets there to be no face
    	runner.setNoFace(true);
    	//checks the gaze duration
    	runner.checkGazeDuration();
    	//checks to see if values have reset 
    	assertEquals(0,runner.getGazeBreakDuration());
    	assertEquals(0,runner.getGazeDuration());
    	
    }
	
    /**
     * tests to see if gaze is broken. 	
     */
	@Test 
	public void testGazeBroken(){
		//previously a face detected
		runner.setNoFace(false);
		//breaks gaze
		runner.setGazeBreakDuration(1);
		//calls checkGazeDuration
		runner.checkGazeDuration();
		//checks to see if the gaze break duration is 0
		assertEquals(0, runner.getGazeBreakDuration());
	}
	
	/**
	 * testDrawROI takes a picture and draws two white 
	 * rectangles. It then checks to see if where the rectangles
	 * were drawn contained white pixels on the top line. 
	 */
	@Test 
    public void testDrawROI(){
		BufferedImage img = new BufferedImage(100,100,BufferedImage.TYPE_BYTE_GRAY);
		byte[] imageByteData;
		//Buffer for the byte data from the BufferedImages raster
		DataBufferByte dataBuffer = ((DataBufferByte) img.getRaster().getDataBuffer());
		//gets the byte data array from the data buffer. 
		imageByteData = dataBuffer.getData();
		//set all pixels as black
		//declare size and position of face and eyes
		Rect faceBounds = new Rect(0,0,80,80);
		Rect eyeBounds = new Rect(30,30,40,40);
		//draws reigion of interest
		runner.drawROI(faceBounds,eyeBounds,img);
		//checks top line of facebounds is white
		for(int i = 0; i < 1;i++){
			//check if every pixel from this point 80 pixels wide is white
			assertEquals(-1,imageByteData[i]);
		}
	}

}
