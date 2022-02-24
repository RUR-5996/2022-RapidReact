package frc.robot;

public class Autonomous {
    public static void periodic(){
        if(RobotMap.encoder.getDistance() < 5) {
            RobotMap.drive.tankDrive(.5, .5);
          } else {
            RobotMap.drive.tankDrive(0, 0);
        }
    }
}
