package com.example.hzx.airwar;

public class backdrop extends airPlane implements Runnable {

    public backdrop() {
        width = Plane.width;
        height = Plane.height * 2;
        img = Plane.background;
        setX ( 0 );
        setY ( -Plane.height );
        new Thread ( this ).start ();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep ( 10 );
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            if (location.top + 2 <= 0) {
                setY ( location.top + 2 );
            } else {
                setY ( -Plane.height );
            }
        }
    }
}