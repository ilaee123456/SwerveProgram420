

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;

public class ManualSwerveDrive extends CommandBase {
  private Joystick joystick;
  private SwerveDrive swerveDrive;

  public ManualSwerveDrive(SwerveDrive swerveDrive, Joystick joystick) {
    this.joystick = joystick;
    this.swerveDrive = swerveDrive;
    addRequirements(swerveDrive);
  }

  @Override
  public void initialize() {
    swerveDrive.resetPIDs();
  }

  @Override
  public void execute() {
    double y = joystick.getRawAxis(1);
    double x = -joystick.getRawAxis(0);
    double rotate = joystick.getRawAxis(2);

    if(Math.abs(y) < 0.1) {
      y = 0;
    }
    if(Math.abs(x) < 0.1) {
      x = 0;
    }
    if(Math.abs(rotate) < 0.1) {
      rotate = 0;
    }

    swerveDrive.drive(x, y, rotate);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
