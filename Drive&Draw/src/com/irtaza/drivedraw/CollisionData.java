package com.irtaza.drivedraw;

import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.base.CollisionDetectedAsyncData.CollisionPower;
import orbotix.sphero.CollisionListener;
import android.graphics.Color;
import android.graphics.Paint;

public class CollisionData {

	static float collisionheading;
	static boolean collisionhandled = true;
	public CollisionListener mCollisionListener = new CollisionListener() {
		
		@Override
		public void collisionDetected(CollisionDetectedAsyncData collisionData) {
	
			//Draw draw = new Draw();
			CollisionPower collisionpower = collisionData.getImpactPower();
			MainActivity.getInstance().textCollisionX.setText("CX: " + collisionpower.x);
			MainActivity.getInstance().textCollisionY.setText("CY: " + collisionpower.y);
			if(collisionpower.x > 100 || collisionpower.y > 100) {
				MainActivity.getInstance().mRobot.setColor(255, 0, 0);
                MainActivity.getInstance().mRobot.stop();
                MainActivity.getInstance().draw.DrawCollision();
                collisionhandled = false;
                //seekBarDrive.setEnabled(false);
                MainActivity.getInstance().textHeading.setTextColor(Color.RED);
                collisionheading = Drive.heading;
            }
			else
				MainActivity.getInstance().mRobot.setColor(0, 255, 0);
			
			
	
		}
	};
}
