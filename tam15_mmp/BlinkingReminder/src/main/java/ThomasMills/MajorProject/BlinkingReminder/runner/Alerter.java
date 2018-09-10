package ThomasMills.MajorProject.BlinkingReminder.runner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Alerter Class
 * This class is used to send alerts to the user when they have a low blink rate, 
 * long stare duration or their face has been too small for one mintute. 
 * It uses what ever the machines default beep is using the java.awt.Toolkit
 * and also uses JavaFX 8 dialog boxes to explain the user what the alert is. 
 * @author Thomas Mills
 *
 */
public class Alerter {

	/**
	 * alert Low BlinkRate (int stareDuration)
	 * this will alert the user if they have been looking at the 
	 * screen for too long. it beep and alert the user of their 
	 * stare duration and to look away from the screen for 10 seconds. 
	 * @param blinkRate
	 */
	public void alertLowBlinkRate(int blinkRate){
		//get machines default beep
		 java.awt.Toolkit.getDefaultToolkit().beep();
		 //initialises a new alert
		 Alert alert = new Alert(AlertType.CONFIRMATION);
		 //sets title
		 alert.setTitle("Alert");
		 //sets header text
		 alert.setHeaderText("BlinkRate Reminder");
		 // sets content text describing blink rate
	     alert.setContentText("Blink Rate too low: " + blinkRate + "Blinks Per Minute");
	     //shows the alert
		 alert.showAndWait();	
	}
	    
	/**
	 * alertLongStare (int stareDuration)
	 * this will alert the user if their blink rate is too low. 
	   As long as there isnt an alert already showing, it will 
	   beep, create a new Alert object (javafx) to alert the user
	   of their stare duration and to look away for 20 seconds.
	 * @param blinkRate
	 */
	public void alertLongStare(int stareDuration){
		 Alert alert = new Alert(AlertType.CONFIRMATION);
		//sets title
	     alert.setTitle("Alert");
	     //sets header text
	     alert.setHeaderText("Stare Duration Reminder");
	     // sets content text describing blink rate
		 alert.setContentText("Stare Duration Too long: " + stareDuration + "minutes");
		 //shows the alert
	     alert.showAndWait();	
	}
	
	public void alertStareDurationBroken(){
		for(int i = 0; i < 2; i++){
			java.awt.Toolkit.getDefaultToolkit().beep();
		}
	}
}
