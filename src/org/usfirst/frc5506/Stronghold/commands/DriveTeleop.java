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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc5506.Stronghold.Robot;

/**
 *
 */
public class DriveTeleop extends Command {
	public boolean usingTankDrive = true;
	public double holdTime = 0;
	public double rumbleTime = 0;
	public boolean useHighPower = false;
	public boolean aPressed = false;
	public double highPower = -1;
	public double lowPower = -0.5;
	public double power = lowPower;
	public boolean wasPressed = false;

	public double minimumInput = 0.03; // The joystick has a slight margin of error, never perfectly at 0
	public byte requiredHoldTime = 50; // How long (x20ms) you need to hold down the buttons to switch drive mode
    public byte responseDuration = 25; // How long (x20ms) to rumble joystick after switching drive mode
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public DriveTeleop() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	/*
		Controls
			Left Bumper
				Set speed to 100% while pressed
			Right Bumer
				Set speed to 75% while pressed
			A (double tap)
				Toggle between a default speed of 50% and 100%
				Starts at 50%
			(Hold both sticks for ~ 1 second)
				Toggle between tank drive (default) and arcade drive
				THE CONTROLLER WILL RUMBLE 100% BRIEFLY WHEN TOGGLED
	*/
	if (rumbleTime > 0)
    		rumbleTime--;
    	if (Robot.oi.getDriverJoystick().getRawButton(9) && Robot.oi.getDriverJoystick().getRawButton(10)) {
    		holdTime++;
    	} else {
    		holdTime = 0;
    	}
    	if (holdTime == requiredHoldTime) {
    		usingTankDrive = !usingTankDrive;
    		rumbleTime = responseDuration;
    	}
    	if (Robot.oi.getDriverJoystick().getRawButton(1) && !aPressed) {
    		aPressed = true;
    		if (wasPressed) {
    			wasPressed = false;
	    		useHighPower = !useHighPower;
	    		rumbleTime = 25;
    		} else
    			wasPressed = true;
    	}
    	if (Robot.oi.getDriverJoystick().getRawButton(6))
    		power = (highPower + lowPower) / 2;
    	else if (Robot.oi.getDriverJoystick().getRawButton(5))
    		power = -1;
    	else
    		power = useHighPower ? highPower : lowPower;
    	if (aPressed && !Robot.oi.getDriverJoystick().getRawButton(1))
    		aPressed = false;
    	if (usingTankDrive) {
	    	float leftSpeed = (float) (Robot.oi.getDriverJoystick().getRawAxis(1) * -power);
	    	float rightSpeed = (float) (Robot.oi.getDriverJoystick().getRawAxis(5) * -power);
	    	tankDrive(leftSpeed, rightSpeed);
	    	if (Math.abs(leftSpeed) >= minimumInput) {
	    		Robot.driveTrain.driveLeft(leftSpeed);
	    		rumble(Robot.oi.getDriverJoystick(), true, Math.abs(leftSpeed));
	    	} else {
	    		Robot.driveTrain.driveLeft(0);
	    		rumble(Robot.oi.getDriverJoystick(), true, 0);
	    	}
	    	if(Math.abs(rightSpeed) >= minimumInput) {
	    		Robot.driveTrain.driveRight(rightSpeed);
	    		rumble(Robot.oi.getDriverJoystick(), false, Math.abs(rightSpeed));
	    	} else {
	    		Robot.driveTrain.driveRight(0);
	    		rumble(Robot.oi.getDriverJoystick(), false, 0);
	    	}
    	} else {
        	arcadeDrive();
    	}
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

    /**
     * Default drive control system
     * @author Tupster24
     * @param leftSpeed y-axis of left joystick
     * @param rightSpeed y-axis of right joystick
     */
    private void tankDrive(float leftSpeed, float rightSpeed) {
    	if (Math.abs(leftSpeed) >= minimumInput) {
    		Robot.driveTrain.driveLeft(leftSpeed);
    		rumble(Robot.oi.getDriverJoystick(), true, Math.abs(leftSpeed));
    	} else {
    		Robot.driveTrain.driveLeft(0);
    		rumble(Robot.oi.getDriverJoystick(), true, 0);
    	}
    	if(Math.abs(rightSpeed) >= minimumInput) {
    		Robot.driveTrain.driveRight(rightSpeed);
    		rumble(Robot.oi.getDriverJoystick(), false, Math.abs(rightSpeed));
    	} else {
    		Robot.driveTrain.driveRight(0);
    		rumble(Robot.oi.getDriverJoystick(), false, 0);
    	}
    }

    private void arcadeDrive() {
    	double forwardSpeed = Robot.oi.getDriverJoystick().getY() * power;
    	double turningSpeed = Robot.oi.getDriverJoystick().getX() * power;
    	if (Math.abs(forwardSpeed) < minimumInput)
    		forwardSpeed = 0;
    	if (Math.abs(turningSpeed) < minimumInput)
    		turningSpeed = 0;
    	if (Math.abs(Robot.oi.getDriverJoystick().getX()) > Math.abs(Robot.oi.getDriverJoystick().getY())) {
        	Robot.oi.getDriverJoystick().setRumble(RumbleType.kLeftRumble, (float) Math.abs(Robot.oi.getDriverJoystick().getX()));
        	Robot.oi.getDriverJoystick().setRumble(RumbleType.kRightRumble, (float) Math.abs(Robot.oi.getDriverJoystick().getX()));
    	} else {
        	Robot.oi.getDriverJoystick().setRumble(RumbleType.kLeftRumble, (float) Math.abs(Robot.oi.getDriverJoystick().getY()));
        	Robot.oi.getDriverJoystick().setRumble(RumbleType.kRightRumble, (float) Math.abs(Robot.oi.getDriverJoystick().getY()));
    	}
    	Robot.driveTrain.drive(forwardSpeed, turningSpeed);
    }

    /**
     * Rumble the joystick for a certain amount
     * @param joy the joystick to rumble
     * @param left is left or right rumble?
     * @param amount amount to rumble
     */
    private void rumble(Joystick joy, boolean left, float amount) {
    	if (rumbleTime > 0)
    		amount = 1;
    	if(left) {
    		joy.setRumble(RumbleType.kLeftRumble, amount);
    	} else {
    		joy.setRumble(RumbleType.kRightRumble, amount);
    	}
    }
}
