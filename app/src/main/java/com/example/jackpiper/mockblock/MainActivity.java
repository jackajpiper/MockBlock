package com.example.jackpiper.mockblock;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
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


        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DrawView thingView = findViewById(R.id.drawView);
                if (isChecked) {
                    // The toggle is enabled
                    Log.d("thing","switch enabled");
                    thingView.flipSwitch(true);

                } else {
                    // The toggle is disabled
                    Log.d("thing","switch disabled");
                    thingView.flipSwitch(false);
                }
            }
        });

        SeekBar seekBar = findViewById(R.id.seekDist);
        seekBar.setProgress(100);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            TextView distText = (TextView) findViewById(R.id.distText);
            distText.setText("Distance: " + progress);
            DrawView thingView = findViewById(R.id.drawView);
            thingView.changeLength(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
            DrawView thingView = findViewById(R.id.drawView);
            thingView.changeLength(seekBar.getProgress());
        }
    };

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
