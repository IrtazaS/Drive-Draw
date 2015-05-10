package com.irtaza.drivedraw;

import android.graphics.Color;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @author irtaza Die Klasse Drive ist implementiert alle Steuerungselemente für Sphero.
 * Diese beinhaltet das Fahren, sowie die Lenkung von Sphero. Bei einer Kollision wird das
 * Fahren deaktiviert. Der Benutzer muss die Position ändern, um das Fahren zu reaktivieren.
 *
 */
public class Drive {

	boolean activatedrawing;
	static float heading;
	public OnSeekBarChangeListener mSeekBarDriveListener = new OnSeekBarChangeListener() {

		GetVelocity velocity = new GetVelocity();
		float seekBarProgress = 0;

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
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
	public OnSeekBarChangeListener mSeekBarTailListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			MainActivity.getInstance().mRobot.setBackLEDBrightness(0);
			MainActivity.getInstance().textGeneral.setEnabled(false);

		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int arg1, boolean arg2) {
			MainActivity.getInstance().mRobot.setBackLEDBrightness(1);
			heading = seekBar.getProgress();
			MainActivity.getInstance().mRobot.rotate(heading);
			MainActivity.getInstance().textGeneral.setEnabled(true);
			if (CollisionData.collisionheading != heading) {
				MainActivity.getInstance().textGeneral.setText("Heading: " + arg1);
				MainActivity.getInstance().textGeneral.setTextColor(Color.BLACK);
				MainActivity.getInstance().seekBarDrive.setEnabled(true);
			} else {
				MainActivity.getInstance().textGeneral.setText("Heading: " + arg1);
				MainActivity.getInstance().textGeneral.setTextColor(Color.RED);
				MainActivity.getInstance().seekBarDrive.setEnabled(false);
			}
			
			if(CollisionData.collisionX != LocationData.mPositionX || CollisionData.collisionY != LocationData.mPositionY)
			{
				CollisionData.collisionheading = 361;
				CollisionData.collisionhandled = true;
			}

		}
	};

}
