package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

public class NeoSwerveDef {

    public static AHRS gyro = new AHRS(SPI.Port.kMXP);

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

    public static final double MAX_STEERING_SPEED_PERCENT = 1; //change if willing to change

    public static final double STEER_SENSOR_COEFF_TO_RAD = 2*Math.PI/5; // rads/volt
    public static final double STEER_SENSOR_COEFF_TE_DEG = 360/5; // degs/volt

    public static final double NEO_TICKS_PER_MOTOR_REV = 2048;
    public static final double STEER_FEEDBACK_COEFFICIENT = 0; // think of an efficient way to do this
    public static final double DRIVE_DIST_PER_WHEEL_REV = 0.1; // meters
    public static final double DRIVE_TICKS_PER_MOTOR_REV = NEO_TICKS_PER_MOTOR_REV * 10; // TICKS PER MOTOR REV*DRIVE
                                                                                            // GEAR RATIO
    public static final double MAX_WHEEL_SPEED = 0;
    public static final double MAX_ROBOT_SPEED = 0; //mps
    public static final double WHEEL_BASE_WIDTH = 0;
    public static final double TRACK_WIDTH = 0;

    public static final double SPEED_RATIO = 0.5;

    public static final int TIMEOUT_MS = 20;

    public static SteerMotor flSteer = new SteerMotor(1, FL_STEER_INVERTED);
    public static DriveMotor flDrive = new DriveMotor(1, FL_DRIVE_INVERTED);
    public static SteerSensor flSensor = new SteerSensor(0, FL_STEER_OFFSET);
    public static SwerveModule flModule = new SwerveModule(flSteer, FL_STEER_PID_VALUES, flSensor, flDrive, FL_DRIVE_PID_VALUES,
            FL_STEER_OFFSET);

    public static SteerMotor frSteer = new SteerMotor(3, FR_STEER_INVERTED);
    public static DriveMotor frDrive = new DriveMotor(2, FR_DRIVE_INVERTED);
    public static SteerSensor frSensor = new SteerSensor(1, FR_STEER_OFFSET);
    public static SwerveModule frModule = new SwerveModule(frSteer, FR_STEER_PID_VALUES, frSensor, frDrive, FR_DRIVE_PID_VALUES,
            FR_STEER_OFFSET);

    public static SteerMotor rlSteer = new SteerMotor(2, RL_STEER_INVERTED);
    public static DriveMotor rlDrive = new DriveMotor(3, RL_DRIVE_INVERTED);
    public static SteerSensor rlSensor = new SteerSensor(2, RL_STEER_OFFSET);
    public static SwerveModule rlModule = new SwerveModule(rlSteer, RL_STEER_PID_VALUES, rlSensor, rlDrive, RL_DRIVE_PID_VALUES,
            RL_STEER_OFFSET);

    public static SteerMotor rrSteer = new SteerMotor(4, RR_STEER_INVERTED);
    public static DriveMotor rrDrive = new DriveMotor(4, RR_DRIVE_INVERTED);
    public static SteerSensor rrSensor = new SteerSensor(3, RR_STEER_OFFSET);
    public static SwerveModule rrModule = new SwerveModule(rrSteer, RR_STEER_PID_VALUES, rrSensor, rrDrive, RR_DRIVE_PID_VALUES,
            RR_STEER_OFFSET);

    public static class DriveMotor extends CANSparkMax {
        public boolean inverted;

        public DriveMotor(int id, boolean isInverted) {
            super(id, CANSparkMaxLowLevel.MotorType.kBrushless); // works only with neos
            inverted = isInverted;
        }
    }

    public static class SteerMotor extends VictorSPX {
        public boolean inverted;

        public SteerMotor(int id, boolean isInverted) {
            super(id);
            inverted = isInverted;
        }
    }

    public static class SteerSensor extends AnalogInput {
        public double offset;
        public AnalogInput sensor;
        public SteerSensor(int id, double offset) {
            super(id);
            this.offset = offset;
        }
    }

    // should create a lib for this
    public static class SwerveModule { //Add PID controllers for each module
        public SteerSensor steerSensor;
        public SteerMotor steerMotor;
        public DriveMotor driveMotor;
        public pidValues drivePID, steerPID;
        public double steerOffset;
        public SteerPID steerPIDController;

        public SwerveModule(SteerMotor sMotor, pidValues sPID, SteerSensor sSensor, DriveMotor dMotor, pidValues dPID, double sOffset) {
            steerMotor = sMotor;
            driveMotor = dMotor;
            drivePID = dPID;
            steerPID = sPID;
            steerOffset = sOffset;

            steerPIDController = new SteerPID(steerPID.kP, steerPID.kI, steerPID.kD, MAX_STEERING_SPEED_PERCENT);
        }

