package com.irtaza.drivedraw;

import orbotix.robot.base.Robot;
import orbotix.sphero.Sphero;
import orbotix.view.connection.SpheroConnectionView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	static MainActivity mainactivity;
	ImageView imageView;
	Sphero mRobot;
	Display display;
	SeekBar seekBarTail;
	SeekBar seekBarDrive;
	TextView velocityView;
	TextView textPositionX, textPositionY, textCollisionX, textCollisionY, textHeading; 
	int displayWidth, displayHeight;
	/** The Sphero Connection View */
	private SpheroConnectionView mSpheroConnectionView;
	CollisionData collisiondata;
	LocationData locatordata;
	Drive drive;
	Draw draw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mainactivity = this;
		velocityView = (TextView)findViewById(R.id.speedView);
		
		imageView = (ImageView)findViewById(R.id.imageView1);
		seekBarDrive = (SeekBar)findViewById(R.id.seekBarDrive);
		seekBarTail = (SeekBar)findViewById(R.id.seekBarTail);
		textCollisionX = (TextView)findViewById(R.id.textViewCollisionX);
		textCollisionY = (TextView)findViewById(R.id.textViewCollisionY);
		textPositionX = (TextView)findViewById(R.id.textViewPositionX);
		textPositionY = (TextView)findViewById(R.id.textViewPositionY);
		textHeading = (TextView)findViewById(R.id.textViewHeading);
		
		display = getWindowManager().getDefaultDisplay();
		displayWidth = MainActivity.getInstance().display.getWidth();
		displayHeight = MainActivity.getInstance().display.getHeight();
		displayHeight = displayHeight / 2;
		
		draw = new Draw();
		collisiondata = new CollisionData();
		locatordata = new LocationData();
		drive = new Drive();
		//draw.CreateBitmap();
		
		mSpheroConnectionView = (SpheroConnectionView) findViewById(R.id.sphero_connection_view);
		mSpheroConnectionView
				.addConnectionListener(new orbotix.sphero.ConnectionListener() {

					@Override
					public void onDisconnected(Robot sphero) {
						mSpheroConnectionView.startDiscovery();
					}

					@Override
					public void onConnectionFailed(Robot sphero) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onConnected(Robot sphero) {
						// TODO Auto-generated method stub
						mRobot = (Sphero) sphero;
						mRobot.startCalibration();
						mRobot.getCollisionControl().addCollisionListener(
								collisiondata.mCollisionListener);
						mRobot.getSensorControl().addLocatorListener(
								locatordata.mlocatorListener);
						mRobot.getCollisionControl().startDetection(45, 45,
								100, 100, 100);
						mRobot.stopCalibration(true);
//						drawView = new DrawView(MainActivity.this);
//		        		drawView.setBackgroundColor(Color.WHITE);
//		        		setContentView(drawView);

					}
				});
		seekBarDrive.setOnSeekBarChangeListener(drive.mSeekBarDriveListener);
		seekBarTail.setOnSeekBarChangeListener(drive.mseekBarTailListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	        	try {
	        		draw.ClearBitmap();
				} catch (Exception e) {
					connectionMessageBox();
				}
	            return true;
	        case R.id.action_sleep:
	        	try {
	        		mRobot.getCollisionControl().stopDetection();
	        		mRobot.getSensorControl().removeLocatorListener(locatordata.mlocatorListener);
	        		mRobot.sleep(0);
				} catch (Exception e) {
					connectionMessageBox();
				}
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void connectionMessageBox(){
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Caution:")
    .setMessage("No Device Connected")
    .setCancelable(false)
    .setNegativeButton("OK",new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
    });
    AlertDialog alert = builder.create();
    alert.show();
}

	@Override
	protected void onResume() {
		super.onResume();
		// Refresh list of Spheros
		mSpheroConnectionView.startDiscovery();
	}

	/** Called when the user presses the back or home button */
	@Override
	protected void onPause() {
		super.onPause();
		if (mRobot != null) {
			mRobot.getCollisionControl().stopDetection();
			// Remove async data listener
			// mRobot.getCollisionControl().removeCollisionListener(mCollisionListener);
			mRobot.getSensorControl().removeLocatorListener(locatordata.mlocatorListener);
			// Disconnect Robot properly
			mRobot.disconnect();
		}
	}
	
	public static MainActivity getInstance(){
		   return   mainactivity;
		 } 
	
	
}