package ThomasMills.MajorProject.BlinkingReminder.guitest;

import static org.junit.Assert.*;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import ThomasMills.MajorProject.BlinkingReminder.gui.MainStage;

import javafx.stage.Stage;

/**
 * MainStageTest uses TestFX4 to test the GUI 
 * @author Thomas Andrew Mills
 */

public class MainStageTest extends ApplicationTest {
	
	private Stage eyeStage;
	private boolean isLaunched = false;

	/**
	 * Note: Catches ConcurrentModificationException cant find bug. 
	 */
	@Before
	public void setUp() {
		try {
			//as long as the application isnt already launched. 
			if(isLaunched!=true){
				//launches application
			    ApplicationTest.launch(MainStage.class, new String[0]);
			    isLaunched = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * after tests have ran any stages will be hidden. 
	 */
	@After
	public void shutDown(){
	   try {
		FxToolkit.hideStage();
	} catch (TimeoutException e) {
		e.printStackTrace();
	}
	}

	
	/**
	 * launches the stages if it is not already launched
	 */
	@Override
	public void start(Stage eyeStage){
		if(isLaunched!=true){
		    this.eyeStage = eyeStage;
		}
	}
	
	/**
	 * testIsCorrectHeight() tests to see if the eyeStage is the correct height
	 */
	@Test
	public void testIsCorrectHeight(){
		// gets height of eyestage
		int height = (int) eyeStage.getHeight();
		//checks that the height is 25
		assertEquals(height,25);
	}
	/**
	 * testIsCorrectWidth() tests to see if the eyeStage is the correct height
	 */
	@Test
	public void testIsCorrectWidth(){
		//gets the width of the eye stage
		int width = (int) eyeStage.getWidth();
		//checks that the witdh is 40. 
		assertEquals(width,40);
	}

}
