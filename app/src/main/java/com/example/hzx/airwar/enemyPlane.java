package com.example.hzx.airwar;

public class enemyPlane extends airPlane implements Runnable {
    private long speed = (long) (Math.random () * 10) + 10;

    public enemyPlane() {
        ;
        width = height = 200 * Plane.ratio;
        setX ( (float) (Math.random () * (Plane.width - width)) );
        setY ( -height );
        img = Plane.enemy;
        healthpoint = 12;
        Plane.list.add ( this );
        Plane.enemylist.add ( this );
        new Thread ( this ).start ();
    }

    @Override
    public void run() {
        while (healthpoint > 0) {
            try {
                Thread.sleep ( speed );
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            setY ( location.top + 2 * Plane.ratio );
            if (location.top >= Plane.height) break;
        }
        Plane.list.remove ( this );
        Plane.enemylist.remove ( this );
    }
}

