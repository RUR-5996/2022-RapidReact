package frc.robot;

public class Shooting {
    public static double getSpeed(double distance) {
        return Math.sqrt(59.56 * Math.pow(distance, 2) * (1.428 * distance - 2.12))
                / (2.456 * distance - 4.24);
    }
}
