package com.example.jackpiper.mockblock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class DrawView extends View {
    Paint paint = new Paint();
    Paint paintFull = new Paint();
    int x = 0;
    int y = 0;
    int leftNum = 7;
    int rightNum = 7;
    int topNum = 7;

    int posX1 = 150;
    int posY1 = 150;
    int posX2 = 570;
    int posY2 = 570;

    private void init() {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paintFull.setColor(Color.GRAY);
        paintFull.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawRect(25, 25, 695, 695, paint);


        paintFull.setColor(Color.GRAY);

//        Full length is 670
//        Half length is 335.
//        Half way is 360.

        int incrementLeft = 670/(leftNum+1);
        for(int i = 1; i <= leftNum; i++) {
            double vis = visibility(150,150,570,570, 60,25+(incrementLeft*i),45);
            drawBody(canvas, 60,25+(incrementLeft*i), vis);
        }

        int incrementRight = 670/(rightNum+1);
        for(int i = 1; i <= rightNum; i++) {
            double vis = visibility(150,150,570,570, 660,25+(incrementRight*i),45);
            drawBody(canvas, 660,25+(incrementRight*i), vis);
        }

        int incrementTop = 670/(topNum+1);
        for(int i = 1; i <= topNum; i++) {
            double vis = visibility(150,150,570,570, 25+(incrementTop*i),60,45);
            drawBody(canvas, 25+(incrementTop*i),60, vis);
        }















        int bestX1 = 0;
        int bestY1 = 0;
        int bestX2 = 0;
        int bestY2 = 0;

        Random rand = new Random();
        double maxTotal = 0.0;
        for(int i = 0; i<1000000; i++) {

            if(10000%(i+1) == 0) Log.d("thing", "Ten thousand down");
            int x1 = rand.nextInt((570 - 150) + 1) + 150;
            int y1 = rand.nextInt((570 - 150) + 1) + 150;
            int x2 = rand.nextInt((570 - 150) + 1) + 150;
            int y2 = rand.nextInt((570 - 150) + 1) + 150;

            double total = 0.0;

            for(int n = 1; n <= leftNum; n++) {
                total += visibility(150,150,570,570, 60,25+(incrementLeft*n),10);
            }

            for(int n = 1; n <= rightNum; n++) {
                total += visibility(150,150,570,570, 660,25+(incrementRight*n),10);
            }

            for(int n = 1; n <= topNum; n++) {
//                TODO: Why the hell are there so many good seats with such a low fov
                total += visibility(150,150,570,570, 25+(incrementTop*n),60,10);
            }


            if(total>maxTotal) {
                maxTotal = total;
                bestX1 = x1;
                bestY1 = y1;
                bestX2 = x2;
                bestY2 = y2;
            }

        }

        canvas.drawLine(bestX1, bestY1, bestX2, bestY2, paint);

        paintFull.setColor(Color.RED);
        canvas.drawCircle(bestX1,bestY1,20,paintFull);
        paintFull.setColor(Color.GREEN);
        canvas.drawCircle(bestX2,bestY2,20,paintFull);









    }


    public void drawBody(Canvas canvas, int x, int y, double vis) {

        if(vis>0) {
            paintFull.setColor(Color.GREEN);
            paintFull.setStyle(Paint.Style.FILL);
            int a = (int) Math.round(vis*255);
            paintFull.setAlpha(255-a);
//            Log.d("thing","VISIBLE, alpha = "+Integer.toString(255-a));
        }
        else {
            paintFull.setColor(Color.GRAY);
            paintFull.setStyle(Paint.Style.STROKE);
            paintFull.setAlpha(255);
//            Log.d("thing","NOT VISIBLE");
        }

//        Log.d("thing","alpha ============ "+paintFull.getAlpha());

        canvas.drawCircle(x, y-5, 8, paintFull);
        RectF oval = new RectF(x - 12, y+2, x + 12, y + 38);
        canvas.drawArc(oval, 180, 180, true, paintFull);
        canvas.drawRect(x-20, y-20, x+20, y+20, paint);

    }

//    public void change (int xChange, int yChange) {
////        path = new Path();
////        invalidate();
//        x += xChange;
//        y += yChange;
//        postInvalidate();
//    }

    public int incLeft() {
        leftNum++;
        postInvalidate();
        return leftNum;
    }

    public int decLeft() {
        if(leftNum>0) leftNum--;
        postInvalidate();
        return leftNum;
    }

    public int incRight() {
        rightNum++;
        postInvalidate();
        return rightNum;
    }

    public int decRight() {
        if(rightNum>0) rightNum--;
        postInvalidate();
        return rightNum;
    }

    public int incTop() {
        topNum++;
        postInvalidate();
        return topNum;
    }

    public int decTop() {
        if(topNum>0) topNum--;
        postInvalidate();
        return topNum;
    }

    public int getBearing(int posX1, int posY1, int posX2, int posY2) {

        float angle = (float) Math.toDegrees(Math.atan2(posY2 - posY1, posX2 - posX1));

        if(angle < 0){
            angle += 360;
        }
//        Makes it a bearing, not sure why
        angle += 90;

        if(angle >= 360) {
            angle -=360;
        }

        return (int) Math.round(angle);
    }

    public double visibility(int x1, int y1, int x2, int y2, int seatX, int seatY, int fov) {

        int bearingMain1 = getBearing(x1,y1,x2,y2);
        int bearingMain2 = getBearing(x2,y2,x1,y1);
        int bearingSeat1 = getBearing(x1,y1,seatX,seatY);
        int bearingSeat2 = getBearing(x2,y2,seatX,seatY);

        double value1 = 0;
        double value2 = 0;

        double fovDouble = (double) fov;

        if(Math.abs(bearingMain1-bearingSeat1) < fov && Math.abs(bearingMain1-bearingSeat1) > 5) {
            value1 = Math.abs(bearingMain1-bearingSeat1)/fovDouble;
//            Log.d("thing","YAAAAAAAAAAAY "+Double.toString((bearingMain-bearingSeat1)/45.0));
        }

        if(Math.abs(bearingMain2-bearingSeat2) < fov && Math.abs(bearingMain2-bearingSeat2) > 5) {
            value2 = Math.abs(bearingMain2-bearingSeat2)/fovDouble;
        }

//        Log.d("thing","value1 = "+value1);

        double vis = (value1+value2)/2;

//        vis is now between 0 and 1
        return vis;
    }

}