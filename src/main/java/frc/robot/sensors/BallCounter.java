package frc.robot.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;

public class BallCounter {
    public static int ballCount() {
        boolean top = isTopBallPresent(), bottom = isBottomBallPresent();
        
        if (top && bottom)
            return 2;
        else if (top || bottom)
            return 1;
        else
            return 0;
    }

    static boolean isTopBallPresent() {
        SmartDashboard.putNumber("Color sensor proximity", RobotMap.colorSensor.getProximity());
        return RobotMap.colorSensor.getProximity() > 1800;
    }

    static boolean isBottomBallPresent() {
        return RobotMap.ballButton.get();
    }
}
