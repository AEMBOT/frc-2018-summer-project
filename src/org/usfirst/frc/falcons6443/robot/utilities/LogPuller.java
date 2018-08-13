package org.usfirst.frc.falcons6443.robot.utilities;

import org.apache.tools.ant.Task;

import java.io.File;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogPuller extends Task {

    //Booleans used to specify weather or not the USB Drive and the robot are connected
    private boolean isUSBConnected = false;
    private boolean isRobotConnected = false;
    private Process process;

    public void execute() {

        String drivePath = "F:\\";
        Path rioPath = Paths.get("/home");
        double rioMem = 0;

        log("BOOLIN");


        try {
            rioMem = getUsableSpace(rioPath);
        } catch (Exception e){
            log("Couldn't access available rio memory");
        }



       isUSBConnected = checkIfDirectoryExists(drivePath);

       if(isUSBConnected && isRobotConnected && (rioMem != 0 && rioMem > 5))
       {
            //When both are connected run batch file
         try {
             process = Runtime.getRuntime().exec("loggerRetrieval.bat");
         }catch (IOException e){
             log("File couldn't / didn't run");
         }
       }
       else
       {
            log("USB or Robot not connected. Please Try again");
       }
    }




    //Method used to return the boolean value of the isUSBConnected variable
    public  boolean checkIfDirectoryExists(String usbPath){

        //Creates a local variable named directory then checks if that value does infact exist and sets the isUSBConnected variable accordingly
        File directory = new File(usbPath);
        if (directory.exists())
        {
            isUSBConnected = true;
        }
        else
        {
            isUSBConnected = false;
        }
        return isUSBConnected;
    }


    //Returns total unused space of param (RIO) in MB
    private double getUsableSpace(Path path) throws Exception{
       return Files.getFileStore(path).getTotalSpace() / 1048576;
    }

    public void setUSBConnected(boolean USBConnected) {
        isUSBConnected = USBConnected;
    }

    public void setRobotConnected(boolean robotConnected) {
        isRobotConnected = robotConnected;
    }
}
