package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.XboxController;

public class SwerveDrive {

    static XboxController controller = SystemDef.controller;
    static final double MF_DRIVE_COEFF = 0.5; // change to lower if needed
    static double addDriveCoeff = 1;

    private static SwerveDrive SWERVE = new SwerveDrive();
    private static double xSpeed = 0;
    private static double ySpeed = 0;
    private static double rotation = 0;

    static final Translation2d FL_LOC = new Translation2d(SwerveDef.WHEEL_BASE_WIDTH / 2, SwerveDef.TRACK_WIDTH / 2);
    static final Translation2d FR_LOC = new Translation2d(SwerveDef.WHEEL_BASE_WIDTH / 2, -SwerveDef.TRACK_WIDTH / 2);
    static final Translation2d RL_LOC = new Translation2d(-SwerveDef.WHEEL_BASE_WIDTH / 2, SwerveDef.TRACK_WIDTH / 2);
    static final Translation2d RR_LOC = new Translation2d(-SwerveDef.WHEEL_BASE_WIDTH / 2, -SwerveDef.TRACK_WIDTH / 2);
    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(FL_LOC, FR_LOC, RL_LOC,
            RR_LOC);

    public static boolean fieldOriented = false;
    public static double holdAngle = 0; // change to sth convenient
    public static boolean fieldRelative = false; // bind to button
    public static PIDController angleHoldController = new PIDController(0.1, 0, 0); // edit the vals
    public static SwerveDriveOdometry odometry;

    public static SwerveDef.initPID flInitController;
    public static SwerveDef.initPID frInitController;
    public static SwerveDef.initPID rlInitController;
    public static SwerveDef.initPID rrInitController;

    public static SwerveDrive getInstance() {
        return SWERVE;
    }

    public void init() {
        odometry = new SwerveDriveOdometry(swerveKinematics, SwerveDef.gyro.getRotation2d());
        angleHoldController.disableContinuousInput();
        angleHoldController.setTolerance(Math.toRadians(2)); // the usual drift
    }

    public static void initFieldOriented() {
        fieldRelative = true;
        holdAngle = SwerveDef.gyro.getRotation2d().getRadians();
    }

    public static void initRobotOriented() {
        fieldRelative = false;
    }

    public static void periodic() {
        if (controller.getLeftBumperPressed()) {
            initFieldOriented();
        } else if (controller.getRightBumperPressed()) {
            initRobotOriented();
        }

        if (fieldRelative) {
            orientedDrive();
        } else {
            drive();
        }

        if (controller.getBackButtonPressed()) {
            gyroReset();
        }

        // resetZero();
    }

    public static void drive() {
        xSpeed = deadzone(-controller.getLeftX()) * SwerveDef.MAX_SPEED_MPS * SwerveDef.DRIVE_COEFFICIENT
                * addDriveCoeff; // revert if needed
        ySpeed = deadzone(-controller.getLeftY()) * SwerveDef.MAX_SPEED_MPS * SwerveDef.DRIVE_COEFFICIENT
                * addDriveCoeff; // revert if needed
        rotation = deadzone(-controller.getRightX()) * SwerveDef.MAX_SPEED_RADPS * SwerveDef.TURN_COEFFICIENT
                * addDriveCoeff; // revert if needed

        SwerveModuleState[] states = swerveKinematics.toSwerveModuleStates(new ChassisSpeeds(ySpeed, xSpeed, rotation));

        SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveDef.MAX_SPEED_MPS);

