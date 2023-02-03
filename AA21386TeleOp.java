package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp
public class AA21386TeleOp extends LinearOpMode {
    private DcMotor Arm;
    private DcMotor RF;
    private DcMotor LF;
    private DcMotor RB;
    private DcMotor LB;
    private Servo Claw;
    
    private double LIFT_POWER = 0.45;
    private int LIFT_INCREMENT = 50;
    private int MAX_LIFT_POS = 4500;
    
    
    @Override
    public void runOpMode() throws InterruptedException {
        double Liftpower=0.35;
        Arm = hardwareMap.get(DcMotor.class, "motor");
        RF = hardwareMap.get(DcMotor.class, "RF");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        LB = hardwareMap.get(DcMotor.class, "LB");
        Claw = hardwareMap.get(Servo.class, "Claw");
        double ServoPosition;
        double ServoSpeed;
        int currentPos;
        ServoPosition = 1;
        ServoSpeed = 1;
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setDirection(DcMotorSimple.Direction.REVERSE);
        
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up){
                //Incremental Slide up
                Arm.setPower(LIFT_POWER);
                currentPos = Arm.getCurrentPosition();
                if (currentPos < MAX_LIFT_POS )
                    Arm.setTargetPosition(currentPos + LIFT_INCREMENT );
            }
            else if (gamepad1.dpad_down){
                //Incremental Slide down
                Arm.setPower(LIFT_POWER);
                currentPos = Arm.getCurrentPosition();
                if (currentPos >  LIFT_INCREMENT)
                    Arm.setTargetPosition(currentPos - LIFT_INCREMENT );
                
            }
            if (gamepad1.x) {
                //Slide resets to 0 position
                Arm.setTargetPosition(0);
                Arm.setPower(LIFT_POWER);
                Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Claw.setPosition(.65);
                while (Arm.isBusy()) {
                    telemetry.addData("Current Position", Arm.getCurrentPosition());
                    telemetry.addData("Target Position", Arm.getTargetPosition());
                    telemetry.update();
                }
                Arm.setPower(0);
            }
            else if (gamepad1.a) {
                //Slide presets to height = small junction
                Arm.setTargetPosition(1750);
                Arm.setPower(LIFT_POWER);
                Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (Arm.isBusy()) {
                    telemetry.addData("Current Position", Arm.getCurrentPosition());
                    telemetry.addData("Target Position", Arm.getTargetPosition());
                    telemetry.update();
                }
                //Arm.setPower(0.45);
            }
            else if (gamepad1.b) {
                //Slide presets to height = mediium junction
                Arm.setTargetPosition(2800);
                Arm.setPower(LIFT_POWER);
                Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                
                while (Arm.isBusy()) {
                    telemetry.addData("Current Position", Arm.getCurrentPosition());
                    telemetry.addData("Target Position", Arm.getTargetPosition());
                    telemetry.update();
                }
                //Arm.setPower(0.45);
            }
            else if (gamepad1.y) {
                //Slide presets to height = high junction
                Arm.setTargetPosition(4000);
                Arm.setPower(LIFT_POWER);
                Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (Arm.isBusy()) {
                    telemetry.addData("Current Position", Arm.getCurrentPosition());
                    telemetry.addData("Target Position", Arm.getTargetPosition());
                    telemetry.update();
                }
                //Arm.setPower(0.45);
            }
            if (gamepad1.left_bumper) {
                Claw.setPosition(.25);
                ServoPosition += ServoSpeed;
                telemetry.addData("Opening","Claw is opening");
                telemetry.update();
            }
            else if (gamepad1.right_bumper) {
                Claw.setPosition(.65);
                ServoPosition += -ServoSpeed;
                telemetry.addData("Closing","Claw is closing");
                telemetry.update();
            }
            // Keep Servo position in valid range
            ServoPosition = Math.min(Math.max(ServoPosition, 0), 1);
            telemetry.addData("Servo", ServoPosition);
            telemetry.update();
            
            //Logic to move forward/backward
            if (gamepad1.left_stick_y != 0) {
                LF.setDirection(DcMotorSimple.Direction.FORWARD);
                RF.setDirection(DcMotorSimple.Direction.REVERSE);
                LB.setDirection(DcMotorSimple.Direction.FORWARD);
                RB.setDirection(DcMotorSimple.Direction.REVERSE);
                LF.setPower(1 * gamepad1.left_stick_y);
                RF.setPower(1 * gamepad1.left_stick_y);
                LB.setPower(1 * gamepad1.left_stick_y);
                RB.setPower(1 * gamepad1.left_stick_y);
            }
            //Logic to turn left/right
            if (gamepad1.right_stick_x != 0) {
                LB.setDirection(DcMotorSimple.Direction.FORWARD);
                LF.setDirection(DcMotorSimple.Direction.FORWARD);
                RB.setDirection(DcMotorSimple.Direction.FORWARD);
                RF.setDirection(DcMotorSimple.Direction.FORWARD);
                LF.setPower(-1 * 1 * gamepad1.right_stick_x);
                RF.setPower(-1 * 1 * gamepad1.right_stick_x);
                LB.setPower(-1 * 1 * gamepad1.right_stick_x);
                RB.setPower(-1 * 1 * gamepad1.right_stick_x);
            }
            //Logic to STRAFE left/right
            if (gamepad1.left_stick_x != 0) {
              LB.setDirection(DcMotorSimple.Direction.FORWARD);
              LF.setDirection(DcMotorSimple.Direction.REVERSE);
              RB.setDirection(DcMotorSimple.Direction.FORWARD);
              RF.setDirection(DcMotorSimple.Direction.REVERSE);
              LF.setPower(gamepad1.left_stick_x * 1);
              RF.setPower(gamepad1.left_stick_x * 1);
              LB.setPower(gamepad1.left_stick_x * 1);
              RB.setPower(gamepad1.left_stick_x * 1);
            }
            telemetry.update();
            LF.setPower(0);
            RF.setPower(0);
            LB.setPower(0);
            RB.setPower(0);
            //Arm.setPower(0);
            
         }
    }
}
