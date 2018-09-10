package ThomasMills.MajorProject.BlinkingReminder.gui;

import ThomasMills.MajorProject.BlinkingReminder.runner.Run;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;

/**
 * MainStage 
 * The main stage is a class to represent the small toggleable eye
 * icon. Its job is to launch the configuration page. The stage is 
 * undecorated to make it as small as possible so I cannot move it 
 * by the window frame. This class also implements the logic for 
 * moving the stage. 
 * @author Tom Mills
 */

public class MainStage extends Application {
	
	
	public static final int STAGE_HEIGHT = 25;
	public static final int STAGE_WIDTH = 40;	
	private ConfigStage configStage;
	private BorderPane borderPane;
	private MainStageController mainStageController;
	private boolean hasLaunched = false;
	
	
	/**
	 * main(String[]args) loads the core OpenCV library and 
	 * then launches the fxml start(final Stage eyeStage) method.
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
    	Run runner = new Run();
    	runner.loadOpenCV();
        launch(args); // launch start()        
        }
    

   
    /**
     * start(final Stage eyeStage) creates a new javafx stage and sets
     * it to undecorated. It also instantiates a configuration stage (The 
     * visualisation window). It loads the backgrounds (green and red eye
     * pictures) ready to change (green when everythings ok, red when theres
     * a problem). It then creates a borderpane container and adds the green 
     * eye background as default. 
     * The method also initializes a new controller for the page
     * and calls the loadBacgrounds and initializes the mouse events (to move the window)
     * It then creates the scene passing in the border pane and a fixed position of 
     * 25x40 (the size of the eye picture). 
     */
    
    @Override
    public void start(final Stage eyeStage) {	
    	if(hasLaunched!=true){
    	mainStageController = new MainStageController(); //initialises controller
        eyeStage.initStyle(StageStyle.UNDECORATED); //sets stage to blank
        configStage = new ConfigStage(); //initialises config page
        configStage.start(eyeStage); //calls the config pages launch (this does not show he stage)
        borderPane= new BorderPane();   // initialises the border pane
        mainStageController.loadBackgrounds(borderPane);  //calls the controllers loadBackgrounds() method
        mainStageController.initMouseEvents(borderPane, eyeStage,configStage); /* calls the controllers 
                                                                              initMouseEvens(borderPane)
                                                                                  method */      
  
        eyeStage.setScene(new Scene(borderPane,STAGE_WIDTH,STAGE_HEIGHT)); //creates the scene 
        eyeStage.show(); //shows the stage
    	}
    }
    
    /**
     * getBorderPane()
     * @return the Eye Stages borderPane
     */
    public BorderPane getBorderPane(){
    	return borderPane;
    }
    
    /**
     * returns its its controller. 
     * @return
     */
    public MainStageController getController(){
    	return mainStageController;
    }
    


  
}