        public void moduleInit() {
            //driveMotor.configFactoryDefault();
            //driveMotor.setInverted(driveMotor.inverted);
            //driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT_MS);
            //driveMotor.config_kP(0, drivePID.kP, TIMEOUT_MS);
            //driveMotor.config_kI(0, drivePID.kI, TIMEOUT_MS);
            //driveMotor.config_kD(0, drivePID.kD, TIMEOUT_MS);
            //driveMotor.config_kF(0, drivePID.kF, TIMEOUT_MS);
            //driveMotor.config_IntegralZone(0, 300); // do we need this?

            steerMotor.configFactoryDefault();
            steerMotor.configFeedbackNotContinuous(false, TIMEOUT_MS);
            steerMotor.setInverted(steerMotor.inverted);
            //steerMotor.configSelectedFeedbackCoefficient(1 / STEER_FEEDBACK_COEFFICIENT,
            //0, TIMEOUT_MS);
            //steerMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0,
            //TIMEOUT_MS);
            //steerMotor.config_kP(0, steerPID.kP, TIMEOUT_MS);
            //steerMotor.config_kI(0, steerPID.kI, TIMEOUT_MS);
            //steerMotor.config_kD(0, steerPID.kD, TIMEOUT_MS);
            //steerMotor.config_kF(0, steerPID.kF, TIMEOUT_MS);
            //steerMotor.configAllowableClosedloopError(0, 1, TIMEOUT_MS);
        }

        public void disabledInit() {
            steerMotor.setNeutralMode(NeutralMode.Coast);
            driveMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        }

        public void enabledInit() {
            steerMotor.setNeutralMode(NeutralMode.Brake);
            driveMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }

        public SwerveModuleState getState() {
            return new SwerveModuleState(
                    driveMotor.getEncoder().getVelocity() * DRIVE_DIST_PER_WHEEL_REV
                            / (0.1 * DRIVE_TICKS_PER_MOTOR_REV),
                    new Rotation2d((steerSensor.getVoltage()-steerSensor.offset)*STEER_SENSOR_COEFF_TO_RAD)); //test signs
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
            double encoderPosition = (steerSensor.getVoltage()-steerSensor.offset)*STEER_SENSOR_COEFF_TE_DEG; //in degs, test sign correction
            double toFullCircle = Math.IEEEremainder(encoderPosition, 360);
            double newAngle = angleToSet + encoderPosition - toFullCircle;

            if (newAngle - encoderPosition > 180.1) {
                newAngle -= 360;
            } else if (newAngle - encoderPosition < -180.1) {
                newAngle += 360;
            }

            steerPIDController.setTarget(newAngle);
            steerPIDController.setOffset(encoderPosition);
            steerMotor.set(VictorSPXControlMode.PercentOutput, steerPIDController.pidGet());
        } 

        public void setState(SwerveModuleState stateToSet) {
            SwerveModuleState optimizedState = optimizeState(stateToSet,
                    new Rotation2d((steerSensor.getVoltage()-steerSensor.offset)*STEER_SENSOR_COEFF_TO_RAD)); //test signs
            // double speed =
            // optimizedState.speedMetersPerSecond*(0.1*DRIVE_TICKS_PER_MOTOR_REV/(DRIVE_DIST_PER_WHEEL_REV));
            // probably not needed

            setAngle(optimizedState.angle.getDegrees());

            driveMotor.set(optimizedState.speedMetersPerSecond / MAX_WHEEL_SPEED); //is this mps or percent???
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

    public static class SteerPID extends PIDController {

        private double target, offset, maxSpeed;
        boolean onTarget;
        Timer targeting;
        
        public SteerPID(double kP, double kI, double kD, double mSpeed) {
            super(kP, kI, kD);

            super.setTolerance(1); //degs

            targeting = new Timer();
            targeting.stop();
            targeting.reset();

            onTarget = false;

            maxSpeed = mSpeed;
        }   

        public void setTarget(double newTarget) {
            target = newTarget;
        }

        public void checkTarget() {
            if(Math.abs(super.getPositionError()) <= 1 && targeting.get() == 0) {
                targeting.start();
                onTarget = false;
            } else if(Math.abs(super.getPositionError()) > 1 && targeting.get() < 0.5) {
                targeting.reset();
                onTarget = false;
            } else if(Math.abs(super.getPositionError()) <= 1 && targeting.get() < 0.5) {
                onTarget = false;
            } else if(Math.abs(super.getPositionError()) <= 1 && targeting.get() >= 0.5) {
                onTarget = true;
            }
        }

        public void setOffset(double position) {
            offset = position;
        }

        public double pidGet() {
            checkTarget();
            double speed = MathUtil.clamp(super.calculate(offset, target), -maxSpeed, maxSpeed);
            return speed;
        }
    }

}
