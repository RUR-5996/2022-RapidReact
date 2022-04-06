package frc.robot.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;

public class BallCounter {
    private static final int PROXIMITY_THRESHOLD = 120;

    public static void periodic() {
        SmartDashboard.putNumber("Balls", ballCount());
    }

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
        SmartDashboard.putBoolean("Proximity", proximity > PROXIMITY_THRESHOLD);
        return proximity > PROXIMITY_THRESHOLD;
    }

    public static boolean isBottomBallPresent() {
        SmartDashboard.putBoolean("Ball button", !RobotMap.ballButton.get());
        return !RobotMap.ballButton.get();
    }
}
