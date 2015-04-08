package com.irtaza.drivedraw;

import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.base.CollisionDetectedAsyncData.CollisionPower;
import orbotix.sphero.CollisionListener;
import android.graphics.Color;
import android.graphics.Paint;

public class CollisionData {

	static float collisionheading;
	static boolean collisionhandled = true;
	int collisionSensitivity = 100;
	public CollisionListener mCollisionListener = new CollisionListener() {
		
		@Override
		public void collisionDetected(CollisionDetectedAsyncData collisionData) {
	
			collisionSensitivity = Integer.parseInt(MainActivity.getInstance().settings.getString("collision_sensitivity", "100"));
			//Draw draw = new Draw();
			CollisionPower collisionpower = collisionData.getImpactPower();
			//MainActivity.getInstance().textCollisionX.setText("CX: " + collisionpower.x);
			//MainActivity.getInstance().textCollisionY.setText("CY: " + collisionpower.y);
			if(collisionpower.x > 100 || collisionpower.y > 100) {
				MainActivity.getInstance().mRobot.setColor(255, 0, 0);
                MainActivity.getInstance().mRobot.stop();
                MainActivity.getInstance().draw.DrawCollision();
                collisionhandled = false;
                MainActivity.getInstance().seekBarDrive.setEnabled(false);
                MainActivity.getInstance().textGeneral.setEnabled(true);
                MainActivity.getInstance().textGeneral.setTextColor(Color.RED);
                MainActivity.getInstance().textGeneral.setText("Change Sphero's heading!");
                collisionheading = Drive.heading;
            }
			else
				MainActivity.getInstance().mRobot.setColor(0, 255, 0);
		}
	};
}
