package com.example.mapview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Pair;
import android.view.MotionEvent;

import com.example.myapplication.R;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class RouteLayer extends MapBaseLayer {

    private List<Integer> routeList; // routes list
    private List<Pair<Integer,PointF>> nodeList; // nodes list

    private float routeWidth; // the width of route

    private Bitmap routeStartBmp;
    private Bitmap routeEndBmp;

    private Paint paint;
    private int floor;

    public RouteLayer(MapView mapView) {
        this(mapView, null, null);
    }

    public RouteLayer(MapView mapView, List<Pair<Integer,PointF>> nodeList, List<Integer> routeList) {
        super(mapView);
        this.nodeList = nodeList;
        this.routeList = routeList;
        level = ROUTE_LEVEL;

        initLayer();
    }

    private void initLayer() {
        this.routeWidth = 10;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        routeStartBmp = BitmapFactory.decodeResource(mapView.getResources(),
                R.mipmap.point_start);
        routeEndBmp = BitmapFactory.decodeResource(mapView.getResources(),
                R.mipmap.point_end);
    }

    @Override
    public void onTouch(MotionEvent event) {

    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, float
            currentRotateDegrees) {
        if (isVisible && routeList != null && nodeList != null) {
            canvas.save();

            drawing:
            if (!routeList.isEmpty() && !nodeList.isEmpty()) {
                // draw route
                for (int i = 0; i < routeList.size() - 1; i++) {
                    if (routeList.get(i) >= nodeList.size() ||
                            routeList.get(i + 1) >= nodeList.size()) {
                        break drawing;
                    }
                    if(nodeList.get(routeList.get(i)).first== floor &&nodeList.get(routeList.get(i+1)).first== floor) {
                        float[] goal1 = {nodeList.get(routeList.get(i)).second.x,
                                nodeList.get(routeList.get(i)).second.y};
                        float[] goal2 = {nodeList.get(routeList.get(i + 1)).second.x,
                                nodeList.get(routeList.get(i + 1)).second.y};
                        currentMatrix.mapPoints(goal1);
                        currentMatrix.mapPoints(goal2);
                        //For test
                        paint.setColor(Color.parseColor("#F9aa33"));
                        paint.setStrokeWidth(routeWidth);
                        canvas.drawLine(goal1[0], goal1[1], goal2[0], goal2[1], paint);
                    }
                }
                float[] goal1 = null;
                float[] goal2 = null;
                // draw bmp
                if(nodeList.get(routeList.get(0)).first== floor) {
                    goal1 = new float[]{nodeList.get(routeList.get(0)).second.x,
                            nodeList.get(routeList.get(0)).second.y};
                    currentMatrix.mapPoints(goal1);
                    canvas.drawBitmap(routeStartBmp,
                            goal1[0] - routeStartBmp.getWidth() / 2, goal1[1]
                                    - routeStartBmp.getHeight(), paint);
                }
                if(nodeList.get(routeList.get(routeList.size() - 1)).first== floor) {
                    goal2 = new float[]{
                            nodeList.get(routeList.get(routeList.size() - 1)).second.x,
                            nodeList.get(routeList.get(routeList.size() - 1)).second.y};
                    currentMatrix.mapPoints(goal2);
                    canvas.drawBitmap(routeEndBmp,
                            goal2[0] - routeEndBmp.getWidth() / 2, goal2[1]
                                    - routeEndBmp.getHeight(), paint);
                }
            }

            canvas.restore();

        }
    }

    public void setNodeList(List<Pair<Integer,PointF>> nodeList) {
        this.nodeList = nodeList;
    }

    public void setRouteList(List<Integer> routeList) {
        this.routeList = routeList;
    }
    public void setFloor (int floor){ this.floor =floor;}
}