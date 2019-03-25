package com.example.hzx.airwar;


public class myPlane extends airPlane implements Runnable {

    public myPlane(){
        width = height = 200 * Plane.ratio;
        setX ( Plane.width / 2 - width / 2 );
        setY ( Plane.height * 0.7f - height / 2 );
        img = Plane.plane;
        Plane.list.add ( this );
        new Thread ( this ).start ();
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep ( 200 );
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            new AirWar.myBullet ( this );
        }
    }
}
