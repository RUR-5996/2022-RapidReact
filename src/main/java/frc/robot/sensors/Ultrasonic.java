package frc.robot.sensors;

import frc.robot.Constants;
import frc.robot.RobotMap;

public class Ultrasonic {
    public static double leftDistance;
    public static double rightDistance;

    public static void periodic() {
        leftDistance = RobotMap.ultrasonicLeft.getVoltage() * Constants.VOLTS_TO_DISTANCE;
        rightDistance = RobotMap.ultrasonicRight.getVoltage() * Constants.VOLTS_TO_DISTANCE;
    }
}
