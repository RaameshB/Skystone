package iancode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.teamcode.Robot.Config;

@TeleOp(name = "Oldbot Teleop76")
public class MecanumK extends LinearOpMode {

    MecanumConfig robot = new MecanumConfig();

    double frontLeft;
    double frontRight;
    double backLeft;
    double backRight;
    double side;

    boolean pressed = false;

    double power = 0.5;
    boolean inverted;
    boolean released = true;

    double veloc = 0;
    double dist = 0;
    double indist = 0;
    double accel = 0;

    Acceleration gravity;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);


        robot.doIMU();
        robot.IMUf=true;
       // MecanumRedux2 mr = new MecanumRedux2(this);
//        robot.doIMU();
//        robot.imu.initialize(robot.parameters);

        //telemetry.addData("Mode", "calibrating...");
        //telemetry.update();

//        // make sure the imu gyro is calibrated before continuing.
//        while (!isStopRequested() && !robot.imu.isGyroCalibrated())
//        {
//            sleep(50);
//            idle();
//        }

        robot.imu.initialize(robot.parameters);

        //while(!isStopRequested() && !robot.imu.isAccelerometerCalibrated());

        telemetry.addData("Mode", "waiting for start");
        //telemetry.addData("imu calib status", robot.imu.getCalibrationStatus().toString());
        telemetry.update();

        waitForStart();

        ElapsedTime tim = new ElapsedTime();

        //robot.backReverse();
        while (!isStopRequested()) {

            if (gamepad1.right_bumper && !pressed && power < 1) {
                power += 0.25;
                pressed = true;
            }
            if (gamepad1.left_bumper && !pressed && power > 0.25) {
                power -= 0.25;
                pressed = true;
            }

            if (!gamepad1.left_bumper && !gamepad1.right_bumper) {
                pressed = false;
            }
            if (gamepad2.dpad_up&&!gamepad2.dpad_down){



         //       robot.armServo.setPosition(0.5);

                sleep(200);

            }

            if (!gamepad2.dpad_up&&gamepad2.dpad_down){



           //     robot.armServo.setPosition(0);

                sleep(200);

            }
            if (gamepad2.dpad_left&&!gamepad2.dpad_right){
            //    robot.capServo.setPosition(1);

                sleep(200);

            }

            if (gamepad2.dpad_right&&!gamepad2.dpad_left){



//                robot.capServo.setPosition(0);

                sleep(200);

            }
            if (gamepad2.left_bumper&&!gamepad2.right_bumper){
//                robot.stoneServo.setPosition(0);
                telemetry.addData("stone: ",0);
                sleep(200);

            }

            if (gamepad2.right_bumper&&!gamepad2.left_bumper){


                telemetry.addData("stone: ",180);
//                robot.stoneServo.setPosition((1));

                sleep(200);

            }
            side = (gamepad1.left_stick_x + gamepad1.right_stick_x) / 2;
            if (gamepad1.y) {
                if (released) {
                    if (inverted) {
                        side = -side;

                        inverted = false;

                    } else {
                        side = -side;
                        inverted = true;
                    }
                    released = false;
                }
            }
            if (!released) {
                if (!gamepad1.y) {
                    released = true;
                }
            }
            telemetry.addData("Power:", power);

            telemetry.update();


            frontRight = -gamepad1.left_stick_y + side;
            backRight = -gamepad1.left_stick_y - side;
            frontLeft = -gamepad1.right_stick_y - side;
            backLeft = -gamepad1.right_stick_y + side;
/*
            if (gamepad1.dpad_left) {
//                frontLeft = -1 - gamepad1.right_trigger;
//                frontRight = 1 * (1 - gamepad1.right_trigger);
//                backLeft = 1 * (1 - gamepad1.right_trigger);
//                backRight = -1 - gamepad1.right_trigger;
                mr.middleDrive(robot,90,(float)power);
            } else if (gamepad1.dpad_right) {
//                frontLeft = 1 * (1 - gamepad1.right_trigger);
//                frontRight = -1 - gamepad1.right_trigger;
//                backLeft = -1 - gamepad1.right_trigger;
//                backRight = 1 * (1 - gamepad1.right_trigger);
                mr.middleDrive(robot,-90,(float)power);
            }
            */


            if (!gamepad1.x) {
                if (inverted) {
//                    robot.forward();
                    robot.frontRight.setPower(frontLeft * power);
                    robot.backRight.setPower(backLeft * power);
                    robot.frontLeft.setPower(frontRight * power);
                    robot.backLeft.setPower(backRight * power);
                }
                else {

                    //test
                    /*
                    robot.frontLeft.setPower(-frontLeft * power);
                    robot.backLeft.setPower(-backLeft * power);
                    robot.frontRight.setPower(-frontRight * power);
                    robot.backRight.setPower(-backRight * power);
                    */
                    /*
                    robot.backLeft.setPower(frontLeft*power);
                    robot.backRight.setPower(frontRight*power);
                    robot.frontRight.setPower(backRight*power);
                    robot.frontLeft.setPower(backLeft*power);
                     */

                    robot.reverse();
                    robot.frontRight.setPower(frontLeft * power);
                    robot.backRight.setPower(backLeft * power);
                    robot.frontLeft.setPower(frontRight * power);
                    robot.backLeft.setPower(backRight * power);
                }
            } else {
                //rotate(170,1);
                telemetry.addData("Update","Unknown Function!");
                telemetry.update();
            }
        }
    }
