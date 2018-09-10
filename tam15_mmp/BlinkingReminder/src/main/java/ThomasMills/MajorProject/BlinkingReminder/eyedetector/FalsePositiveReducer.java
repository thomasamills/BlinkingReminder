package ThomasMills.MajorProject.BlinkingReminder.eyedetector;


import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class FalsePositiveReducer {
	
	public FalsePositiveReducer(){		
	}
	/**
	 * findCorrectFace(MatOfRect features)
	 * This method takes in a MatOfRect (matrix of rectangles returned 
	 * from the haarcascade face detection classifier) and tries to find 
	 * the biggest one to reduce false positives(other smaller detected faces in 
	 * the  background and things that arent faces at all) and returns the best.   
	 * @param features
	 * @return
	 */
	
	public Rect findCorrectFace(Rect[] features){
		// as long as there was a face detected
		if(features.length > 0){ 
			 // if only one face
	        if(features.length == 1){
	        	// return the only face
	        	return features[0]; 
	        }
	        else{
	        	// to store the face with the largest area
	        	Rect largest = null; 
	        	// loops through every detected face
	        	for(Rect face: features){ 
	        		 // checks if its the first time running
	        		if(largest==null){ 
	        			largest = face;
	        		}
	        		else{
	        			/* checks to see if face
                        is bigger than the largest */
	        			if(face.area() > largest.area()){ 
	        				 // sets this one as the current largest
	        				largest = face; 
	        			}
	        		}
	        	}
	        	// returns the largest rect found.
	        	return largest;	  	
	        }          
		}else{
			return null;
		}	
	}
	

	/**
	 * findCorrectEye(MatOfRect features, Mat croppedFace)
	 * This method takes in a MatOfRect (matrix of rectangles returned 
	 * from the haarcascade right eye classifier) and, if more than one 
	 * eye has been detected (sometimes it picks up the left eye and nostrils)
	 * it will return the features in the top left hand (frontal view) side of
	 * the picture and also the biggest if more than one. 
	 * @return The feature best posit. 
	 */
	
	public Rect findCorrectEye(Rect[] features, Mat croppedFace){
		 //as long as there was an eye detected	
		if(features.length > 0){		
			//if only one detected
			if(features.length == 1){
				//return only one 
				return features[0];
			}
			else{
				// to store the rect
				Rect largest = null;
				//loops through every feature
				for(Rect eye : features){
					//if feature in left half side of the picture
					if(eye.x < (croppedFace.width()/2 - 10)){
						//if feature in the top half of the picture
						if(eye.y < croppedFace.height()/2){
							//if there is no current largest
							if(largest == null){
								//set this one as largest
								largest = eye;
							}
							//else, if already a largest and this ones area is larger
							else if(eye.area() > largest.area()){
								// set this one as largest
								largest = eye;
							}
						}
						}
					}
				
				//returns the largest feature as a rect.
				return largest;
				}
				
			}
		return null;
		}
}
