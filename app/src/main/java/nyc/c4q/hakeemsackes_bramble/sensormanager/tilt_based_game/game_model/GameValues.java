package nyc.c4q.hakeemsackes_bramble.sensormanager.tilt_based_game.game_model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nyc.c4q.hakeemsackes_bramble.sensormanager.R;

/**
 * Created by hakeemsackes-bramble on 2/16/17.
 */
public class GameValues {

    private static final float ALPHA = 0.1f;
    float[] accelerometer;
    float[] outputAccel = new float[]{0, 0, 0};
    float playerDeviationRatio;
    private float playerXPosition;
    private float playerYPosition;
    Bitmap backGround;
    DecimalFormat df = new DecimalFormat("###.#");
    ArrayList<float[]> window = new ArrayList<>();


    public GameValues(Context context) {
        this.backGround = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.copybee);
    }


    protected void lowPass() {
        if (outputAccel == null) {
            outputAccel = accelerometer;
        }
        for (int i = 0; i < accelerometer.length; i++) {
            outputAccel[i] = Float.valueOf(df.format(outputAccel[i] + ALPHA * (accelerometer[i] - outputAccel[i])));
        }
    }

    protected void slidingWindow(float[] newInput) {
        float[] firstInput = {0, 0, 0};
        window.add(newInput);
        if (window.size() > 1000) {
            firstInput = window.get(0);
            window.remove(0);
        }
        for (int i = 0; i < newInput.length; i++) {
            newInput[i] += Float.valueOf(df.format((accelerometer[i] + newInput[i] - firstInput[i]) / window.size()));
        }
    }

    public float[] getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(float[] newAccelerometer) {
        this.accelerometer = newAccelerometer;
        slidingWindow(accelerometer);
        lowPass();
    }

    public float getPlayerDeviationRatio() {
        return playerDeviationRatio;
    }

    public void setPlayerDeviationRatio(float playerDeviationRatio) {
        this.playerDeviationRatio = playerDeviationRatio;
    }

    public float getPlayerXPosition() {
        return playerXPosition;
    }

    public void setPlayerXPosition(float playerXPosition) {
        this.playerXPosition = playerXPosition;
    }

    public float getPlayerYPosition() {
        return playerYPosition;
    }

    public void setPleyarYPosition(float playerYPosition) {
        this.playerYPosition = playerYPosition;
    }

    public float[] getOutputAccel() {
        return outputAccel;
    }

    public void setOutputAccel(float[] outputAccel) {
        this.outputAccel = outputAccel;
    }

    public Bitmap getBackGround() {
        return backGround;
    }

    public void setBackGround(Bitmap backGround) {
        this.backGround = backGround;
    }

}
