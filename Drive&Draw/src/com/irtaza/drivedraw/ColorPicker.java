package com.irtaza.drivedraw;

import android.graphics.Color;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @author irtaza Die Klasse ColorPicker liefert RGB Werte für Spheros RGB-LED und 
 * für die Farbe des Zeichenstifts.
 *
 */
public class ColorPicker {

	static int red, blue = 0;
	static int green = 255;
	static int alpha = 110;
	
	public OnSeekBarChangeListener seekBarAlphaListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekbaralpha, int progressAlpha, boolean arg2) {
			alpha = progressAlpha;
			//ColorPickerActivity.colorButton.setBackgroundColor(Color.argb(alpha, red, green, blue));
			ColorPickerActivity.colorspheroImage.setBackgroundColor(Color.argb(alpha, red, green, blue));
			MainActivity.getInstance().mRobot.setColor(red, green, blue);
		}
	};
	public OnSeekBarChangeListener seekBarRedListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekbarred, int progressRed, boolean arg2) {
			// TODO Auto-generated method stub
			red = progressRed;
			//ColorPickerActivity.colorButton.setBackgroundColor(Color.argb(alpha, red, green, blue));
			ColorPickerActivity.colorspheroImage.setBackgroundColor(Color.argb(alpha, red, green, blue));
			MainActivity.getInstance().mRobot.setColor(red, green, blue);
		}
	};
	
	public OnSeekBarChangeListener seekBarGreenListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekbargreen, int progressGreen,
				boolean fromUser) {
			green = progressGreen;
			//ColorPickerActivity.colorButton.setBackgroundColor(Color.argb(alpha, red, green, blue));
			ColorPickerActivity.colorspheroImage.setBackgroundColor(Color.argb(alpha, red, green, blue));
			MainActivity.getInstance().mRobot.setColor(red, green, blue);
			
		}
	};
	
	public OnSeekBarChangeListener seekBarBlueListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekbarblue, int progressBlue,
				boolean fromUser) {
			
			blue = progressBlue;
			//ColorPickerActivity.colorButton.setBackgroundColor(Color.argb(alpha, red, green, blue));
			ColorPickerActivity.colorspheroImage.setBackgroundColor(Color.argb(alpha, red, green, blue));
			MainActivity.getInstance().mRobot.setColor(red, green, blue);
		}
	};
	
	public static int getAlpha()
	{
		return alpha;
	}
	public static int getRed()
	{
		return red;
	}
	public static int getGreen()
	{
		return green;
	}
	public static int getBlue()
	{
		return blue;
	}
}
