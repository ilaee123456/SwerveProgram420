package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import frc.robot.pidcontroller;

public class SwerveWheel {
    private TalonFX angleMotor;
    private TalonFX speedMotor;
    private CANCoder encoder;
    private pidcontroller pidController;

    private double offset;

    public SwerveWheel(int angleMotor, int speedMotor, int encoder,double offset) {
        this.angleMotor = new TalonFX(angleMotor);
        this.speedMotor = new TalonFX(speedMotor);
        this.encoder = new CANCoder(encoder);
        pidController = new pidcontroller(0.008, 0, 0, 0);

        this.offset = offset;

        pidController.reset();
        pidController.setOutputLimit(-0.4, 0.4);
    }

    public void drive(double speed, double angle) {
        speedMotor.set(ControlMode.PercentOutput, speed);
        double currentAngle = fixAngle(encoder.getAbsolutePosition() - offset);
        
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

}
