package org.usfirst.frc.falcons6443.robot.utilities;

import org.usfirst.frc.falcons6443.robot.RobotMap;
import java.io.File;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LogPuller {

    //Booleans used to specify weather or not the USB Drive and the robot are connected
    private boolean isUSBConnected = false;
    private boolean isRobotConnected = false;
    private Process batchRunProc;
    private double usableSpace;

    public void execute()  {

        //USB drive path, Robot USB IP, and rio home path variables
        String robotIP = "10.64.43.2";
        String drivePath = "F:\\";
        Path rioPath = Paths.get("/home");

        //Rio Memory
        double rioMem = 0;

        //Attempts to set amount of usable memory on the RIO
        rioMem = getUsableSpace(rioPath);

        //Sets variable depending on if robot is connected or not
        isUSBConnected = checkIfDirectoryExists(drivePath);
        isRobotConnected = checkIfRobotIsConnected(robotIP);

                                    //Shouldn't this be ran if the usable space is too small, not larger than 5?
        if(isUSBConnected && isRobotConnected && (rioMem != 0 && rioMem > 5) && RobotMap.LogPuller && RobotMap.Logger) {
            //When both are connected run batch file and
            //Check if it should run before running
            System.out.println("Do you wish to pull the logs (Y or N): ");
            Scanner s = new Scanner(System.in);
            String response = s.nextLine();
            if(response.toLowerCase() == "y") {
                try {
                    batchRunProc = Runtime.getRuntime().exec("C:\\Users\\jwilt\\IdeaProjects\\frc-2018-summer-project\\loggerRetrieval.bat");
                }
                catch (IOException e){
                    System.out.println("File couldn't / didn't run");
                }
            }

        }
        else if(!RobotMap.LogPuller || !RobotMap.Logger) {
            System.out.println("Logger or log puller setting false. Set true in RobotMap.");
        }
        else {
            System.out.println("USB or Robot not connected. Please Try again.");
        }
    }

    //Method used to return the boolean value of the isUSBConnected variable
    private  boolean checkIfDirectoryExists(String usbPath){

        //Creates a local variable named directory then checks if that value does
        //in fact exist and sets the isUSBConnected variable accordingly
        File directory = new File(usbPath);
        isUSBConnected = directory.exists();
        return isUSBConnected;
    }

    //Method used to determine weather or not the robot is connected to the computer
    private boolean checkIfRobotIsConnected(String robotIP) {

        String pingOut = "";

        //Creates a new cmd process to ping the robot
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", "ping " + robotIP);
        builder.redirectOutput();
        try {
            Process proc = builder.start();

            //Gathers command lines response and passes it into variable line
            BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) break;

                //It then checks if the response was positive or negative
                isRobotConnected = !line.contains("Request timed out.");
            }
        }
        catch(IOException e){

        }
        return isRobotConnected;
    }

    //Returns total unused space of param (RIO) in MB
    private double getUsableSpace(Path path) {
        try {
             usableSpace = Files.getFileStore(path).getUsableSpace() / 1048576;
        }
        catch (IOException e){

        }
        return usableSpace;
    }

    public void setUSBConnected(boolean USBConnected) {
        isUSBConnected = USBConnected;
    }

    public void setRobotConnected(boolean robotConnected) {
        isRobotConnected = robotConnected;
    }
}
