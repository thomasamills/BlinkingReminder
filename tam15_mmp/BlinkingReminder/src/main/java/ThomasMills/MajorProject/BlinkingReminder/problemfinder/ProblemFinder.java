package ThomasMills.MajorProject.BlinkingReminder.problemfinder;

import org.opencv.core.Mat;
/**
 * ProblemFinderClass used in order to control classes that try to detect 
 * problems in the pictures to help the user. 
 * 
 * @author Thomas Mills
 */
public class ProblemFinder {
	//size checker object
	private SizeChecker sizeChecker;
	
	public ProblemFinder(){
		//instantiates size checker
		sizeChecker = new SizeChecker();
	}
	
	/**
	 * checkSize is a method used to check if the size of the face is too small 
	 * (this is so that the program will alert the user if they are sitting too 
	 * far away from the camera). 
	 * @param croppedFace // the croppedFace Mat. 
	 * @param totalWidth // the total size of the webcam input
	 * @param totalHeight
	 * @return true if face is reasonable size. 
	 */
	public boolean checkSize(Mat croppedFace, int totalWidth, int totalHeight){
		/*calls the size checkers checkSize method (with the cropped face and the 
		sizes of the input picture*/
		return sizeChecker.checkSize(croppedFace, totalWidth * totalHeight);
	}
	


}
