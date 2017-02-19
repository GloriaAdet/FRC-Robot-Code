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
	RobotDrive rDrive; //drives robot
	Joystick xboxController; //xbox controller
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	Timer timer;
	Spark leftM; //controls left wheels
	Spark rightM; // controls right wheels
	Spark shootM; //motor for shooter
	Spark climbM; //motor for climber
	int curvestraight;
	boolean buttonY; //controller shooter speed
	boolean buttonX; //controls shooter speed
	double rightXAxis; //right joystick x axis
	double rightYAxis; //right joystick y axis
	double leftYAxis; //left joystick y axis

	@Override
	public void robotInit() {
//Initialize everything

		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		xboxController = new Joystick(0);
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
		rightXAxis = xboxController.getRawAxis(4);//right joystick x axis
		rightYAxis = xboxController.getRawAxis(5);//right joystick y axis
		curvestraight = 0;
		buttonY = xboxController.getRawButton(2);
		buttonX = xboxController.getRawButton(3);
		leftYAxis = xboxController.getRawAxis(6);//left joystick x axis


		if(rightYAxis < 0 || rightYAxis > 0){
			rDrive.drive(rightXAxis, curvestraight);
		}
		//drives straight forward or straight backward
		
		if(rightXAxis > 0 || rightXAxis < 0){
			rDrive.drive(rightYAxis, rightXAxis); //turns right or left
		}
		

		if(leftYAxis < 0 || leftYAxis < 0 || leftYAxis == 0){
			climbM.set(leftYAxis);
			Timer.delay(0.01); //climbs according to value of y axis on left joystick
		}

		if(buttonY){
			shootM.set(1.0);
			Timer.delay(0.01);
		}
		if (buttonX){
			shootM.set(-1.0);
			Timer.delay(0.01);
		}

	}

}
