package org.usfirst.frc.falcons6443.robot.subsystems;

import java.io.File;
import java.io.*;

public class LogPuller {

    //Booleans used to specify weather or not the USB Drive and the robot are connected
    static boolean isUSBConnected = false;
    static boolean isRobotConnected = false;

    public static void main(String[] args) {

        String drivePath = "F:\\";
       isUSBConnected = checkIfDirectoryExists(drivePath);

       if(isUSBConnected && isRobotConnected)
       {
            //When both are connected run batch file
       }
       else
       {
            System.out.println("USB or Robot not connected. Please Try again");
       }
    }


    //Method used to return the boolean value of the isUSBConnected variable
    public static boolean checkIfDirectoryExists(String usbPath){

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
}
