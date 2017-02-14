package nyc.c4q.hakeemsackes_bramble.sensormanager.tilt_based_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;


/**
 * Created by hakeemsackes-bramble on 2/11/17.
 */

public class TiltCharacterView extends View {

    float xPosition = 0;
    float yPosition = 0;
    private Paint paint;
    private float cRadius;


    public TiltCharacterView(Context context, float xPosition, float yPosition) {
        super(context);
        this.xPosition += xPosition;
        this.yPosition += yPosition;
        paint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(xPosition,yPosition,cRadius, paint);
    }
}
