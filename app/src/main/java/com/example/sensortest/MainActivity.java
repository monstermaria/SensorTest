package com.example.sensortest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private List<Sensor> availableSensors;
    private List<Map<String, String>> sensorInfo;
    private TextView textView;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        availableSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorInfo = new ArrayList<>();
        for (Sensor s : availableSensors) {
            HashMap m = new HashMap(3);
            m.put("name", s.getName());
            m.put("typeString", s.getStringType());
            m.put("typeInt", s.getType());
        }

        textView = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);

        String text = "Number of available sensors: " + availableSensors.size();
        textView.setText(text);


        ArrayAdapter<Sensor> adapter = new ArrayAdapter<Sensor>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                availableSensors
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new TestOnClick());
    }

    private class TestOnClick implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Sensor s = (Sensor) parent.getItemAtPosition(position);
            Log.d("Help", "Before toast");
            Toast.makeText(getApplicationContext(), s.getName(), Toast.LENGTH_SHORT).show();
            openShowSensor(s.getName(), Integer.toString(s.getType()));
        }
    }
    void openShowSensor(String sensorName, String sensorType) {

        Intent i = new Intent(this, ShowSensorActivity.class);
        i.putExtra("sensor", sensorName);
        i.putExtra("type", sensorType);

        startActivity(i);
    }
 }