        SwerveDef.flModule.setState(states[0]);
        SwerveDef.frModule.setState(states[1]);
        SwerveDef.rlModule.setState(states[2]);
        SwerveDef.rrModule.setState(states[3]);
    }

    public static void drive(double xSpeed, double ySpeed, double rotation) {
        SwerveModuleState[] states = swerveKinematics.toSwerveModuleStates(new ChassisSpeeds(ySpeed, xSpeed, rotation));

        SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveDef.MAX_SPEED_MPS);

        SwerveDef.flModule.setState(states[0]);
        SwerveDef.frModule.setState(states[1]);
        SwerveDef.rlModule.setState(states[2]);
        SwerveDef.rrModule.setState(states[3]);
    }

    public static void orientedDrive() {
        xSpeed = deadzone(-controller.getLeftX()) * SwerveDef.MAX_SPEED_MPS * SwerveDef.DRIVE_COEFFICIENT
                * addDriveCoeff; // revert if needed
        ySpeed = deadzone(-controller.getLeftY()) * SwerveDef.MAX_SPEED_MPS * SwerveDef.DRIVE_COEFFICIENT
                * addDriveCoeff; // revert if needed
        rotation = deadzone(-controller.getRightX()) * SwerveDef.MAX_SPEED_RADPS * SwerveDef.TURN_COEFFICIENT
                * addDriveCoeff; // revert if needed (delete addDriveCoeff)

        SwerveModuleState[] states = swerveKinematics.toSwerveModuleStates(
                ChassisSpeeds.fromFieldRelativeSpeeds(ySpeed, xSpeed, rotation,
                        SwerveDef.gyro.getRotation2d()));

        SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveDef.MAX_SPEED_MPS);

        SwerveDef.flModule.setState(states[0]);
        SwerveDef.frModule.setState(states[1]);
        SwerveDef.rlModule.setState(states[2]);
        SwerveDef.rrModule.setState(states[3]);
    }

    public static double deadzone(double input) {
        if (Math.abs(input) < 0.2) {
            return 0;
        } else {
            return input;
        }
    }

    public static void resetZero() {
        if (controller.getAButtonPressed()) {
            SwerveDef.flModule.zeroSwerve();
            SwerveDef.frModule.zeroSwerve();
            SwerveDef.rlModule.zeroSwerve();
            SwerveDef.rrModule.zeroSwerve();
        }
    }

    public static void testInit() {
        flInitController = new SwerveDef.initPID(0.012, 0.0402645, 0.000894, 0.75, 0);
        frInitController = new SwerveDef.initPID(0.012, 0.0402645, 0.000894, 0.75, 0);
        rlInitController = new SwerveDef.initPID(0.012, 0.0402645, 0.000894, 0.75, 0);
        rrInitController = new SwerveDef.initPID(0.012, 0.0402645, 0.000894, 0.75, 0);
    }

    public static void zeroDrive() {
        flInitController.setOffset(SwerveDef.flModule
                .clampContinuousDegs(SwerveDef.flModule.getBetterAnalogDegs() - SwerveDef.FL_STEER_OFFSET));

        frInitController.setOffset(SwerveDef.frModule
                .clampContinuousDegs(SwerveDef.frModule.getBetterAnalogDegs() - SwerveDef.FR_STEER_OFFSET));

        rlInitController.setOffset(SwerveDef.rlModule
                .clampContinuousDegs(SwerveDef.rlModule.getBetterAnalogDegs() - SwerveDef.RL_STEER_OFFSET));

        rrInitController.setOffset(SwerveDef.rrModule
                .clampContinuousDegs(SwerveDef.rrModule.getBetterAnalogDegs() - SwerveDef.RR_STEER_OFFSET));

        SwerveDef.flSteer.set(ControlMode.PercentOutput, flInitController.pidGet());
        SwerveDef.frSteer.set(ControlMode.PercentOutput, frInitController.pidGet());
        SwerveDef.rlSteer.set(ControlMode.PercentOutput, rlInitController.pidGet());
        SwerveDef.rrSteer.set(ControlMode.PercentOutput, rrInitController.pidGet());

        resetZero();
    }

    static void gyroReset() {
        SwerveDef.gyro.reset();
    }

    static void updateOdometry() {
        odometry.update(SwerveDef.gyro.getRotation2d(), SwerveDef.flModule.getState(), SwerveDef.frModule.getState(),
                SwerveDef.rlModule.getState(), SwerveDef.rrModule.getState());
    }

    static void resetOdometry() {
        odometry.resetPosition(new Pose2d(), SwerveDef.gyro.getRotation2d());
    }

    static void setOdometry(Pose2d pose, Rotation2d rot) {
        odometry.resetPosition(pose, rot);
    }
}
