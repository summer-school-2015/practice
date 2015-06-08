package com.example.apl;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class apl2 extends Activity implements SensorEventListener{
    /**
     * Called when the activity is first created.
     */
   // Sensor gyroscoper;
    Sensor ac;
    SensorManager sm;
    TextView tx;
    TextView ty;
    TextView tz;
    Button b;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
       // gyroscoper = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        ac = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,ac,SensorManager.SENSOR_DELAY_NORMAL);
       // sm.registerListener(this,gyroscoper,SensorManager.SENSOR_DELAY_NORMAL);
        tx = (TextView)findViewById(R.id.gx);
        ty = (TextView)findViewById(R.id.gy);
        tz = (TextView)findViewById(R.id.gz);
        b = (Button)findViewById(R.id.button);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tx.setText("X: "+event.values[0]);
        ty.setText("Y: "+event.values[1]);
        tz.setText("Z: "+event.values[2]);
            if (event.values[0]>0)
                b.setText("LEFT");
            else b.setText("RIGHT");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
