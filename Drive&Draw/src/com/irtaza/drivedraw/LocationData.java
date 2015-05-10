package com.irtaza.drivedraw;

import orbotix.sphero.LocatorListener;

/**
 * 
 * @author irtaza LocationData liefert die aktuelle Position von Sphero in Echtzeit.
 *
 */
public class LocationData {

	public static float mPositionX, mPositionY = 0;
	public static float lastPositionX = 0, lastPositionY = 0;

	public LocatorListener mlocatorListener = new LocatorListener() {
		@Override
		public void onLocatorChanged(
				orbotix.robot.sensor.LocatorData locatorData) {
			if (locatorData != null) {
				mPositionX = locatorData.getPositionX();
				mPositionY = locatorData.getPositionY();

				MainActivity.getInstance().textPositionX.setText("X: "
						+ mPositionX);
				MainActivity.getInstance().textPositionY.setText("Y: "
						+ mPositionY);

				if (CollisionData.collisionhandled) {
					MainActivity.getInstance().draw.DrawMap();
				}
				lastPositionX = mPositionX;
				lastPositionY = mPositionY;

				MainActivity.getInstance().imageView.invalidate();
			}
		}
	};
}
