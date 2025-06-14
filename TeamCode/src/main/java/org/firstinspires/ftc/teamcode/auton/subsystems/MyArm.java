package org.firstinspires.ftc.teamcode.auton.subsystems;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.FieldLocations.*;

public class MyArm {
    private final DcMotorEx armMotor, viperMotor;
    private final ServoImplEx wristServo, twistServo, gripServo;
    private final ElapsedTime timer = new ElapsedTime();

    public MyArm(HardwareMap hardwareMap){
        armMotor = hardwareMap.get(DcMotorEx.class, "viperslide axis");
        viperMotor = hardwareMap.get(DcMotorEx.class, "viperslide extension");
        wristServo = (ServoImplEx) hardwareMap.get(Servo.class, "wrist servo");
        twistServo = (ServoImplEx) hardwareMap.get(Servo.class, "twist servo");
        gripServo = (ServoImplEx) hardwareMap.get(Servo.class, "intake servo");
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setPower(0);
        viperMotor.setPower(0);
    }

    public class MoveArmTo implements Action {
        private boolean initialized = false;
        private int armTarget;
        private int armVelocity;


        public MoveArmTo(int armTarget, int armVelocity) {
            this.armTarget = armTarget;
            this.armVelocity = armVelocity;
        }

            @Override
            public boolean run(TelemetryPacket packet) {
                double armPos = armMotor.getCurrentPosition();
                if (!initialized && Math.abs(armPos - armTarget) > 15) {

                    timer.reset();
                    armMotor.setTargetPosition(armTarget);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setVelocity(armVelocity);
                    //while (armMotor.isBusy()) {}
                    initialized = true;
                }
                //while (armMotor.isBusy()) {}
                packet.put("ArmPos", armPos);
                packet.put("ArmTaget", armTarget);

                if (armPos + 15 < armTarget) {
                    if(timer.seconds() <= 2) {
                        return true;
                    }
                    else { return false;}
                } else {
                    //armMotor.setPower(0);
                    return false;
                }


                //return !armMotor.isBusy();
            }
    }
    public Action moveArmTo(int armTarget, int armVelocity) {
        return new MoveArmTo(armTarget, armVelocity);
    }



    public class MoveViperTo implements Action {
        private boolean initialized = false;
        private int vipTarget;
        private int vipVelocity;

        public MoveViperTo(int vipTarget, int vipVelocity) {
            this.vipTarget = vipTarget;
            this.vipVelocity = vipVelocity;
        }
        @Override
        public boolean run(TelemetryPacket packet) {
            double vipPos = viperMotor.getCurrentPosition();
            if (!initialized && Math.abs(vipPos - vipTarget) > 15) {
                timer.reset();
                viperMotor.setTargetPosition(vipTarget);
                viperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                viperMotor.setVelocity(vipVelocity);
                //while (viperMotor.isBusy()) {}
                initialized = true;
            }
            //while (armMotor.isBusy()) {}
            packet.put("ViperPos", vipPos);
            packet.put("ViperTarget", vipTarget);

            if (vipPos + 15 < vipTarget) {
                if(timer.seconds() <= 2) {
                    return true;
                }
                else { return false;}
            } else {
                //viperMotor.setPower(0);
                return false;
            }


            //return false;
            //return !viperMotor.isBusy();
        }
    }

    public Action moveViperTo(int target, int velocity) {
        return new MoveViperTo(target, velocity);
    }


    public class TwistTo implements Action {
        private boolean initialized = false;
        private double target;
        private int velocity;

        public TwistTo(double target) {
            this.target = target;

        }
        @Override
        public boolean run(TelemetryPacket packet) {
            if (!initialized) {
                timer.reset();


                initialized = true;
            }
            twistServo.setPosition(target);
            return timer.seconds() <= servo_delay;
        }

    }
    public Action twistTo(double target) {

        return new TwistTo(target);
    }

    public class WristTo implements Action {
        private boolean initialized = false;
        private double target;
        private int velocity;

        public WristTo(double target) {
            this.target = target;

        }
        @Override
        public boolean run(TelemetryPacket packet) {


            if (!initialized) {
                timer.reset();


                initialized = true;
            }
            wristServo.setPosition(target);
            return timer.seconds() <= servo_delay + .25;
        }

    }
    public Action wristTo(double target) {

        return new WristTo(target);
    }

    public class ArmOff implements Action {
        private boolean initialized = false;



        @Override
        public boolean run(TelemetryPacket packet) {


            armMotor.setPower(0);
            return false; //run will run repeatedly until it returns false
        }

    }
    public Action armOff() {

        return new ArmOff();
    }




    public class GrabTo implements Action {
        private boolean initialized = false;
        private double target;
        private int velocity;

        public GrabTo(double target) {
            this.target = target;

        }
        @Override
        public boolean run(TelemetryPacket packet) {


            if (!initialized) {
                timer.reset();


                initialized = true;
            }
            gripServo.setPosition(target);
            if (target < .4){
                return timer.seconds() <= servo_delay;}
            else {
                return timer.seconds() <= .3;
            }
        }
    }
    public Action grabTo(double target2) {
        return new GrabTo(target2);
    }
    public void initArm() {
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void initViper() {
        viperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }




    /*public void moveViperTo(int target, int velocity) {
        viperMotor.setTargetPosition(target);
        viperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperMotor.setVelocity(velocity);
    }*/
    public int getArmPosition() {
        return armMotor.getCurrentPosition();
    }
    public int getViperPosition() {
        return viperMotor.getCurrentPosition();
    }

}
