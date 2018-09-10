package ThomasMills.MajorProject.BlinkingReminder.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfigStage extends Application{
	
	Rectangle2D screenDimensions; // dimensions of the current screen size
	double screenX,screenY;//used to store the maxX and maxY of the screen 
	double height,width; //used to store the height of the config stage
	private Stage configStage; //the configStage
	private BorderPane borderPane; //the borderPane
	private Stage eyeStage; // the eyeStage
	
	
	
	public void start(Stage eyeStage){
			screenDimensions = Screen.getPrimary().getVisualBounds();
	    	screenX = screenDimensions.getMaxX();
	    	screenY = screenDimensions.getMaxY();
	        height = screenY * 0.50;
	    	width = screenX * 0.30;	    	
			try{
				configStage = new Stage(); // initialises the configStage
				configStage.initStyle(StageStyle.UNDECORATED);  //sets it as undecorated
				this.eyeStage = eyeStage; //initialises the eyeStage
				FXMLLoader fxmlLoader; //loader to load the configStage.fxml
				fxmlLoader = new FXMLLoader(getClass().getResource("ConfigStage.fxml")); 
				borderPane = (BorderPane) fxmlLoader.load(); //loads the borderPane from the fxml document. 
				Scene scene = new Scene(borderPane, width, height); /* sets scene to be size of calculated 
				                                                       width and height*/
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				                                                    /*loads the stylesheets (unimplemented 
				                                                     * in this current version. 
				                                                     */
				configStage.setScene(scene); // sets the scene
			
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
    }
	
	/**
	 * calculateShow Position (calculates the position of the configStage based on where the 
	 * eyeStage is. It takes in two parameters x and y (x and y co-ordinates of the eyeStage)
	 * it checks to see which corner of the screen it is and then sets the x and y co-ords of
	 * the configStage to be displayed in the correct manner so it is allways on the screen. 
	 * @param x
	 * @param y
	 */
	
	public void calculateShowPosition(double x, double y){
		if((x >= screenX/2) && (y<screenY/2)){ // if the eyeStage is in the top right section of the screen
    		configStage.setY(eyeStage.getY()); // y is already correct. 
    		configStage.setX(eyeStage.getX() - width); /* x needs to be adjusted minusing the length of the 
    		                                              width of the configStage */
    	}
    	if((x < screenX/2)&&(y<screenY/2)){ // if the eyeStage is in the top left section of the screen
    		configStage.setY(eyeStage.getY()); // y is already correct
    		configStage.setX(eyeStage.getX() + 40); // x needs to be adjusted adding 40px (width of the eyeStage)
    	}
    	if((x > screenX/2) && (y>=screenY/2)){ //if the eyeStage is in the bottom right section of the screen
   		    configStage.setY(eyeStage.getY() - height + 25); /* adjust the y co-ordinate to minus the height 
   		                                                        of the configStage but + the height of the eyeStage
   		                                                        (25px) */
   		    configStage.setX(eyeStage.getX() - width); //x needs adjusting minusing 40px (with of the eyeStage)
   	    }
    	if((x < screenX/2)&&(y>=screenY/2)){ // if the eyeStage is in the bottom left section of the screen
    		 configStage.setY(eyeStage.getY() - height +25); /* adjust Y minus the height of the configStage but 
    		                                                    + the height of the eyeStage (25px) */
    		 configStage.setX(eyeStage.getX() + 40);   //adjust X plus the width of the eyeStage
    	}
		if(configStage.isShowing()){ //makes sure that it continues to show. 
			configStage.show();
		}
	}
	
	/**
	 * show() shows the configStage
	 */
	
	public void show(){
		configStage.show();
	}
	
	/**
	 * hide() hides the configStage
	 */
	
	public void hide(){
		configStage.hide();
	}
	
	/**
	 * isVisible() checks to see if the configStage is showing
	 * @return
	 */
	public boolean isVisible(){
		return configStage.isShowing();
	}
	
    

}
