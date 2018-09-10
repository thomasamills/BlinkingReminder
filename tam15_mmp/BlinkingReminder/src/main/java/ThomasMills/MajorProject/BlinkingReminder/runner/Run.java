package ThomasMills.MajorProject.BlinkingReminder.runner;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.lang.reflect.Field;



import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import ThomasMills.MajorProject.BlinkingReminder.blinkchecker.BlinkChecker;
import ThomasMills.MajorProject.BlinkingReminder.data.UIData;
import ThomasMills.MajorProject.BlinkingReminder.eyedetector.EyeDetector;
import ThomasMills.MajorProject.BlinkingReminder.problemfinder.ProblemFinder;

public class Run {
	
	//eye detector object
	private EyeDetector eyeDetector;
	//blink calculator object
	private BlinkCalculator blinkCalculator;
	//blink checker object
	private BlinkChecker blinkChecker;
	//the webcam input image
	private Mat inputImage;
	//ui output image
	private BufferedImage outputImage;
	//no eye present
	private boolean noEye;
	//no face present
	private boolean noFace;
	//matrix to store cropped eye
	private Mat croppedEyeMat;
	//iteration count (1 = 100ms)
	private int iteration;
	//duration spent looking at screen (1 = 100ms)
	private int gazeDuration;
	//duration spent not looking at the screen (1 = 100ms)
	private int gazeBreakDuration;
	//blink rate	
	private int blinksPerMinute;
	//problemFinder object. 
	private ProblemFinder problemFinder;
	//is the face too small
	private boolean faceTooSmall = false;
	//if the blink rate is too low.
	private boolean blinkRateTooLow;
	

	
	public Run(){
		this.loadOpenCV();
		//initialises componenets
		eyeDetector = new EyeDetector();
		blinkCalculator = new BlinkCalculator();
		blinkChecker = new BlinkChecker();
		problemFinder = new ProblemFinder();
		//sets the iteration as 0 to begin with.
		iteration = 0;	
	}
	
//shift to left
	/**
	 * This is the code where I used a solution from stack overflow: 
	 * Reference: Stack Overflow "How to prepare runnable jar for Open cv java project?” 2017[online] Available:  http://stackoverflow.com/questions/43155384/how-to-prepare-runnable-jar-for-open-cv-java-project date accessed 02/05/2017 
“                        This was a stack overflow forum that discussed a problem I was having with accessing OpenCV’s native library”

	 */
	   public void loadOpenCV(){  
		   try {
			   //native library .dll filepath
			   String openCVNativeLibrary = "nativeOpenCV";
			   //sets path
		       System.setProperty("java.library.path", openCVNativeLibrary);        
			   try {
				   Field sysPaths = ClassLoader.class.getDeclaredField("sys_paths");
				   //makes it accessible
				   sysPaths.setAccessible(true); 
			       try {
			    	   //sets it to new value 
					   sysPaths.set(null, null);
					   sysPaths.setAccessible(false);
				   } catch (IllegalArgumentException | IllegalAccessException e) {
					   e.printStackTrace();
				   }
			   } catch (NoSuchFieldException e) {
					e.printStackTrace();
			   } catch (SecurityException e) {
					e.printStackTrace();
			   }    	
			   //Loads up core
		   }finally{
			   System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // core openCV library 
		   }
	   }
	   
	/**
	 * stopProgram is a method that first checks to see if the connection j
	 * to the webcam is opened. If it is it will close the connection. 
	 */
	public void stopProgram(){
		if(this.eyeDetector!=null){
			if(eyeDetector.webcamOpen()){
				//closes the webcam
				eyeDetector.closeWebcam();
			}
		}
	}
	/**
	 * run() contains the method that integrates all methods in this class and 
	 * implements one iteration of the main loop. 
	 * @return
	 */
	public UIData run(){
		//creates a new ui data object
		UIData data = new UIData();	
		// if 60 seconds have passed
		if(iteration == 600){
			//reset the iteration count to 0
			iteration = 0;
			//calculate blink rate
			blinksPerMinute = blinkCalculator.getBlinkCount();	
			// if the blinkrate is too low
			if(blinksPerMinute < 15){
				blinkRateTooLow=true;
			}
			else{
				blinkRateTooLow = false;
			}
			// resets the blinks for to process the next minute. 
		    blinkCalculator.reset();
		}		
		//gets the eye. 
		getEye();
		//as long as there was an eye detected
		if(croppedEyeMat != null){
			// detect the blink. 
			detectBlink();
		}
		// calls check gaze duration
		checkGazeDuration();
		//If the user has been looking at the screen for more than 20 minutes
		if(gazeDuration > 12000){
			data.setLongStare(true);
		}
		else{
			data.setLongStare(false);
		}
		// sets UI data values. 
		data.setDisplayImage(outputImage);
		data.setTotalBlinks(blinkCalculator.getTotalBlinks());
		data.setGazeDuration(gazeDuration);
		data.setLowBlink(blinkRateTooLow);
		if(faceTooSmall == true){
			data.setFaceTooSmall(true);
		}
		else{
			data.setFaceTooSmall(false);
		}
		data.setBlinksPerMinute(blinksPerMinute);
		//increments the iteration count. 
		iteration++;
		return data;
	}
	/**
	 * checkGazeDuration
	 * this is a method to detect how long the user has been looking 
	 * at the screen for. If no face is detected. The amount of time 
	 * not looking at the screen (gazeBreakDuration) increments. when 
	 * the face is detected again and this method is ran. It will check 
	 * too see if the gazeBreakDuration was bigger than 5 seconds, if it 
	 * was it breaks the gaze, if not it will assume that it was for a 
	 * very short time and unintentional. If there is a face, the gaze
	 * break duration will end and the gaze duration will be incremented. 
	 */
	public void checkGazeDuration(){
		//if no face detected
		if(noFace){
			//increment gazeBreakDuration
			gazeBreakDuration +=1;
			//if gaze broken for more than 20 seconds
			if(gazeBreakDuration >=200){
				//reset everything. 
				gazeDuration = 0;	
				gazeBreakDuration = 0;
				return;
			}			
			//increment the gaze duration (just incase it was less than 5 seconds)
			gazeDuration++; 
		}
		//if face detected
		else{
			//if gaze was previously being broken
			if(gazeBreakDuration >= 1){
				//reset gazeBreakDuration
				gazeBreakDuration = 0;
			}
			//increment the gazeDuration.
			gazeDuration +=1;
		}
	}
	
