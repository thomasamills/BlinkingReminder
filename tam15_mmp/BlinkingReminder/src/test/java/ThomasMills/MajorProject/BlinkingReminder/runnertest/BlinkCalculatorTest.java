package ThomasMills.MajorProject.BlinkingReminder.runnertest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ThomasMills.MajorProject.BlinkingReminder.runner.BlinkCalculator;

/**
 * BlinkCalculatorTest is used in order to test the BlinkCalcuator class
 * @author Thomas Mills
 *
 */
public class BlinkCalculatorTest {
	
	private BlinkCalculator blinkCalculator;
	
	/**
	 * initialises the BlinkCalculator
	 */
	@Before
	public void setUp(){
	    blinkCalculator = new BlinkCalculator();
	    // sets blink count to 10
	    blinkCalculator.setBlinkCount(10);
	    // sets blink count to 10
	    blinkCalculator.setTotalBlinks(10);
	}

	/**
	 * tests to see if the blink count is reset
	 */
	@Test
	public void testResetBlinkCount() {
		// resets blink count
		blinkCalculator.reset();
		// checks to see if the blink count is 0
		assertEquals(blinkCalculator.getBlinkCount(), 0);
	}
	
	/**
	 * tess to see if the total blinks are reset after calling 
	 * the resetTotal method. 
	 */
	@Test
	public void testResetTotalBlinks(){
		blinkCalculator.resetTotal();
		assertEquals(blinkCalculator.getTotalBlinks(),0);
	}
	

	/**
	 * tests to see if the total number of blinks has been incremented with 
	 * the blink caluclator. 
	 */
	@Test
	public void testAddBlink(){
		blinkCalculator.addBlink();
		assertEquals(blinkCalculator.getTotalBlinks(),11);
		assertEquals(blinkCalculator.getBlinkCount(),11);
	}
	

}
