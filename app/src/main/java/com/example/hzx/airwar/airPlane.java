package com.example.hzx.airwar;

import android.graphics.Bitmap;
import android.graphics.RectF;

//飞机父类
public class airPlane {
    public RectF location = new RectF ();
    public int healthpoint;
    public float width, height;
    public Bitmap img;

    public void setX(float x) {
        location.left = x;
        location.right = x + width;
    }
    public void setY(float y) {
        location.top = y;
        location.bottom = y + height;
    }

    public boolean Bump(airPlane obj, float px) {
        px *= Plane.ratio;
        if (location.left + px - obj.location.left <= obj.width && obj.location.left - this.location.left + px <= this.width - px - px)
            if (location.top + px - obj.location.top <= obj.height && obj.location.top - location.top + px <= height - px - px) {
                return true;
            }
        return false;
    }
}
