package com.example.jackpiper.mockblock;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.WHITE);
//        setContentView(drawView);

        setContentView(R.layout.activity_main);

//        int bearing = drawView.getBearing(150, 150, 570, 570);
//        TextView bearingText = findViewById(R.id.bearingText);
//        bearingText.setText(Integer.toString(bearing));
    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }

//    public void addX(View view) {
//        DrawView thingView = findViewById(R.id.drawView);
//        thingView.change(-50,0);
//    }
//
//    public void subtractX(View view) {
//        DrawView thingView = findViewById(R.id.drawView);
//        thingView.change(50,0);
//    }
//
//    public void addY(View view) {
//        DrawView thingView = findViewById(R.id.drawView);
//        thingView.change(0,-50);
//    }
//
//    public void subtractY(View view) {
//        DrawView thingView = findViewById(R.id.drawView);
//        thingView.change(0,50);
//    }

    public void incLeft(View view) {
        DrawView thingView = findViewById(R.id.drawView);
        int num = thingView.incLeft();

        TextView text = (TextView) findViewById(R.id.leftCount);
        text.setText(Integer.toString(num));
    }

    public void decLeft(View view) {
        DrawView thingView = findViewById(R.id.drawView);
        int num = thingView.decLeft();

        TextView text = (TextView) findViewById(R.id.leftCount);
        text.setText(Integer.toString(num));
    }

    public void incRight(View view) {
        DrawView thingView = findViewById(R.id.drawView);
        int num = thingView.incRight();

        TextView text = (TextView) findViewById(R.id.rightCount);
        text.setText(Integer.toString(num));
    }

    public void decRight(View view) {
        DrawView thingView = findViewById(R.id.drawView);
        int num = thingView.decRight();

        TextView text = (TextView) findViewById(R.id.rightCount);
        text.setText(Integer.toString(num));
    }

    public void incTop(View view) {
        DrawView thingView = findViewById(R.id.drawView);
        int num = thingView.incTop();

        TextView text = (TextView) findViewById(R.id.topCount);
        text.setText(Integer.toString(num));
    }

    public void decTop(View view) {
        DrawView thingView = findViewById(R.id.drawView);
        int num = thingView.decTop();

        TextView text = (TextView) findViewById(R.id.topCount);
        text.setText(Integer.toString(num));
    }

    public void refreshDraw(View vew) {
        DrawView thingView = findViewById(R.id.drawView);
        thingView.refresh();
        Log.d("thing","GOH DARN REFRESH");
    }

}
