package frc.robot;

public class Encoders {
    public static void getDriveDist() {
        Constants.leftDriveTicks = RobotMap.rearLeft.getSelectedSensorPosition(0);
        Constants.rightDriveTicks = RobotMap.frontRight.getSelectedSensorPosition(0);
    }
}
