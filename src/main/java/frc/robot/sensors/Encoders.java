package frc.robot.sensors;

import frc.robot.RobotMap;

public class Encoders {
    public static int driveTicksLeft, driveTicksRight;
    public static double distanceLeft, distanceRight;

    public static void periodic() {
        driveTicksLeft = (int) RobotMap.rearLeft.getSelectedSensorPosition(0);
        driveTicksRight = (int) RobotMap.rearRight.getSelectedSensorPosition(0);

        distanceLeft = driveTicksLeft / 1;
        distanceRight = driveTicksRight / 1;
    }
}
