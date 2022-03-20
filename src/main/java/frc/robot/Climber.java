package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {

    static boolean stopped = false;
    static Timer buttonTimer = new Timer();
    static int type = 0;

    public static void periodic() {
        changeType();
        if(stopped) {
            stopAll();
        } else if(type == 1) {
            pullStatic();
        } else if(type == 2) {
            pullMoving();
        } else if(type == 3) {
            holyShitTheBotIsRotating();
        } else {
            stopAll();
        }

    }

    public static void pullMoving() {
        RobotMap.climberLeft.set(RobotMap.testController.getY());
        RobotMap.climberRight.set(RobotMap.testController.getY());
        RobotMap.climberLarge.set(0);
        RobotMap.climberRotate.set(0);
    }

    public static void pullStatic() {
        RobotMap.climberLarge.set(RobotMap.testController.getY());
        RobotMap.climberLeft.set(0);
        RobotMap.climberRight.set(0);
        RobotMap.climberRotate.set(0);
    }

    public static void holyShitTheBotIsRotating() {
        RobotMap.climberRotate.set(RobotMap.testController.getY());
        RobotMap.climberLeft.set(0);
        RobotMap.climberRight.set(0);
        RobotMap.climberLarge.set(0);
    }

    public static void stopAll() {
        RobotMap.climberLarge.set(0);
        RobotMap.climberLeft.set(0);
        RobotMap.climberRight.set(0);
        RobotMap.climberRotate.set(0);
    }

    public static void changeType() {
        if(RobotMap.two.get()&&type!=3&&buttonTimer.get()>0.2) {
            type++;
        } else if(RobotMap.two.get()&&type==3&&buttonTimer.get()>0.2) {
            type = 0;
        } else if(RobotMap.three.get()&&type!=0&&buttonTimer.get()>0.2) {
            type--;
        } else if(RobotMap.three.get()&&type==0&&buttonTimer.get()>0.2) {
            type = 3;
        }
        SmartDashboard.putNumber("climbingType", type);
        if(RobotMap.one.get()&&buttonTimer.get()>0.2) {
            stopped = true;
        }
    }
    
}
