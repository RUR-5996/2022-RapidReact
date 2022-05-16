// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  static NeoSwerveDrive SWERVE;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    NeoSwerveDef.flModule.moduleInit();
    NeoSwerveDef.frModule.moduleInit();
    NeoSwerveDef.rlModule.moduleInit();
    NeoSwerveDef.rrModule.moduleInit();

    SWERVE = NeoSwerveDrive.getInstance();

    SWERVE.init();
  }

  @Override
  public void robotPeriodic() {
    Tests.periodic();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    NeoSwerveDef.flModule.enabledInit();
    NeoSwerveDef.frModule.enabledInit();
    NeoSwerveDef.rlModule.enabledInit();
    NeoSwerveDef.rrModule.enabledInit();
  }

  @Override
  public void teleopPeriodic() {
    NeoSwerveDrive.periodic(); //probably going to explode
  }

  @Override
  public void disabledInit() {
    NeoSwerveDef.flModule.disabledInit();
    NeoSwerveDef.frModule.disabledInit();
    NeoSwerveDef.rlModule.disabledInit();
    NeoSwerveDef.rrModule.disabledInit();
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
