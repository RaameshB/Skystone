package org.firstinspires.ftc.teamcode.Autons;

import android.hardware.camera2.CameraDevice;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Robot.Drive;
import org.firstinspires.ftc.teamcode.Robot.Path;
import org.firstinspires.ftc.teamcode.Robot.Point;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


@Autonomous(name = "VuAuto")
public class VuForia extends LinearOpMode {

    boolean pastWaitForStart = false;

    Drive robot = new Drive(this);

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    private static final String VUFORIA_KEY =
            "AQ7NeE//////AAABmcO5W35F+Ur8mG9itZB/8upQX47ZmLaFnIfCEODFQntqFeIDP38tlDqLWj9UQhUS5ldoZ/5j1US5ygvuRMO87Q2CnZXWjW9UQ6hxElxuAV1o+M2/k5lKTFn6UA0WauQpvF7NsLb5lW19y18yFWLaJ8WzIdGz1y2ytRszOBce/roQAwx92zDSdCRw73hrFgV1hvRKijCvX83ZAoHewLIT0ox/lPnWhSlXhvgjhEv6/WZ+00eCqCtOLnH0h2x4ntu60mC+DOtfGCDB/OWM8NZdQfF0hjWZxTN93nUrh5cWNT4mjQ9qLnTxY/bumoyi0LhGEim82CHrB9YDZCfcpo2Vgjc4ZdOd8e8obLr+P35caNbr";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = new OpenGLMatrix();
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;


    VectorF translation = lastLocation.getTranslation();

    VectorF vuForiaTranslation = translation;

    double translationX;

    double translationY;

    double translationZ;

    boolean isThreadStopRequested;


