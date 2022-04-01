package frc.robot;

import frc.robot.sensors.*;

public class Sensors {
    public static void periodic() {
        NavX.periodic();
        Ultrasonic.getDistance();
        Encoders.getDriveDist();
        Odometry.periodic();
        // BallCounter.isTopBallPresent();
    }
}
