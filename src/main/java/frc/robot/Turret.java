package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Sensors.LimelightMode;

public class Turret {

    static double feedSleepTimer = 1.5; // varies from 1.5 to 3 seconds
    static double shooterSpeed = 0.9; // can be overriden from Shuffleboard

    static boolean hoodDown = false; // not in use RN
    static boolean hoodUp = false; // not in use RN, should be detected by a limit switch
    static boolean hoodSafe = true; // not in use RN, needs reworking
    static final double turretSafeVoltage = 1; // not in use RN, might get deprecated and replaced by buttons
    static Timer safeHoodMove; // timer that should be deprecated once buttons are implemented

    static boolean turretLeft = false; // is defined by encoder position, but drifts alot (replace by a button)
    static boolean turretRight = false; // is defined by encoder position, but drifts alot (replace by a button)
    static boolean turretSafe = true; // defined by variables above but can get deprecated soon
    static final double hoodSafeVoltage = 1;// will get deprecated once functions using this are
    static Timer safeTurretMove; // timer that should be deprecated

    static Timer feedSleep; // important timer that defines the time for the flywheel to get up to speed

    // auto stuff
    static enum TurretTurnMode { // enums defining the current manipulation mode of the turret rotation
        MANUAL, // self explanatory
        LIMELIGHT, // the turret is always looking for vision target and tracking it
        ENCODER; // drifting alot, kind of useless for any use, might get deprecated
    }

    static PIDController turretLLController = new PIDController(0.008, 0, 0); // PID controller that defines the motion
                                                                              // when tracking vision targets
    static PIDController turretController = new PIDController(0.0025, 0, 0); // PID controller that defines the motion
                                                                             // when using encoder to get to specific
                                                                             // position
    static double autoTurretSpeed = 0; // this value gets overriden by various PID controllers
    static final double MAX_TURRET_TICKS_RIGHT = 50000; // has to be recalibrated a lot, should't be relied on
    static final double MAX_TURRET_TICKS_LEFT = -50000; // drifts alot
    static boolean turretLLOnTarget = false; // visual aid to see, if the turret is mostly aligned with the vision
                                             // target
    static boolean turretOnTarget = false; // visual aid to see, if the turret is mostly aligned with the vision target
    static TurretTurnMode turnMode = TurretTurnMode.MANUAL; // the robot starts in manual control of the turret

    /**
     * The initializing function for Turret
     * Sets up timers
     * Defines behavior of motor controller
     * sets tolerances for PID controllers
     * Should be placed in Robot's robotInit() function
     */
    public static void init() {
        feedSleep = new Timer();
        feedSleep.reset();
        feedSleep.stop();

        safeHoodMove = new Timer();
        safeHoodMove.reset();
        safeHoodMove.stop();

        safeTurretMove = new Timer();
        safeTurretMove.reset();
        safeTurretMove.stop();

        SystemDef.shooter.configFactoryDefault();
        SystemDef.shooter.setInverted(false);
        SystemDef.shooter.configOpenloopRamp(0.8);
        SystemDef.shooter.configNominalOutputForward(0, 20);
        SystemDef.shooter.configNominalOutputReverse(0, 20);
        SystemDef.shooter.configPeakOutputForward(1, 20);
        SystemDef.shooter.configPeakOutputReverse(-1, 20);
        SystemDef.shooter.configAllowableClosedloopError(0, 0, 20);
        SystemDef.shooter.configNeutralDeadband(0.05, 20);
        SystemDef.shooter.setNeutralMode(NeutralMode.Coast);

        SystemDef.hoodMotor.configFactoryDefault();
        SystemDef.hoodMotor.configNominalOutputForward(0, 20);
        SystemDef.hoodMotor.configNominalOutputReverse(0, 20);
        SystemDef.hoodMotor.configPeakOutputForward(1, 20);
        SystemDef.hoodMotor.configPeakOutputReverse(-1, 20);
        SystemDef.hoodMotor.configAllowableClosedloopError(0, 0, 20);
        SystemDef.hoodMotor.configNeutralDeadband(0.05, 20);
        SystemDef.hoodMotor.setNeutralMode(NeutralMode.Brake);

        SystemDef.turretMover.configFactoryDefault();
        SystemDef.turretMover.configNominalOutputForward(0, 20);
        SystemDef.turretMover.configNominalOutputReverse(0, 20);
        SystemDef.turretMover.configPeakOutputForward(1, 20);
        SystemDef.turretMover.configPeakOutputReverse(-1, 20);
        SystemDef.turretMover.configAllowableClosedloopError(0, 0, 20);
        SystemDef.turretMover.configNeutralDeadband(0.05, 20);
        SystemDef.turretMover.setNeutralMode(NeutralMode.Brake);

        SystemDef.feeder.configFactoryDefault();
        SystemDef.feeder.setInverted(true);
        SystemDef.feeder.configNominalOutputForward(0, 20);
        SystemDef.feeder.configNominalOutputReverse(0, 20);
        SystemDef.feeder.configPeakOutputForward(1, 20);
        SystemDef.feeder.configPeakOutputReverse(-1, 20);
        SystemDef.feeder.configAllowableClosedloopError(0, 0, 20);
        SystemDef.feeder.configNeutralDeadband(0.05, 20);
        SystemDef.feeder.setNeutralMode(NeutralMode.Coast);

        turretLLController.setTolerance(0.2);
        turretController.setTolerance(10);

        // Shuffleboard.getTab("SmartDashboard").add("shooterSpeed", 0.9); // TODO test
        // if this breaks the robot
    }

