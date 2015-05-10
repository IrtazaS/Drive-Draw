package com.irtaza.drivedraw;

import java.util.ArrayList;
import orbotix.robot.base.ConfigureLocatorCommand;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.PointF;
import android.util.Log;

/**
 * 
 * @author irtaza Die Klasse Draw zeichnet einen Bitmap mit einem Canvas (Zeichenfläche), auf der die Kartierung
 * erfolgt. Dabei wird Spheros Position auf der Zeichenfläche gezeichnet.
 * Liegt die Position außerhalb der Zeichenfläche, so wird die Zeichenfläche vergrößert.
 *
 */
public class Draw {
	
	Canvas canvas;
	Bitmap bitmap;
	Bitmap scaledbitmap;
	static float mPositionX, mPositionY;
	final int displayWidth, displayHeight;
	int scaleddisplayWidth, scaleddisplayHeight;
	int radius;
	int alpha;
	Paint paint;
	ArrayList<PointF> coordinateList;
	static PointF drawingPoint;
	public Draw()
	{
		scaledbitmap = null;
		radius = Integer.parseInt(MainActivity.getInstance().settings.getString("stroke_width", "10"));

		alpha = 110;
		displayWidth = MainActivity.getInstance().displayWidth;
		displayHeight = MainActivity.getInstance().displayHeight;
		scaleddisplayWidth = displayWidth;
		scaleddisplayHeight = displayHeight;
		
		coordinateList = new ArrayList<PointF>();
		
		bitmap = Bitmap.createBitmap(displayWidth, displayHeight, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
		//canvas.translate(0,canvas.getHeight());   // reset where 0,0 is located
		//canvas.scale(1,-1);    // invert
		MainActivity.getInstance().imageView.setImageBitmap(bitmap);
	}
	
	public PointF DrawMap()
	{
		paint = new Paint();
		paint.setARGB(ColorPicker.getAlpha(), ColorPicker.getRed(), ColorPicker.getGreen(), ColorPicker.getBlue());
		drawingPoint = new PointF();
		LocationData.mPositionY *= -1;
		float drawPositionX = (LocationData.mPositionX) + (displayWidth/2);
		float drawPositionY = (LocationData.mPositionY) + (displayHeight/2);
		Log.i("DrawingParameter: ", "drawPositionX: " +  drawPositionX + "drawPositionY: "+drawPositionY + "scaleddisplaywidth: "+scaleddisplayWidth + "scaleddsplayheight: " + scaleddisplayHeight);
		drawingPoint.x = drawPositionX;
		drawingPoint.y = drawPositionY;
		canvas.drawCircle(drawPositionX, drawPositionY, radius, paint);

		if(LocationData.lastPositionX != LocationData.mPositionX || LocationData.lastPositionY != LocationData.mPositionY)
		{
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
		scaleddisplayWidth = scaleddisplayWidth + (displayWidth/3);
		scaleddisplayHeight = scaleddisplayHeight + (displayWidth/3);
		paint.setARGB(ColorPicker.getAlpha(), ColorPicker.getRed(), ColorPicker.getGreen(), ColorPicker.getBlue());
		scaledbitmap = Bitmap.createBitmap(scaleddisplayWidth, scaleddisplayHeight, Config.ARGB_8888);
		MainActivity.getInstance().imageView.setImageBitmap(scaledbitmap);
		canvas.setBitmap(scaledbitmap);

		for (PointF point : coordinateList) {
			canvas.drawCircle(point.x, point.y, radius, paint);
		}
	}
	
	public void ClearBitmap()
	{
		LocationData.mPositionX = 0;
    	LocationData.mPositionY = 0;
    	scaleddisplayWidth = displayWidth;
    	scaleddisplayHeight = displayHeight;
    	coordinateList.clear();
    	bitmap.eraseColor(0);
        Paint paintBitmap = new Paint();
        paintBitmap.setColor(Color.WHITE);
        if(scaledbitmap != null)
        {
        	scaledbitmap.eraseColor(0);
        	bitmap = Bitmap.createBitmap(displayWidth, displayHeight, Bitmap.Config.ARGB_8888);
    		canvas = new Canvas(bitmap);
        }
        scaledbitmap = null;
        canvas.drawColor(0);
        canvas.drawBitmap(bitmap, 0, 0, paintBitmap);
        //canvas.translate(0,canvas.getHeight());   // reset where 0,0 is located
		//canvas.scale(1,-1);    // invert
        MainActivity.getInstance().imageView.setImageBitmap(bitmap);
        ConfigureLocatorCommand.sendCommand(MainActivity.getInstance().mRobot,ConfigureLocatorCommand.ROTATE_WITH_CALIBRATE_FLAG_OFF, 0, 0, 0);
	}
}
