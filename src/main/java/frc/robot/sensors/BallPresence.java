package frc.robot.sensors;

import frc.robot.*;

public class BallPresence {
    public static boolean isTopBallPresent() {
        return RobotMap.colorSensor.getProximity() > 1800;
    }

    public static boolean isBottomBallPresent() {
        return RobotMap.ballButton.get();
    }
}
