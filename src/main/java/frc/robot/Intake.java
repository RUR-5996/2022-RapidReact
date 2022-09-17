package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake {

    static boolean intakeOut = false;

    public static void init() {
        SystemDef.intake.configFactoryDefault();
        SystemDef.intake.setInverted(false);
        SystemDef.intake.configOpenloopRamp(0.8);
        SystemDef.intake.configNominalOutputForward(0, 20);
        SystemDef.intake.configNominalOutputReverse(0, 20);
        SystemDef.intake.configPeakOutputForward(1, 20);
        SystemDef.intake.configPeakOutputReverse(-1, 20);
        SystemDef.intake.configAllowableClosedloopError(0, 0, 20);
        SystemDef.intake.configNeutralDeadband(0.05, 20);
        SystemDef.intake.setNeutralMode(NeutralMode.Coast);
    }

    public static void periodic() {
        if (SystemDef.controller.getLeftTriggerAxis() > 0.75) {
            intake(0.32);
        } else if (SystemDef.controller.getRightTriggerAxis() > 0.75) {
            intake(-0.3);
        } else {
            intake(0);
        }

        // Pneumatics
        /*
         * if (SystemDef.logitechSix.get()||SystemDef.controller.getYbuttonPressed()) {
         * //xbox is the way to go
         * if (intakeOut) {
         * intakeOut = false;
         * inOut(Value.kForward); // check
         * } else if (!intakeOut) {
         * intakeOut = true;
         * inOut(Value.kReverse); // add delay between presses
         * }
         * }
         */
    }

    static void intake(double direction) {
        SystemDef.intake.set(1 * direction);
    }

    static void inOut(Value direction) {
        // SystemDef.intakeSolenoid.set(direction);
    }
}
