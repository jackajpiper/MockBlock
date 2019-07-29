package com.example.jackpiper.mockblock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
//    Color myWhite = new Color(255, 255, 255); // Color white
    int x = 0;
    int y = 0;
    int leftNum = 7;
    int rightNum = 7;
    int topNum = 7;

    int fov = 90;

    int posX1 = 150;
    int posY1 = 150;
    int posX2 = 570;
    int posY2 = 570;

    int bestX1 = 0;
    int bestY1 = 0;
    int bestX2 = 0;
    int bestY2 = 0;

    boolean updateLine = true;

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
            double vis = visibility(160,160,560,560, 60,25+(incrementLeft*i),fov);
            drawBody(canvas, 60,25+(incrementLeft*i), vis);
        }

        int incrementRight = 670/(rightNum+1);
        for(int i = 1; i <= rightNum; i++) {
            double vis = visibility(160,160,560,560, 660,25+(incrementRight*i),fov);
            drawBody(canvas, 660,25+(incrementRight*i), vis);
        }

        int incrementTop = 670/(topNum+1);
        for(int i = 1; i <= topNum; i++) {
            double vis = visibility(160,160,560,560, 25+(incrementTop*i),60,fov);
            drawBody(canvas, 25+(incrementTop*i),60, vis);
        }






        if(updateLine) {
            double maxTotal = 0.0;

            bestX1 = 0;
            bestY1 = 0;
            bestX2 = 0;
            bestY2 = 0;

            Log.d("thing","ksbdfgsdkgjb");

            int centreX = 360;
            int centreY = 360;

            for(int i=0; i<360; i+=1) {

                double bearingRadians = Math.toRadians(i);

                int x1 = 360 + (int) Math.round(300*Math.cos(bearingRadians));
                int y1 = 360 + (int) Math.round(300*Math.sin(bearingRadians));
                int x2 = 360 - (int) Math.round(300*Math.cos(bearingRadians));
                int y2 = 360 - (int) Math.round(300*Math.sin(bearingRadians));

                if(x1<120) x1=120; else if(x1>600)x1=600;
                if(y1<120) y1=120; else if(y1>600)y1=600;
                if(x2<120) x2=120; else if(x2>600)x2=600;
                if(y2<120) y2=120; else if(y2>600)y2=600;

//                canvas.drawLine(x1,y1,x2,y2,paint);

                double total = 0.0;

                for(int n = 1; n <= leftNum; n++) {
                    total += visibility(x1,y1,x2,y2, 60,25+(incrementLeft*n),fov);
                }

                for(int n = 1; n <= rightNum; n++) {
                    total += visibility(x1,y1,x2,y2, 660,25+(incrementRight*n),fov);
                }

                for(int n = 1; n <= topNum; n++) {
//                TODO: Why the hell are there so many good seats with such a low fov
                    total += visibility(x1,y1,x2,y2, 25+(incrementTop*n),60,fov);
                }


                if(total>maxTotal) {
                    maxTotal = total;
                    bestX1 = x1;
                    bestY1 = y1;
                    bestX2 = x2;
                    bestY2 = y2;
                }
            }





        }

//        canvas.drawLine(bestX1, bestY1, bestX2, bestY2, paint);
//
//        paintFull.setColor(Color.RED);
//        canvas.drawCircle(bestX1,bestY1,20,paintFull);
//        paintFull.setColor(Color.GREEN);
//        canvas.drawCircle(bestX2,bestY2,20,paintFull);
//
//        canvas.drawLine(160, 160, 560, 560, paint);

        double bearing = getBearing(160,160,560,560);
        Log.d("thing","bearing isss: "+bearing+" and in radians: "+Math.toRadians(bearing));

        double bearingRadians = Math.toRadians(bearing);

//        TODO: why is this *400 here? Is it distance? Why is it necessary?
        int xthing = 160 + (int) Math.round(80*Math.cos(bearingRadians));
        int ything = 160 + (int) Math.round(80*Math.sin(bearingRadians));

//        canvas.drawLine(160,160,xthing,ything,paint);



        double angle1 = bearing+(fov/2);
        double angle2 = bearing-(fov/2);

        double angle1R = Math.toRadians(angle1);
        double angle2R = Math.toRadians(angle2);

        int distance = 80;
        int x1 = 160 + (int) Math.round(distance*Math.cos(angle1R));
        int y1 = 160 + (int) Math.round(distance*Math.sin(angle1R));

        int x2 = 160 + (int) Math.round(distance*Math.cos(angle2R));
        int y2 = 160 + (int) Math.round(distance*Math.sin(angle2R));

        Log.d("thing","angleR="+angle1R+", angle2R="+angle2R+"    ("+x1+","+y1+")    ("+x2+","+y2+")");


        Paint fovPaint = new Paint();
