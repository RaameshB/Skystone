package org.firstinspires.ftc.teamcode.RobotControl.HardwareObjects;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/** A class to control the robot chassis' main drive motors **/

public final class Motors {
    // This is our hardwaremap object, we use this for initializing the motors.
    private HardwareMap hwmp;
    // Our DcMotors
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private DcMotor armLeft;
    private DcMotor armRight;
    // Our Servo helps us auto determine our chassis
    private Servo chassisCheck;
    // LinearOpMode for telemetry and sleep
    private LinearOpMode lin;
    // Our class constructor.
    public Motors(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        hwmp = hardwareMap;
        lin = linearOpMode;
    }
    //The Variable that our function use to determine whether the motors are initialized or not.
    private boolean isInit;
    private void init(){
        boolean isNewChassis = true;
        // This is where we initialize our motors.
        intakeLeft  = hwmp.get(DcMotor.class,"front_left_motor" );
        intakeRight = hwmp.get(DcMotor.class,"front_right_motor");
        armLeft     = hwmp.get(DcMotor.class,"back_left_motor"  );
        armRight    = hwmp.get(DcMotor.class,"back_right_motor" );
        // Set isInit to true so our functions know that the motors are initialized
        isInit = true;
        // Chassis auto determine code
        try {
            chassisCheck = hwmp.get(Servo.class,"armServo");
        } catch (Exception e) {
            // The new chassis
            isNewChassis = false;
        }
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeRight.setDirection(DcMotorSimple.Direction.FORWARD);
        if (isNewChassis) {
            armLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            armRight.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            armLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            armRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    public void setDirection(DcMotorSimple.Direction frontLeft, DcMotorSimple.Direction frontRight, DcMotorSimple.Direction backLeft, DcMotorSimple.Direction backRight) {
        if (!isInit) {init();}
        intakeLeft.setDirection(frontLeft);
        intakeRight.setDirection(frontRight);
        armLeft.setDirection(backLeft);
        armRight.setDirection(backRight);
    }
    public void reverse() {
        if (!isInit) {
            init();
        }
        if (intakeLeft.getDirection() == DcMotorSimple.Direction.REVERSE) {
            intakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if (intakeRight.getDirection() == DcMotorSimple.Direction.REVERSE) {
            intakeRight.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if (armLeft.getDirection() == DcMotorSimple.Direction.REVERSE) {
            armLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            armLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if (armRight.getDirection() == DcMotorSimple.Direction.REVERSE) {
            armRight.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            armRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }
    /** Deprecated in favor of using anglePower from the imuMotors RobotFunctionGroup Class **/
    @Deprecated
    public void anglePower(float angle, double power) {
        angle -= 45;
        if(!isInit) {
            init();
        }
        intakeLeft.setPower(Math.cos(angle) * power + checkDirection());
        intakeRight.setPower(Math.sin(angle) * power - checkDirection());
        armLeft.setPower(Math.sin(angle) * power + checkDirection());
        armRight.setPower(Math.cos(angle) * power - checkDirection());
    }
    /** Deprecated in favor of using checkDirection from the imuMotors RobotFunctionGroup Class **/
    @Deprecated
    private double checkDirection() {
        return 0;
    }
    public void individualPower(double frontLeft, double frontRight, double backLeft, double backRight) {
        if (!isInit) {
            init();
        }
        intakeLeft.setPower(frontLeft);
        intakeRight.setPower(frontRight);
        armLeft.setPower(backLeft);
        armRight.setPower(backRight);
    }
    public void frontSwitch() {
        if (!isInit) {
            init();
        }
        DcMotor cache = intakeLeft;
        intakeLeft = intakeRight;
        intakeRight = cache;
    }
    public void backSwitch() {
        if (!isInit) {
            init();
        }
        DcMotor cache = armLeft;
        armLeft = armRight;
        armRight = cache;
    }
    public void frontReverse() {
        if (!isInit) {
            init();
        }
        if (intakeLeft.getDirection() == DcMotorSimple.Direction.REVERSE) {
            intakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if (intakeRight.getDirection() == DcMotorSimple.Direction.REVERSE) {
            intakeRight.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }
    public void backReverse() {
        if (!isInit) {
            init();
        }
        if (armLeft.getDirection() == DcMotorSimple.Direction.REVERSE) {
            armLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            armLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if (armRight.getDirection() == DcMotorSimple.Direction.REVERSE) {
            armRight.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            armRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }
    public void frontAndBackSwitch() {
        if (!isInit) {
            init();
        }
        DcMotor cache = intakeLeft;
        intakeLeft = armLeft;
        armLeft = cache;
        cache = intakeRight;
        intakeRight = armRight;
        armRight = cache;
    }


}
