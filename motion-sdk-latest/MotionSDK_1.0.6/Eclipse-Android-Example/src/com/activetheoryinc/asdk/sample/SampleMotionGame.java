/*
 * @author: keerthik
 * This game is a sample that uses the Android BitGym Library
 * to implement a motion game. 
 * Remember to look at the AndroidManifest for permissions!
 *  - Camera
 *  - Accelerometer (if necessary)
 *  - Facebook/internet (when implemented)
 */

package com.activetheoryinc.asdk.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.activetheoryinc.sdk.lib.BGBodyReadingData;
import com.activetheoryinc.sdk.lib.BitGymMotionActivity;
import com.activetheoryinc.sdk.lib.ReadingListener;

public class SampleMotionGame extends BitGymMotionActivity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //SetPlayerCount(2);

        bodyReadingListener = new ReadingListener<BGBodyReadingData>() {
    		public void OnNewReading(BGBodyReadingData reading) {
            	// Run some processing algorithm on the reading data
            	//Log.i("ExampleListener", reading.toString());
    		}
        };
        
        setContentView(R.layout.activity_sample_motion_game);
        FrameLayout layout = (FrameLayout) findViewById(R.id.frameLayout2);
        //mPreview.Hide();
        layout.addView(mPreview);
        
        // Some feedback stuff - pre-Alpha - Use Unity Version
        ((FrameLayout) findViewById(R.id.frameLayout1)).addView(mFeedback);
        mFeedback.requestFocus();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sample_motion_game, menu);
        return true;
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        UnregisterBodyReadingUpdateListener(bodyReadingListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RegisterBodyReadingUpdateListener(bodyReadingListener);
    }
}