//        fovPaint.setColor(Color.YELLOW);
//        fovPaint.setStyle(Paint.Style.STROKE);
        float r = 180.0f;
        float g = 172.0f;
        float b = 0.0f;
        float a = 1.0f;
//        fovColour.alpha() = 0.2f;
        fovPaint.setColor(Color.GRAY);
        fovPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        Path fovView = new Path();
        fovView.moveTo(160,160);
        fovView.lineTo(x1,y1);
        fovView.lineTo(xthing,ything);
        fovView.lineTo(x2,y2);

//        canvas.drawPath(fovView,fovPaint);



//        canvas.drawLine(160,160,x1,y1,fovPaint);
//        canvas.drawLine(160,160,x2,y2,fovPaint);
//
//        canvas.drawLine(x1,y1,xthing,ything,fovPaint);
//        canvas.drawLine(x2,y2,xthing,ything,fovPaint);


//        int startAngle = (int) (180 / Math.PI * Math.atan2(y2 - y1, x2 - x1));
//        float radius = 80;
//        final RectF oval = new RectF();
//        oval.set(x1 - radius, y1 - radius, x1 + radius, y1 + radius);
//        Log.d("thing","x1 is "+x1+", y1 is "+y1+", x2 is "+x2+", y2 is "+y2);
//        Path myPath = new Path();
//        myPath.arcTo(oval, startAngle, fov, true);
//        canvas.drawPath(myPath, paint);

        Log.d("thing","Bearing is:      "+Double.toString(getBearing(160, 160, 560, 560)));



        canvas.drawLine(bestX1,bestY1,bestX2,bestY2,paint);
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






    public int incLeft() {
        leftNum++;
        updateLine = false;
        postInvalidate();
        return leftNum;
    }

    public int decLeft() {
        if(leftNum>0) leftNum--;
        updateLine = false;
        postInvalidate();
        return leftNum;
    }

    public int incRight() {
        rightNum++;
        updateLine = false;
        postInvalidate();
        return rightNum;
    }

    public int decRight() {
        if(rightNum>0) rightNum--;
        updateLine = false;
        postInvalidate();
        return rightNum;
    }

    public int incTop() {
        topNum++;
        updateLine = false;
        postInvalidate();
        return topNum;
    }

    public int decTop() {
        if(topNum>0) topNum--;
        updateLine = false;
        postInvalidate();
        return topNum;
    }

    public void refresh() {
        updateLine = true;
        postInvalidate();
    }

    public double getBearing(int posX1, int posY1, int posX2, int posY2) {

        float angle = (float) Math.toDegrees(Math.atan2(posY2 - posY1, posX2 - posX1));

//        Makes it a bearing, not sure why
//        angle -= 1;

        if(angle < 0){
            angle += 360;
        }

        if(angle >= 360) {
            angle -=360;
        }

//        return 3;
//        TODO: This is clearly being interpreted as radians, not degrees
        return angle;
    }

    public double visibility(int x1, int y1, int x2, int y2, int seatX, int seatY, int fov) {

//        HAHA! The way to determine occlusion by near actor is using tan(angle)=O/A where O is radius of actor circle and A is distance between actors
//        At the moment it's 2 degrees.




        double bearingMain1 = getBearing(x1,y1,x2,y2);
        double bearingMain2 = getBearing(x2,y2,x1,y1);
        double bearingSeat1 = getBearing(x1,y1,seatX,seatY);
        double bearingSeat2 = getBearing(x2,y2,seatX,seatY);

        double value1 = 0;
        double value2 = 0;

        double fovDouble = (double) fov;

        double diff1 = bearingMain1-bearingSeat1;
        double diff2 = bearingMain2-bearingSeat2;

        if(Math.abs(diff1) < fov && Math.abs(diff1) > 2) {
            value1 = Math.abs(diff1)/fovDouble;
//            Log.d("thing","YAAAAAAAAAAAY "+Double.toString((bearingMain-bearingSeat1)/45.0));
        }

        if(Math.abs(diff2) < fov && Math.abs(diff2) > 2) {
            value2 = Math.abs(diff2)/fovDouble;
        }

//        Log.d("thing","value1 = "+value1);

        double vis = (value1+value2)/2;

//        vis is now between 0 and 1
        return vis;
    }

}