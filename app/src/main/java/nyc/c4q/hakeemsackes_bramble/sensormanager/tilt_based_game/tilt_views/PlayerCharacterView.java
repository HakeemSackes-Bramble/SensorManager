package nyc.c4q.hakeemsackes_bramble.sensormanager.tilt_based_game.tilt_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import nyc.c4q.hakeemsackes_bramble.sensormanager.tilt_based_game.game_model.GameValues;


/**
 * Created by hakeemsackes-bramble on 2/11/17.
 */

public class PlayerCharacterView extends View {

    private Paint pathPaint;
    private float xVelocity;
    private float yVelocity;
    private float pCAdderX;
    private float pCAdderY;
    private Paint paint;
    private float cRadius;
    private float heightPC;
    private float widthPC;
    GameValues gameValues;
    private Path drawPath;
    private Canvas drawCanvas;
    ArrayList<Path> pathArrays;
    private boolean isDrawing;
    private float touchX;
    private float touchY;
    private float xPosition;
    private float yPosition;
    private Bitmap newBitmap;
    private float xPicPosition;
    private float yPicPosition;
    float playerRadius;
    float deviationRatio;
    private float ballBounce;

    public PlayerCharacterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        cRadius = 20;
        paint.setAntiAlias(true);
        paint.setColor(Color.DKGRAY);
        drawPath = new Path();
        drawCanvas = new Canvas();
        pathArrays = new ArrayList<>();
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(10);
        pathPaint.setColor(Color.BLACK);
        ballBounce = .9f;

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEvent(event);
                return false;
            }
        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        heightPC = this.getMeasuredHeight();
        widthPC = this.getMeasuredWidth();
        xVelocity = 0;
        yVelocity = 0;
        xPosition = widthPC / 2;
        yPosition = heightPC / 2;
        xPicPosition = 0 % gameValues.getBackGround().getWidth();
        yPicPosition = 0 % gameValues.getBackGround().getHeight();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pCAdderX = gameValues.getOutputAccel()[0];
        pCAdderY = gameValues.getOutputAccel()[1];
        xVelocity -= pCAdderX / 4;
        yVelocity += pCAdderY / 4;

        if (xPosition < 0 || xPosition > this.getMeasuredWidth()) {
            xVelocity *= -1 * ballBounce;

        }
        if (yPosition < 0 || yPosition > this.getMeasuredHeight()) {
            yVelocity *= -1 * ballBounce;
        }
        xPosition += xVelocity / 2;
        yPosition += yVelocity / 2;


        if (isDrawing) {
            pathArrays.get(pathArrays.size() - 1).moveTo(touchX, touchY);
            pathArrays.get(pathArrays.size() - 1).lineTo(xPosition, yPosition);
            touchX = xPosition;
            touchY = yPosition;
        }
        for (Path path : pathArrays) {
            canvas.drawPath(path, pathPaint);
        }


        // playerRadius = getWidth() / 8;
        // deviationRatio = 1 - distanceFromPoint(xVelocity, yVelocity) / playerRadius;
//         xPicPosition = (xPicPosition - pCAdderX) % (gameValues.getBackGround().getWidth() - 640);
//         yPicPosition = (yPicPosition + pCAdderY) % (gameValues.getBackGround().getHeight() - 960);
//        if (xPicPosition < 0) {
//            xPicPosition += gameValues.getBackGround().getWidth() - 640;
//        }
//        if (yPicPosition < 0) {
//            yPicPosition += gameValues.getBackGround().getHeight() - 960;
//        }
//
//        newBitmap = Bitmap.createBitmap(gameValues.getBackGround(),
//                (int) xPicPosition,
//                (int) yPicPosition,
//                640,
//                960);

        canvas.drawCircle(xPosition, yPosition, cRadius, paint);
        //this.setImageBitmap(newBitmap);
    }

    public void setGameValues(GameValues gameValues) {
        this.gameValues = gameValues;
    }


    public float distanceFromPoint(double x, double y) {
        return (float) Math.sqrt(Math.pow(x - this.getWidth() / 2, 2) + Math.pow(y - this.getHeight() / 2, 2));
    }

    public boolean onTouchEvent(MotionEvent event) {
        touchX = xPosition;
        touchY = yPosition;
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                pathArrays.add(new Path());
                isDrawing = true;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, pathPaint);
                isDrawing = false;
                break;
            default:
                return false;
        }
        return true;
    }

    protected void pageRefresher() {

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();

    }
}
