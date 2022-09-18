package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret {

    static double feedSleepTimer = 1.5;
    static double shooterSpeed = 0.9;

    static boolean hoodDown = false;
    static boolean hoodUp = false;
    static boolean hoodSafe = true;
    static final double turretSafeVoltage = 1;
    static Timer safeHoodMove;

    static boolean turretLeft = false;
    static boolean turretRight = false;
    static boolean turretSafe = true;
    static final double hoodSafeVoltage = 1;// placeholder
    static Timer safeTurretMove;

    static Timer feedSleep;

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
    }

    public static void periodic() {

        // shooting+reverse+feeder
        if (SystemDef.logitechTrigger.get()) {
            if (feedSleep.get() == 0) {
                feedSleep.start();
                shoot(shooterSpeed);
            } else if (feedSleep.get() >= feedSleepTimer) {
                shoot(shooterSpeed);
                feed(1);
            }
        } else if (SystemDef.logitechSeven.get()) {
            shoot(-0.9);
            feed(-0.75);
        } else {
            shoot(0);
            feed(0);
            if (feedSleep.get() > 0) {
                feedSleep.reset();
                feedSleep.stop();
            }
        }

        // rotating
        if (SystemDef.logitechFour.get()) {
            rotate(-1);
            turretProtection(-1);
        } else if (SystemDef.logitechFive.get()) {
            rotate(1);
            turretProtection(1);
        } else {
            rotate(0.31);
        }

        // hood
        if (SystemDef.logitechThree.get()) {
            hoodAdjust(-1);
            hoodProtection(-1);
        } else if (SystemDef.logitechTwo.get()) {
            hoodAdjust(1);
            hoodProtection(1);
        } else {
            hoodAdjust(0);
        }

        clampFeedSleep();
        resetHoodProtection();
        resetTurretProtection(); // if works, implement into logical tree (if left, cant go left etc.)

        report();

    }

    static void shoot(double ratio) {
        SystemDef.shooter.set(1 * ratio);
    }

    static void rotate(double direction) {
        SystemDef.turretMover.set(0.15 * direction);
    }

    static void hoodAdjust(double direction) {
        SystemDef.hoodMotor.set(0.2 * direction);
    }

    static void feed(double direction) {
        SystemDef.feeder.set(0.5 * direction);
    }

    static void report() {
        SmartDashboard.putNumber("turretMover voltage", SystemDef.turretMover.getMotorOutputVoltage());
        SmartDashboard.putNumber("hoodMotor voltage", SystemDef.hoodMotor.getMotorOutputVoltage());
        SmartDashboard.putBoolean("turretSafe", turretSafe);
        SmartDashboard.putBoolean("turretLeft", turretLeft);
        SmartDashboard.putBoolean("turretRight", turretRight);
        SmartDashboard.putBoolean("hoodSafe", hoodSafe);
        SmartDashboard.putBoolean("hoodUp", hoodUp);
        SmartDashboard.putBoolean("hoodDown", hoodDown);
        // SmartDashboard.putNumber("shooterSpeed", shooterSpeed);
        shooterSpeed = SmartDashboard.getNumber("shooterSpeed", 0.9);
        SmartDashboard.putNumber("feeder Delay", feedSleepTimer);
    }

    // experimental
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

    static void clampFeedSleep() {
        feedSleepTimer = -SystemDef.logitech.getZ() + 2;
    }
}