    Runnable r = new Runnable() {
        int cameraMonitorViewId;
        VuforiaLocalizer.Parameters parameters;
        VuforiaTrackables targetsSkyStone;
        VuforiaTrackable stoneTarget;
        VuforiaTrackable blueRearBridge;
        VuforiaTrackable redRearBridge;
        VuforiaTrackable redFrontBridge;
        VuforiaTrackable blueFrontBridge;
        VuforiaTrackable red1;
        VuforiaTrackable red2;
        VuforiaTrackable front1;
        VuforiaTrackable front2;
        VuforiaTrackable blue1;
        VuforiaTrackable blue2;
        VuforiaTrackable rear1;
        VuforiaTrackable rear2;
        List<VuforiaTrackable> allTrackables;
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line
        OpenGLMatrix robotFromCamera;
        @Override
        public void run() {
            function1();
            while (opModeIsActive() && !pastWaitForStart && !isThreadStopRequested);
            function2();
            targetsSkyStone.deactivate();
        }
        public void function1() {
            cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
            parameters.vuforiaLicenseKey = VUFORIA_KEY;
            parameters.cameraDirection   = CAMERA_CHOICE;
            vuforia = ClassFactory.getInstance().createVuforia(parameters);
            targetsSkyStone = vuforia.loadTrackablesFromAsset("Skystone");
            stoneTarget = targetsSkyStone.get(0);
            stoneTarget.setName("Stone Target");
            blueRearBridge = targetsSkyStone.get(1);
            blueRearBridge.setName("Blue Rear Bridge");
            redRearBridge = targetsSkyStone.get(2);
            redRearBridge.setName("Red Rear Bridge");
            redFrontBridge = targetsSkyStone.get(3);
            redFrontBridge.setName("Red Front Bridge");
            blueFrontBridge = targetsSkyStone.get(4);
            blueFrontBridge.setName("Blue Front Bridge");
            red1 = targetsSkyStone.get(5);
            red1.setName("Red Perimeter 1");
            red2 = targetsSkyStone.get(6);
            red2.setName("Red Perimeter 2");
            front1 = targetsSkyStone.get(7);
            front1.setName("Front Perimeter 1");
            front2 = targetsSkyStone.get(8);
            front2.setName("Front Perimeter 2");
            blue1 = targetsSkyStone.get(9);
            blue1.setName("Blue Perimeter 1");
            blue2 = targetsSkyStone.get(10);
            blue2.setName("Blue Perimeter 2");
            rear1 = targetsSkyStone.get(11);
            rear1.setName("Rear Perimeter 1");
            rear2 = targetsSkyStone.get(12);
            rear2.setName("Rear Perimeter 2");
            allTrackables = new ArrayList<VuforiaTrackable>();
            allTrackables.addAll(targetsSkyStone);
            stoneTarget.setLocation(OpenGLMatrix
                    .translation(0, 0, stoneZ)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

            //Set the position of the bridge support targets with relation to origin (center of field)
            blueFrontBridge.setLocation(OpenGLMatrix
                    .translation(-bridgeX, bridgeY, bridgeZ)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

            blueRearBridge.setLocation(OpenGLMatrix
                    .translation(-bridgeX, bridgeY, bridgeZ)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

            redFrontBridge.setLocation(OpenGLMatrix
                    .translation(-bridgeX, -bridgeY, bridgeZ)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

            redRearBridge.setLocation(OpenGLMatrix
                    .translation(bridgeX, -bridgeY, bridgeZ)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

            //Set the position of the perimeter targets with relation to origin (center of field)
            red1.setLocation(OpenGLMatrix
                    .translation(quadField, -halfField, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

            red2.setLocation(OpenGLMatrix
                    .translation(-quadField, -halfField, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

            front1.setLocation(OpenGLMatrix
                    .translation(-halfField, -quadField, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

            front2.setLocation(OpenGLMatrix
                    .translation(-halfField, quadField, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

            blue1.setLocation(OpenGLMatrix
                    .translation(-quadField, halfField, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

            blue2.setLocation(OpenGLMatrix
                    .translation(quadField, halfField, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

            rear1.setLocation(OpenGLMatrix
                    .translation(halfField, quadField, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

            rear2.setLocation(OpenGLMatrix
                    .translation(halfField, -quadField, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
            if (CAMERA_CHOICE == BACK) {
                phoneYRotate = -90;
            } else {
                phoneYRotate = 90;
            }

            // Rotate the phone vertical about the X axis if it's in portrait mode
            if (PHONE_IS_PORTRAIT) {
                phoneXRotate = 90 ;
            }
            robotFromCamera = OpenGLMatrix
                    .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

            /**  Let all the trackable listeners know where the phone is.  */
            for (VuforiaTrackable trackable : allTrackables) {
                ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
            }
        }
        void function2() {



            targetsSkyStone.activate();
            while (!isStopRequested() && !isThreadStopRequested) {
                /** // check all the trackable targets to see which one (if any) is visible. **/
                targetVisible = false;
                for (VuforiaTrackable trackable : allTrackables) {
                    if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                        telemetry.addData("Visible Target", trackable.getName());
                        targetVisible = true;

                        // getUpdatedRobotLocation() will return null if no new information is available since
                        // the last time that call was made, or if the trackable is not currently visible.
                        OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                        if (robotLocationTransform != null) {
                            lastLocation = robotLocationTransform;
                        }
                        break;
                    }
                }

                // Provide feedback as to where the robot is located (if we know).
                if (targetVisible) {
                    // express position (translation) of robot in inches.

                    vuForiaTranslation = translation;

                    //telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translationX = translation.get(1);
                    translationY = translation.get(2);
                    translationZ = translation.get(3);

                    // express the rotation of the robot in degrees.
                    Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                    //telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                }
                else {
                    //telemetry.addData("Visible Target", "none");
                }
                //telemetry.update();
            }

        }
    };
    Thread t = new Thread() {
        @Override
        public void run() {
            super.run();
            r.run();
        }

        @Override
        public synchronized void start() {
            super.start();
            run();
        }
    };













































    Path path = new Path();
    Path path1 = new Path();

    int positionOfSkyStone;

    @Override
    public void runOpMode() {
        t.start();
        waitForOpModeStart();
        simpleEncoders(12,0,0.8,1,0);
        if (translationY < 0) {
            positionOfSkyStone = -2;
        }
        if (translationY > 0) {
            positionOfSkyStone = -1;
        }
        if (!targetVisible) {
            positionOfSkyStone = 0;
        }
        telemetry.addData("Skystone Position:",positionOfSkyStone + 3);
        telemetry.update();
        positionOfSkyStone *= 9;
        path1.addPoint(12,0,0);
        path1.addPoint(12,positionOfSkyStone,0);
        path1.addPoint(30,positionOfSkyStone,0);
        robot.runPath(path1,0.7,1);
        path1.clear();
        robot.intake1.setPower(0.7);
        robot.intake2.setPower(0.7);

        robot.backLeft.setPower(0.5);
        robot.backRight.setPower(0.5);
        robot.frontLeft.setPower(0.5);
        robot.backRight.setPower(0.5);


        ElapsedTime stopWatch = new ElapsedTime();

        stopWatch.reset();

        //TODO: ADD Distance Sensor / Color Sensor

        while (!gamepad1.a && opModeIsActive() && (stopWatch.seconds() < 3));

        robot.stop();

        path1.addPoint(robot.encoderDistanceX(),robot.encoderDistanceY());

        path1.addPoint(6,positionOfSkyStone);

        path1.addPoint(6,0,-90);

        path1.addPoint(6,-84,-90);

        path1.addPoint(30,-84);

        robot.runPath(path1,0.7,1);

        robot.foundationServo.setPosition(0);

        path1.clear();

        //path1.addPoint(robot.x(),robot.y());

        path1.addPoint(6,-72,0);

        path1.addPoint(2,-48,0);

        robot.runPath(path1,0.7,1);

        path1.clear();

        a = 0;

        simpleEncoders(2,-96,0.7,1);

        simpleEncoders(2,-36,0.7,1);

    }
    double a = 0;


    public void simpleEncoders(double distX, double distY, double power, double drift, double angle) {
        a = angle;
        path.addPoint(distX,distY,a);
        robot.runPath(path,power,drift);
        path.clear();
    }

    public void simpleEncoders(double distX, double distY, double power, double drift) {
        path.addPoint(distX,distY,a);
        robot.runPath(path,power,drift);
        path.clear();
    }
//    void simpleMotorNoReset(double distX, double distY, double power, double drift) {
//        path.addPoint(distX,distY,a);
//        robot.runPath(path,power,drift);
//    }
//    void simpleMotorNoReset(double distX, double distY, double power, double drift, double angle) {
//        a = angle;
//        path.addPoint(distX,distY,a);
//        robot.runPath(path,power,drift);
//    }



















    void waitForOpModeStart() {
        waitForStart();
        pastWaitForStart = true;
    }
}
