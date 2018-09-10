package ThomasMills.MajorProject.BlinkingReminder.problemfinder;

import org.opencv.core.Mat;
/**
 * Size checker is a class in order to check the size of 
 * the cropped face compared to the whole image size. 
 * @author Thomas Mills
 *
 */
public class SizeChecker {
	
	/**
	 * checkSize
	 * @param croppedFace the cropped face mat
	 * @param totalArea the area of whole picture
	 * @return true if reasonable size. 
	 */
	
	public boolean checkSize(Mat croppedFace, int totalArea){
		// gets 15% of the total area. 
		double sizeThreshold = totalArea * 0.15;
		//if the cropped face's area is less than 15% of the total area
		if(croppedFace.height() * croppedFace.width() <= sizeThreshold){
			return false;
		}
		return true;	
	}

}
