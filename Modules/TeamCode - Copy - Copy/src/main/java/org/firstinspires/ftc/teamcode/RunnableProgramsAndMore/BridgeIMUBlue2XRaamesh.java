package org.firstinspires.ftc.teamcode.RunnableProgramsAndMore;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.PhantomConfig4200X;
import org.firstinspires.ftc.teamcode.RunnableProgramsAndMore.proto.BlueAuto;


@Autonomous(name="BlueFoundationSide (Ends Close To Wall)",group="autonomous")
public final class BridgeIMUBlue2XRaamesh extends BlueAuto {

    PhantomConfig4200X robot = new PhantomConfig4200X();

    IMURedux MecanumInstance = new IMURedux(this,robot,0.025f);
    @Override
    public void runOpMode() throws InterruptedException {

        super.d();

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

        robot.startShip(hardwareMap);
      //  robot.doIMU();
      //  robot.reverse();
      //  robot.doNewC();
        MecanumInstance.calibrate();
        //robot.skyServo.setPosition(0);
        robot.armServo.setPosition(1);
        waitForStart();
        robot.armServo.setPosition(0.7);
        MecanumInstance.imuDrive(0,0.2f,0.3f);
        MecanumInstance.imuDrive(-90,1.2f,0.5f);
        MecanumInstance.imuDrive(0,1.8f,0.5f);
        MecanumInstance.imuDrive(0,0.533333333f,0.3f);
        robot.armServo.setPosition(1);
        sleep(3000);
        MecanumInstance.imuDrive(0,2.45f,-0.5f);
        sleep(500);

        MecanumInstance.imuTurn(90,1.8);

        robot.armServo.setPosition(0.7);
        sleep(3000);

        MecanumInstance.imuDrive(0,0.35f,0.7f);

        MecanumInstance.imuDrive(180,1f,0.4f);

        MecanumInstance.imuDrive(-90,0.625,0.7);

     //   MecanumInstance.imuDrive(90,3,0.5f);
        MecanumInstance.imuDrive(180,2f,0.4f);
        /*MecanumInstance.imuDrive(-90,2*1.1f,0.5f);
        MecanumInstance.imuDrive(90,3f,0.5f); */

    }
}
