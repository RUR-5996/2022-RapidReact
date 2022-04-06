package frc.robot;

public class Encoders {
    public static void getDriveDist() {
        Constants.driveTicksLeft = RobotMap.rearLeft.getSelectedSensorPosition(0);
        Constants.driveTicksRight = RobotMap.frontRight.getSelectedSensorPosition(0);
    }
}
