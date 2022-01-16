package frc.robot;

public class Drive {
    static final double SPEED_CONSTANT = 0.8;

    public static void periodic() {
        double speed = RobotMap.controller.getLeftY() * SPEED_CONSTANT;
        double rotation = RobotMap.controller.getRightX() * SPEED_CONSTANT;

        // True to square the speed for finer control
        RobotMap.drive.arcadeDrive(speed, rotation, true);
    }
}