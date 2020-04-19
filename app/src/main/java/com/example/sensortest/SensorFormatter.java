package com.example.sensortest;

import android.hardware.Sensor;

class SensorFormatter {

    static String[] format(Sensor sensor) {

        if (sensor == null) {
            return new String[] {"No sensor found"};
        } else {
            return new String[] {
                    "Name: " + sensor.getName(),
                    "Vendor: " + sensor.getVendor(),
                    "Type: " + sensor.getType()
            };
        }
    }
}