    /**
     * The function that holds the decision tree of the shooter logic
     * includes the control of feeder
     */
    public static void periodic() {

        // this changes the control mode of turret turning
        if (SystemDef.logitechTen.get()) {
            turnMode = TurretTurnMode.LIMELIGHT;
            Sensors.pipeline.setNumber(0);
            Sensors.currentMode = Sensors.LimelightMode.PROCESSING;
        } else if (SystemDef.logitechEleven.get()) {
            turnMode = TurretTurnMode.ENCODER;
            Sensors.pipeline.setNumber(1);
            Sensors.currentMode = Sensors.LimelightMode.DRIVING;
        } /*
           * else if ((SystemDef.getPressed(SystemDef.logitechTen) ||
           * SystemDef.getPressed(SystemDef.logitechEleven))
           * && !turnMode.equals(TurretTurnMode.MANUAL)) {
           */
        else if (SystemDef.logitechNine.get()) {
            turnMode = TurretTurnMode.MANUAL;
            Sensors.pipeline.setNumber(1);
            Sensors.currentMode = Sensors.LimelightMode.DRIVING;
        } // hope this works
        else if (SystemDef.controller.getXButtonPressed() && turnMode.equals(TurretTurnMode.MANUAL)) {

            turnMode = TurretTurnMode.LIMELIGHT;
            Sensors.pipeline.setNumber(0);
            Sensors.currentMode = Sensors.LimelightMode.PROCESSING;

        } else if (SystemDef.controller.getYButtonPressed()) {
            turnMode = TurretTurnMode.MANUAL;
            Sensors.pipeline.setNumber(1);
            Sensors.currentMode = Sensors.LimelightMode.DRIVING;
        }

        // controls the turret based on the control mode applied
        switch (turnMode) {
            case MANUAL:
                if ((SystemDef.logitechFour.get() || SystemDef.controller.getPOV() == 270) && !turretLeft) {
                    rotate(-1);
                    // turretProtection(-1);
                } else if ((SystemDef.logitechFive.get() || SystemDef.controller.getPOV() == 90) && !turretRight) {
                    rotate(1);
                    // turretProtection(1);
                } else {
                    rotate(0);
                }
                break;

            case LIMELIGHT:
                limeLightTurn();
                break;

            case ENCODER:
                homeTurret();
                break;
        }

        // The shooter+feeder characteristics
        if (SystemDef.logitechTrigger.get() || SystemDef.controller.getLeftBumper()) {
            if (feedSleep.get() == 0) {
                feedSleep.start();
                shoot(1);
            } else if (feedSleep.get() >= feedSleepTimer) {
                shoot(1);
                feed(1);
            }
        } else if (SystemDef.logitechSeven.get()) {
            shoot(-1);
            feed(-1);
        } else {
            shoot(0);
            feed(0);
            if (feedSleep.get() > 0) {
                feedSleep.reset();
                feedSleep.stop();
            }
        }

        // control loop of the hood mechanism
        if (SystemDef.logitechThree.get() || SystemDef.controller.getPOV() == 0) {
            hoodAdjust(-1);
            // hoodProtection(-1);
        } else if (SystemDef.logitechTwo.get() || SystemDef.controller.getPOV() == 180) {
            hoodAdjust(1);
            // hoodProtection(1);
        } else {
            hoodAdjust(0);
        }

        // resetHoodProtection(); //mostly deprecated, shold be replaced by setting the
        // limits with buttons
        // resetTurretProtection(); // if works, implement into logical tree (if left,
        // cant go left etc.)

        clampFeedSleep(); // sets the delay on feeder based on the position of z-axis on logitech
                          // controller
        hardTurretStop(); // checks, if the turret has reached theoretical limits - doesn't really work,
                          // should use buttons, not encoder

    }

