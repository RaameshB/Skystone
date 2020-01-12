package org.firstinspires.ftc.teamcode.Unstable;

public class GlobalAngleThread {

/*
    private boolean isThreadStopRequested;
    private PhantomConfig400X robot;
    private PhantomConfig4200X robot1;
    private LinearOpMode lin;
    private Orientation angles;
    private int chassis;
    private HardwareMap ahwmap;
    public GlobalAngleThread(PhantomConfig4200X Config,LinearOpMode linearOpMode, HardwareMap hwMap) {
        robot1 = Config;
        lin= linearOpMode;
        chassis = 1;
        angles = robot1.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        ahwmap = hwMap;
    }
    public GlobalAngleThread(PhantomConfig400X Config, LinearOpMode linearOpMode, HardwareMap hwMap) {
        robot = Config;
        lin= linearOpMode;
        chassis = 0;
        angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        ahwmap = hwMap;
    }

    @Override
    public void run() {
        isThreadStopRequested = false;
        robot.startShip(ahwmap);
        lin.telemetry.addData("IMU:", "Calibrating...");
        lin.telemetry.update();
        robot.imu.initialize(robot.parameters);
        //if (Sensors == CaliEnum_I.ALL) {
        while (!lin.isStopRequested() && !robot.imu.isGyroCalibrated()) {
            lin.sleep(50);
            lin.idle();
        }
        lin.telemetry.addData("IMU:", "Calibration Finished!");
        lin.telemetry.update();
        if (chassis == 0) {
            while (!lin.opModeIsActive() && !isThreadStopRequested) {


                double deltaAngle = angles.firstAngle - robot.lastAngles.firstAngle;

                if (deltaAngle < -180) {
                    deltaAngle += 360;
                } else if (deltaAngle > 180) {
                    deltaAngle -= 360;
                }

                robot.globalAngle += deltaAngle;
                robot.offSetAngle += deltaAngle;
                robot.lastAngles = angles;
            }
        }
        if (chassis == 1) {
            while (!lin.opModeIsActive() && !isThreadStopRequested) {


                double deltaAngle = angles.firstAngle - robot1.lastAngles.firstAngle;

                if (deltaAngle < -180) {
                    deltaAngle += 360;
                } else if (deltaAngle > 180) {
                    deltaAngle -= 360;
                }

                robot1.globalAngle += deltaAngle;
                robot1.offSetAngle += deltaAngle;
                robot1.lastAngles = angles;
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        run();
    }
    public void tStop() {
        isThreadStopRequested = true;
    } */
}
