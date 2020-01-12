package org.firstinspires.ftc.teamcode.Unstable.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.MecanumConfig;

public class Config extends MecanumConfig {

    public DcMotor frontLeft  = null;
    public DcMotor backLeft   = null;
    public DcMotor frontRight = null;
    public DcMotor backRight  = null;
    public DcMotor intake1 = null;
    public DcMotor intake2 = null;
    public DcMotor lift = null;
    public Servo leftBase  = null;
    public Servo rightBase = null;
    public Servo leftStabilization  = null;
    public Servo rightStabilization = null;
    public Servo leftGrip  = null;
    public Servo rightGrip = null;
    public BNO055IMU imu      = null;
    HardwareMap hwMap = null;
    boolean IMU=false;

    public BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    public Orientation             lastAngles = new Orientation();


    public double                  globalAngle;


    public Config() {}
//@Override
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        super.init(hwMap);

        frontLeft = hwMap.get(DcMotor.class, "front_left_motor");
        backLeft = hwMap.get(DcMotor.class, "back_left_motor");
        frontRight = hwMap.get(DcMotor.class, "front_right_motor");
        backRight = hwMap.get(DcMotor.class, "back_right_motor");

        intake1 = hwMap.get(DcMotor.class, "intake1");
        intake2 = hwMap.get(DcMotor.class, "intake2");
        lift = hwMap.get(DcMotor.class, "lift");
        
        leftBase = hwMap.get(Servo.class, "left base");
        rightBase = hwMap.get(Servo.class, "right base");
        leftStabilization = hwMap.get(Servo.class, "left stabilization");
        rightStabilization = hwMap.get(Servo.class, "right stabilization");
        leftGrip = hwMap.get(Servo.class, "left grip");
        rightGrip = hwMap.get(Servo.class, "right grip");

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        intake1.setPower(0);
        intake2.setPower(0);
        lift.setPower(0);

        intake1.setDirection(DcMotorSimple.Direction.FORWARD);
        intake2.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setDirection(DcMotorSimple.Direction.FORWARD);

        intake1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    public void doIMU(){
        parameters.mode =  BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }

    public void motorsOn() {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void zeroFloat() {
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void zeroBrake() {
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void reverse() {
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
