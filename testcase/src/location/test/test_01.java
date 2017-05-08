package location.test;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * @author Robin Wu, Song Zhe & Feifei Liu
 */
public class test_01 extends Activity{
    /** Called when the activity is first created. */
	
    private Button startServiceButton;
    private Button changeStatusButton;
    private Button stopServiceButton;
    private Button updateButton;
    private Button exitButton;
    
    private TextView textLatitude;
    private TextView textLongitude;
    private TextView textDistance;
    private TextView textAverageSpeed;
    private TextView textSteps;
    private TextView textOrientation;
    
    private boolean misbind = false;
    private ServiceConnection serviceConnection = null;
      
    IGetLocationService getLocationServiceProxy;
    
   
    
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startServiceButton = (Button) findViewById(R.id.start_service_button);
        startServiceButton.setOnClickListener(new StartServiceButtonListener());
        changeStatusButton = (Button) findViewById(R.id.change_status_button);
        changeStatusButton.setOnClickListener(new ChangeStatusButtonListener());
        stopServiceButton = (Button) findViewById(R.id.stop_service_button);
        stopServiceButton.setOnClickListener(new StopServiceButtonListener());
        updateButton = (Button) findViewById(R.id.update_values_button);
        updateButton.setOnClickListener(new UpdateButtonListener());
        exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new ExitButtonListener());
        textLatitude = (TextView) findViewById(R.id.latitude_text);
        textLongitude = (TextView) findViewById(R.id.longitude_text);
        textDistance = (TextView) findViewById(R.id.distance_text);
        textAverageSpeed = (TextView) findViewById(R.id.avg_speed_text);
        textSteps = (TextView) findViewById(R.id.steps_text);
        textOrientation = (TextView) findViewById(R.id.orientation_text);
        
    }
    
    
    

    
    // Create an anonymous implementation of OnClickListener
    private class StartServiceButtonListener implements OnClickListener{
        public void onClick(View v){
        	serviceConnection = new ServiceConnection(){
        	    
        		@Override
        		public void onServiceConnected(ComponentName classname, IBinder service  ){
        			
        			getLocationServiceProxy = IGetLocationService.Stub.asInterface(service);
        			
        			System.out.println("Service Started");
        			UpdateLocation();
        		}
        		@Override
        		public void onServiceDisconnected(ComponentName name){
        			
        		}

        	};
        	
        	Intent intent = new Intent(test_01.this,GetLocationService.class);
        	startService(intent);
        	bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
        	misbind = true;
        }
    }

    private class ChangeStatusButtonListener implements OnClickListener{
        public void onClick(View v){
        	if(misbind){
        	try {
				if(getLocationServiceProxy.getGPSStatus()){
					getLocationServiceProxy.changeGPSStatus();
					getLocationServiceProxy.startDeadReckoningService();
				}else if(getLocationServiceProxy.getSensorStatus()){
					getLocationServiceProxy.changeSensorStatus();
				}else{
					System.out.println("please start service");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}else{
        		System.out.println("Pleas start service");
        	}
        }
    }
    
    // Create an anonymous implementation of OnClickListener
    private class StopServiceButtonListener implements OnClickListener{
        public void onClick(View v){
        	if(serviceConnection != null){
        		try {
					if(getLocationServiceProxy.getGPSStatus()){
						getLocationServiceProxy.unRegisterGPS();
					}else if(getLocationServiceProxy.getSensorStatus()){
						getLocationServiceProxy.unRegisterSensors();
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		unbindService(serviceConnection);
        		serviceConnection = null;
        		misbind = false;
        		Intent intent = new Intent(test_01.this,GetLocationService.class);
                stopService(intent);
                
        	}
        }
    }
    
    // Create an anonymous implementation of OnClickListener
    private class UpdateButtonListener implements OnClickListener{
        public void onClick(View v){
        	if(misbind == true){
        		try {
					if(getLocationServiceProxy.getGPSStatus()){
						UpdateLocation();
					}else if(getLocationServiceProxy.getSensorStatus()){
						showSteps();
		        		showOrientation();
		        		UpdateLocation();
					}else{
						System.out.println("no data");
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	} else{
        		System.out.println("Please Start Service first");
        	}
        }
    }
    
    // Create an anonymous implementation of OnClickListener
    private class ExitButtonListener implements OnClickListener{
	   
	   @Override
        public void onClick(View v){
            finish();
        }
    }
  

    /**
	 * Update the current GPS information
	 * @exception RemoteException
	 * 
	*/
    protected void UpdateLocation(){
    	
    	try {
			if((getLocationServiceProxy.getLatitude()>90)||(getLocationServiceProxy.getLatitude()<-90)||(getLocationServiceProxy.getLongitude()>180)||(getLocationServiceProxy.getLongitude()<-180)){
				textLatitude.setText("No Data");
				textLongitude.setText("No Data");
				textDistance.setText("No Data");
				textAverageSpeed.setText("No Data");
			} else {
				textLatitude.setText(Double.toString(getLocationServiceProxy.getLatitude()));
				textLongitude.setText(Double.toString(getLocationServiceProxy.getLongitude()));
				DecimalFormat dfDistance = new DecimalFormat("#,###,###,##0.0");
				DecimalFormat dfAverageSpeed = new DecimalFormat("#,###,###,##0.000");
				textDistance.setText(dfDistance.format(getLocationServiceProxy.getDistance()) + " m");
				textAverageSpeed.setText(dfAverageSpeed.format(getLocationServiceProxy.getAverageSpeed()) + " m/s");
				
			}
    		
			
    		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected void showSteps(){
    	try {
			textSteps.setText(Double.toString(getLocationServiceProxy.countSteps()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected void showOrientation(){
    	try {
			textOrientation.setText(Double.toString(getLocationServiceProxy.getDegree()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
}
