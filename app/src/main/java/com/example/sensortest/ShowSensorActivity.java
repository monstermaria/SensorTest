package com.example.sensortest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sensor);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        Intent intent = getIntent();
        String sensorName = intent.getStringExtra("sensor");
        int type = Integer.parseInt(intent.getStringExtra("type"));
        List<Sensor> deviceSensors = sensorManager.getSensorList(type);
        Log.d("Sensor", deviceSensors.toString());
        for (Sensor s : deviceSensors) {
            Log.d("Sensor", s.getName());
            if (s.getName().equals(sensorName)) {
                Log.d("Sensor", "found");
                sensor = s;
                break;
            }
        }

        ListView listView = findViewById(R.id.listViewSensorInfo);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, SensorFormatter.format(sensor)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //super.onAccuracyChanged();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        String text = "Sensor values:\n";
        TextView textView = findViewById(R.id.textViewSensorValues);
        textView.setText(text);

        ArrayList<String> textValues = new ArrayList<String>();

        for (int i = 0; i < values.length; i ++) {
            textValues.add(Float.toString(values[i]));
        }
        ListView listView = findViewById(R.id.listViewSensorValues);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, textValues));

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
