package nyc.c4q.hakeemsackes_bramble.sensormanager;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nyc.c4q.hakeemsackes_bramble.sensormanager.tilt_based_game.game_model.GameValues;
import nyc.c4q.hakeemsackes_bramble.sensormanager.tilt_based_game.tilt_views.BackgroundView;
import nyc.c4q.hakeemsackes_bramble.sensormanager.tilt_based_game.tilt_views.PlayerCharacterView;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private static final float ALPHA = 0.1f;
    /**
     * TODO: 1) implement a dynamic alpha value that relates the error in measurement to the error in estimate for improved accuracy
     * TODO: 2) ADD all shared values to GameValues class for one single Model class
     */

    SensorManager sm;
    Sensor sensorAccel;
    Sensor sensorGyro;
    /**
     * this will be needed for implementing future sensor fusion code;
     */
    float[] input;
    float[] output;
    DecimalFormat df = new DecimalFormat("###.#");
    ArrayList<float[]> window = new ArrayList<>();
    PlayerCharacterView playerCharacterView;
    BackgroundView backgroundView;
    GameValues gameValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGyro = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sm.registerListener(this, sensorAccel, SensorManager.SENSOR_DELAY_GAME);
      //  sm.registerListener(this, sensorGyro, SensorManager.SENSOR_DELAY_GAME);
        output = new float[]{0f, 0f, 0f};
        playerCharacterView = (PlayerCharacterView) findViewById(R.id.player_view);
        backgroundView = (BackgroundView) findViewById(R.id.background_view);
        /**
         *  game values will be the place where i'll store all values shared by other views
         *  event.values
         *  float playerDeviationRatio
         *  private float playerXPosition;
         *  private float playerYPosition;
         *  Bitmap backGround;
         */
        gameValues = new GameValues(getApplicationContext());

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        gameValues.setAccelerometer(event.values);
        playerCharacterView.setGameValues(gameValues);
//        slidingWindow(input);
//        lowPass(input);
//        playerCharacterView.setpCAdderX(-2 * gameValues.getOutputAccel()[0]);
//        playerCharacterView.setpCAdderY(2 *  gameValues.getOutputAccel()[1]);
//        backgroundView.setbGAdderX(-2 * output[0]);
//        backgroundView.setbGAdderY(2 * output[1]);
       pageRefresher();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void lowPass(float[] in) {
        if (output == null) {
            output = in;
        }
        for (int i = 0; i < in.length; i++) {
            output[i] = Float.valueOf(df.format(output[i] + ALPHA * (in[i] - output[i])));
        }
    }

    protected void slidingWindow(float[] newInput) {
        float[] firstInput;
        for (int i = 0; i < newInput.length; i++) {
            window.add(newInput);
            if (window.size() > 1000) {
                firstInput = window.get(0); window.remove(0);
                newInput[i] += Float.valueOf(df.format((input[i] + newInput[i] - firstInput[i]) / window.size()));
            } else {
                newInput[i] += Float.valueOf(df.format((input[i] + newInput[i]) / window.size()));
            }
        }
    }

    protected void pageRefresher() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerCharacterView.invalidate();
    }
}
