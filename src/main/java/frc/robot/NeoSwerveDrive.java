package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.XboxController;

public class NeoSwerveDrive {

    static XboxController controller = SystemDef.controller;

    private static NeoSwerveDrive SWERVE = new NeoSwerveDrive();
    private static double xSpeed = 0;
    private static double ySpeed = 0;
    private static double rotation = 0;

    public static double holdAngle = 0; //change to sth convenient
    public static boolean fieldRelative = false; //bind to button
    public static PIDController angleHoldController = new PIDController(0, 0, 0); //edit the vals

    static final Translation2d FL_LOC = new Translation2d(NeoSwerveDef.WHEEL_BASE_WIDTH/2, NeoSwerveDef.TRACK_WIDTH/2);
    static final Translation2d FR_LOC = new Translation2d(NeoSwerveDef.WHEEL_BASE_WIDTH/2, NeoSwerveDef.TRACK_WIDTH/2);
    static final Translation2d RL_LOC = new Translation2d(NeoSwerveDef.WHEEL_BASE_WIDTH/2, NeoSwerveDef.TRACK_WIDTH/2);
    static final Translation2d RR_LOC = new Translation2d(NeoSwerveDef.WHEEL_BASE_WIDTH/2, NeoSwerveDef.TRACK_WIDTH/2);
    public static final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(FL_LOC, FR_LOC, RL_LOC, RR_LOC);
    public static SwerveDriveOdometry odometry;
    
    public static NeoSwerveDrive getInstance() {
        return SWERVE;
    }

    public void init() {
        odometry = new SwerveDriveOdometry(kinematics, NeoSwerveDef.gyro.getRotation2d());
        angleHoldController.disableContinuousInput();
        angleHoldController.setTolerance(Math.toRadians(2)); //the usual drift
    }

    public static void periodic() {
        if(controller.getLeftBumperPressed()) {
            initRobotOriented();
        } else if(controller.getRightBumperPressed()) {
            initFieldOriented();
        }
        if(fieldRelative) {
            fieldOrientedDrive();
        } else {
            drive();
        }
    }

    public static void drive() {
        xSpeed = controller.getLeftX()*NeoSwerveDef.SPEED_RATIO;
        ySpeed = controller.getLeftY()*NeoSwerveDef.SPEED_RATIO;
        rotation = controller.getRightX()*NeoSwerveDef.SPEED_RATIO;

        SwerveModuleState [] states = kinematics.toSwerveModuleStates(new ChassisSpeeds(xSpeed, ySpeed, rotation));
        SwerveDriveKinematics.desaturateWheelSpeeds(states, NeoSwerveDef.MAX_ROBOT_SPEED);
        NeoSwerveDef.flModule.setState(states[0]);
        NeoSwerveDef.frModule.setState(states[1]);
        NeoSwerveDef.rlModule.setState(states[2]);
        NeoSwerveDef.rrModule.setState(states[3]);
    }

    public static void initFieldOriented() {
        fieldRelative = true;
        holdAngle = NeoSwerveDef.gyro.getRotation2d().getRadians();
    }

    public static void initRobotOriented() {
        fieldRelative = false;
    }


    public static void fieldOrientedDrive() {
        xSpeed = controller.getLeftX()*NeoSwerveDef.SPEED_RATIO;
        ySpeed = controller.getLeftY()*NeoSwerveDef.SPEED_RATIO;
        rotation = controller.getRightX()*NeoSwerveDef.SPEED_RATIO;
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, NeoSwerveDef.gyro.getRotation2d()));
        SwerveDriveKinematics.desaturateWheelSpeeds(states, NeoSwerveDef.MAX_ROBOT_SPEED);
        NeoSwerveDef.flModule.setState(states[0]);
        NeoSwerveDef.frModule.setState(states[1]);
        NeoSwerveDef.rlModule.setState(states[2]);
        NeoSwerveDef.rrModule.setState(states[3]);
    }
}
