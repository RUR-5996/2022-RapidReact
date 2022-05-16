package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class NeoSwerveDef {

    public static final boolean FL_STEER_INVERTED = false;
    public static final boolean FR_STEER_INVERTED = false;
    public static final boolean RL_STEER_INVERTED = false;
    public static final boolean RR_STEER_INVERTED = false; // or true

    public static final boolean FL_DRIVE_INVERTED = false;
    public static final boolean FR_DRIVE_INVERTED = false;
    public static final boolean RL_DRIVE_INVERTED = false;
    public static final boolean RR_DRIVE_INVERTED = false; // or true

    public static final pidValues FL_STEER_PID_VALUES = new pidValues(0, 0, 0);
    public static final pidValues FR_STEER_PID_VALUES = new pidValues(0, 0, 0);
    public static final pidValues RL_STEER_PID_VALUES = new pidValues(0, 0, 0);
    public static final pidValues RR_STEER_PID_VALUES = new pidValues(0, 0, 0);

    public static final pidValues FL_DRIVE_PID_VALUES = new pidValues(0, 0, 0);
    public static final pidValues FR_DRIVE_PID_VALUES = new pidValues(0, 0, 0);
    public static final pidValues RL_DRIVE_PID_VALUES = new pidValues(0, 0, 0);
    public static final pidValues RR_DRIVE_PID_VALUES = new pidValues(0, 0, 0);

    public static final double FL_STEER_OFFSET = 0.98; // V
    public static final double FR_STEER_OFFSET = 1.71;
    public static final double RL_STEER_OFFSET = 2.29;
    public static final double RR_STEER_OFFSET = 3.63;

    public static final double FALCON_TICKS_PER_MOTOR_REV = 2048;
    public static final double STEER_FEEDBACK_COEFFICIENT = 0; // think of an efficient way to do this
    public static final double DRIVE_DIST_PER_WHEEL_REV = 0.1; // meters
    public static final double DRIVE_TICKS_PER_MOTOR_REV = FALCON_TICKS_PER_MOTOR_REV * 10; // TICKS PER MOTOR REV*DRIVE
                                                                                            // GEAR RATIO
    public static final double MAX_WHEEL_SPEED = 0;
    public static final double WHEEL_BASE_WIDTH = 0;
    public static final double TRACK_WIDTH = 0;

    public static final double SPEED_RATIO = 0.5;

    public static final int TIMEOUT_MS = 20;

    public static SteerMotor flSteer = new SteerMotor(1, FL_STEER_INVERTED);
    public static DriveMotor flDrive = new DriveMotor(1, FL_DRIVE_INVERTED);
    public static SwerveModule flModule = new SwerveModule(flSteer, FL_STEER_PID_VALUES, flDrive, FL_DRIVE_PID_VALUES,
            FL_STEER_OFFSET);

    public static SteerMotor frSteer = new SteerMotor(3, FR_STEER_INVERTED);
    public static DriveMotor frDrive = new DriveMotor(2, FR_DRIVE_INVERTED);
    public static SwerveModule frModule = new SwerveModule(frSteer, FR_STEER_PID_VALUES, frDrive, FR_DRIVE_PID_VALUES,
            FR_STEER_OFFSET);

    public static SteerMotor rlSteer = new SteerMotor(2, RL_STEER_INVERTED);
    public static DriveMotor rlDrive = new DriveMotor(3, RL_DRIVE_INVERTED);
    public static SwerveModule rlModule = new SwerveModule(rlSteer, RL_STEER_PID_VALUES, rlDrive, RL_DRIVE_PID_VALUES,
            RL_STEER_OFFSET);

    public static SteerMotor rrSteer = new SteerMotor(4, RR_STEER_INVERTED);
    public static DriveMotor rrDrive = new DriveMotor(4, RR_DRIVE_INVERTED);
    public static SwerveModule rrModule = new SwerveModule(rrSteer, RR_STEER_PID_VALUES, rrDrive, RR_DRIVE_PID_VALUES,
            RR_STEER_OFFSET);

    public static class SteerMotor extends CANSparkMax {
        public boolean inverted;

        public SteerMotor(int id, boolean isInverted) {
            super(id, CANSparkMaxLowLevel.MotorType.kBrushless); // works only with neos
            inverted = isInverted;
        }
    }

    public static class DriveMotor extends VictorSPX {
        public boolean inverted;

        public DriveMotor(int id, boolean isInverted) {
            super(id);
            inverted = isInverted;
        }
    }

    // Use this structure when using off-motor encoder. Implement in SwerveModule
    // class!
    public static class SwerveSensor {
        /*
         * public SwerveSensor(int id) {
         * super(id);
         * }
         */
    }

    // should create a lib for this
    public static class SwerveModule {
        public SteerMotor steerMotor;
        public DriveMotor driveMotor;
        public pidValues drivePID, steerPID;
        public double steerOffset;

        public SwerveModule(SteerMotor sMotor, pidValues sPID, DriveMotor dMotor, pidValues dPID, double sOffset) {
            steerMotor = sMotor;
            driveMotor = dMotor;
            drivePID = dPID;
            steerPID = sPID;
            steerOffset = sOffset;
        }

        public void moduleInit() {
            driveMotor.configFactoryDefault();
            driveMotor.setInverted(driveMotor.inverted);
            driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT_MS);
            driveMotor.config_kP(0, drivePID.kP, TIMEOUT_MS);
            driveMotor.config_kI(0, drivePID.kI, TIMEOUT_MS);
            driveMotor.config_kD(0, drivePID.kD, TIMEOUT_MS);
            driveMotor.config_kF(0, drivePID.kF, TIMEOUT_MS);
            driveMotor.config_IntegralZone(0, 300); // do we need this?

            // steerMotor.restoreFactoryDefaults();
            // steerMotor.configFeedbackNotContinuous(false, TIMEOUT_MS);
            // steerMotor.configSelectedFeedbackCoefficient(1 / STEER_FEEDBACK_COEFFICIENT,
            // 0, TIMEOUT_MS);
            // steerMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0,
            // TIMEOUT_MS);
            // steerMotor.config_kP(0, steerPID.kP, TIMEOUT_MS);
            // steerMotor.config_kI(0, steerPID.kI, TIMEOUT_MS);
            // steerMotor.config_kD(0, steerPID.kD, TIMEOUT_MS);
            // steerMotor.config_kF(0, steerPID.kF, TIMEOUT_MS);
            // steerMotor.configAllowableClosedloopError(0, 1, TIMEOUT_MS);
        }

        public void disabledInit() {
            driveMotor.setNeutralMode(NeutralMode.Coast);
            steerMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        }

        public void enabledInit() {
            driveMotor.setNeutralMode(NeutralMode.Brake);
            steerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }

        public void zeroSwerve() {
            // steerMotor.setSelectedSensorPosition(steerMotor.getSelectedSensorPosition(),
            // 0, TIMEOUT_MS);
        }

        public SwerveModuleState getState() {
            return new SwerveModuleState(
                    driveMotor.getSelectedSensorVelocity(0) * DRIVE_DIST_PER_WHEEL_REV
                            / (0.1 * DRIVE_TICKS_PER_MOTOR_REV),
                    new Rotation2d(Math.toRadians(steerMotor.getEncoder().getPosition())));
            // new Rotation2d(Math.toRadians(steerMotor.getSelectedSensorPosition())));
        }

        public SwerveModuleState optimizeState(SwerveModuleState stateToOptimize, Rotation2d moduleAngle) {
            Rotation2d diff = stateToOptimize.angle.minus(moduleAngle);

            if (Math.abs(diff.getDegrees()) > 90) {
                return new SwerveModuleState(-stateToOptimize.speedMetersPerSecond,
                        stateToOptimize.angle.rotateBy(Rotation2d.fromDegrees(180)));
            } else {
                return new SwerveModuleState(stateToOptimize.speedMetersPerSecond, stateToOptimize.angle);
            }
        }

        public void setAngle(double angleToSet) {
            double encoderPosition = steerMotor.getEncoder().getPosition();
            double toFullCircle = Math.IEEEremainder(encoderPosition, 360);
            double newAngle = angleToSet + encoderPosition - toFullCircle;

            if (newAngle - encoderPosition > 180.1) {
                newAngle -= 360;
            } else if (newAngle - encoderPosition < -180.1) {
                newAngle += 360;
            }

            steerMotor.getEncoder().setPosition(newAngle); // runs PID
        }

        public void setState(SwerveModuleState stateToSet) {
            SwerveModuleState optimizedState = optimizeState(stateToSet,
                    new Rotation2d(Math.toRadians(steerMotor.getEncoder().getPosition())));
            // double speed =
            // optimizedState.speedMetersPerSecond*(0.1*DRIVE_TICKS_PER_MOTOR_REV/(DRIVE_DIST_PER_WHEEL_REV));
            // probably not needed

            setAngle(optimizedState.angle.getDegrees());

            driveMotor.set(ControlMode.PercentOutput, optimizedState.speedMetersPerSecond / MAX_WHEEL_SPEED);
        }

    }

    // compact way to stiore PID controller constants
    public static class pidValues {
        public double kP;
        public double kI;
        public double kD;
        public double kF;

        public pidValues(double kP, double kI, double kD) {
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            this.kF = 0;
        }

        public pidValues(double kP, double kI, double kD, double kF) {
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            this.kF = kF;
        }
    }

}
