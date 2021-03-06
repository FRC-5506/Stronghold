// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5506.Stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5506.Stronghold.Robot;

/**
 *
 */
public class LoaderTeleop extends Command {
	public double minimumInput = 0.03;
	public boolean altControl = false;
	public double holdTime = 0;
	public double requiredHoldTime = 25;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public LoaderTeleop() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.loader);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.oi.getFunctionJoystick().getRawButton(3))
    		holdTime++;
    	if (!Robot.oi.getFunctionJoystick().getRawButton(3))
    		holdTime = 0;
    	if (holdTime == requiredHoldTime) {
    		holdTime = 0;
    		altControl = !altControl;
    	}
    	if (altControl) {
    		double liftSpeed = Robot.oi.getFunctionJoystick().getPOV();
    		if (liftSpeed == -1)
    			liftSpeed = 0;
    		else {
    			liftSpeed -= 90;
    			liftSpeed = liftSpeed / Math.abs(liftSpeed);
    			if (!Robot.oi.getFunctionJoystick().getRawButton(1))
    				liftSpeed *= 0.78;
    		}
    		Robot.loader.getMotor().set(liftSpeed);
    		return;
    	}
		double liftSpeed = Robot.oi.getFunctionJoystick().getRawAxis(1);
		/*double m = liftSpeed / Math.abs(liftSpeed);
		if (Math.abs(liftSpeed) > 1 / 3) {
			liftSpeed -= m * 1 / 3;
			if (Robot.oi.getFunctionJoystick().getRawButton(1))
				liftSpeed *= 1.5;
		} else
			liftSpeed = 0;
		SmartDashboard.putNumber("Forklift Speed", liftSpeed);*/
		if (!Robot.oi.getFunctionJoystick().getRawButton(1))
			liftSpeed *= 0.7;
    	Robot.loader.getMotor().set(liftSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
