package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Lednicev2 {

    static Timer timer = new Timer();
    static boolean tykadlaOut = false;
    static int ballsIn = 0;
    static boolean ballTop = false;
    static boolean ballBottom = false;
    static boolean shooting = false;
    static boolean shooterReady = false;
    static boolean shootingHigh = false;
    static boolean passingBall = false;

    public static void periodic() {
        tykadla();
        countBalls();
        intake();
        setShooting();
        shoot();
    }

    public static void tykadla() {
        if(RobotMap.controller.getPOV() == 90) {
            tykadlaOut = true;
        } else if(RobotMap.controller.getPOV() == 270) {
            tykadlaOut = false;
        }
        if(tykadlaOut) {
            RobotMap.leftIntake.set(Value.kForward);
            RobotMap.rightIntake.set(Value.kForward);
        } else {
            RobotMap.leftIntake.set(Value.kReverse);
            RobotMap.rightIntake.set(Value.kReverse);
        }
    }
    public static void countBalls() {
        if(RobotMap.colorSensor.getProximity() > 1500) {
            ballTop = true;
        }
        if(RobotMap.ballButton.get()) {
            ballBottom = true;
        }

        if(ballTop&&ballBottom) {
            ballsIn = 2;
        } else if(ballTop&&!ballBottom || ballBottom&&!ballTop) {
            ballsIn = 1;
        } else {
            ballsIn = 0;
        }
    }

    public static void intake() {
        if(ballsIn == 0) {
            if(RobotMap.controller.getRightTriggerAxis()>0) {
                RobotMap.intake.set(Constants.INTAKE_CONSTANT);
                RobotMap.shooterBottom.set(Constants.INTAKE_CONSTANT);
            }
        }
        if(ballsIn == 1 && !ballTop && !shooting) {
            passingBall=true;
        }
        if(passingBall&&!ballTop && !shooting) {
            RobotMap.intake.set(Constants.INTAKE_CONSTANT);
            RobotMap.shooterBottom.set(Constants.INTAKE_CONSTANT); //test this speed
        }
        if(ballsIn == 1 && ballTop && !shooting) {
            passingBall = false;
            if(RobotMap.controller.getRightTriggerAxis()>0) {
                RobotMap.intake.set(Constants.INTAKE_CONSTANT);
                RobotMap.shooterBottom.set(0);
            }
        } else {
            
        }
    }

    public static void setShooting() {
        if(RobotMap.controller.getXButtonPressed()) {
            shooting = !shooting;
            timer.reset();
        }
        if(shooting&&!shooterReady) {
            RobotMap.intake.set(-Constants.INTAKE_CONSTANT);
            RobotMap.shooterBottom.set(-Constants.INTAKE_CONSTANT);
        }
        if(shooting&&!ballTop&&timer.get() > 0.5) { //test the timing
            shooterReady = true;
            timer.reset();
        }

    }

    public static void shoot() {
        if(shooting&&shooterReady&&shootingHigh) {
            RobotMap.shooterTop.set(Constants.HIGH_SHOOTING_CONSTANT);
            RobotMap.shooterBottom.set(Constants.HIGH_SHOOTING_CONSTANT);
            if(timer.get() > 0.5) {
                RobotMap.intake.set(Constants.INTAKE_CONSTANT);
            } else {
                RobotMap.intake.set(0);
            }
        }else if(shooting&&shooterReady&&!shootingHigh) {
            RobotMap.shooterTop.set(Constants.LOW_SHOOTING_CONSTANT);
            RobotMap.shooterBottom.set(Constants.LOW_SHOOTING_CONSTANT);
            if(timer.get() > 0.5) {
                RobotMap.intake.set(Constants.INTAKE_CONSTANT);
            } else {
                RobotMap.intake.set(0);
            }
        } else {
            RobotMap.shooterTop.set(0);
        }
    }

    public static void setShootingPosition() {
        if(RobotMap.controller.getPOV() == 0) {
            shootingHigh = true;
        } else if(RobotMap.controller.getPOV() == 180) {
            shootingHigh = false;
        }
    }
    
}
