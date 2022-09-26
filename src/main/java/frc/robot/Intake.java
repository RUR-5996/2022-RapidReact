package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake {

    static boolean intakeOut = false; // self explanatory
    static final double INTAKE_SPEED = 0.75; // change if needed (this is for NEO with 4:1 gearbox)

    public static void init() {
        /*
         * falcon settings (probably deprecated forever)
         * SystemDef.intake.configFactoryDefault();
         * SystemDef.intake.setInverted(false);
         * SystemDef.intake.configOpenloopRamp(0.8);
         * SystemDef.intake.configNominalOutputForward(0, 20);
         * SystemDef.intake.configNominalOutputReverse(0, 20);
         * SystemDef.intake.configPeakOutputForward(1, 20);
         * SystemDef.intake.configPeakOutputReverse(-1, 20);
         * SystemDef.intake.configAllowableClosedloopError(0, 0, 20);
         * SystemDef.intake.configNeutralDeadband(0.05, 20);
         * SystemDef.intake.setNeutralMode(NeutralMode.Coast);
         */

        // neo settings
        SystemDef.intakeSpark.setIdleMode(IdleMode.kCoast);
        SystemDef.intakeSpark.setOpenLoopRampRate(0.6);
        SystemDef.intakeSpark.setInverted(false);
    }

    /**
     * Function with the decision tree
     * Should be placed in Robot's ...periodic()
     */
    public static void periodic() {
        if (SystemDef.controller.getLeftTriggerAxis() > 0.75) {
            intake(1);
        } else if (SystemDef.controller.getRightTriggerAxis() > 0.75) {
            intake(-1);
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

    /**
     * Function to spin the intake wheels
     * 
     * @param direction 1 to intake, -1 to outtake, 0 to stop
     */
    static void intake(double direction) {
        SystemDef.intakeSpark.set(INTAKE_SPEED * direction);
    }

    /**
     * Function to extend/retract the whole intake
     * 
     * @param direction kForward to extend, kReverse to retract
     */
    static void inOut(Value direction) {
        // SystemDef.intakeSolenoid.set(direction);
    }
}
