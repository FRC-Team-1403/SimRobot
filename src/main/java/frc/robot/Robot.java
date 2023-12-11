package frc.robot;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

import java.util.List;

public class Robot extends TimedRobot {
  private final XboxController m_keyboard = new XboxController(0);

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0
  // to 1.
  private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);
  //declaring all of the subsystems
  private final Drivetrain m_drive = new Drivetrain();
  private final Elevator m_elevator = new Elevator();
  private final Arm m_arm = new Arm();
  private final RamseteController m_ramsete = new RamseteController();
  private final Timer m_timer = new Timer();
  //Auto Stuff
  private Trajectory m_trajectory;

  @Override
  public void robotInit() {
    m_trajectory =
        TrajectoryGenerator.generateTrajectory(
            new Pose2d(2, 2, new Rotation2d()),
            List.of(),
            new Pose2d(6, 4, new Rotation2d()),
            new TrajectoryConfig(2, 2));
  }

  @Override
  public void robotPeriodic() {
    // Update the telemetry, including mechanism visualization, regardless of mode.
    m_elevator.updateTelemetry();
    
    m_drive.periodic();
  }

  @Override
  public void autonomousInit() {
    m_timer.restart();
    m_drive.resetOdometry(m_trajectory.getInitialPose());
  }

  @Override
  public void autonomousPeriodic() {
    double elapsed = m_timer.get();
    Trajectory.State reference = m_trajectory.sample(elapsed);
    ChassisSpeeds speeds = m_ramsete.calculate(m_drive.getPose(), reference);
    m_drive.drive(speeds.vxMetersPerSecond, speeds.omegaRadiansPerSecond);
  }
  @Override
  public void teleopInit() {
    m_arm.loadPreferences();
  }
  @Override
  public void teleopPeriodic() {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    double xSpeed = -m_speedLimiter.calculate(m_keyboard.getLeftY()) * Drivetrain.kMaxSpeed;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    double rotaion = -m_rotLimiter.calculate(m_keyboard.getLeftX()) * Drivetrain.kMaxAngularSpeed;
    m_drive.drive(xSpeed, rotaion);
    if (m_keyboard.getAButton()) {
      // Here, we set the constant setpoint of 0.75 meters.
      m_elevator.reachGoal(Constants.kSetpointMeters);
    } else {
      // Otherwise, we update the setpoint to 0.
      m_elevator.reachGoal(0.0);
    }
    if (m_keyboard.getBButton()) {
      // Here, we run PID control like normal.
      m_arm.reachSetpoint();
    } else {
      // Otherwise, we disable the motor.
      m_arm.stop();
    }
  }

  @Override
  public void simulationPeriodic() {
  // Update the simulation model.
    m_drive.simulationPeriodic();
    m_arm.simulationPeriodic();
    m_elevator.simulationPeriodic();
  }

  @Override
  public void disabledInit() {
    // This just makes sure that our simulation code knows that the motor's off.
    m_elevator.stop();
    m_arm.stop();
  }

  @Override
  public void close() {
    m_elevator.close();
    m_arm.close();
    super.close();
  }
}
