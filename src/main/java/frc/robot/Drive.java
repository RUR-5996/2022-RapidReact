package frc.robot;

public class Drive {
    static final double SPEED_CONSTANT = 0.8;
    static final double INTAKE_CONSTANT = -0.5;

    public static void periodic() {
        double speed = RobotMap.controller.getRightX() * SPEED_CONSTANT;
        double rotation = -RobotMap.controller.getLeftY() * SPEED_CONSTANT;

        // True to square the speed for finer control
        RobotMap.drive.arcadeDrive(speed, rotation, true);

        // Intake
        if (RobotMap.controller.getRightBumper()) {
            RobotMap.intake.set(-0.5 * INTAKE_CONSTANT);
        } else {
            double intakeSpeed = RobotMap.controller.getRightTriggerAxis() * INTAKE_CONSTANT;
            RobotMap.intake.set(intakeSpeed);
        }
    }
}