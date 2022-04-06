package frc.robot.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class Aligner {
    public static void periodic() {
        boolean isAligned = RobotMap.alignerLeft.getDistance() < 30
                && RobotMap.alignerRight.getDistance() < 30;

        SmartDashboard.putBoolean("Alignment", isAligned);
    }
}