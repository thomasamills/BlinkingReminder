Inscructions to build project in eclipse
-Download the latest version of OpenCV (For my project I used OpenCV 3.2) 
-Make sure that you are on a windows machine
-Make sure that the windows machine has JavaFX8 running on it
-Make sure the machine has the latest version of maven. 

-Open Eclipse:
  -Choose to import project 
    -Choose to import existing maven project 
    -This will be the main "Blinking Reminder folder" (this files parent folder)
    -Once project is imported change the project compliance to JRE8
    -Choose to go to java build path. Create a new user library and call it OpenCV. 
      -choose to add external jars to this library. 
        -Add the opencv executable jar file to this library (should be called opencv-320.jar)

project should now be loaded into eclipse. 

Possible problems adding project (may have a problem including the TestFX4 dependencies, this is done in the 
maven pom.xml file). 

Runnable Jar File:

I have created a runnable .jar file. This is packaged with its dependencies (I am allowed to do this 
because OpenCV is opensource and I am allowed to modify/redistribute any binaries) This is only runnable on windows
and has been tested on the university windows machines. 

Problems and how to use the project. 

This project has been implemented on various machines. The latest camera resolution tested had an input of 640px*480px

Instructions for use: 
Do not position camera above the users head. User must look down at the camera. 
Do not move more than 2 feet from the camera to avoid it detecting un-neccesary blinks. 