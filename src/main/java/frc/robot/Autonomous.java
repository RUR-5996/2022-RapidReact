package frc.robot;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.math.*;

public class Autonomous {

    static double elapsedTime;
    static PathPlannerTrajectory trajOne;// = PathPlanner.loadPath("New Path", 3, 2.5);
    static double kP = 3;
    static double startTime = 0;
    static String status = "";
    static HolonomicDriveController driveController = new HolonomicDriveController(new PIDController(kP, 0, 0),
            new PIDController(kP, 0, 0),
            new ProfiledPIDController(kP * SwerveDef.MAX_SPEED_RADPS / SwerveDef.MAX_SPEED_MPS, 0, 0,
                    new Constraints(SwerveDef.MAX_SPEED_RADPS, SwerveDef.MAX_SPEED_RADPS)));

    public static void init() {

    }

    public static void report() {
        SmartDashboard.putNumber("swerveX", Robot.SWERVE.odometry.getPoseMeters().getX());
        SmartDashboard.putNumber("swerveY", Robot.SWERVE.odometry.getPoseMeters().getY());
    }

    static void runTrajectory(PathPlannerTrajectory trajectory, SwerveDriveOdometry odometry, Rotation2d rot2d) {
        elapsedTime = Timer.getFPGATimestamp() - startTime;

        switch (status) {
            case "setup":
                Robot.SWERVE.setOdometry(((PathPlannerState) trajectory.getInitialState()).poseMeters,
                        ((PathPlannerState) trajectory.getInitialState()).poseMeters.getRotation());
                startTime = Timer.getFPGATimestamp();
                status = "execute";
                break;

            case "execute":
                if (elapsedTime < ((PathPlannerState) trajectory.getEndState()).timeSeconds) {
                    ChassisSpeeds speeds = driveController.calculate(odometry.getPoseMeters(),
                            ((PathPlannerState) trajectory.sample(elapsedTime)),
                            ((PathPlannerState) trajectory.sample(elapsedTime)).holonomicRotation);
                    Robot.SWERVE.drive(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond,
                            speeds.omegaRadiansPerSecond);
                } else {
                    Robot.SWERVE.drive(0, 0, 0);
                    Robot.SWERVE.holdAngle = ((PathPlannerState) trajectory.getEndState()).holonomicRotation
                            .getRadians();
                    status = "done";
                }
                break;
            default:
                Robot.SWERVE.drive(0, 0, 0);
                break;
        }
    }

    static void newTrajectory() {
        status = "setup";
    }
}
