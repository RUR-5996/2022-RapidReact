package frc.robot.sensors;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Odometry {
    static Pose2d pose = RobotMap.odometry.update(
            Rotation2d.fromDegrees(-RobotMap.navX.getAngle()),
            Constants.leftDistance,
            Constants.rightDistance);

    static double x = 0;
    static double y = 0;
    static double rot = 0;

    public static void periodic() {
        pose = RobotMap.odometry.update(Rotation2d.fromDegrees(-RobotMap.navX.getAngle()), Constants.leftDistance,
                Constants.rightDistance);
        x = pose.getX(); // meters
        y = pose.getY(); // meters
        rot = pose.getRotation().getDegrees(); // degrees
    }
}