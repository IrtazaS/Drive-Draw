package com.irtaza.drivedraw;

import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.base.CollisionDetectedAsyncData.CollisionPower;
import orbotix.sphero.CollisionListener;
import android.graphics.Color;

/**
 * 
 * @author irtaza Die Klasse CollisionData implementiert Spheros Funktion zur
 *         Kollisionserkennung. Der Schwellenwert fŸr die Kollision wird vom
 *         Nutzer in der App festgelegt. Der CollisionListener ŸberprŸft
 *         Kollisionen an der X und Y-Achse. Sobald der Schwellenwert bei einer
 *         Kollision Ÿbersteigt wird der Code fŸr die Collisionhandling
 *         ausgelšst.
 */
public class CollisionData {

	static float collisionheading;
	static float collisionX, collisionY;
	static boolean collisionhandled = true;
	private int collisionSensitivity = 100;
	private boolean collisionDetection = true;
	public CollisionListener mCollisionListener = new CollisionListener() {

		@Override
		public void collisionDetected(CollisionDetectedAsyncData collisionData) {

			collisionDetection = MainActivity.getInstance().settings
					.getBoolean("pref_toggle_collision_detection", true);

			collisionSensitivity = Integer
					.parseInt(MainActivity.getInstance().settings.getString(
							"collision_sensitivity", "100"));
			CollisionPower collisionpower = collisionData.getImpactPower();

			if (collisionDetection) {
				// Set and Check Threshold parameters
				if (collisionpower.x > collisionSensitivity
						|| collisionpower.y > collisionSensitivity) {
					MainActivity.getInstance().mRobot.setColor(255, 0, 0);
					MainActivity.getInstance().mRobot.stop();
					MainActivity.getInstance().draw.DrawCollision();
					collisionhandled = false;
					collisionX = LocationData.mPositionX;
					collisionY = LocationData.mPositionY;
					MainActivity.getInstance().seekBarDrive.setEnabled(false);
					MainActivity.getInstance().textGeneral.setEnabled(true);
					MainActivity.getInstance().textGeneral
							.setTextColor(Color.RED);
					MainActivity.getInstance().textGeneral
							.setText("Change Sphero's heading!");
					collisionheading = Drive.heading;
				} else {
					collisionhandled = true;
					MainActivity.getInstance().mRobot.setColor(
							ColorPicker.getRed(), ColorPicker.getGreen(),
							ColorPicker.getBlue());
				}
			}
		}
	};
}
