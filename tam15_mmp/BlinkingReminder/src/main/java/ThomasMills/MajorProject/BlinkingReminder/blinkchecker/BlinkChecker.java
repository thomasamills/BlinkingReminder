package ThomasMills.MajorProject.BlinkingReminder.blinkchecker;

import org.opencv.core.Mat;


public class BlinkChecker {
	
	//HaarOpenEyeFinder initialisation
	private HaarOpenEyeFinder haarFinder;
	//class to get image threshold and count black pixels around eye lid
	private EyelidPixelCounter eyeClassifier; 
	
	/*blink duration is used to determine the length of a blink (this is 
	 to tackle the problem of catching the same blink twice and looking
     away from the screen */
    
	private int blinkDuration = 0;
	
	/*
	 * constructor to initialise the two blink blink detection classes
	 */
	public BlinkChecker(){
		haarFinder = new HaarOpenEyeFinder();
		eyeClassifier = new EyelidPixelCounter();	
	}
	
	
	/**
	 * checkForBlink(Mat croppedEye)
	 * This method is used to define whether the picture of the cropped eye
	 * gathered from the eyeDetector class is open or closed. It uses two 
	 * methodologies, the first being a haarcascade eye detector that has been 
	 * trained to only detect open eyes. If there is no eye detected, the image
	 * will be further classified by using an "aggressive threshold" to find the 
	 * darkest areas. it then cropps the picture where the eyelid would be and 
	 * does a pixel count. If it is lower than a certain amount it will return 
	 * true (blink)
	 * @param croppedEye
	 * @return true if a blink, false if no blink
	 */
	public boolean checkForBlink(Mat croppedEye){
		//as long as the croppedEye picture isn't null
		if(croppedEye != null){ 
			//if the haarFinder cant detect an open eye of reasonable size
		    if(haarFinder.checkForBlink(croppedEye) == true){
		    	//eye classified by threshold and pixel count. 
		    	boolean b = eyeClassifier.classify(croppedEye);
		    	//if there is a blink
			    if(b == true){	
			    	    //increment the blinkDuration counter
				    	blinkDuration += 1;
				    	return false;
			    }
			    else{ // if there was no blink/blink cancelled		    	
			    	if(blinkDuration >= 1){
			    		//if blink was longer than 0.5 seconds
			    		if(blinkDuration >= 5){
			    			//set the blink duration to 0
			    			blinkDuration = 0;
			    			//will return false (not a blink, just looking down)
			    			return false;
			    		}		    		
			    	}	
			    	//eyes open and also previously open			 
			    }
		    }
		    //if there was an open blink but no open eye detected by haarcascade
		    if(blinkDuration >= 1){ //as long as blink duration is bigger than 0 seconds
	    		if(blinkDuration >= 5){ // if the blink lasted for longer than 0.5 seconds
	    			blinkDuration = 0; // blink duration reset
	    			return false; // no blink
	    		}else{
	    			//blink duration reset
	    			blinkDuration = 0;
	    			//blink
		    		return true;
	    		}    		
	    	}		    
		    //blink duration reset
		    blinkDuration = 0;
		    return false;
	    }
		    return false;
	    }

    }