//
//    private void resetAngle()
//    {
//        robot.lastAngles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//
//        robot.globalAngle = 0;
//    }
//
//    /**
//     * Get current cumulative angle rotation from last reset.
//     * @return Angle in degrees. + = left, - = right.
//     */
//    private double getAngle()
//    {
//        // We experimentally determined the Z axis is the axis we want to use for heading angle.
//        // We have to process the angle because the imu works in euler angles so the Z axis is
//        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
//        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.
//
//        // Who wrote the above comment? - Ian
//
//
//        // is this the correct angle? Th Controller is mounted on it's side, so theoretically we need to use the X axis.  -Ian
//        Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//
//        double deltaAngle = angles.firstAngle - robot.lastAngles.firstAngle;
//
//        if (deltaAngle < -180)
//            deltaAngle += 360;
//        else if (deltaAngle > 180)
//            deltaAngle -= 360;
//
//        robot.globalAngle += deltaAngle;
//
//        robot.lastAngles = angles;
//
//        return robot.globalAngle;
//    }
//    private void rotate(int degrees, double rPower)
//    {
//        double  leftPower, rightPower;
//
//        // restart imu movement tracking.
//        resetAngle();
//
//        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
//        // clockwise (right).
//
//        if (degrees < 0)
//        {   // turn right.
//            leftPower = rPower;
//            rightPower = -rPower;
//        }
//        else if (degrees > 0)
//        {   // turn left.
//            leftPower = -rPower;
//            rightPower = rPower;
//        }
//        else return;
//
//        // set power to rotate.
//        robot.backLeft.setPower(leftPower);
//        robot.frontLeft.setPower(leftPower);
//        robot.backRight.setPower(rightPower);
//        robot.frontRight.setPower(rightPower);
//        // rotate until turn is completed.
//        if (degrees < 0)
//        {
//            // On right turn we have to get off zero first.
//            while (opModeIsActive() && getAngle() == 0) {telemetry.addData("heading",robot.globalAngle); telemetry.update();}
//
//            while (opModeIsActive() && getAngle() > degrees) {telemetry.addData("heading",robot.globalAngle); telemetry.update();}
//        }
//        else    // left turn.
//            while (opModeIsActive() && getAngle() < degrees) {telemetry.addData("heading",robot.globalAngle); telemetry.update();}
//
//        // turn the motors off.
//        robot.backLeft.setPower(0);
//        robot.frontLeft.setPower(0);
//        robot.backRight.setPower(0);
//        robot.frontRight.setPower(0);
//        // wait for rotation to stop.
//        sleep(500);
//
//        // reset angle tracking on new heading.
//        resetAngle();
//    }

}
