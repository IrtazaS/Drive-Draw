package com.irtaza.drivedraw;

import android.graphics.Color;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Drive {

	boolean activatedrawing;
	static float heading;
	public OnSeekBarChangeListener mSeekBarDriveListener = new OnSeekBarChangeListener() {

		GetVelocity velocity = new GetVelocity();
		float seekBarProgress = 0;

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			MainActivity.getInstance().mRobot.setBackLEDBrightness(0);
			activatedrawing = false;
			MainActivity.getInstance().mRobot.stop();
			MainActivity.getInstance().textGeneral.setEnabled(false);

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			MainActivity.getInstance().mRobot.drive(heading,
					velocity.getVelocity(seekBarProgress));
			activatedrawing = true;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			MainActivity.getInstance().mRobot.setBackLEDBrightness(1);
			seekBarProgress = seekBar.getProgress();
			MainActivity.getInstance().mRobot.drive(heading,
					velocity.getVelocity(seekBarProgress));
			MainActivity.getInstance().textGeneral.setEnabled(true);
			MainActivity.getInstance().textGeneral.setText("Speed: " + progress);

		}
	};
	public OnSeekBarChangeListener mseekBarTailListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			MainActivity.getInstance().mRobot.setBackLEDBrightness(0);
			MainActivity.getInstance().textGeneral.setEnabled(false);

		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int arg1, boolean arg2) {
			// TODO Auto-generated method stub
			MainActivity.getInstance().mRobot.setBackLEDBrightness(1);
			heading = seekBar.getProgress();
			//MainActivity.getInstance().textHeading.setText("H: " + heading);
			MainActivity.getInstance().mRobot.rotate(heading);
			MainActivity.getInstance().textGeneral.setEnabled(true);
			if (CollisionData.collisionheading != heading) {
				MainActivity.getInstance().textGeneral.setText("Heading: " + arg1);
				MainActivity.getInstance().textGeneral.setTextColor(Color.BLACK);
				//MainActivity.getInstance().textHeading.setTextColor(Color.BLACK);
				MainActivity.getInstance().seekBarDrive.setEnabled(true);
			} else {
				MainActivity.getInstance().textGeneral.setText("Heading: " + arg1);
				MainActivity.getInstance().textGeneral.setTextColor(Color.RED);
				MainActivity.getInstance().seekBarDrive.setEnabled(false);
				//MainActivity.getInstance().textHeading.setTextColor(Color.RED);
			}
			
			if(CollisionData.collisionX != LocationData.mPositionX || CollisionData.collisionY != LocationData.mPositionY)
			{
				CollisionData.collisionheading = 361;
				CollisionData.collisionhandled = true;
			}

		}
	};

}
