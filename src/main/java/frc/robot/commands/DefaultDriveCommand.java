package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DefaultDriveCommand extends CommandBase {
    private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);
    Drivetrain m_drivetrain;
    Supplier<Double> m_leftJoyX, m_leftJoyY;

    // Set up a command for joystick driving
    public DefaultDriveCommand(Drivetrain drivetrain, Supplier<Double> leftJoyX, Supplier<Double> leftJoyY) {
        m_drivetrain = drivetrain;
        m_leftJoyX = leftJoyX;
        m_leftJoyY = leftJoyY;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Get the x speed. We are inverting this because Xbox controllers return
        // negative values when we push forward.
        double xSpeed = -m_speedLimiter.calculate(m_leftJoyY.get()) * Drivetrain.kMaxSpeed;

        // Get the rate of angular rotation. We are inverting this because we want a
        // positive value when we pull to the left (remember, CCW is positive in
        // mathematics). Xbox controllers return positive values when you pull to
        // the right by default.
        double rotaion = -m_rotLimiter.calculate(m_leftJoyX.get()) * Drivetrain.kMaxAngularSpeed;
        m_drivetrain.drive(xSpeed, rotaion);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drivetrain.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
