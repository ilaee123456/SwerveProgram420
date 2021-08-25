package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

import frc.robot.pidcontroller;

public class WorseSwerveWheel {
    private TalonFX angleMotor;
    private TalonFX speedMotor;
    private TalonSRX encoderTalon;
    private pidcontroller pidController;

    public WorseSwerveWheel(int angleMotor, int speedMotor, int encoder) {
        this.angleMotor = new TalonFX(angleMotor);
        this.speedMotor = new TalonFX(speedMotor);
        this.encoderTalon = new TalonSRX(encoder);
        pidController = new pidcontroller(0.008, 0, 0, 0);

        this.encoderTalon.setSelectedSensorPosition(0);
        
        pidController.reset();
        pidController.setOutputLimit(-0.4, 0.4);
    }

    public void drive(double speed, double angle) {
        speedMotor.set(ControlMode.PercentOutput, speed);
        double currentAngle = fixAngle(getAngle());
        
        double pidValue = pidController.applyAsDouble(currentAngle, translatedAngle(angle, currentAngle));
        angleMotor.set(ControlMode.PercentOutput, pidValue);
    }

    public void resetPID() {
        pidController.reset();
    }

    private double translatedAngle(double targetAngle, double currentAngle) {
        double distance = (targetAngle - currentAngle + 540 ) % 360 - 180;
        return currentAngle + distance;
    }

    private double fixAngle(double angle) {
        return (angle<0) ? 360+angle : angle;
    }

    private double getAngle() {
        double angle = encoderTalon.getSelectedSensorPosition()/4096 * 360 % 360;

        return angle < 0 ? angle + 360 : angle;
    }
}
