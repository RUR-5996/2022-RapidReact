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

    public static boolean isTopBallPresent() {
        int proximity = RobotMap.colorSensor.getProximity();
        SmartDashboard.putNumber("Color sensor proximity", proximity);
        return proximity > 1800;
    }

    public static boolean isBottomBallPresent() {
        return RobotMap.ballButton.get();
    }
}
