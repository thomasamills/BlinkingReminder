package ThomasMills.MajorProject.BlinkingReminder.gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The  MainSageController class is a class that controlls the main
 * stage (the small eye icon). It initalises the mouse events to toggle
 * and move the stages. It also loads the eyestages backgrounds and provides
 * a method to switch the colour of the eye background.
 * @author tam15
 *
 */

public class MainStageController {
	
	private BackgroundImage greenEyeBG, redEyeBG; // the two backgrounds
	
	
	/**
	 * initMouseEvents takes in three parameters, the borderPane of the eyeStage
	 * the eyeStage and the configStage. It first declares an event handler to, 
	 * when dragged, update the eyeStages position with the x and y co-ordinates 
	 * of the current screenY/X position. But minuses half of the height and width 
	 * of the stage for the x and y co-ordinates so that it is centered. 
	 * 
	 * It also initialises an event to run some code when anywhere in the 
	 * borderPane is clicked to, if the configStage is visible, toggle it 
	 * invisible and if it is invisible toggle it visible. Also it will only 
	 * run this if the stage has been still since the press (not onMouseDragged)
	 * @param borderPane
	 * @param eyeStage
	 * @param configStage
	 */
	public void initMouseEvents(BorderPane borderPane, final Stage eyeStage, ConfigStage configStage){   	       
        borderPane.setOnMouseDragged(new EventHandler<MouseEvent>(){ /* when the mouse is dragged while on the
        	                                                            border pane */
            public void handle(MouseEvent e){
            	eyeStage.setY(e.getScreenY() - (eyeStage.getHeight()/2)); /* sets eyeStages y co-ord as the 
            	                                                            screensY - half the width of the
            	                                                            eyeStages height (to center)          	                                    
            	                                                           */
                eyeStage.setX(e.getScreenX() - (eyeStage.getWidth()/2));  /* sets eyeStages y co-ord as the 
                                                                              screensY - half the width of the
                                                                              eyeStages height (to center)          	                                    
                                                                           */
                configStage.calculateShowPosition(eyeStage.getX(), eyeStage.getY()); /*runs the calculate showPosition 
                                                                                       from the config stage (updates 
                                                                                       its position to not be off the screen)
                                                                                      
                                                                                        */
            }
        });
        borderPane.setOnMouseClicked(new EventHandler<MouseEvent>(){ // when the area inside borderPane is clicked
            public void handle(MouseEvent e){
                	if(e.isStillSincePress()){ // as long as the mouse has been still since pressed
                        if(configStage.isVisible()){ // if the configStage is visible
        	                configStage.hide(); // hide the config stage
                        }                  
                        else{ // if it isn't visible
                        	configStage.calculateShowPosition(eyeStage.getX(),eyeStage.getY()); /*runs the calculate showPosition 
                                                                                                  from the config stage (updates 
                                                                                                  its position to not be off the screen)                 
                                                                                     */
                        	configStage.show(); //shows the config stage
                        }          
                	}              
            }
        });  
    }
	
	/**
	 * loadBackgrounds(Borderpane bp)loads the two images (green and red eye) into 
	 * backgrounds ready for them to be set and then calls the switch eyecolour 
	 * passing in the borderPane and 1 (green). 
	 * 
	 * @param bp the borderPane of the eyeStage
	 */
    public void loadBackgrounds(BorderPane bp){
    	Image greenEyeImg = new Image("file:greeneye.png"); //loads the greeneye.png
        greenEyeBG= new BackgroundImage(greenEyeImg,null,null, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT); //default position 0,0, default size = size of picture)
        Image redEyeImg = new Image("file:redeye.png"); // loads the redeye.png
        redEyeBG= new BackgroundImage(redEyeImg,null,null, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT); //default position 0,0, default size = size of picture)
        switchEyeColour(0,bp);
        
    }
    
    
    /**
     * switchEyeColour
     * @param i 1 for green 0 for red 
     * @param bp eyeStages borderPane
     */
    public void switchEyeColour(int i, BorderPane bp){
    	if(i == 1){ //green
    		bp.setBackground(new Background(greenEyeBG)); // sets background as greenEyeBG
    	}
    	else if(i == 0){ //red
    		bp.setBackground(new Background(redEyeBG)); // sets background as redyeBG
    	}
    	else{ //if i is neither 0 or 1
    		System.err.println("Error: switchEyeColour(int i, BorderPane bp) parameter invalid"); 
    	}
    }
       

}
