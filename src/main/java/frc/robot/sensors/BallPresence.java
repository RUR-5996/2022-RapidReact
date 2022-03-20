package frc.robot.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;

public class BallPresence {
    public static boolean isTopBallPresent() {
        SmartDashboard.putNumber("Color sensor proximity", RobotMap.colorSensor.getProximity());
        return RobotMap.colorSensor.getProximity() > 1800;
    }

    public static boolean isBottomBallPresent() {
        return RobotMap.ballButton.get();
    }
}
