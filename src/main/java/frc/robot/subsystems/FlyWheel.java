package frc.robot.subsystems;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FlyWheel extends SubsystemBase {
    // Create Sim Motor
    private final DCMotor m_flyWheelMotor = DCMotor.getVex775Pro(1);
    // Create Simbase
    FlywheelSim m_simbase = new FlywheelSim(m_flyWheelMotor, Constants.kFlyWheelGearing,
            Constants.kFlyWheelJKgMetersSquared);
    // Make Motor
    private final PWMSparkMax m_motor = new PWMSparkMax(Constants.kFlyWheelMotorPort);
    private double m_motorSpeed = 0.0;

    /** Update the simulation model. */
    public void simulationPeriodic() {
        // In this method, we update our simulation of what our arm is doing
        // we set our "inputs" (voltages)
        m_simbase.setInput(m_motor.get() * RobotController.getBatteryVoltage());
        // Next, we update it. The standard loop time is 20ms.
        m_simbase.update(0.020);
        // SimBattery estimates loaded battery voltages
        RoboRioSim.setVInVoltage(
                BatterySim.calculateDefaultBatteryLoadedVoltage(m_simbase.getCurrentDrawAmps()));
    }
    /** Set FlyWheel to double Speed. */
    public void setSpeed(double Speed) {
        m_motorSpeed = (Math.sqrt(Speed) * m_motorSpeed) + 0.1 * (RoboRioSim.getVInVoltage() / 50);
        m_motor.set(m_motorSpeed);
    }
    /** Stop the control loop and motor output. */
    public void stop() {
        setSpeed(0.0);
    }

    // Code that runs every 2ms
    public void periodic() {
        SmartDashboard.putNumber("FlyWheel Rpm", m_motor.get());
    }


}
