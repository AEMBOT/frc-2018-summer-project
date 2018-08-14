package org.usfirst.frc.falcons6443.robot.utilities;


import java.io.File;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogPuller {

    //Booleans used to specify weather or not the USB Drive and the robot are connected

    private boolean isUSBConnected = false;
    private boolean isRobotConnected = false;
    private Process batchRunProc;

    public void execute() throws Exception {

        //USB drive path, Robot USB IP, and rio home path variables
        String robotIP = "";
        String drivePath = "F:\\";
        Path rioPath = Paths.get("/home");

        //Rio Memory
        double rioMem = 0;

        //Attempts to set amount of usable memory on the RIO
        try {
            rioMem = getUsableSpace(rioPath);
        } catch (Exception e){
            System.out.println("Couldn't access available rio memory");
        }

        //Sets variable depending on if robot is connected or not
       isUSBConnected = checkIfDirectoryExists(drivePath);
       isRobotConnected = checkIfRobotIsConnected(robotIP);

       if(isUSBConnected && isRobotConnected && (rioMem != 0 && rioMem > 5))
       {
            //When both are connected run batch file and
         try {
             batchRunProc = Runtime.getRuntime().exec("loggerRetrieval.bat");
         }catch (IOException e){
             System.out.println("File couldn't / didn't run");
         }
       }
       else
       {
           System.out.println("USB or Robot not connected. Please Try again");
       }
    }




    //Method used to return the boolean value of the isUSBConnected variable
    private  boolean checkIfDirectoryExists(String usbPath){

        //Creates a local variable named directory then checks if that value does infact exist and sets the isUSBConnected variable accordingly
        File directory = new File(usbPath);
        isUSBConnected = directory.exists();
        return isUSBConnected;
    }

    //Method used to determine weather or not the robot is connected to the computer
    private boolean checkIfRobotIsConnected(String robotIP) throws Exception {

        String pingOut = "";

        //Creates a new cmd process to ping the robot
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", "ping " + robotIP);
        builder.redirectOutput();

        Process proc = builder.start();

        //Gathers command lines response and passes it into variable line
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line;
        while (true){
            line = r.readLine();
            if(line == null) {break;}

            //It then checks if the response was positive or negative
            if(line.contains("Request timed out."))
            {
                isRobotConnected = false;
            }
            else
            {
                isRobotConnected = true;
            }
        }
        return isRobotConnected;
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
