// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static CommandBase exampleAuto(Drivetrain drivetrain, Timer timer ) {
    timer.restart();

    return new SequentialCommandGroup(
      new InstantCommand(() -> drivetrain.drive(AutoConstants.speed, AutoConstants.rotation), drivetrain),
      new WaitCommand(AutoConstants.waitTimeSeconds),
      new RunCommand(() -> drivetrain.fastStop(), drivetrain)
    );
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
