package org.firstinspires.ftc.teamcode.RobotControl.HardwareObjects;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.annotation.Native;

public final class Motors {
    private HardwareMap hwmp;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private DcMotor armLeft;
    private DcMotor armRight;
    private Servo chassisCheck;
    private LinearOpMode lin;
    public Motors(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        hwmp = hardwareMap;
        lin = linearOpMode;
    }
    private boolean isInit;
    private void init(){
        intakeLeft  = hwmp.get(DcMotor.class,"frontLeft" );
        intakeRight = hwmp.get(DcMotor.class,"frontRight");
        armLeft     = hwmp.get(DcMotor.class,"backLeft"  );
        armRight    = hwmp.get(DcMotor.class,"backLeft  ");
        isInit = true;
        try {
            chassisCheck = hwmp.get(Servo.class,"armServo");
        } catch (Exception e) {
            isNewChassis = false;
        }
        intakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        if (isNewChassis) {
            armLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            armRight.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            armLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            armRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }
    private boolean isNewChassis = true;
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
