package nyc.c4q.hakeemsackes_bramble.sensormanager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by hakeemsackes-bramble on 2/23/17.
 */

public class SensorFusionActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor sensorAccel;
    Sensor sensorGyro;
    DecimalFormat df = new DecimalFormat("###.###");
    float[] outputGyro;
    float[] outputAccel;
    TextView accelX;
    TextView accelY;
    TextView accelZ;
    TextView gyroX;
    TextView gyroY;
    TextView gyroZ;
    private Path drawPath;
    private Canvas drawCanvas;
    private Paint drawPaint;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.value_display_layout);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGyro = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        sm.registerListener(this, sensorAccel, SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(this, sensorGyro, SensorManager.SENSOR_DELAY_NORMAL);
        accelX = (TextView) findViewById(R.id.xvalue);
        accelY = (TextView) findViewById(R.id.yvalue);
        accelZ = (TextView) findViewById(R.id.zvalue);
        gyroX = (TextView) findViewById(R.id.thetaxvalue);
        gyroY = (TextView) findViewById(R.id.thetayvalue);
        gyroZ = (TextView) findViewById(R.id.thetazvalue);
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setColor(Color.BLACK);

        linearLayout = (LinearLayout) findViewById(R.id.sensor_number_layout);
        outputAccel = new float[]{0, 0, 0};
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            outputAccel = event.values;
            accelX.setText("" + df.format(outputAccel[0]));
            accelY.setText("" + df.format(outputAccel[1]));
            accelZ.setText("" + df.format(outputAccel[2]));
        }
        if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            outputGyro = event.values;
            gyroX.setText("" + df.format(outputGyro[0]));
            gyroX.setText("" + df.format(outputGyro[1]));
            gyroX.setText("" + df.format(outputGyro[2]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void pageRefresher() {

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        accelX.invalidate();
        accelY.invalidate();
        accelZ.invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        pageRefresher();
        return true;
    }
}
