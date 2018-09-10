package ThomasMills.MajorProject.BlinkingReminder.runner;

/**
 * BlinkCalculator class 
 * This class is used in order to calculate the blink rate 
 * per minute every minute the program is running for. and 
 * is able to reset itself. 
 * It also stores the total blink count. 
 * @author Thoma
 *
 */
public class BlinkCalculator {
	//for each iteration
	private int blinkCount; 
	 //for whole time ran.
	private int totalBlinks; 
	
	//sets them both to 0 on the constructor
	public BlinkCalculator(){
		setTotalBlinks(0);
		setBlinkCount(0);
	}
	
	/**
	 * addBlink used to increment total blinks and blink count
	 */
	public void addBlink(){
		totalBlinks += 1; 
		blinkCount += 1;
	}

	/**
	 * calculate returns the total blinks (will reset every minute)
	 * @return
	 */
	public int calculate(){
		return blinkCount;
	}
	
	/**
	 * reset resets the blink count (not total blinks)
	 */
	public void reset(){
		setBlinkCount(0);
	}
	
	/**
	 * resetTotal
	 * method used when program stops in order to reset the total 
	 */
	public void resetTotal(){
		setTotalBlinks(0);
		reset();
	}

	/**
	 * getBlinkCount
	 * @return the blinkCount for the 1 minute iteration
	 */
	public int getBlinkCount() {
		return blinkCount;
	}
	
	/**
	 * sets blink count
	 * @param blinkCount
	 */

	public void setBlinkCount(int blinkCount) {
		this.blinkCount = blinkCount;
	}

	/**
	 * getTotalBlinks
	 * @return totalBlinks
	 */
	public int getTotalBlinks() {
		return totalBlinks;
	}
	
	/**setTotalBlinks
	 * @param totalBlinks
	 */

	public void setTotalBlinks(int totalBlinks) {
		this.totalBlinks = totalBlinks;
	}

}
