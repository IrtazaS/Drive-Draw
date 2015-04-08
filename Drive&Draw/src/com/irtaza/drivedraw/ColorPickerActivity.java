package com.irtaza.drivedraw;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

public class ColorPickerActivity extends Activity {

	static Button colorButton;
	// Button spheroRGB;
	static int red, blue, green = 0;
	static int alpha = 110;
	SeekBar seekBarRed;
	SeekBar seekBarGreen;
	SeekBar seekBarBlue;
	SeekBar seekBarAlpha;
	ColorPicker colorpicker;
	static ImageView colorspheroImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_picker);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		colorpicker = new ColorPicker();
		//colorButton = (Button) findViewById(R.id.buttonColor);
		colorspheroImage = (ImageView)findViewById(R.id.imageView1);

		alpha = ColorPicker.getAlpha();
		red = ColorPicker.getRed();
		green = ColorPicker.getGreen();
		blue = ColorPicker.getBlue();
		
		//colorButton.setBackgroundColor(Color.argb(alpha,red, green, blue));
		colorspheroImage.setBackgroundColor(Color.argb(alpha, red, green, blue));
		
		seekBarAlpha = (SeekBar)findViewById(R.id.seekBarAlpha);
		seekBarRed = (SeekBar) findViewById(R.id.seekBarColorRed);
		seekBarGreen = (SeekBar) findViewById(R.id.seekBarColorGreen);
		seekBarBlue = (SeekBar) findViewById(R.id.seekBarColorBlue);
		seekBarAlpha.setProgress(alpha);
		seekBarRed.setProgress(red);
		seekBarGreen.setProgress(green);
		seekBarBlue.setProgress(blue);
		
		seekBarAlpha.setOnSeekBarChangeListener(colorpicker.seekBarAlphaListener);
		seekBarRed.setOnSeekBarChangeListener(colorpicker.seekBarRedListener);
		seekBarGreen.setOnSeekBarChangeListener(colorpicker.seekBarGreenListener);
		seekBarBlue.setOnSeekBarChangeListener(colorpicker.seekBarBlueListener);
	}

	public static int getColor()
	{
		return Color.rgb(red, green, blue);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color_picker, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        finish();
	        return true;
	    }
		return super.onOptionsItemSelected(item);
	}
}
