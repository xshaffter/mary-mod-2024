package com.paramada.marycum2024.math;

import net.minecraft.util.math.Vec3d;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public class Rect {
    private double pitchSin;
    private double pitchCos;
    private double yawCos;
    private double yawSin;
    private final double pointX;
    private final double pointY;
    private final double pointZ;
    private double yaw;
    private double pitch;
    private double maxLenght;


    public Rect(double pointX, double pointY, double pointZ, double yaw, double pitch) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.pointZ = pointZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.yawSin = sin(Functions.toRadians(yaw));
        this.yawCos = cos(Functions.toRadians(yaw));
        this.pitchSin = sin(Functions.toRadians(pitch));
        this.pitchCos = cos(Functions.toRadians(pitch));
    }

    public Rect(double pointX, double pointY, double pointZ) {
        this(pointX, pointY, pointZ, 0, 0);
    }

    public Rect(Vec3d eyeCenter, float yaw, float pitch) {
        this(eyeCenter.x, eyeCenter.y, eyeCenter.z, yaw, pitch);
    }

    private Vec3d getDirectionVector() {
        return new Vec3d(pitchCos * yawCos, pitchSin, pitchCos * yawSin);
    }


    public double distanceToVector(Vec3d vec) {
        var directionVector = getDirectionVector();
        var pMinusR = vec.subtract(this.toVert3d());
        var cross = pMinusR.crossProduct(directionVector);

        var normaC = norm(cross);
        var normaD = norm(directionVector);

        return normaC / normaD;
    }

    private double norm(Vec3d vector) {
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
    }


    public Vec3d toVert3d() {
        return new Vec3d(pointX, pointY, pointZ);
    }

    @Override
    public String toString() {
        var fmt = new DecimalFormat("#.##");
        return "(%s,%s,%s) (%s,%s)".formatted(fmt.format(pointX), fmt.format(pointY), fmt.format(pointZ), fmt.format(yaw), fmt.format(pitch));
    }

    public void proyect(double maxDistance) {
        this.maxLenght = maxDistance;
    }
}