	public void getEye(){
		//resets noEye
		noEye = false;
		//resets noFace
		noFace = false;
		//croppedEye picture is null (incase already filled)
		croppedEyeMat = null;
		//initialises the inputImage
		inputImage = new Mat();
		//grabs the input frame as a opencv mat
		inputImage = eyeDetector.grabFrameAsMat();
		// if the input image isnt null
		if(inputImage != null){
			Mat grayScaleFrame = new Mat();
			//converts the mat to grayscale
		    grayScaleFrame = eyeDetector.convertBGR2Gray(inputImage);
		    //finds width and height of input image
		    int imageWidth = grayScaleFrame.width();
		    int imageHeight = grayScaleFrame.height();
		    //converts it to buffered image (So that it can be displayed in the ui)
		    outputImage = eyeDetector.convertMatToImage(grayScaleFrame,imageWidth,imageHeight);
		    //rect to store the position of the face
			Rect faceBounds = new Rect();	
			//attempts to detect a face
			faceBounds = eyeDetector.detectFace(grayScaleFrame);
			//as long as there was a face detected
			if(faceBounds != null){
				//initialises a new mat to store the cropped face picture
				Mat croppedFace = new Mat();
				//crops it by the faceBounds rect object
				croppedFace = grayScaleFrame.submat(faceBounds);
				//checks for problems in facesize
				if(problemFinder.checkSize(croppedFace, inputImage.height(),inputImage.width())){
					faceTooSmall = false;;
				}
				else{
					faceTooSmall = true;
				}
				//gets eyes from face
				Rect eyeBounds = eyeDetector.detectEyesFromFace(croppedFace);
				//as long as there was an eye detected
				if(eyeBounds != null){
					// initialises a new mat to store the cropped eye picture
					croppedEyeMat = new Mat();
					//crops it by the eye bounds rect
					croppedEyeMat = croppedFace.submat(eyeBounds);
					Size s = new Size(45,45);
					Mat resized = new Mat();
					Imgproc.resize(croppedEyeMat, resized, s);
					//draws the reigion of interests to the output image
					drawROI(faceBounds, eyeBounds,outputImage);					
				}
				// else no eye detected
				else{
					noEye = true;
				}
			}
			//else no face detected
			else{
				noFace = true;
			}			
		}
		
	}
	
	/**
	 * detectBlink calls the blinkCheckers checkForBlink 
	 * method. if it returns true (meaning blink) it will 
	 * tell the blinkCalculator to increment +1 blinks. 
	 */
	public void detectBlink(){
		//calls blinkCheckers checkForBlink method. 
		if(blinkChecker.checkForBlink(croppedEyeMat)){
			//tells calculator to incremtn blinks
			blinkCalculator.addBlink();
		}
	}

	/**
	 * gets NoEye
	 * @return
	 */
	public boolean hasNoEye() {
		return noEye;
	}

	/**
	 * setsNoEye
	 * @param noEye
	 */
	public void setNoEye(boolean noEye) {
		this.noEye = noEye;
	}

	/**
	 * gets noFace
	 * @return
	 */
	public boolean hasNoFace() {
		return noFace;
	}

	/**
	 * setsNoFace
	 * @param noFace
	 */
	public void setNoFace(boolean noFace) {
		this.noFace = noFace;
	}
	
	/**
	 * sets Gaze Duration
	 * @param duration
	 */
	public void setGazeDuration(int duration){
		this.gazeDuration = duration;
	}
	
	/**
	 * gets gaze duration		
	 */
	
	public int getGazeDuration(){
		return gazeDuration;
	}
	
	/**
	 * sets gaze break duration
	 * @param duration
	 */
	public void setGazeBreakDuration(int duration){
		this.gazeBreakDuration = duration;
	}
	
	/**
	 * gets gaze break duration
	 */
	
	public int getGazeBreakDuration(){	
		return gazeBreakDuration;
	}
	
	
	/**
	 * drawROI(Rect faceBounds, Rect eyeBounds)
	 * this takes the rect positions (x, y, width and height)
	 * and draws a rectangle on the output image. where the
	 * eyes and face are. 
	 * @param faceBounds
	 * @param eyeBounds
	 */
	public void drawROI(Rect faceBounds, Rect eyeBounds, BufferedImage outputImage){
		// gets buffered images 2d graphics
		Graphics2D graphics = outputImage.createGraphics();
		// sets draw color to white
        graphics.setColor(Color.WHITE);
        // draws rectangle where face is 
        graphics.draw(new Rectangle(faceBounds.x,faceBounds.y,faceBounds.width,faceBounds.height));
        //draws a rectangle where eye is (by adding the x and y co-ordinates of the face and eye)
        graphics.draw(new Rectangle((faceBounds.x + eyeBounds.x),(faceBounds.y + eyeBounds.y),eyeBounds.width,eyeBounds.height));
        //stops use of graph 
        graphics.dispose();
	}

}
