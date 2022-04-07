package frc.robot;

import frc.robot.autonomous.PathWeaver;
import frc.robot.autonomous.Simple;

public class Autonomous {
    enum Mode {
        SIMPLE,
        PATHWEAVER
    }

    static Mode mode = Mode.SIMPLE;

    public static void init() {
        switch (mode) {
            case PATHWEAVER:
                PathWeaver.init();
                break;
            default:
                break;
        }

    }

    public static void periodic() {
        switch (mode) {
            case SIMPLE:
                Simple.periodic();
                break;
            case PATHWEAVER:
                PathWeaver.periodic();
                break;
        }
    }
}
