package com.irtaza.drivedraw;

import android.graphics.Color;
import android.graphics.Paint;
import orbotix.sphero.LocatorListener;

public class LocationData {

	public static float mPositionX, mPositionY = 0;
	public static float lastPositionX = 0, lastPositionY = 0;
	//Draw draw = new Draw();
	public LocatorListener mlocatorListener = new LocatorListener() {
		@Override
		public void onLocatorChanged(
				orbotix.robot.sensor.LocatorData locatorData) {
			if (locatorData != null) {
				mPositionX = locatorData.getPositionX();
				mPositionY = locatorData.getPositionY();

				// if(mPositionX < 0){
				// mPositionX *= -1;
				// }
				// if(mPositionY < 0){
				// mPositionY *= -1;
				// }
				// mPositionX /= 2; //X & Y coordinates divided by 2,
				// mPositionY /= 2; //to fit Canvas drawing on screen.
				// Log.i("Location", mPositionX + " " + mPositionY);
				MainActivity.getInstance().textPositionX.setText("X: "
						+ mPositionX);
				MainActivity.getInstance().textPositionY.setText("Y: "
						+ mPositionY);
				// drawView.invalidate();
				if (CollisionData.collisionhandled
						|| !CollisionData.collisionhandled) {
					MainActivity.getInstance().draw.SetPoints(mPositionX, mPositionY);
					MainActivity.getInstance().draw.DrawMap();
				}
				lastPositionX = mPositionX;
				lastPositionY = mPositionY;

				MainActivity.getInstance().imageView.invalidate();
			}
		}
	};
}
