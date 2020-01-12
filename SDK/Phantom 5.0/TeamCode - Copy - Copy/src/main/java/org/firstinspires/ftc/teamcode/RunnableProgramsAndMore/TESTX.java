package org.firstinspires.ftc.teamcode.RunnableProgramsAndMore;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.PhantomConfig4200X;
import org.firstinspires.ftc.teamcode.RunnableProgramsAndMore.proto.RedAuto;
@Disabled
@Autonomous(name="TESTING")
public class TESTX extends RedAuto {

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
        //robot.doIMU();
        //robot.reverse();
        //robot.doNewC();
        MecanumInstance.calibrate();
        //robot.skyServo.setPosition(0);
        //robot.armServo.setPosition(1);
        waitForStart();
        //robot.armServo.setPosition(0.7);
        MecanumInstance.imuDrive(0, 30f, 0.3f);
        MecanumInstance.imuDrive(-90, 30f, 0.3f);
    }
}
