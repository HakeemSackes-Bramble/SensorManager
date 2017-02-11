package nyc.c4q.hakeemsackes_bramble.sensormanager;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final float ALPHA = 0.5f;
    TextView xText, yText, zText;
    SensorManager sm;
    Sensor sensorAccel;
    Sensor sensorGyro;
    int smoothCounter = 0;
    float[] input;
    float[] output;
    ArrayList<float[]> window = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGyro = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sm.registerListener(this, sensorAccel, SensorManager.SENSOR_DELAY_GAME);
        xText = (TextView) findViewById(R.id.xvalue);
        yText = (TextView) findViewById(R.id.yvalue);
        zText = (TextView) findViewById(R.id.zvalue);
        output = new float[]{0f, 0f, 0f};
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        input = event.values;
        slidingWindow(input);
        lowPass(input);

        xText.setText("X: " + output[0]);
        yText.setText("Y: " + output[1]);
        zText.setText("Z: " + output[2]);
        System.out.println(Math.sqrt(Math.pow(output[0],2) + Math.pow(output[1],2) + Math.pow(output[2],2)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void lowPass(float[] in) {
        float[] inputs = in;
        if (output == null) {
            output = inputs;
        }
        for (int i = 0; i < inputs.length; i++) {
            output[i] = output[i] + ALPHA * (inputs[i] - output[i]);
        }
    }

    protected void slidingWindow(float[] newInput) {
        float[] firstInput;

        for (int i = 0; i < newInput.length; i++) {
            window.add(newInput);
            if (window.size() > 10000) {
                firstInput = window.get(0);
                window.remove(0);
                input[i] += (input[i] + newInput[i] - firstInput[i]) / window.size();
            } else {
                input[i] += (input[i] + newInput[i]) / window.size();
            }
        }
       // System.out.println(Math.sqrt(Math.pow(window.get(0)[0],2) + Math.pow(window.get(0)[1],2) + Math.pow(window.get(0)[2],2)));
    }
}
