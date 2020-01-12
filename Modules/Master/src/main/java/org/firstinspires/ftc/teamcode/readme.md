#

So this is coding....

Done right.

The Phantom Implementation of True Agile Programming.

Or The PITAP Structure.

    Q: So how do you code in this new structure?

    A: Well it's quite simple, just Follow these rules:
        1: Make decisions that are easy to change later.
        2: Have a separate class for each hardware object and all its control functions so, it is easy to troubleshoot them.
        3: Have the classes named with the common name of the hardware object.
        4: Have a class that is called ("robot {}".format([the iteration of the robot class])), if you knew Python you would understand...
        5: Your Autonomous programs should extend LinearOpMode and supply a "hardwaremap" object to the robot class which will supply to each of the hardware object classes.
        6: Your hardware object classes should have TeleOp in mind when you create them.
        7: Write TeleOp programs in a "while" loop and they should extend LinearOpMode.
        8: All your programs should create a new object of your "robot" class which can supply you access to your hardware objects.
        9: One time use programs should go in the ThrowAways module.
       10: Test and debug programs for the hardware objects should go in the HardwareDebug module.
       11: Separate classes may be created to co-ordinate multiple hardware objects.
       12: Classes co-ordinating multiple hardware objects should be to if all the objects need each other to function.
       13: Hardware object class can depend on other hardware object classes.
       14: Robot Function Groups (RFGs) may be created to manage dependent hardware object classes and their dependencies.
       15: Complex web structures should be avoided at all costs.

    Q: Why is this structure better than all of the existing structures?

    A: Writing programs can be made much easier and faster and troubleshooting bugs is simple because of a lack of a web.