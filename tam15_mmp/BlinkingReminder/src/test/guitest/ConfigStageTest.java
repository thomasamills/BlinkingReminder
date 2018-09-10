package ThomasMills.MajorProject.BlinkingReminder.guitest;

import static org.junit.Assert.*;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import ThomasMills.MajorProject.BlinkingReminder.gui.ConfigStage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * configstage test uses TestFX4 to test the GUI 
 * @author Thomas Andrew Mills
 *
 */
public class ConfigStageTest extends ApplicationTest {
	
	private Stage eyeStage;
	private boolean isLaunched = false;

	/**
	 * Note: Catches ConcurrentModificationException cant find bug. 
	 *
	 */
	@Before
	public void setUp() {
		try {
			//as long as the application isnt already launched
			if(isLaunched!=true){
				//launch a new config stage. 
			    ApplicationTest.launch(ConfigStage.class, new String[0]);
			    isLaunched = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * hides the stage after testing. 
	 */
	@After
	public void shutDown(){
	   try {
		   // hides stage 
		FxToolkit.hideStage();
	} catch (TimeoutException e) {
		e.printStackTrace();
	}
	}

	/**
	 * starts the stage if it hasnt already been started. 
	 */
	@Override
	public void start(Stage eyeStage){
		if(isLaunched!=true){
		    this.eyeStage = eyeStage;
		}
	}
	
	/**
	 * testIsCorrectHeight()
	 */
	@Test
	public void testIsCorrectHeight(){
		//gets the bounds of the screen
		Rectangle2D screenDimensions = Screen.getPrimary().getVisualBounds();
		// gets half of the screen size
    	int screenHeight = (int) ((int) screenDimensions.getMaxY() * 0.5);
    	//the height of the config stage
    	int height2 = (int) eyeStage.getHeight();
    	//checks if they are the same height 
		assertEquals(screenHeight,height2);
	}
	/**
	 * testIsCorrectWidth()
	 */
	@Test
	public void testIsCorrectWidth(){
		//gets the bounds of the screen
        Rectangle2D screenDimensions = Screen.getPrimary().getVisualBounds();	
      //the witdh of the config stage
    	int screenWidth = (int) ((int) screenDimensions.getMaxX() * 0.3);
    	System.out.println(screenWidth);
    	int width = (int) eyeStage.getWidth();
    	//checks if they are the same width
		assertEquals(screenWidth,width);
	}

}