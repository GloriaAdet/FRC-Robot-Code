package org.usfirst.frc.team6213.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot_Code_2017 extends IterativeRobot {
/*This function is run when the robot is first started up and
should be used for any initialization code
*/
	RobotDrive rDrive;
	Joystick xboxController;
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	double maxSpeed;
	Timer timer;
	Spark leftM;
	Spark rightM;
	Spark shootM;
	Spark climbM;
	double curveleft;
	double curveright;
	int curvestraight;
	double outputMagnitudeForward;
	double outputMagnitudeBackward;
	boolean buttonY;
	boolean buttonA;
	boolean buttonX;
	double rightXAxis;
	double rightYAxis;
	double leftYAxis;
	double shooterSpeed;

	@Override
	public void robotInit() {
//Initialize everything

		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		xboxController = new Joystick(0);
		buttonA = xboxController.getRawButton(1);
		buttonY = xboxController.getRawButton(2);
		buttonX = xboxController.getRawButton(3);
		maxSpeed = 0.4;
		shooterSpeed = 1.0;
		timer = new Timer();
		leftM = new Spark(0);
		rightM = new Spark(1);
		shootM = new Spark(2);
		climbM = new Spark(3);
		rDrive = new RobotDrive(leftM,rightM);
		rDrive.setSafetyEnabled(false);
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(1000, 1000);
		CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 1000, 1000);
		System.out.println(camera.isConnected());
		outputMagnitudeForward = 0.5;
		outputMagnitudeBackward = -0.5;
		curveleft = -0.5;
		curveright = 0.5;
		curvestraight = 0;
		rightXAxis = xboxController.getRawAxis(4);//right joystick x axis
		rightYAxis = xboxController.getRawAxis(5);//right joystick y axis#hastag
		leftYAxis = xboxController.getRawAxis(6);//left joystick x axis

	}
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
/*This function is called periodically during operator control*/

	}
	@Override
	public void teleopPeriodic(){


		if(rightXAxis < 0 || rightXAxis > 0){
			rDrive.drive(rightXAxis, curvestraight); //move forward or backward
			Timer.delay(0.01);
		}
		//drives straight forward or straight backward
		
		if(rightYAxis > 0){
			rDrive.drive(rightYAxis, curveright); //turns right
			Timer.delay(0.01);
		}
		
		if(rightYAxis < 0){
			rDrive.drive(rightYAxis, curveleft); //turns left
			Timer.delay(0.01);
		}

		if(leftYAxis < 0){
			climbM.set(maxSpeed);
			Timer.delay(0.01);
		}

		if(leftYAxis > 0){
			climbM.set(-maxSpeed);
			Timer.delay(0.01);
		}
		
		if(leftYAxis == 0){
			climbM.set(0);;
		}

		if(buttonY){
			shootM.set(shooterSpeed);
			Timer.delay(0.01);
		}
		if (buttonX){
			shootM.set(-shooterSpeed);
			Timer.delay(0.01);
		}

	}

}
