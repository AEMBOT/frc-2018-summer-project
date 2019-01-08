package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

/**
 * This class was imported from Simbotics code.
 * Includes more encoder options. Some added functions to fit code needs
 *
 * NOTE: The GreyHill encoders are 256 ticks per rev. Make sure to calculate in if you have
 * a gearbox or not (eg: GreyHill on a 4:1 gearbox is 1024 ticks per rev, 256 times 4)
 *
 * @author Simbotics 2017
 */
public class Encoders{
    private int prev;
    private int speed;
    private int offset;
    private int ticksPerRev = 256;
    private double diameter;

    public Encoders(int channelA, int channelB) {
       // super(channelA, channelB);
        prev = channelA;
    }
}