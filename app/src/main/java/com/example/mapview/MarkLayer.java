package com.example.mapview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myapplication.R;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarkLayer extends MapBaseLayer {

    private Map<String, PointF> marks;
    private MarkIsClickListener oneTouchListener;
    private MarkIsClickListener twoTouchListener;

    private Bitmap bmpMark, bmpMarkTouch;

    private float radiusMark;
    private boolean isClickMark = false;
    private String nameClicked;

    int floor;

    private Paint paint;

    public MarkLayer(MapView mapView) {
        this(mapView, null, 0);
    }

    public MarkLayer(MapView mapView, Map<String, PointF> marks, int floor) {
        super(mapView);
        this.marks = marks;
        level = MARK_LEVEL;
        this.floor = floor;
        initLayer();
    }

    private void initLayer() {
        radiusMark = setValue(10f);

        bmpMark = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.mark);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onTouch(MotionEvent event) {
        int count = 0;
        if (marks != null) {
            if (!marks.isEmpty()) {
                float[] goal = mapView.convertMapXYToScreenXY(event.getX(), event.getY());
                for (Map.Entry<String, PointF> entry : marks.entrySet()) {
                    if (floor == (entry.getKey().charAt(0) - '0')) {
                        if (MapMath.getDistanceBetweenTwoPoints(goal[0], goal[1],
                                entry.getValue().x - bmpMark.getWidth() / 2, entry.getValue().y - bmpMark
                                        .getHeight() / 2) <= 50) {
                            nameClicked = entry.getKey();
                            isClickMark = true;
                            break;
                        }

                        if (count == marks.size() - 1) {
                            isClickMark = false;
                        }
                        count++;
                    }
                }
            }

            if (oneTouchListener != null && isClickMark) {
                oneTouchListener.markIsClick(nameClicked);
                mapView.refresh();
            }
        }
    }

    @Override
    public void onTwoTouch(MotionEvent event) {
        int count = 0;
        if (marks != null) {
            if (!marks.isEmpty()) {
                float[] goal = mapView.convertMapXYToScreenXY(event.getX(), event.getY());
                for (Map.Entry<String, PointF> entry : marks.entrySet()) {
                    if (entry.getKey().charAt(0) == floor) {
                        if (MapMath.getDistanceBetweenTwoPoints(goal[0], goal[1],
                                entry.getValue().x - bmpMark.getWidth() / 2, entry.getValue().y - bmpMark
                                        .getHeight() / 2) <= 50) {
                            nameClicked = entry.getKey();
                            isClickMark = true;
                            break;
                        }

                        if (count == marks.size() - 1) {
                            isClickMark = false;
                        }
                        count++;
                    }
                }
            }

            if (twoTouchListener != null && isClickMark) {
                twoTouchListener.markIsClick(nameClicked);
                mapView.refresh();
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, float
            currentRotateDegrees) {
        if (isVisible && marks != null) {
            canvas.save();
            if (!marks.isEmpty()) {
                for (Map.Entry<String, PointF> entry : marks.entrySet()) {
                    PointF mark = entry.getValue();
                    String markName = entry.getKey();
                    if (floor == (markName.charAt(0) - '0')) {
                        float[] goal = {mark.x, mark.y};
                        currentMatrix.mapPoints(goal);

                        paint.setColor(Color.BLACK);
                        paint.setTextSize(radiusMark);
                        //mark name
                        if (mapView.getCurrentZoom() > 1.0 && markName != null) {
                            canvas.drawText(markName, goal[0] - radiusMark, goal[1] -
                                    radiusMark / 2, paint);
                        }
                        //mark ico
                        canvas.drawBitmap(bmpMark, goal[0] - bmpMark.getWidth() / 2,
                                goal[1] - bmpMark.getHeight() / 2, paint);
                    }
                }
            }
            canvas.restore();
        }
    }

    public String getNameClicked() {
        return nameClicked;
    }

    public void setNum(String nameClicked) {
        this.nameClicked = nameClicked;
    }

    public Map<String, PointF> getMarks() {
        return marks;
    }

    public void setMarks(Map<String, PointF> marks) {
        this.marks = marks;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isClickMark() {
        return isClickMark;
    }

    public void setOneTouchMarkIsClickListener(MarkIsClickListener listener) {
        this.oneTouchListener = listener;
    }

    public void setTwoTouchMarkIsClickListener(MarkIsClickListener listener) {
        this.twoTouchListener = listener;
    }

    public interface MarkIsClickListener {
        void markIsClick(String nameClicked);
    }
}
