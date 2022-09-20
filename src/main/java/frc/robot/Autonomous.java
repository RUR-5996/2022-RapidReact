package frc.robot;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.trajectory.Trajectory;

public class Autonomous {

    static double elapsedTime;
    static PathPlannerTrajectory trajOne = PathPlanner.loadPath("New Path", 8, 5);
    static PPSwerveControllerCommand command = new PPSwerveControllerCommand(trajectory, pose, kinematics, xController, yController, thetaController, outputModuleStates, requirements)

    public static void init() {

    }

    public static void periodic() {

    }

    static void runTrajectory(PathPlannerTrajectory trajectory, SwerveDriveOdometry odometry, Rotation2d rot2d) {
        elapsedTime = 
    }
}
