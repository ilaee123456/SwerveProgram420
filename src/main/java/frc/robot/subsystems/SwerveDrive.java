// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrive extends SubsystemBase {

  public final double DriveTrainLenght= 75;
  public final double DriveTrainWidth = 75;
  private SwerveWheel rightFront;
  private SwerveWheel leftFront;
  private SwerveWheel leftBack;
  private WorseSwerveWheel rightBack;

  public SwerveDrive() {
    rightFront = new SwerveWheel(3,4,12,140.62);
    leftFront = new SwerveWheel(5,6,13,3);
    leftBack = new SwerveWheel(7,8,14,-4.2);
    rightBack = new WorseSwerveWheel(2, 1, 14);
  }

  public void drive (double x, double y, double rotation) {
    double robotDiagonal = Math.sqrt((DriveTrainLenght * DriveTrainLenght) * (DriveTrainWidth * DriveTrainWidth));

    y *= -1;
  
    double a = x - rotation * (DriveTrainLenght / robotDiagonal);
    double b = x + rotation * (DriveTrainLenght / robotDiagonal);
    double c = y - rotation * (DriveTrainWidth / robotDiagonal);
    double d = y + rotation * (DriveTrainWidth / robotDiagonal);

    double backRightSpeed = Math.sqrt((a * a) + (d * d));
    double backLeftSpeed = Math.sqrt((a * a) + (c * c));
    double frontRightSpeed = Math.sqrt((b * b) + (d * d));
    double frontLeftSpeed = Math.sqrt((b * b) + (c * c));

    double backRightAngle = fixAngle(Math.atan2(a, d) / Math.PI * 180); 
    double backLeftAngle = fixAngle(Math.atan2(a, c) / Math.PI * 180);
    double frontRightAngle = fixAngle(Math.atan2(b, d) / Math.PI * 180);
    double frontLeftAngle = fixAngle(Math.atan2(b, c) / Math.PI * 180);
    
    leftBack.drive(backLeftSpeed, backLeftAngle);
    leftFront.drive(frontLeftSpeed, frontLeftAngle);
    rightFront.drive(frontRightSpeed, frontRightAngle);
    rightBack.drive(backRightSpeed, backRightAngle);
  }

  public void resetPIDs() {
    leftFront.resetPID();
    leftBack.resetPID();
    rightFront.resetPID();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private double fixAngle(double angle) {
    return (angle<0) ? 360+angle : angle;
  }
}
