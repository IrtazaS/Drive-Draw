package com.irtaza.drivedraw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import orbotix.robot.base.ConfigureLocatorCommand;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.graphics.PointF;

public class Draw {
	
	Canvas canvas;
	Bitmap bitmap;
	Bitmap scaledbitmap;
	static float mPositionX, mPositionY;
	final int displayWidth, displayHeight;
	int scaleddisplayWidth, scaleddisplayHeight;
	final int radius;
	HashMap<Float, Float> positionMap;
	ArrayList<PointF> coordinateList;
	static PointF drawingPoint;
	public Draw()
	{
		scaledbitmap = null;
		radius = 40;
		
		displayWidth = MainActivity.getInstance().displayWidth;
		displayHeight = MainActivity.getInstance().displayHeight;
		scaleddisplayWidth = displayWidth;
		scaleddisplayHeight = displayHeight;
		
		positionMap = new HashMap<Float, Float>();
		coordinateList = new ArrayList<PointF>();
		
		bitmap = Bitmap.createBitmap(displayWidth, displayHeight, Bitmap.Config.ARGB_8888);
		//Log.i("Screensize", screenHeight +" "+ screenWidth + screenheightChopped);
		canvas = new Canvas(bitmap);
		MainActivity.getInstance().imageView.setImageBitmap(bitmap);
	}
	
	public void SetPoints(float x, float y)
	{
		this.mPositionX = x;
		this.mPositionY = y;
	}
	public PointF GetPoints()
	{
		PointF points = new PointF(mPositionX, mPositionY);
		return points;
	}
	public PointF DrawMap()
	{
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		drawingPoint = new PointF();
		float drawPositionX = (LocationData.mPositionX) + (displayWidth/2);
		float drawPositionY = (LocationData.mPositionY) + (displayHeight/2);
		drawingPoint.x = drawPositionX;
		drawingPoint.y = drawPositionY;
		canvas.drawCircle(drawPositionX, drawPositionY, radius, paint);

		if(LocationData.lastPositionX != LocationData.mPositionX || LocationData.lastPositionY != LocationData.mPositionY)
		{
			//positionMap.put(drawPositionX, drawPositionY);
			PointF coordinates = new PointF(drawPositionX, drawPositionY);
			coordinateList.add(coordinates);
		}
		if(drawPositionX >= scaleddisplayWidth || drawPositionY >= scaleddisplayHeight)
    		DrawScaledBitmap();
		
		return drawingPoint;
	}
	
	public void DrawCollision()
	{
		Paint collisionPaint = new Paint();
		collisionPaint.setColor(Color.RED);
		canvas.drawCircle(drawingPoint.x, drawingPoint.y, radius, collisionPaint);
	}
	
	private void DrawScaledBitmap()
	{
		scaleddisplayWidth = scaleddisplayWidth + 600;
		scaleddisplayHeight = scaleddisplayHeight + 600;
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		scaledbitmap = Bitmap.createBitmap(scaleddisplayWidth, scaleddisplayHeight, Config.ARGB_8888);
		MainActivity.getInstance().imageView.setImageBitmap(scaledbitmap);
		canvas.setBitmap(scaledbitmap);
		
//		for (Entry<Float, Float> entry : positionMap.entrySet()) {
//			canvas.drawCircle(entry.getKey(), entry.getValue(), radius, paint);
//		}
		
		for (PointF point : coordinateList) {
			canvas.drawCircle(point.x, point.y, radius, paint);
		}
	}
	
	public void ClearBitmap()
	{
		LocationData.mPositionX = 0;
    	LocationData.mPositionY = 0;
    	positionMap.clear();
    	bitmap.eraseColor(0);
        Paint paintBitmap = new Paint();
        paintBitmap.setColor(Color.WHITE);
        if(scaledbitmap != null)
        {
        	scaledbitmap.eraseColor(0);
        	bitmap = Bitmap.createBitmap(displayWidth, displayHeight, Bitmap.Config.ARGB_8888);
    		//Log.i("Screensize", screenHeight +" "+ screenWidth + screenheightChopped);
    		canvas = new Canvas(bitmap);
        }
        scaledbitmap = null;
        canvas.drawColor(0);
        canvas.drawBitmap(bitmap, 0, 0, paintBitmap);
        MainActivity.getInstance().imageView.setImageBitmap(bitmap);
        ConfigureLocatorCommand.sendCommand(MainActivity.getInstance().mRobot,ConfigureLocatorCommand.ROTATE_WITH_CALIBRATE_FLAG_OFF, 0, 0, 0);
	}
}
