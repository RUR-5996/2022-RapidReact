package frc.robot.sensors;

import frc.robot.*;

public class NavX {
    public static float xAcceleration = 0;
    public static float yAcceleration = 0;
    public static double angle = 0;

    public static void periodic() {
        xAcceleration = RobotMap.navX.getWorldLinearAccelX();
        yAcceleration = RobotMap.navX.getWorldLinearAccelY();
        angle = RobotMap.navX.getAngle();
    }
}
