package com.example.hzx.airwar;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class AirWar extends View{
    private Paint p = new Paint ();
    private float x, y;
    private float myx, myy;//主机坐标

    public AirWar(Context context) {
        super ( context );
        setOnTouchListener ( new OnTouchListener () {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if (e.getAction () == MotionEvent.ACTION_DOWN) {
                    x = e.getX ();
                    y = e.getY ();
                    myx = Plane.myair.location.left;
                    myy = Plane.myair.location.top;
                }
                float xx = myx + e.getX () - x;
                float yy = myy + e.getY () - y;
                xx = xx < Plane.width - Plane.myair.width / 2 ? xx : Plane.width - Plane.myair.width / 2;
                xx = xx > -Plane.myair.width / 2 ? xx : -Plane.myair.width / 2;
                yy = yy < Plane.height - Plane.myair.height / 2 ? yy : Plane.height - Plane.myair.height / 2;
                yy = yy > -Plane.myair.height / 2 ? yy : -Plane.myair.height / 2;
                Plane.myair.setX ( xx );
                Plane.myair.setY ( yy );
                return true;
            }
        } );

        Plane.plane = BitmapFactory.decodeResource ( getResources (), R.drawable.plane );
        Plane.enemy = BitmapFactory.decodeResource ( getResources (), R.drawable.enemy );
        Plane.bullet = BitmapFactory.decodeResource ( getResources (), R.drawable.bullet );
        Plane.background = BitmapFactory.decodeResource ( getResources (), R.drawable.background );

        new Thread ( new repaint () ).start ();
        new Thread ( new loadenemy () ).start ();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw ( canvas );
        canvas.drawBitmap ( Plane.backdrop.img, null, Plane.backdrop.location, p );

        for (int i = 0; i < Plane.list.size (); i++) {
            airPlane h = Plane.list.get ( i );
            canvas.drawBitmap ( h.img, null, h.location, p );
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        super.onSizeChanged ( width, height, width, oldheight );
        Plane.width = width;
        Plane.height = height;
        Plane.ratio = (float) (Math.sqrt ( Plane.width * Plane.height ) / Math.sqrt ( 1920 * 1080 ));
        p.setTextSize ( 50 * Plane.ratio );
        p.setColor ( Color.WHITE );
        Plane.backdrop = new backdrop ();
        Plane.myair = new myPlane ();
    }

    private class repaint implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep ( 10 );
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
                postInvalidate ();
            }
        }
    }
    private class loadenemy implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep ( 1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
                try {
                    new enemyPlane ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        }
    }

    static class myBullet extends airPlane implements Runnable {
        private int damage;
        private float speed;

        public myBullet(airPlane airplane) {
            width = height = 30 * Plane.ratio;
            img = Plane.bullet;
            speed = 6 * Plane.ratio;
            damage = 6;
            setX ( airplane.location.left + airplane.width / 2 - width / 2 );
            setY ( airplane.location.top - height / 2 );
            Plane.list.add ( this );
            new Thread ( this ).start ();
        }

        @Override
        public void run() {
            boolean flag = false;
            while (true) {
                try {
                    Thread.sleep ( 5 );
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
                setY ( location.top - speed );

                try {
                    for (int i = 0; i < Plane.enemylist.size (); i++) {
                        airPlane h = Plane.enemylist.get ( i );
                        if (Bump ( h, 30 )) {
                            h.healthpoint -= damage;
                            flag = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                    break;
                }
                if (flag || location.top + height <= 0) break;
            }
            Plane.list.remove ( this );
        }
    }
}