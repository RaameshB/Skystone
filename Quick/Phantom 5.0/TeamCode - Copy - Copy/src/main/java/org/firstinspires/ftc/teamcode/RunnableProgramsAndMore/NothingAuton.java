package org.firstinspires.ftc.teamcode.RunnableProgramsAndMore;

import android.media.MediaActionSound;

import com.qualcomm.ftccommon.CommandList;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PhantomConfig4200X;

@Autonomous(name="Just Parking Autonomous")
public class NothingAuton extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        int sadMusic = hardwareMap.appContext.getResources().getIdentifier("sadviolin", "raw", hardwareMap.appContext.getPackageName());

        PhantomConfig4200X robot = new PhantomConfig4200X();

        IMURedux MecanumInstance = new IMURedux(this,robot,0.025f);

        robot.startShip(hardwareMap);

        MecanumInstance.calibrate();

        waitForStart();

        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, sadMusic);

        sleep(20000);

        MecanumInstance.imuDrive(180,3f,0.4f);
    }
}
