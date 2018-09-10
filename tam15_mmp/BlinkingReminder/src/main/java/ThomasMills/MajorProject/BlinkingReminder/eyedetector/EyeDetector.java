package ThomasMills.MajorProject.BlinkingReminder.eyedetector;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
/**
 * Class EyeDetector is a class that uses the openCV library
 * and its Mat (matrix) data structure in order to get the users 
 * eye. It Contains all the methods used in order to 
 * take in an image from a webcam, convert it from BGR to Gray-
 * scale, detect a face. Crop the detected face, then gets an 
 * eye, crops the detected eye. It also converts the input 
 * matrix 
 * @author Thomas Mills
 * @Version 1.0 Release
 *
 */

public class EyeDetector  {
	//Used to store the Viola-Jones Haar cascade classifer (right eye)
	private CascadeClassifier faceDetect;
	//Used to store the Viola Jones Haar cascade classifier (face)
	private CascadeClassifier rightEyeDetect;
	//Used to store the detected features after classifiying the input image 
	private MatOfRect detectedFeatures;
	//Used to store the detected eyes from the cropped face
	private MatOfRect detectedEyeFeatures;
	/*FalsePositive reducer (finds features with most reasonable size/position if 
	  more than one feature is found */
	private FalsePositiveReducer fpReducer;
	//The webcam video capture object. 
	private VideoCapture webcam;

	
	public EyeDetector(){
		// Default Main Camera
		webcam = new VideoCapture(0); 
		// Loads the haarcascade face classifier
		faceDetect = new CascadeClassifier("OpenCVXMLClassifiers/haarcascade_frontalface_alt.xml");
		//Loads the haarcascade right eye classifier
		rightEyeDetect = new CascadeClassifier("OpenCVXMLClassifiers/haarcascade_righteye_2splits.xml");
		//instantiates the detectedFeatures variable. 
		detectedFeatures = new MatOfRect();
		//instantiates the detectedEyeFeatures variable
		detectedEyeFeatures = new MatOfRect();
		//instantiates a new FalsePositiveReducer
		fpReducer = new FalsePositiveReducer();
	}

	/**
	 * convertMatToImage(Mat m)
	 * this method takes in the matrix gathered from the webcam input. As the 
	 * method of retrieving the data from the mat objects .get() method deals 
	 * with byte arrays. I use a buffer to to get the byte data from the buffered
	 * images raster. Then use the opencv matrix objects get method to copy the 
	 * byte data of the matrix (webcamInput) to the buffered images byte data. 
	 */
	
  
	public BufferedImage convertMatToImage(Mat webcamInput, int width, int height){
    	//The buffered image to be returned. 
		BufferedImage cameraFrameImage;
		cameraFrameImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
		//new array to store the byte data of the image
		byte[] imageByteData;
		//Buffer for the byte data from the BufferedImages raster
		DataBufferByte dataBuffer = ((DataBufferByte) cameraFrameImage.getRaster().getDataBuffer());
		//gets the byte data array from the data buffer. 
		imageByteData = dataBuffer.getData();
		//copies the byte array data from the Mat taken from the webcam to the BufferedImages raster. 
		webcamInput.get(0,0,imageByteData);
		//returns the webcam input as a BufferedImage. 
        return cameraFrameImage;
	}
    
    /**
     * convertBGR2Gray(Mat m) takes in a matrix 
     * then uses openCV's cvtColor() method to 
     * convert it to grayscale (from BGR) meaning 
     * it only has one color channel.  
     * @param m the webcam input matrix
     * @return the webcam input matrix in grayscale
     */
    public Mat convertBGR2Gray(Mat m){
    	//parameters:(src, destination, conversion method)
    	Imgproc.cvtColor(m,m,Imgproc.COLOR_BGR2GRAY);
    	return m;
    }
    
    /**
     * grabFrameAsMat() first initialises a new mat object
     * It then checks to see if the webcam VideoCapture
     * is open, if its open it will then try to read the 
     * webcams input and store it as an OpenCV matrix object. 
     */
	public Mat grabFrameAsMat(){
    	if(webcam.isOpened()){
    		
    	}
    	else{
    		webcam = new VideoCapture(0);
    	}
    	//initialises new mat object
		Mat frame = new Mat(); 
		//initialised it
		Mat resized = new Mat();
		//as long as the videocapture is open
		if (webcam.isOpened()){ 
				try{
					//attempts to read frame. 
					webcam.read(frame); 
					Size s = new Size(360,280);
					Imgproc.resize(frame, resized, s);
                    

				}catch (Exception e){
					
				}
			}		
		// returns webcam frame as mat.
			return resized;  
	}
    
    /**
     * detectFace(mat m) is a method used to detect faces using the 
     * OpenCV Haarcascade frontalface xml tree classifier. It then calls
     * the false positive reducer class in order to detect the best matching 
     * feature. 
     */

    public Rect detectFace(Mat m){
    	// as long as the input matrix isnt empty
    	if(!m.empty()){ 
        /*uses the faceDetect (haarcascade classifier) and stores the returned 
    	  features in the detectedFeatures (mat of rects) */
    	faceDetect.detectMultiScale(m, detectedFeatures);
    	//chooses the best feature by size and position. 
    	Rect face = fpReducer.findCorrectFace(detectedFeatures.toArray());	
    	return face;
    	}
    	return null;
    }
    
    /**
     * webcamOpen checks to see if the webcam is opened
     * @return true if the webcam is open false if not. 
     */
    public boolean webcamOpen(){
    	if(this.webcam.isOpened()){
    		return true;	
    	}
    	return false;
    }
    
    /**
     * closeWebcam closes the connection to the webcam
     */
    public void closeWebcam(){
    	webcam.release();
    }
    
    /**
     * detectEyesFromFace(Mat cropped) 
     * this method is used to detect eyes from the cropped face picture. 
     * As long as the input matrix isn't empty it will use the opencv 
     * haarcascade_righteye_2splits.xml classifier to detect eyes. 
     * It will then use the false positives reducer object to find the best
     * positioned feature to be an eye. (by both size and position). 
     */

	public Rect detectEyesFromFace(Mat cropped) {	
		//as long as the input matrix isnt empty
		if(!cropped.empty()){
		/*uses the rightEyeDetect (haarcascade classifier) and stores the returned 
	      eyefeatures in the detectedEyeFeatures (mat of rects) */			
		rightEyeDetect.detectMultiScale(cropped, detectedEyeFeatures);
		//finds the best positioned and sized eye from the detected features. 
    	Rect eyeBounds = fpReducer.findCorrectEye(detectedEyeFeatures.toArray(),cropped);
    	return eyeBounds;
		} 	
		return null;
	}
	
	
	



}
