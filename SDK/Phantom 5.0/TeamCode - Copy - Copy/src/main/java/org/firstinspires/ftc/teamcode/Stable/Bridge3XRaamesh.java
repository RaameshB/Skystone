package org.firstinspires.ftc.teamcode.Stable;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RunnableProgramsAndMore.proto.RedAuto;
import org.firstinspires.ftc.teamcode.Unstable.Robot.Drive;
import org.firstinspires.ftc.teamcode.Unstable.Robot.Path;
@Disabled
@Autonomous(name="Raamesh",group="autonomous")
public class Bridge3XRaamesh extends RedAuto {

    Drive robot = new Drive(this);

   // IMURedux MecanumInstance = new IMURedux(this,robot,0.025f);
    Drive redux = new Drive(this);
    Path path1 = new Path();
    @Override
    public void runOpMode() throws InterruptedException {

        super.d();
        final Runnable t1 =  new Runnable() {
            public void run() {
                while (!isStopRequested()) {
                    telemetry.addData("power",(robot.frontLeft.getPower() + robot.frontRight.getPower() + robot.backLeft.getPower() + robot.backRight.getPower())/4);
                    telemetry.update();
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public synchronized void start() {
                super.start();
                t1.run();
            }
        };
        //this.sideA="blue";
        /* --OLD
        robot.init(hardwareMap);
        robot.reverse();
        //robot.imu.initialize(robot.parameters);
        MecanumRedux2 MecanumInstance = new MecanumRedux2(this);
        waitForStart();
        MecanumInstance.drive(robot,MecanumInstance.sideify(-90),1.2f,0.5f);
        MecanumInstance.drive(robot,0,2f,0.5f);
        MecanumInstance.drive(robot,0,0.333333333f,0.3f);
        robot.armServo.setPosition(1);
        sleep(3000);
        MecanumInstance.drive(robot,0,3f,-0.5f);
        sleep(1000);
        robot.armServo.setPosition((20/180));
        sleep(3000);
        MecanumInstance.drive(robot,MecanumInstance.sideify(90),3.2f,0.5f);
        MecanumInstance.drive(robot,0,1.2f,0.5f);
        MecanumInstance.drive(robot,MecanumInstance.sideify(-90),2f,0.5f);
        MecanumInstance.drive(robot,MecanumInstance.sideify(90),3f,0.5f);
*/

        robot.init(hardwareMap);
        //robot.doIMU();
        robot.reverse();
        //robot.doNewC();
        //MecanumInstance.calibrate();
        //robot.skyServo.setPosition(0);
        robot.armServo.setPosition(1);
        t2.start();
        waitForStart();
        robot.armServo.setPosition(0.7);

        //path1.addPoint(0, 2, 0);
        //robot.runPath(path1, 0.5, 1);
        path1.addPoint(1,2);
        robot.runPath(path1,0.5,1);
        //pather(0,2,0.5);
        //redux.relativeDrive(0,2,0.3);
        //MecanumInstance.imuDrive(0,0.2f,0.3f);
        //path1.addPoint(-36,2,0);
        //robot.runPath(path1, 0.5,1);
        pather(-36,2,0.5);
        //redux.relativeDrive(-90,36,0.5);
        //MecanumInstance.imuDrive(-90,1.2f,0.5f);

        //redux.relativeDrive(0,48,0.5);
        //MecanumInstance.imuDrive(0,1.8f,0.5f);
        //MecanumInstance.imuDrive(0,0.533333333f,0.3f);
        pather(-36,32,0.5);
        robot.armServo.setPosition(1);
        sleep(3000);

        //path1.addPoint(-36,-24);
        //robot.runPath(path1,0.5,1);
        pather(-36,6,0.5);
        //redux.relativeDrive(180,26,0.5);
        //MecanumInstance.imuDrive(0,2.45f,-0.5f);
        sleep(500);

        //MecanumInstance.imuTurn(-90,1.8);

        //path1.addPoint(-36,-24,-90);

        //robot.runPath(path1,);

        pather(-36,6,0.5,-90);

        robot.armServo.setPosition(0.7);
        sleep(500);



        //redux.relativeDrive(0,12,0.7);

        //MecanumInstance.imuDrive(0,0.35f,0.7f);

        pather(-42,6,0.7);

        //redux.relativeDrive(-90,24,0.4);
        pather(-42,2,0.5);
        //MecanumInstance.imuDrive(-90,2.5,0.4);

        //   MecanumInstance.imuDrive(90,3,0.5f);
        pather(12,2,0.5);
        //redux.relativeDrive(180,,0.4);
        //MecanumInstance.imuDrive(180,3f,0.4f);
        /*MecanumInstance.imuDrive(-90,2*1.1f,0.5f);
        MecanumInstance.imuDrive(90,3f,0.5f); */

    }
    float fa = 0;
    void pather(double pointX, double pointY,double power, double faceAngle) {
        path1.clear();
        fa = (float) faceAngle;
        path1.addPoint(pointX,pointY,(double) fa);
        robot.runPath(path1,power,1);
    }
    void pather(double pointX, double pointY,double power) {
        path1.clear();
        path1.addPoint(pointX,pointY,(double) fa);
        robot.runPath(path1,power,1);
    }

}
