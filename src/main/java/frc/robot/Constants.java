// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
      //DriveTrain Constants
      public static final int kEncoder1 = 0;
      public static final int kEncoder2 = 1;
      public static final int kEncoder3 = 2;
      public static final int kEncoder4 = 3;
  
      // Elevator Constants
      public static final int kElevatorMotorPort = 5;
      public static final int kElevatorEncoderAChannel = 4;
      public static final int kElevatorEncoderBChannel = 5;
      public static final int kJoystickPort = 0;
  
      public static final double kElevatorKp = 5;
      public static final double kElevatorKi = 0;
      public static final double kElevatorKd = 0;
  
      public static final double kElevatorkS = 0.0; // volts (V)
      public static final double kElevatorkG = 1.762; // volts (V)
      public static final double kElevatorkV = 1.762; // volt per velocity (V/(m/s))
      public static final double kElevatorkA = 0.0; // volt per acceleration (V/(m/s²))
  
      public static final double kElevatorGearing = 2.0;
      public static final double kElevatorDrumRadius = Units.inchesToMeters(2.0);
      public static final double kCarriageMass = 1.0; // kg
  
      public static final double kSetpointMeters = 50;
      // Encoder is reset to measure 0 at the bottom, so minimum height is 0.
      public static final double kMinElevatorHeightMeters = 0.0;
      public static final double kMaxElevatorHeightMeters = 50;
  
      // distance per pulse = (distance per revolution) / (pulses per revolution)
      //  = (Pi * D) / ppr
      public static final double kElevatorEncoderDistPerPulse =
      2.0 * Math.PI * kElevatorDrumRadius / 4096;
      public static final int kArmMotorPort = 6;
      public static final int kArmEncoderAChannel = 6;
      public static final int kArmEncoderBChannel = 7;
  
      public static final String kArmPositionKey = "ArmPosition";
      public static final String kArmPKey = "ArmP";
  
      // The P gain for the PID controller that drives this arm.
      public static final double kDefaultArmKp = 50.0;
      public static final double kDefaultArmSetpointDegrees = 75.0;
  
      // distance per pulse = (angle per revolution) / (pulses per revolution)
      //  = (2 * PI rads) / (4096 pulses)
      public static final double kArmEncoderDistPerPulse = 2.0 * Math.PI / 4096;
  
      public static final double kArmReduction = 200;
      public static final double kArmMass = 8.0; // Kilograms
      public static final double kArmLength = Units.inchesToMeters(30);
      public static final double kMinAngleRads = Units.degreesToRadians(-75);
      public static final double kMaxAngleRads = Units.degreesToRadians(255);
      // Turret Constants
      public static final double kFlyWheelGearing = 3.0;
      public static final double kFlyWheelJKgMetersSquared = 2;
      public static final int kFlyWheelMotorPort = 7;

}
