
package org.usfirst.frc.team687.robot;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	Joystick leftJoy, rightJoy;
	VictorSP ftLeft, ftRight, bkLeft, bkRight;
	IMUAdvanced imu;
	boolean first_iteration = true;
	
    public void robotInit() {
    	leftJoy = new Joystick(0);
    	rightJoy = new Joystick(1);
    	ftLeft = new VictorSP(2);
    	ftRight = new VictorSP(3);
    	bkLeft = new VictorSP(4);
    	bkRight = new VictorSP(5);
    	imu = new IMUAdvanced(new SerialPort(57600,SerialPort.Port.kMXP));
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	boolean is_calibrating = imu.isCalibrating();
        if(first_iteration && is_calibrating)   {
            Timer.delay(0.3);
            imu.zeroYaw();
            first_iteration = false;
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	boolean is_calibrating = imu.isCalibrating();
        if(first_iteration && is_calibrating)   {
            Timer.delay(0.3);
            imu.zeroYaw();
            first_iteration = false;
        }
    	NerdyDrive.setHeader(-imu.getYaw());
    	SmartDashboard.putNumber("yaw",imu.getYaw());
        boolean beta = rightJoy.getRawButton(4);
        double fl, fr, bl, br;
        if(beta)	{
        	NerdyDrive.driveBeta(leftJoy.getX(), -leftJoy.getY(), rightJoy.getX(), -rightJoy.getY());
        }	else	{
        	NerdyDrive.driveAlpha(leftJoy.getX(), -leftJoy.getY(), rightJoy.getX());
        }
        
        if(leftJoy.getRawButton(4)){
        	imu.zeroYaw();
        }
        
        fl = NerdyDrive.getFtLeft();
        fr = NerdyDrive.getFtRight();
        bl = NerdyDrive.getBkLeft();
        br = NerdyDrive.getBkRight();
        
        ftLeft.set(fl);
        ftRight.set(fr);
        bkLeft.set(bl);
        bkRight.set(br);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
