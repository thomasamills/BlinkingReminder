package ThomasMills.MajorProject.BlinkingReminder.blinkchecker;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * EyelidPixelCounter is a class to take in a mat (the cropped
 * eye that the HaarOpenEyeFinder did not manage to find the 
 * open eye in). It sizes the eye to 45*45, this is so the location
 * of the eyelid can be found easily. It then performs a median blur
 * to reduce noise, then an adaptive thresholding using adptive theshold
 * gaussian c to get  a binary threshold image of the eye. It then, crops 
 * the picture where the eye lid will be and counts the pixels. If under
 * a certain amount, it is classified as a blink. 
 * 
 * @author Thomas Mills
 *
 */


public class EyelidPixelCounter {	 

	/**
	 * resizeEyeMat(Mat croppedEye)
	 * as long as the cropped eye picture isn't null, 
	 * a new mat will be initialised. Then the opencv's
	 * resize method will be called using croppedEye as
	 * the src and resized as the destination. setting the 
	 * size to 45*45
	 * @param croppedEye
	 * @return resized mat
	 */
	public Mat resizeEyeMat(Mat croppedEye){
		//as long as the croppedEye mat is not null
		if(croppedEye!=null){
			//initialises a new mat
		    Mat resized = new Mat();
		    //initialises a new size of 45*45
		    Size s = new Size(45,45);
		    //resizes croppedEye into resized
		    Imgproc.resize(croppedEye, resized, s);
		return resized;
		}
		return null;
	}
	
	/**
	 * blurEye uses opencvs median blur to reduce 
	 * noise before it is thresholded. as long as 
	 * the input mat isnt null, a new one will be 
	 * initialised and medianBlur will be called 
	 * with a kernel size of 5. 
	 * @param m
	 * @return
	 */
	public Mat blurEye(Mat m){
		//as long as mat isnt null
		if(m !=null){
			//initialises new mat
		    Mat blurred = new Mat();
		    //calls opencv median blur, kernelSize 5
		    Imgproc.medianBlur(m, blurred, 5);
		    return blurred;
		}
		return null;
	}
	
	/**
	 * adaptiveThreshold is a method that calls the use 
	 * of opencvs ADAPTIVE_THESH_GAUSSIAN_C. Which is 
	 * a thresholding way that is adaptive to different 
	 * histograms. As long as the input mat isnt null. 
	 * a new mat will be iniitalised to store the result. 
	 * The method uses a blocksize of 29 and c of 2. 
	 * @param m (input eye mat)
	 * @return (output thesholded image). 
	 */
	public Mat adaptiveThreshold(Mat m){
		if(m!=null){
			//initialises a new threshold
			Mat threshold = new Mat();
			//calls the adaptive threshold method. blocksize: 29 c:2
			Imgproc.adaptiveThreshold(m, threshold, 255,
			         Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY,29,2);
			return threshold;
		}


		return null;
	}
	
	/**
	 * cropAtEyelid(Mat m)
	 * this method crops the thresholded picture of the eye 
	 * where the eye lid area is, 
	 * @param m
	 * @return
	 */
	public Mat cropAtEyelid(Mat m){
	
		//as long as the mat isnt null
		if(m!=null){
			//initialises a new mat to store the croppedThreshold
			Mat croppedThreshold = new Mat();
			// new rect with the position of the eyelid hard coded
			Rect r = new Rect(11,25,20,5);
			//crops the mat by the rect. 
			croppedThreshold = m.submat(r);
			//returns the cropped threshold. 
			return croppedThreshold;
		}
		return null;
	}
	
	/**
	 * countPixels(byte[] threshByteData){
	 * counts the amount of black pixels in the threshByteData array
	 * if over 40, it is defined as an open eye. If not it will be 
	 * defined as closed. 
	 * @param threshByteData
	 * @return
	 */
	public boolean countPixels(byte[] threshByteData){
		//counter to count the black pixels
		int counter = 0;
		//as long as the array isnt empty
		if(threshByteData.length > 0){
	    //loops through the array 
		for(int i = 0; i < threshByteData.length; i++){
			//if 0 (black)
			if((int) threshByteData[i] == 0){
			//increment counter
			counter++;
			}
		}
		//if it has over 40 black pixels, the eye is open
		if(counter <40){
			return true;
		}
		else{
			return false;
		}
		}
		return false;
	}
	/**
	 * classify is a method used to run all the other methods in the class
	 * in sequence. it resizes the eye, blurrs it to reduce noise, crops
	 * it at the eyelid, thresholds the cropped picture and counts the 
	 * amount of pixels to check for a blink.
	 * @param croppedEye
	 * @return
	 */
	public boolean classify(Mat croppedEye){
		//as long as cropped eye isnt empty
		if(!croppedEye.empty()){	
			//resize mat
			Mat resized = resizeEyeMat(croppedEye);	
			//median blur to reduce the noise
		    Mat blurredEye = blurEye(resized);
		    //crop eye at eyelid
			Mat cropped = cropAtEyelid(blurredEye);	
			//perform adaptive threshold on cropped eyelid
			Mat croppedThreshold = adaptiveThreshold(cropped);
			//finds out how long the byte data array should be
			int dataLength = croppedThreshold.height() * croppedThreshold.width() * croppedThreshold.channels();
			//initialises new byte array
			byte[] threshByteData = new byte[dataLength];
			//copies matrix byte data into the byte[]
			croppedThreshold.get(0,0,threshByteData);
            //if there is under 40 pixels
			if(countPixels(threshByteData)){
				//blink
				return true;
			}
		}
		//eye open
		return false;
	}

}
