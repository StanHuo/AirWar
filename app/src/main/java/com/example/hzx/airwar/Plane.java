package com.example.hzx.airwar;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Vector;

//静态全局变量
public class Plane extends AirWar{
    public static int width, height;
    public static float ratio;
    public static Vector<airPlane> list = new Vector <airPlane> ();
    public static Vector <airPlane> enemylist = new Vector <airPlane> ();
    public static Bitmap plane, enemy, background, bullet;
    public static myPlane myair;
    public static backdrop backdrop;

    public Plane(Context context) {
        super ( context );
    }
}
