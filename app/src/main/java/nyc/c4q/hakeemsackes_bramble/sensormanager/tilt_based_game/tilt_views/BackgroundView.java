package nyc.c4q.hakeemsackes_bramble.sensormanager.tilt_based_game.tilt_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hakeemsackes-bramble on 2/14/17.
 */

public class BackgroundView extends View {

    /**
     * not ready to implement yet
     * GameValues gameValues;
     */
    private float xPosition;
    private float yPosition;
    private float bGAdderX;
    private float bGAdderY;
    private Bitmap bitmap;
    private float heightBG;
    private float widthBG;
    private Bitmap newBitmap;


    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        heightBG = this.getMeasuredHeight();
        widthBG = this.getMeasuredWidth();
        yPosition = heightBG / 2;
        xPosition = widthBG / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        xPosition = (xPosition - bGAdderX) % bitmap.getWidth();
//        yPosition = (yPosition - bGAdderY) % bitmap.getHeight();
//      //  newBitmap = Bitmap.createBitmap(bitmap, (int) xPosition,
//       //         (int) yPosition, (int) widthBG, (int) heightBG);
    }

    public float getbGAdderY() {
        return bGAdderY;
    }

    public void setbGAdderY(float bGAdderY) {
        this.bGAdderY = bGAdderY;
    }

    public float getbGAdderX() {
        return bGAdderX;
    }

    public void setbGAdderX(float bGAdderX) {
        this.bGAdderX = bGAdderX;
    }

}
