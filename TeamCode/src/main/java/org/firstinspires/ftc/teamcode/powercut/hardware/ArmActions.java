package org.firstinspires.ftc.teamcode.powercut.hardware;

import androidx.annotation.NonNull;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.PIDEx;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.powercut.RobotSettings;

public class ArmActions extends ArmSystem {
    public class PresetArm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            boolean isReset = armResetTouchSensor.isPressed();

            if (isReset) {
                armMotor.setPower(0);
                resetArmEncoder();
                return false;
            } else {
                armMotor.setPower(0.4);
                return true;
            }

        }
    }

    public Action presetArm() {
        return new PresetArm();
    }

    public class PresetWrist implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            boolean isReset = wristResetTouchSensor.isPressed();

            if (isReset) {
                wristMotor.setPower(0);
                resetWristEncoder();
                return false;
            } else {
                wristMotor.setPower(0.1);
                return true;
            }

        }
    }

    public Action presetWrist() {
        return new PresetWrist();
    }

    public class ArmToResetPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (armResetTouchSensor.isPressed()) {
                resetArmEncoder();
                return false;
            } else {

                double armTarget = 0;
                packet.put("armTarget", armTarget);

                double armPos = armMotor.getCurrentPosition();
                packet.put("armPos", armPos);

                double armPower;
                armPower = armPID.calculate(armTarget, armPos);
                packet.put("armPower", armPower);

                armMotor.setPower(armPower);


                if ((Math.abs(armMotor.getCurrentPosition()) > Math.abs(armTarget) - RobotSettings.armDeadband) && (Math.abs(armMotor.getCurrentPosition()) < Math.abs(armTarget) + RobotSettings.armDeadband)) {
                    armMotor.setPower(0);
                    return false;
                } else {
                    return true;
                }

            }

        }
    }

    public Action armToResetPosition() {
        return new ArmToResetPosition();
    }

    public class WristToResetPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (wristResetTouchSensor.isPressed()) {
                resetWristEncoder();
                return false;
            } else {

                double wristTarget = 0;
                packet.put("wristTarget", wristTarget);

                double wristPos = wristMotor.getCurrentPosition();
                packet.put("wristPos", wristPos);

                double wristPower;
                wristPower = wristPID.calculate(wristTarget, wristPos);
                packet.put("wristPower", wristPower);

                wristMotor.setPower(wristPower);

                if ((Math.abs(wristMotor.getCurrentPosition()) > Math.abs(wristTarget) - RobotSettings.wristDeadband) && (Math.abs(wristMotor.getCurrentPosition()) < Math.abs(wristTarget) + RobotSettings.wristDeadband)) {
                    wristMotor.setPower(0);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public Action wristToResetPosition() {
        return new WristToResetPosition();
    }


    // set grip
    public void gripLeftActivate() {
        gripLeft.setPosition(0.09);
    }

    public class GripLeftActivate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripLeftActivate();
            return false;
        }
    }

    public Action gripLeftActivateAction() {
        return new GripLeftActivate();
    }


    public void gripRightActivate() {
        gripRight.setPosition(0.09);
    }

    public class GripRightActivate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripRightActivate();
            return false;
        }
    }

    public Action gripRightActivateAction() {
        return new GripRightActivate();
    }


    public void gripLeftRelease() {
        gripLeft.setPosition(0.15);
    }

    public class GripLeftRelease implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripLeftRelease();
            return false;
        }
    }

    public Action gripLeftReleaseAction() {
        return new GripLeftRelease();
    }


    public void gripRightRelease() {
        gripRight.setPosition(0.15);
    }

    public class GripRightRelease implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripRightRelease();
            return false;
        }
    }

    public Action gripRightReleaseAction() {
        return new GripLeftRelease();
    }

    public void gripLeftTuck() {
        gripLeft.setPosition(0.5);
    }

    public class GripLeftTuck implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripLeftTuck();
            return false;
        }
    }

    public Action gripLeftTuckAction() {
        return new GripLeftTuck();
    }

    public void gripRightTuck() {
        gripRight.setPosition(0.5);
    }

    public class GripRightTuck implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripRightTuck();
            return false;
        }
    }

    public Action gripRightTuckAction() {
        return new GripRightTuck();
    }

    public class GripActivate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripLeftActivate();
            gripRightActivate();
            return false;
        }
    }

    public Action gripActivate() {
        return new GripActivate();
    }

    public class GripRelease implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripLeftRelease();
            gripRightRelease();
            return false;
        }
    }

    public Action gripRelease() {
        return new GripRelease();
    }

    public class GripTuck implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            gripLeftTuck();
            gripRightTuck();
            return false;
        }
    }

    public Action gripTuck() {
        return new GripTuck();
    }

    //BIG STUFF
    public class ArmUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            double armTarget = RobotSettings.armUpPosition;
            packet.put("armTarget", armTarget);

            double armPos = armMotor.getCurrentPosition();
            packet.put("armPos", armPos);

            double armPower;
            armPower = armPID.calculate(armTarget, armPos);
            packet.put("armPower", armPower);

            armMotor.setPower(armPower);


            if ((Math.abs(armMotor.getCurrentPosition()) > Math.abs(armTarget) - RobotSettings.armDeadband) && (Math.abs(armMotor.getCurrentPosition()) < Math.abs(armTarget) + RobotSettings.armDeadband)) {
                armMotor.setPower(0);
                packet.put("Arm complete", 1);
                return false;
            } else {
                return true;
            }


        }
    }

    public Action armUp() {
        return new ArmUp();
    }

    public class WristUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            double wristTarget = RobotSettings.wristUpPosition;
            packet.put("wristTarget", wristTarget);

            double wristPos = wristMotor.getCurrentPosition();
            packet.put("wristPos", wristPos);

            double wristPower;
            wristPower = wristPID.calculate(wristTarget, wristPos);
            packet.put("wristPower", wristPower);

            wristMotor.setPower(wristPower);

            if ((Math.abs(wristMotor.getCurrentPosition()) > Math.abs(wristTarget) - RobotSettings.wristDeadband) && (Math.abs(wristMotor.getCurrentPosition()) < Math.abs(wristTarget) + RobotSettings.wristDeadband)) {
                wristMotor.setPower(0);
                packet.put("Wrist complete", 1);
                return false;
            } else {
                return true;
            }

        }
    }

    public Action wristUp() {
        return new WristUp();
    }

    public class ArmDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (armResetTouchSensor.isPressed()) {
                resetArmEncoder();
                return false;
            } else {
                double armTarget = RobotSettings.armDownPosition;
                packet.put("armTarget", armTarget);

                double armPos = armMotor.getCurrentPosition();
                packet.put("armPos", armPos);

                double armPower;
                armPower = armPID.calculate(armTarget, armPos);
                packet.put("armPower", armPower);

                armMotor.setPower(armPower);

                if ((Math.abs(armMotor.getCurrentPosition()) > Math.abs(armTarget) - RobotSettings.armDeadband) && (Math.abs(armMotor.getCurrentPosition()) < Math.abs(armTarget) + RobotSettings.armDeadband)) {
                    armMotor.setPower(0);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public Action armDown() {
        return new ArmDown();
    }

    public class WristDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            double wristTarget = RobotSettings.wristDownPosition;
            packet.put("wristTarget", wristTarget);

            double wristPos = wristMotor.getCurrentPosition();
            packet.put("wristPos", wristPos);

            double wristPower;
            wristPower = wristPID.calculate(wristTarget, wristPos);
            packet.put("wristPower", wristPower);

            wristMotor.setPower(wristPower);

            if ((Math.abs(wristMotor.getCurrentPosition()) > Math.abs(wristTarget) - RobotSettings.wristDeadband) && (Math.abs(wristMotor.getCurrentPosition()) < Math.abs(wristTarget) + RobotSettings.wristDeadband)) {
                wristMotor.setPower(0);
                return false;
            } else {
                return true;
            }

        }
    }

    public Action wristDown() {
        return new WristDown();

    }
}
