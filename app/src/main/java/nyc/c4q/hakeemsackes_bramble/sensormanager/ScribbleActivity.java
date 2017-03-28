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

import java.text.DecimalFormat;

/**
 * Created by hakeemsackes-bramble on 2/25/17.
 */

public class ScribbleActivity extends AppCompatActivity implements SensorEventListener {

    private Path drawPath;
    private Canvas drawCanvas;
    private Paint drawPaint;
    SensorManager sm;
    Sensor sensorAccel;
    DecimalFormat df = new DecimalFormat("###.###");
    float[] outputAccel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setColor(Color.BLACK);
        outputAccel = new float[]{0, 0, 0};
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        outputAccel = event.values;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(this);
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

    protected void pageRefresher() {

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
