package frc.robot;

import frc.robot.sensors.*;

public class Sensors {
    public static void periodic() {
        NavX.periodic();
        Aligner.periodic();
        BallCounter.periodic();
        Odometry.periodic();
        Encoders.periodic();
    }
}
