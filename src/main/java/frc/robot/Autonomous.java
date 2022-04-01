package frc.robot;

public class Autonomous {
    public static void periodic() { //missing :(((
        /*
         * if(RobotMap.encoder.getDistance() < 5) {
         * RobotMap.drive.tankDrive(.5, .5);
         * } else {
         * RobotMap.drive.tankDrive(0, 0);
         * }
         */
        RobotMap.intake.set(0.75);
        RobotMap.shooterBottom.set(0.5);
        RobotMap.shooterTop.set(0.5);
    }
}
