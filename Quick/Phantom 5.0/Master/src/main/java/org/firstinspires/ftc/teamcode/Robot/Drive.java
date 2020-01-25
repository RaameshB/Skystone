package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/** A class for using the encoders and robot control **/


public class Drive extends Config {

    public LinearOpMode opMode;

    public Drive(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    //runtime is the timer
    private ElapsedTime runtime = new ElapsedTime();

    //lastAngles is used in the checkDirection() method
    public double lastAngles;

    /** The position tracking thread **/
    Thread PositionTracking = new Thread() {
        @Override
        public void run() {
            try {

                double robotX = 0;
                double robotY = 0;

                double lastEncoderX = 0;
                double lastEncoderY = 0;

                double deltaEncoderX;
                double deltaEncoderY;

                double deltaX;
                double deltaY;

                double lastVelX = 0;
                double lastVelY = 0;

                final double encoderRadius = 13;

                int loopEvent = 0;

                while(!Thread.interrupted() && opMode.opModeIsActive()) {

                    currentAngle = (encoderDistanceY2() - encoderDistanceY1())/(encoderRadius) + (Math.PI / 2);

                    deltaEncoderX = encoderDistanceX() - lastEncoderX;
                    deltaEncoderY = encoderDistanceY() - lastEncoderY;

                    deltaY = Math.sin(currentAngle) * deltaEncoderY + Math.sin(currentAngle - Math.PI/2) * deltaEncoderX;
                    deltaX = Math.cos(currentAngle) * deltaEncoderY + Math.cos(currentAngle - Math.PI/2) * deltaEncoderX;

                    robotX += deltaX;
                    robotY += deltaY;
                    robot.setPoint(robotX, robotY);

                    if(loopEvent==5) {
                        velX = (robot.x - lastVelX) * 20;
                        velY = (robot.y - lastVelY) * 20;
                        lastVelX = robot.x;
                        lastVelY = robot.y;
                        loopEvent = 0;
                    }

                    lastEncoderX = encoderDistanceX();
                    lastEncoderY = encoderDistanceY();

                    loopEvent++;

//                    opMode.telemetry.addData("robot x", robotX);
//                    opMode.telemetry.addData("robot y", robotY);
//                    opMode.telemetry.addData("robot.x", robot.x);
//                    opMode.telemetry.addData("robot.y", robot.y);
//                    opMode.telemetry.addData("currntAngle", currentAngle);
//                    opMode.telemetry.addData("angle degrees", getAngle());
//                    opMode.telemetry.update();

                    try {
                        Thread.sleep(10);
                    } catch(InterruptedException e) {
                        return;
                    }

                    Thread.yield();
                }

            } catch(Exception e) {
                //exception!
            }
        }
    };



    //IMUAngle is used in getAngle()
    double IMUAngle;

    //assume the robot is at coordinates (0,0)
    Point robot = new Point(0,0);

    double velX = 0;
    double velY = 0;

    //current angle in radians
    double currentAngle = 0;
    //variable used for IMU direction correction
    double checkAngle;
    double setAngle = 0;

    //not used yet. Maybe later.
//    double ROBOT_WEIGHT_KG = 10;
//    double WHEEL_FRICTION = 3;

    double COUNTS_PER_REVOLUTION = 1440 / 1.5;
    double WHEEL_DIAMETER_INCHES = 1.5;



    //calculate distance based on encoder counts
    /** Gets the encoder distance from encoder Y1 **/
    public double encoderDistanceY1() {
        //The encoder will be plugged into the same port as the frontLeft motor
        return yEncoder1.getCurrentPosition() * Math.PI * WHEEL_DIAMETER_INCHES / COUNTS_PER_REVOLUTION;
    }
    /** Gets the encoder distance form encoder Y2**/
    public double encoderDistanceY2() {
        //The encoder will be plugged into the same port as the frontRight motor
        return yEncoder2.getCurrentPosition() * Math.PI * WHEEL_DIAMETER_INCHES / COUNTS_PER_REVOLUTION;
    }
    /** Gets the encoder distance from the X encoder **/
    public double encoderDistanceX() {
        //The encoder will be plugged into the same port as the frontRight motor
        return xEncoder.getCurrentPosition() * Math.PI * WHEEL_DIAMETER_INCHES / COUNTS_PER_REVOLUTION;
    }
    /** Gets the average y distance from both the y encoders **/
    public double encoderDistanceY() {
        return (encoderDistanceY1() + encoderDistanceY2())/2;
    }

    /** Start the position tracking thread **/
    public void startPositionTracking() {
        PositionTracking.start();
    }

    /** resets the position **/
    public void resetPosition() {
        PositionTracking.interrupt();
    }

    //return calculated x and y positions

    /** Returns the calculated x position **/
    public double x() {
        return robot.x;
    }

    /** Returns the calculated y position **/
    public double y() {
        return robot.y;
    }

    /** Returns the X Velocity **/
    public double getVelX() {
        return velX;
    }

    /** Returns the Y Velocity **/
    public double getVelY() {
        return velY;
    }
    
    //@Deprecated
    /** Gets the angle from the robot **/
    public double getAngle() {
        return Math.toDegrees(-currentAngle + Math.PI/2);
    }

    double vX;
    double vY;

    /** Sets the angle? **/
    public void setAngle(double angle) {
        setAngle = angle;
    }

    // Sets power to drive at angle
    /** Don't use this, its bad, if you are reading this then you don't need it.**/
    @Deprecated
    public void simpleDrive(double angle, double power) {

        //Change angle by 45
        angle = angle + 45;

        frontRight.setPower((Math.sin(Math.toRadians(angle)) * power));
        frontLeft.setPower((Math.cos(Math.toRadians(angle)) * power));
        backRight.setPower((Math.cos(Math.toRadians(angle)) * power));
        backLeft.setPower((Math.sin(Math.toRadians(angle)) * power));
    }

    //Calculates power to drive at angle with IMU correction
    /** Don't use this, its bad, if you are reading this then you don't need it.**/
    @Deprecated
    private void angleDrive(double angle, double power) {

        //Change angle by 45
        angle = angle + 45;

        frontRight.setPower(Math.sin(angle) * power - checkDirection());
        frontLeft.setPower(Math.cos(angle) * power + checkDirection());
        backRight.setPower(Math.cos(angle) * power - checkDirection());
        backLeft.setPower(Math.sin(angle) * power + checkDirection());
    }
    /** Don't use this, its bad, if you are reading this then you don't need it.**/
    @Deprecated
    public void relativeDrive(double angle, double distance, double power) {

        double d = Math.sqrt(Math.pow(encoderDistanceX(), 2) + Math.pow(encoderDistanceY(), 2));

        while (d < distance) {
            d = Math.sqrt(Math.pow(encoderDistanceX(), 2) + Math.pow(encoderDistanceY(), 2));
            angleDrive(angle, power);
        }
    }


    //Drive to a point on the robot
    /** Drives to assigned points **/
    public void pointDrive(double xPos, double yPos, double power) {
        double rX = xPos - robot.x;
        double rY = yPos - robot.y;
        double distance = Math.sqrt(Math.pow(rX, 2) + Math.pow(rY, 2));
        double angle = Math.atan2(rY, rX) + (currentAngle - Math.PI/2);
        angle -= Math.PI/4;


        frontLeft.setPower(Math.cos(angle) * power + checkDirection());
        frontRight.setPower(Math.sin(angle) * power - checkDirection());
        backLeft.setPower(Math.sin(angle) * power + checkDirection());
        backRight.setPower(Math.cos(angle) * power - checkDirection());
    }
    /** Drives to assigned points **/
    public void pointDrive(Point point, double power) {
        double rX = point.x - robot.x;
        double rY = point.y - robot.y;
        double angle = Math.atan2(rY, rX) - (currentAngle - Math.PI/2);
        double powX = Math.cos(angle);
        double powY = Math.sin(angle) * 0.65;

        double maximizer = power / (Math.abs(powX) + Math.abs(powY));

        frontLeft.setPower(maximizer*(powY + powX) + checkDirection());
        frontRight.setPower(maximizer*(powY - powX) - checkDirection());
        backLeft.setPower(maximizer*(powY - powX) + checkDirection());
        backRight.setPower(maximizer*(powY + powX) - checkDirection());
    }

    /** Makes a path with provided points and drives on the path **/
    public void runPath(Path pointList, double power, double drift) {
        motorsOn();

        boolean driving = true;
        double velX = 0;
        double velY = 0;

        Point crossDist = new Point(0,0);
        Point target = new Point(0,0);
        Point stopPoint = new Point(0,0);

        Line pathLine = new Line(0, 0);
        Line robotLine = new Line(0, 0);

        int currentPoint = 0;

        Point pointOne = new Point(0,0);
        Point pointTwo = new Point(0,0);

        pointOne.setPoint(robot);
        pointTwo.setPoint(pointList.get(0));

        while (driving && !opMode.isStopRequested()) {

            target.setPoint(pointTwo);

            pathLine.setLine(pointOne, pointTwo);

            if (pathLine.slope != 0 && !pathLine.vertical) {
                robotLine.setLine(robot, -1 / pathLine.slope);
            } else if (pathLine.slope == 0) {
                robotLine.setVerticalLine(robot.x);
            } else if (pathLine.vertical) {
                robotLine.setLine(robot, 0);
            }

            stopPoint.setPoint(0.5 * (Math.pow(velX, 2) * drift), 0.5 * (Math.pow(velY, 2) * drift));

            target = target.subtract(stopPoint);

            target = target.subtract(robot);
            target.setPoint(target.x / Math.hypot(target.x, target.y) * 10, target.y / Math.hypot(target.x, target.y) * 10);
            target = target.add(robot);

            if (drift != 0) {
                crossDist.setPoint((pathLine.intersection(robotLine).x - robot.x) / drift, (pathLine.intersection(robotLine).y - robot.y) / drift);
                target = target.add(crossDist);
            }

            if (pointTwo.hasAngle) {
                setAngle = pointTwo.angle;
            } else {
                //setAngle = -Math.toDegrees(Math.atan2(velY, velX))-90;
            }

            pointDrive(target.x, target.y, power);

            opMode.telemetry.addData("target", target.x);
            opMode.telemetry.addData("target", target.y);
            opMode.telemetry.addData("current point", currentPoint);
            opMode.telemetry.addData("robot x", robot.x);
            opMode.telemetry.addData("robot y", robot.y);
            opMode.telemetry.addData("check", checkDirection());
            opMode.telemetry.addData("fl", frontLeft.getPower());
            opMode.telemetry.addData("fr", frontRight.getPower());
            opMode.telemetry.addData("bl", backLeft.getPower());
            opMode.telemetry.addData("br", backRight.getPower());
            opMode.telemetry.update();
            runtime.reset();

            if (pointOne.y < pointTwo.y && pointOne.x != pointTwo.x) {
                if (robot.y + stopPoint.y - pointTwo.y >= -1 / pathLine.slope * (robot.x + stopPoint.x - pointTwo.x)) {

                    if (currentPoint == pointList.length() - 1) {
                        driving = false;
                    } else {
                        pointOne = pointList.get(currentPoint);
                        pointTwo = pointList.get(currentPoint + 1);
                        currentPoint++;
                    }
                }
            } else if (pointOne.y > pointTwo.y) {
                if (robot.y + stopPoint.y - pointTwo.y <= -1 / pathLine.slope * (robot.x + stopPoint.x - pointTwo.x)) {

                    if (currentPoint == pointList.length() - 1) {
                        driving = false;
                    } else {
                        pointOne = pointList.get(currentPoint);
                        pointTwo = pointList.get(currentPoint + 1);
                        currentPoint++;
                    }
                }
            } else if (pointOne.y == pointTwo.y && pointOne.x < pointTwo.x) {
                if (robot.x + stopPoint.x >= pointTwo.x) {

                    if (pointTwo == pointList.get(pointList.length() - 1)) {
                        driving = false;
                    } else {
                        pointOne = pointList.get(currentPoint);
                        pointTwo = pointList.get(currentPoint + 1);
                        currentPoint++;
                    }
                }
            } else if (pointOne.y == pointTwo.y && pointOne.x > pointTwo.x) {
                if (robot.x + stopPoint.x <= pointTwo.x) {

                    if (pointTwo == pointList.get(pointList.length() - 1)) {
                        driving = false;
                    } else {
                        pointOne = pointList.get(currentPoint);
                        pointTwo = pointList.get(currentPoint + 1);
                        currentPoint++;
                    }
                }
            } else if (pointOne.x == pointTwo.x) {
                if (pointOne.y < pointTwo.y) {
                    if (robot.y + stopPoint.y >= pointTwo.y) {
                        if (pointTwo == pointList.get(pointList.length() - 1)) {
                            driving = false;
                        } else {
                            pointOne = pointList.get(currentPoint);
                            pointTwo = pointList.get(currentPoint + 1);
                            currentPoint++;
                        }
                    }
                } else if (pointOne.y > pointTwo.y) {
                    if (robot.y + stopPoint.y <= pointTwo.y) {
                        if (pointTwo == pointList.get(pointList.length() - 1)) {
                            driving = false;
                        } else {
                            pointOne = pointList.get(currentPoint);
                            pointTwo = pointList.get(currentPoint + 1);
                            currentPoint++;
                        }
                    }
                }
            }
            Thread.yield();
        }

        double errX;
        double errY;
        double finishPower;

        while(!(Math.pow(robot.x - pointTwo.x, 2) + Math.pow(robot.y - pointTwo.y, 2) < 1) && !opMode.isStopRequested()) {
            errX = robot.x - pointTwo.x;
            errY = robot.y - pointTwo.y;
            finishPower = Math.sqrt(Math.pow(errX, 2) + Math.pow(errY, 2)) / 30;
            if (finishPower > power) {
                finishPower = power;
            }
            pointDrive(pointTwo, finishPower);
        }
        stop();
    }


    //IMU angle correction methods

    /** Angle Correction **/
    public double checkDirection() {
        checkAngle = Math.toDegrees(-currentAngle + Math.PI/2);

        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction;
        double gain = 0.0125;

        correction = -(checkAngle-setAngle) * gain;        // reverse sign of angle for correction.

        return correction;
    }

    /** Resets the ange **/
    public void resetAngle() {
        lastAngles = 0;

        IMUAngle = 0;
    }

    /** Stops the robot **/
    public void stop() {
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }


    /** Deprecated in favor of using the tread in the main Drive class **/
    @Deprecated
    protected class PositionTracking extends Thread {
        public void run() {
            try {

                double robotX = 0;
                double robotY = 0;
                
                double lastEncoderX = 0;
                double lastEncoderY = 0;

                double deltaEncoderX;
                double deltaEncoderY;

                double deltaX;
                double deltaY;

                double lastVelX = 0;
                double lastVelY = 0;

                final double encoderRadius = 13;

                int loopEvent = 0;

                while(!Thread.interrupted() && opMode.opModeIsActive()) {

                    currentAngle = (encoderDistanceY2() - encoderDistanceY1())/(encoderRadius) + (Math.PI / 2);

                    deltaEncoderX = encoderDistanceX() - lastEncoderX;
                    deltaEncoderY = encoderDistanceY() - lastEncoderY;

                    deltaY = Math.sin(currentAngle) * deltaEncoderY + Math.sin(currentAngle - Math.PI/2) * deltaEncoderX;
                    deltaX = Math.cos(currentAngle) * deltaEncoderY + Math.cos(currentAngle - Math.PI/2) * deltaEncoderX;

                    robotX += deltaX;
                    robotY += deltaY;
                    robot.setPoint(robotX, robotY);

                    if(loopEvent==5) {
                        velX = (robot.x - lastVelX) * 20;
                        velY = (robot.y - lastVelY) * 20;
                        lastVelX = robot.x;
                        lastVelY = robot.y;
                        loopEvent = 0;
                    }

                    lastEncoderX = encoderDistanceX();
                    lastEncoderY = encoderDistanceY();

                    loopEvent++;

//                    opMode.telemetry.addData("robot x", robotX);
//                    opMode.telemetry.addData("robot y", robotY);
//                    opMode.telemetry.addData("robot.x", robot.x);
//                    opMode.telemetry.addData("robot.y", robot.y);
//                    opMode.telemetry.addData("currntAngle", currentAngle);
//                    opMode.telemetry.addData("angle degrees", getAngle());
//                    opMode.telemetry.update();

                    try {
                        Thread.sleep(10);
                    } catch(InterruptedException e) {
                        return;
                    }

                    Thread.yield();
                }

            } catch(Exception e) {
                //exception!
            }
        }
    }
}

