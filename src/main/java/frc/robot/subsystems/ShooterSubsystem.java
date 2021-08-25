

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    private TalonFX upperShooter;
    private TalonFX lowerShooter;
    private TalonFX ShooterAngle;

    public ShooterSubsystem() {
        upperShooter = new TalonFX(51);
        lowerShooter = new TalonFX(52);
        ShooterAngle = new TalonFX(50);
    }

    public void moveUpperShoot(double speed) {
        upperShooter.set(ControlMode.PercentOutput, speed);
    }

    public void moveLowerShooter(double speed) {
        lowerShooter.set(ControlMode.PercentOutput, speed);
    }

    public void moveShooterAngle(double speed) {
        ShooterAngle.set(ControlMode.PercentOutput, speed);
    }

}
