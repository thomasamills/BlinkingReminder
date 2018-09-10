package ThomasMills.MajorProject.BlinkingReminder.blinkchecker;


import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;

import org.opencv.objdetect.CascadeClassifier;


public class HaarOpenEyeFinder {
	// OpenCV haarcascade xml tree
	private CascadeClassifier rightEyeDetect = new CascadeClassifier("OpenCVXMLClassifiers/haarcascade_eye.xml");
	//Matrix of rectangle bounds for detected eyes
	private MatOfRect detectedEyes;
	
	public HaarOpenEyeFinder(){
		//initialises the MatOfRect
		detectedEyes = new MatOfRect();
	}

	/**
	 * findOpenEye(Mat cropped)
	 * this method is used to find an open eye within the detected eye
	 * reigion. It uses an xml tree based viola jones haarcascade classifier 
	 * to find an open eye. It stores the result as a matrix of rectangle bounds.
	 * @param cropped (the cropped mat of the eye reigion)
	 * @return
	 */
	public MatOfRect findOpenEye(Mat cropped) {	
		rightEyeDetect.detectMultiScale(cropped,detectedEyes);	
		if(detectedEyes!=null){
			return detectedEyes;
		}
		return null;
	}
	
	/**
	 * getLargestEye returns the largest eye found int the eye reigion
	 * of intrest
	 * @param rects (rect array). 
	 * @return largest (the largest eye)
	 */
	
	public Rect getLargestEye(Rect[] rects){
		Rect largest = null;
		// loop through the mat of rect 
		for(Rect r: rects){
			// if largest is not set
			if(largest == null){
				//set largest as current rect
				largest = r;
			}
			//if largest is already set
			else{
				/*if the current rects area is larger than 
				 * the largest
				 */
				if(r.area() > largest.area()){
					largest = r;
				}
			}
		}
		// returns the largest once all eyes are processed
		return largest;
	}

	 /**
	  * checkForBlink(Mat cropped)
	  * this method is used to call the findOpenEye method
	  * then, if an open eye was found and it was of reasonable
	  * size, it will not be detected as a blink. 
	  * @param cropped
	  * @return boolean ( true if needed to be classified as closed by 
	  * the EyelidPixelCounter class)
	  */
	public boolean checkForBlink(Mat cropped){
		//MatOfRect to store the detected features
		MatOfRect rects;
		//calls the findOpenEye Method
		rects = findOpenEye(cropped);
		if(cropped == null){
			System.err.println("cropped is null");
		}
		// if no open eye is found
		if(rects.empty()){ 
			return true;
		}		
		else{
			//get the largest eye detected
			Rect largestEye = getLargestEye(rects.toArray());
			//check if largest eye is of reasonable size
			if(checkEyeReasonableSize(cropped.height() * cropped.width(), largestEye.height * largestEye.width)){
				//if detected open eye is reasonable size, no blink detected
				return false;
			}
			else{
				//if detected open eye is isnt a reasonable size, the image will be further classified 
				//using the EyelidPixelCounter class. d
				return true;
			}
		}
	}
	
	/**
	 * checkEyeReasonableSize(int maxArea, int eyeArea)
	 * @param maxArea area of eye reigion of interest
	 * @param eyeArea area of largest detected eye
	 * @return true if the eyeArea isnt smaller than 80%
	 * of the maxArea
	 */
	public boolean checkEyeReasonableSize(int maxArea, int eyeArea){
		if((maxArea*0.8) >= eyeArea){
			return true;
		}
		else{
			return false;
		}
	}

}
