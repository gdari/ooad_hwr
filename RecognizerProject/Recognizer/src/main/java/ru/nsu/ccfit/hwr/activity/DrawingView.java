package ru.nsu.ccfit.hwr.activity;

/**
 * Created by Баира on 21.12.13.
 */

import android.content.Context;
import android.graphics.*;
import android.os.AsyncTask;
import android.util.*;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.ccfit.hwr.logic.recognition.data.PointsContainer;
import ru.nsu.ccfit.hwr.logic.recognition.data.Point;

public class DrawingView extends View {
    private Path drawPath; 
    private Paint drawPaint, canvasPaint; 
    private final int paintColor = 0xFF660000;
    private Canvas drawCanvas; 
    private Bitmap canvasBitmap; 
    private PointsContainer curContainer;
    private final Drawing model = Drawing.getInstance();

    private final List<SmoothingAsyncTask> currentTask = new ArrayList<SmoothingAsyncTask>();

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    public void startNew(){
        drawCanvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        drawPath.reset();
        //drawPaint.reset();
        invalidate();
    }

    private void setupDrawing(){
        //get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(8);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    void drawStrokes() {
        startNew();
        if (model.getPoints().size() > 0) {
            for (PointsContainer pointsContainer : model.getPoints()) {
                List<Point> points = pointsContainer.getPoints();
                if (points.isEmpty()) continue;
                float oldX = points.get(0).x;
                float oldY = points.get(0).y;

                for (int i = 1; i < points.size(); ++i) {
                    drawPath.moveTo(oldX, oldY);
                    drawPath.lineTo(points.get(i).x, points.get(i).y);
                    oldX = points.get(i).x;
                    oldY = points.get(i).y;
                }
            }
        }
        invalidate(); // the force a view to draw
    }

    void commit(PointsContainer pointsContainer) {
        // todo : smoothing task
        SmoothingAsyncTask task = new SmoothingAsyncTask();
        currentTask.add(task);
        task.execute(pointsContainer);

    }

    private class SmoothingAsyncTask extends AsyncTask<PointsContainer, Integer, PointsContainer> {
        public PointsContainer doInBackground(PointsContainer ...pointsContainers) {
            PointsContainer container = pointsContainers[0];
            container.trim();
            return container;
        }
        protected void onPostExecute(PointsContainer container) {
            model.add(container);
            currentTask.remove(this);
            drawStrokes();// TODO :
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view given size
        //super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
       // super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //stroke.clear();
                curContainer = new PointsContainer();
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                //stroke.addPoint(touchX, touchY);
                curContainer.addPoint(new Point(touchX, touchY));
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                //model.addNewStroke(stroke);
                if (curContainer.size() > 0)
                    commit(curContainer);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
}
