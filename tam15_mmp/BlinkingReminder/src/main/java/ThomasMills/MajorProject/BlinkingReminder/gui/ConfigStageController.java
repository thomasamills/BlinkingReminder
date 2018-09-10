package ThomasMills.MajorProject.BlinkingReminder.gui;
//java util imports

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//javafx imports
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

//data package imports
import ThomasMills.MajorProject.BlinkingReminder.data.UIData;
import ThomasMills.MajorProject.BlinkingReminder.runner.Alerter;
//runner package imports
import ThomasMills.MajorProject.BlinkingReminder.runner.Run;


public class ConfigStageController  {
	
	
	@FXML
	private ImageView webcamFrame; //holds the whole image captured by webcam
	
	@FXML
	private Button programStart; // button to start the program
	
	//labels to show the statisics
	
	@FXML 
	private Label stareDuration; 
	
	@FXML
	private Label longStare;
	
	@FXML
	private Label lowBlinkRate;
	
	@FXML 
	private Label blinkRate;
	
	@FXML
	private Label faceTooSmall;
		
	@FXML
	private Label totalBlinks;
	
	private Run runner; // the eyeDetector interface. 
	private ScheduledExecutorService threadRunner; //timer for the thread
	private boolean isRunning;
	private Alerter alerter;
	//message dialog array
    private int iteration = -1;
	
	/**
	 * Start program is the main loop of the program. It is called when 
	 * the start program button is pressed
	 * @param event
	 */
	@FXML
	protected void startProgram(ActionEvent event){
		//if the trhread isnt already running (to avoid errors runnign it wide
		if(!isRunning){
			// sets the thread as running
		isRunning = true;
		if(runner == null){
			// creates a new runner object if one has not already been created. 
        runner = new Run();
        // sets a new alerter. s
        setAlerter(new Alerter());
		}
		
			Runnable BlinkDetectThread = new Runnable() { // Thread	
				public void run(){	
					    if(isRunning){
					    	UIData data = runner.run();
						    Platform.runLater(() -> { //tells this image to execute on the UI thread (after this has
							                    //excecuted. 		    	 
							if(data!=null){	
								// calls the ui update method. 
								updateUI(data);
							}
						});	  
					    }
					}
				};				
				// runs the thread every 100 ms (10 fps)
				threadRunner = Executors.newSingleThreadScheduledExecutor(); // instantiates a new thread executer 
				threadRunner.scheduleAtFixedRate(BlinkDetectThread, 0, 100, TimeUnit.MILLISECONDS); // executes the main thread 
			}
	}		
	
	/**
	 * Update ui is a method used in order to update the Config Stages ui components using the UIData object. 
	 */
	public void updateUI(UIData data){
		//if there is a display image
		if(data.getDisplayImage()!=null){
			// converts the image from swing to javafx. 
	        webcamFrame.imageProperty().set(SwingFXUtils.toFXImage(data.getDisplayImage(),null));
		}
		// sets the labels
		blinkRate.setText(Integer.toString(data.getBlinksPerMinute()));
		totalBlinks.setText(Integer.toString(data.getTotalBlinks()));
		stareDuration.setText(Integer.toString(data.getGazeDuration()/10));
		if(data.isLongStare()){
			longStare.setText("Yes");
		}
		else{
			longStare.setText("No");
		}
		if(data.isLowBlink()){
			lowBlinkRate.setText("Yes");
		}
		else{
			lowBlinkRate.setText("No");
		}
		if(data.isFaceTooSmall() == true){
			faceTooSmall.setText("Yes");
		}
		else{
			faceTooSmall.setText("No");
		}	
		
		iteration++;
		// every 60 seconds halfway through
		if(iteration == 300){
			// checks if the user has  prolonged gaze
			if(data.isLongStare()){
				//alerts the user of their gaze duration
				alerter.alertLongStare(data.getGazeDuration()/10);
			}
		}
		//every 60 seconds at the end of the minute
		if(iteration == 600){
			// if the user has a low blink rate
			if(data.isLowBlink()){
				// alert the user of their blink rate. 
				alerter.alertLowBlinkRate(data.getBlinksPerMinute());
			}
			iteration = 0;
		}
		
	}
	
	
	@FXML 
	protected void stopThreads(ActionEvent event){
		isRunning = false;
		if(threadRunner!=null){
			threadRunner.shutdownNow();		
		}
		if(runner!=null){
			//stops the webcam
			runner.stopProgram();
		}
		Platform.exit();
	    System.exit(0);
		//iteration = 0;
	}

	public Alerter getAlerter() {
		return alerter;
	}

	public void setAlerter(Alerter alerter) {
		this.alerter = alerter;
	}
	
    public Label getStareDuration(){
    	return stareDuration;
    }
    
    public void setStareDurationText(String txt){
    	this.stareDuration.setText(txt);
    }
	
}

