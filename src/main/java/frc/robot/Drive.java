package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
    static final double SPEED_CONSTANT = 0.8;
    static final double ROTATION_CONSTANT = 0.65;

    static boolean slowMode = false;

    static boolean aimingForHub = false;

    public static void periodic() {
        if (RobotMap.controller.getLeftTriggerAxis() > 0.1) {
            double angle = Limelight.getAngleToHub();
            double rotation = angle / 28 * RobotMap.controller.getLeftTriggerAxis();

            if (angle > 1 || angle < -1)
                RobotMap.drive.arcadeDrive(rotation, 0, true);
        } else {
            double speed = -RobotMap.controller.getLeftY() * SPEED_CONSTANT;
            double rotation = RobotMap.controller.getRightX() * ROTATION_CONSTANT;

            if (RobotMap.controller.getStartButtonPressed())
                slowMode = !slowMode;

            SmartDashboard.putBoolean("Slow mode", slowMode);

            // True to square the speed for finer control
            RobotMap.drive.arcadeDrive(
                    rotation * (slowMode ? 0.8 : 1),
                    speed * (slowMode ? 0.6 : 1),
                    true);
        }
    }
}