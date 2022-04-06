package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import frc.robot.sensors.Encoders;
import frc.robot.sensors.NavX;

public class Autonomous {
    static final String trajectoryJSON = "paths/auto.wpilib.json";
    static Trajectory trajectory;

    double distance;

    public boolean StateHasFinished = false;
    public Boolean StateHasInitialized = false;

    // public AutoPose chosenPath;
    // public AutoPose[] myAutoContainer;

    static Timer timer = new Timer();

    static final RamseteController ramseteController = new RamseteController();
    static Field2d field = new Field2d();

    static Pose2d pose = RobotMap.odometry.update(
            Rotation2d.fromDegrees(-NavX.angle),
            Encoders.distanceLeft,
            Constants.rightDistance);

    TrajectoryConfig config = new TrajectoryConfig(5, 5)
            .setKinematics(new DifferentialDriveKinematics(0.525));

    public static void init() {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }

        field.getObject("traj").setTrajectory(trajectory);

        RobotMap.odometry.resetPosition(
                trajectory.getInitialPose(),
                Rotation2d.fromDegrees(-NavX.angle));

        timer.reset();
        timer.start();

        // make your waypoint number variable 0
        // resetOdometry stuff

    }

    public static void periodic() {
        RobotMap.odometry.update(
                Rotation2d.fromDegrees(-NavX.angle),
                Encoders.distanceLeft,
                Encoders.distanceRight);

        if (!timer.hasElapsed(trajectory.getTotalTimeSeconds())) {
            State desiredPose = trajectory.sample(timer.get());
            ChassisSpeeds speeds = ramseteController.calculate(
                    RobotMap.odometry.getPoseMeters(),
                    desiredPose);

            RobotMap.drive.arcadeDrive(speeds.vxMetersPerSecond, speeds.omegaRadiansPerSecond);
        }

        // This should be all you need to run everything in periodic
        // waypointRunner(chosenWaypoint);
    }

    // public void loadAutoPaths() {
    // // We broke our auto paths into different segments, where an action is
    // performed
    // // between each segment:
    // // IE after segment 1, intake a ball, after segment 2, shoot the ball
    // var segment1 = "loadPartOfPathHere";
    // var segment2 = "load2ndPartOfPathHere";

    // // btw the segments shouldn't be Strings, just left it like that as example

    // var ExamplePath = new Waypoint[] {
    // new Waypoint(intakeHere, xPosition, yPosition, segment1),
    // new Waypoint(shootHere, xPositon, yPosition, segment2)
    // };

    // // A nice little thing to keep all your different autos
    // myAutoContainer = new AutoPose[] {
    // new AutoPose("ExampleAuto", xDistance, yDistance, rotation, ExampleAuto)
    // };
    // }

    // // we have a part of our code here that adds all our different paths to
    // // shuffleboard

    // public class Waypoint {
    // public Runnable action;
    // public double posX;
    // public double posY;

    // // [Pathweaver object] = variable name
    // // you will need this if you want to shoot and intake during auto with our
    // logic
    // // stuff

    // // include your pathweaver object in the parameters of Waypoint
    // public Waypoint(Runnable _action, double _x, double _y) {
    // action = _action;
    // posX = _x;
    // posY = _y;
    // // set pathweaver thingy here
    // }
    // }

    // public class AutoPose {

    // public double thisX;
    // public double thisY;
    // public double thisRot;
    // public Waypoint[] thisWPset;
    // public String name;

    // // I'm gonna be honest, idk why this is giving this error
    // public AutoPose(String _S, double _x, double _y, double _rot, Waypoint[] _WP)
    // {
    // thisX = _x;
    // thisY = _y;
    // thisRot = _rot;
    // thisWPset = _WP;
    // name = _S;

    // }
    // }

    // public void waypointRunner(Waypoint[] thisWaypointSet) {
    // // If we made one round with the state, we have succesfully initialized
    // if (!StateHasInitialized) {
    // // we have a Swerve method referenced here, which:
    // // gets the FPGATimeStamp
    // // then calculates speeds for swerve motors to follow the trajectory of auto
    // // path given specific distance and angles
    // // not sure how you have coded your drive system, so I paraphrased what we
    // have
    // }

    // if (StateHasFinished) {
    // // increment a waypoint number variable here
    // StateHasFinished = false;
    // StateHasInitialized = false;
    // }
    // }

    // public double getDistance(double X1, double Y1, double X2, double Y2) {
    // distance = Math.sqrt(Math.pow((X2 - X1), 2) + Math.pow((Y2 - Y1), 2));
    // return distance;
    // }
}