    /**
     * Sets the speed of the shooter based on direction
     * 
     * @param direction 1 to shoot, -1 to reverse, 0 to stop the motor
     */
    static void shoot(double direction) {
        SystemDef.shooter.set(0.6 * direction); // Falcon with 1:1 gearing
    }

    /**
     * Rotates the whole turret based on direction
     * 
     * @param direction 1 to turn right, -1 to turn left, 0 to stop
     */
    static void rotate(double direction) {
        SystemDef.turretMover.set(0.15 * direction); // Redline with a 64:1 gearbox
    }

    /**
     * Sets the hood speed based on direction
     * 
     * @param direction 1 to move up, -1 to move down, 0 to stop
     */
    static void hoodAdjust(double direction) {
        SystemDef.hoodMotor.set(0.2 * direction); // Redline with a 64:1 gearbox
    }

    /**
     * Sets the feeder speed based on direction
     * 
     * @param direction 1 to feed in, -1 to feed out, 0 to stop
     */
    static void feed(double direction) {
        SystemDef.feeder.set(0.5 * direction); // Redline with a 20:1 gearbox
    }

    /**
     * Sends the data into networkTables
     * Should be placed in Robot's robotPeriodic() function
     */
    static void report() {
        SmartDashboard.putNumber("turretMover voltage", SystemDef.turretMover.getMotorOutputVoltage());
        SmartDashboard.putNumber("hoodMotor voltage", SystemDef.hoodMotor.getMotorOutputVoltage());
        SmartDashboard.putBoolean("turretSafe", turretSafe);
        SmartDashboard.putBoolean("turretLeft", turretLeft);
        SmartDashboard.putBoolean("turretRight", turretRight);
        SmartDashboard.putBoolean("hoodSafe", hoodSafe);
        SmartDashboard.putBoolean("hoodUp", hoodUp);
        SmartDashboard.putBoolean("hoodDown", hoodDown);
        SmartDashboard.putNumber("feeder Delay", feedSleepTimer);

        SmartDashboard.putNumber("autoTurretSpeed", autoTurretSpeed);
        SmartDashboard.putBoolean("turretllOnTarget", turretLLOnTarget);
        SmartDashboard.putBoolean("turretOnTarget", turretOnTarget);
        SmartDashboard.putString("turretMode", turnMode.toString());

        shooterSpeed = SmartDashboard.getNumber("shooterSpeed", 0.9);
    }

    /**
     * Protects the hood against stalling based on voltage readings
     * 
     * @deprecated
     * 
     * @param direction checks the direction the hood is moving in and decides, in
     *                  which limit position the hood is
     */
    static void hoodProtection(int direction) {
        if (SystemDef.hoodMotor.getMotorOutputVoltage() > hoodSafeVoltage) {
            hoodSafe = false;
            if (direction < 0) {
                hoodDown = true;
                hoodUp = false;
            }
            if (direction > 0) {
                hoodUp = true;
                hoodDown = false;
            }
        } else {
            hoodSafe = true;
        }
    }

    /**
     * Checks, if the voltage on hood motor is safe to operate and resets the
     * variables that disable the hood from moving in one direction
     * 
     * @deprecated
     */
    static void resetHoodProtection() {
        if (SystemDef.hoodMotor.getMotorOutputVoltage() > 0 && (hoodDown || hoodUp) && safeHoodMove.get() == 0) {
            safeHoodMove.start();
        } else if (SystemDef.hoodMotor.getMotorOutputVoltage() > 0 && (hoodDown || hoodUp)
                && safeHoodMove.get() > 0.5) {
            hoodUp = false;
            hoodDown = false;
            hoodSafe = true;
            safeHoodMove.reset();
            safeHoodMove.stop();
        } else {

        }
    }

