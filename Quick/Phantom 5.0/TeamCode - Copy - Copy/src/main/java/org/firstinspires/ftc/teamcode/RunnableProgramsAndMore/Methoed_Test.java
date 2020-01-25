package org.firstinspires.ftc.teamcode.RunnableProgramsAndMore;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.PhantomConfig4200X;
import org.firstinspires.ftc.teamcode.RunnableProgramsAndMore.proto.RedAuto;
@Disabled
@Autonomous(name = "test")
public class Methoed_Test extends RedAuto {

    PhantomConfig4200X robot = new PhantomConfig4200X();

    IMURedux redux = new IMURedux(this,robot,0.025f);


    @Override
    public void runOpMode() throws InterruptedException {
        super.d();

        robot.startShip(hardwareMap);
        //robot.doIMU();
        //robot.reverse();
        //robot.doNewC();
        redux.calibrate();

        waitForStart();

        redux.imuDrive(90,4.9,1);

        redux.imuDrive(-90,4.9,1);

        int i = 0;

        while (!isStopRequested() && i < 4) {
            redux.imuTurn(90,3);

            i++;
        }

        redux.imuDrive(90,4.9,1);

        redux.imuDrive(-90,4.9,1);

    }
}