    /**
     * Checks the physical limits of the turret rotation by reading the voltage on
     * the turret rotating motor
     * 
     * @deprecated
     *             ,
     * @param direction the direction, in which is the turret moving
     */
    static void turretProtection(int direction) {
        if (SystemDef.turretMover.getMotorOutputVoltage() > turretSafeVoltage) {
            turretSafe = false;
            if (direction < 0) {
                turretLeft = true;
                turretRight = false;
            }
            if (direction > 0) {
                turretRight = true;
                turretLeft = false;
            }
        } else {
            turretSafe = true;
        }
    }

    /**
     * Checks, if the voltage on turret mover motor is safe to operate and resets
     * the variables that disable the turret from moving in one direction
     * 
     * @deprecated
     */
    static void resetTurretProtection() {
        if (SystemDef.turretMover.getMotorOutputVoltage() > 0 && (turretLeft || turretRight)
                && safeTurretMove.get() == 0) {
            safeTurretMove.start();
        } else if (SystemDef.turretMover.getMotorOutputVoltage() > 0 && (turretLeft || turretRight)
                && safeTurretMove.get() > 0.5) {
            turretLeft = false;
            turretRight = false;
            turretSafe = true;
            safeTurretMove.reset();
            safeTurretMove.stop();
        } else {

        }
    }

    /**
     * Transforms the z-axis input into values between 1.5 and 3
     */
    static void clampFeedSleep() {
        feedSleepTimer = -SystemDef.logitech.getZ() + 2;
    }

    /**
     * Searches for vision targets and tries to remain in line with the center of
     * the target
     */
    static void limeLightTurn() {
        autoTurretSpeed = MathUtil.clamp(turretLLController.calculate(Sensors.x, 0), -0.35, 0.35);
        turretLLOnTarget = turretLLController.atSetpoint();

        if ((autoTurretSpeed < 0 && turretLeft) || (autoTurretSpeed > 0 && turretRight)) { // might need to reverse
                                                                                           // dirctions
            SystemDef.turretMover.set(0);
        } else {
            SystemDef.turretMover.set(-autoTurretSpeed);
        }
    }

    /**
     * Checks, if the turret has broken the hard-coded limits on rotation and from
     * whitch direction.
     */
    static void hardTurretStop() {
        if (Sensors.turretTicks >= MAX_TURRET_TICKS_RIGHT) {
            turretRight = true;
            turretLeft = false;
        } else if (Sensors.turretTicks <= MAX_TURRET_TICKS_LEFT) {
            turretLeft = true;
            turretRight = false;
        } else {
            turretLeft = false;
            turretRight = false;
        }
    }

    /**
     * Changes the Limelight settings based on selected turret operation mode
     * 
     * @deprecated
     */
    static void changeTurretMode() {
        switch (turnMode) {
            case MANUAL:
                Sensors.pipeline.setNumber(1); // processing cam
                Sensors.currentMode = Sensors.LimelightMode.DRIVING;
                break;
            case LIMELIGHT:
                Sensors.pipeline.setNumber(0); // driving cam
                Sensors.currentMode = Sensors.LimelightMode.PROCESSING;
                break;
            case ENCODER:
                Sensors.pipeline.setNumber(0);
            default:
                turnMode = TurretTurnMode.MANUAL;
                break;
        }
    }

    /**
     * Rotates the turret back to the origin point
     */
    static void homeTurret() {
        autoTurretSpeed = MathUtil.clamp(turretController.calculate(Sensors.turretTicks, 0), -0.15, 0.15);
        turretOnTarget = turretController.atSetpoint();
        if ((autoTurretSpeed < 0 && turretLeft) || (autoTurretSpeed > 0 && turretRight)) { // might need to reverse
                                                                                           // dirctions
            SystemDef.turretMover.set(0);
        } else {
            SystemDef.turretMover.set(autoTurretSpeed);
        }
    }
}