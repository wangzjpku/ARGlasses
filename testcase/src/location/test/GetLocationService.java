package location.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.format.Time;
import android.util.Xml;

/**
 * @author Robin Wu, Song Zhe & Feifei Liu
 */
public class GetLocationService extends Service{
	
	
	private LocationManager locationManager;
	TestLocationListener testLocationListener = new TestLocationListener();
	public double latitude = 0;
	public double longitude = 0;
	public double distance = 0;
	public double speed = 0;
	private Location lastKnownLocation;
	boolean locationChanged = false;
	private long currentTime = 0;
	private long lastTime = 0;
	private WorkerThread worker;
	
	protected static final int STOP = 1;
	protected static final int POSCHANGED = 2;
	protected static final int UPDATE = 3;
	
	//definition for the Accelerometer
	SensorManager sensorManager;
	boolean first_accelerometer = true;
	boolean flag = true;
	float[] values;
	AccelerometerListener accelerometerListener = new AccelerometerListener();
	long lastTime_acc = 0;
	long currentTime_acc = 0;
	double x1,y1,z1,x2,y2,z2,x3,y3,z3,length1,length2,length3;
	int steps = 0;
	int countSteps = 0;
	int countNewSteps = 0;
	
	//definition for the orintation
	boolean first_orietation = true;
	OrientationListener orientationListener = new OrientationListener();
	float currentDegree = 0;
	float lastDegree = 0;
	float degree = 0;
	
	//definition for the dead reckoning
	double stepsDistance = 0;
	int earthRadius = 6381000;
	double stepLength = 0.75;
	double longitude_dr = 0;  
	double latitude_dr = 0;
	double curLongitude = 0;
	double curLatitude = 0;
	long lastTime_ori = 0;
	long currentTime_ori = 0;
	boolean first = true;
	int countSteps_dr = 0;
	
	//status
	boolean gps_status = false;
	boolean sensor_status = false;

	//Location Information
	private List<Location> locations = new ArrayList<Location>();
	private Location locationElement = null;
	//LogFile
    public File sdDir = Environment.getExternalStorageDirectory();
    public File gpxFile = new File(sdDir,"track.gpx");
    public File logFile = new File(sdDir,"Log.txt");
    
    /**
     * The thread that writes new GPS info in log file
     */
	private class WorkerThread extends Thread {
    
		public Handler handler;
    	@Override public void run() {
    		System.out.println("Thread Running!");
    		Looper.prepare();
    		handler = new Handler() {
    			public void handleMessage(Message msg) {
    				switch (msg.what) {
    					case STOP : Looper.myLooper().quit();
    						
    						break;
    					case UPDATE: UpdateLogfile();
    					default: 
    						
    						super.handleMessage(msg);
    				    					
    				}	
    			}
    		};
    		Looper.loop(); }	
    	
    }
	
	
	private void startGetLocationService(){
		/**
	     * Initialize service
	     */
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, testLocationListener);
		lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		gps_status = true;
		System.out.println("gps service started");
		if(lastKnownLocation != null){
			latitude = lastKnownLocation.getLatitude();
			longitude = lastKnownLocation.getLongitude();
			lastTime = lastKnownLocation.getTime();
			System.out.println("has last known location");
		}
		else {
			latitude = 500;
			longitude = 500;
		}
		if(logFile.exists()){
			logFile.delete();
			System.out.println("Log File Deleted");
		}
		worker = new WorkerThread();
	    worker.start();
	    
	    
		
	}
	
	private class TestLocationListener implements LocationListener{

		
		@Override
		public void onLocationChanged(Location location) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
			// if there is a last known location, then calculate the distance and speed
			if(lastKnownLocation != null){
				distance = location.distanceTo(lastKnownLocation);
				lastTime = lastKnownLocation.getTime();
				System.out.println("On LocationChanged     lastKnownLocation is not null");
			} else {
				System.out.println("On LocationChanged     lastKnownLocation is null");
			}
			lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			currentTime = location.getTime();
			
			// inform the worker thread to record new GPS info
			Message msg = Message.obtain(worker.handler, POSCHANGED);
        	worker.handler.sendMessage(msg);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("Disable");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("Enable");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
    	
    }
	
	public void operationData(){
		stepsDistance = stepLength * countNewSteps;
		System.out.println("stepsDistance" + stepsDistance);
		latitude_dr = Math.toDegrees(Math.asin(Math.cos(degree*Math.PI/180) * stepsDistance / earthRadius ));
		System.out.println("latitude_distance" + (latitude_dr * 111363));
		curLatitude = latitude + latitude_dr;
		
		longitude_dr = (Math.sin(degree*Math.PI/180) * stepsDistance )/ (111363 * Math.cos(curLatitude*Math.PI/180));
		System.out.println("longitude_distance" + (longitude_dr * 73000));
		curLongitude = longitude + longitude_dr;
		
		latitude = curLatitude;
		longitude = curLongitude;
	
	}
	
		private class AccelerometerListener implements SensorEventListener{

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if (first_accelerometer)
			{	
				 		x1 = event.values[SensorManager.DATA_X];
				 		y1 = event.values[SensorManager.DATA_Y];
				 		z1 = event.values[SensorManager.DATA_Z];
				 		x2 = event.values[SensorManager.DATA_X];
				 		y2 = event.values[SensorManager.DATA_Y];
				 		z2 = event.values[SensorManager.DATA_Z];
				 		x3 = event.values[SensorManager.DATA_X];
				 		y3 = event.values[SensorManager.DATA_Y];
				 		z3 = event.values[SensorManager.DATA_Z];
				 		first_accelerometer = false;
				 		}else{
				 			x3 = x2;
				 			y3 = y2;
				 			z3 = z2;
				 			x2 = x1;
				 			y2 = y1;
				 			z2 = z1;
				 			x1=event.values[SensorManager.DATA_X];
				 			y1=event.values[SensorManager.DATA_Y];
				 			z1=event.values[SensorManager.DATA_Z];
				 			length1=(Math.sqrt(x1*x1+y1*y1+z1*z1));
				 			length2=(Math.sqrt(x2*x2+y2*y2+z2*z2));
				 			length3=(Math.sqrt(x3*x3+y3*y3+z3*z3));
				 			
				 			if(length2 > 12.5){
				 				if(length2 > length3 && length2 > length1){
				 					steps = 1;
				 				}
				 			}
				 			
				 			if(steps ==1 && length2 < 8.5){
				 				if(length2 < length3 && length2 < length1){
				 					if(flag){
				 						currentTime_acc = System.currentTimeMillis();
				 						countSteps++;
				 						steps = 0;
				 						flag = false;
				 					}else{
				 						currentTime_acc = System.currentTimeMillis();
				 						steps = 0;
				 						if(currentTime_acc - lastTime_acc > 30 && currentTime_acc - lastTime_acc < 2000){
				 							countSteps++;
				 						}
				 					}
				 					lastTime_acc = currentTime_acc;
				 				}
				 			}
				 			
				 		}
		}
	}
		
		private class OrientationListener implements SensorEventListener{

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				
				if(first_orietation){
					
					lastDegree = event.values[0];
					first_orietation = false;
				}else{
					currentDegree = event.values[0];
					if(Math.abs(currentDegree - lastDegree) > 10){
							degree = lastDegree;
							//System.out.println("lastdegree" + degree);
							//System.out.println("currentdegree" + currentDegree);
							if(countSteps_dr != countSteps){
								//System.out.println("countsteps" + countSteps);
								//System.out.println("countnewsteps1a" + countNewSteps);
								//System.out.println("countSteps_dr1a" + countSteps_dr);
							countNewSteps = countSteps - countSteps_dr;
							countSteps_dr = countSteps;
								//System.out.println("countnewsteps2a" + countNewSteps);
								//System.out.println("countSteps_dr2a" + countSteps_dr);
								
							operationData();
							
							//add location info in to location list
							locationElement = new Location(LocationManager.GPS_PROVIDER);
							locationElement.setLatitude(latitude);
							locationElement.setLongitude(longitude);
							locationElement.setTime(System.currentTimeMillis());
							
							locations.add(locationElement);
							locationElement = null;
							}
							lastDegree = currentDegree;
					}
					
					
				}     
			}
			
		}

	@Override
	public void onCreate(){
		super.onCreate();	
		System.out.println("Service onCreate");
		}
	
	@Override  
    public void onStart(Intent intent, int startId) {     
        super.onStart(intent, startId);  
        startGetLocationService();
        System.out.println("service onstart");
    } 
	
	@Override
	public IBinder onBind(Intent intent){
		
		return new GetLocationServiceImpl();
	}

	private class GetLocationServiceImpl extends IGetLocationService.Stub{
		
		
		/**
	     * returns the latitude of current location
	     * @return the latitude 
	     */
		public double getLatitude() {
			return latitude;
			
		}
    
		/**
	     * returns the longitude of current location
	     * @return the longitude 
	     */
		public double getLongitude() {
			return longitude;
  
		}
		
		/**
	     * returns the distance between the current location and the last known location
	     * @return the distance between the current location and the last known location
	     */
		public double getDistance() throws RemoteException {
			// TODO Auto-generated method stub
			if(gps_status){
			return distance;
			}else{
				return (countSteps * 0.75);
			}
		
		} 


		/**
	     * returns the average speed
	     * @return the average speed
	     */
		public double getAverageSpeed() throws RemoteException {
			// TODO Auto-generated method stub
			
			if((currentTime != lastTime) && (lastTime != 0) && (gps_status)){
				speed = (distance * 1000) / (currentTime - lastTime); 
			}
			else if(sensor_status){
				if(first){
					lastTime_ori = System.currentTimeMillis();
					first = false;
					speed = 0;
				}else {
					currentTime_ori = System.currentTimeMillis();
					speed = (countSteps * 0.75 * 1000) / (currentTime_ori - lastTime_ori);
				}
			}
			return speed;
		}
		
		//return the Accelerometer
		public int countSteps(){
			return countSteps;
		}
		
		public double getDegree(){
			return currentDegree;
		}
		
		public void startDeadReckoningService(){
			
			sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
			Sensor acceleromererSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			Sensor orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
			sensorManager.registerListener(accelerometerListener,acceleromererSensor, SensorManager.SENSOR_DELAY_UI);
			sensorManager.registerListener(orientationListener,orientationSensor,SensorManager.SENSOR_DELAY_UI);
			sensor_status = true;
			System.out.println("deadReckoningService started");
		}
		
		public boolean getGPSStatus(){
			return gps_status;
		}
		
		public boolean getSensorStatus(){
			return sensor_status;
		}
		
		public void changeGPSStatus(){
			locationManager.removeUpdates(testLocationListener);
			gps_status = false;
			System.out.println("gps status changed");
		}
		public void changeSensorStatus(){
			sensorManager.unregisterListener(accelerometerListener);
			sensorManager.unregisterListener(orientationListener);
			sensor_status = false;
			first_accelerometer = true;
			countSteps = 0;
			countNewSteps = 0;
			first = true;
			System.out.println("sensor status changed");
		}
		public void unRegisterSensors(){
			sensorManager.unregisterListener(accelerometerListener);
			sensorManager.unregisterListener(orientationListener);
			
		}
		public void unRegisterGPS(){
			locationManager.removeUpdates(testLocationListener);
		}

		
		
    }
	
	@Override  
	 public boolean onUnbind(Intent intent) {      
	        return super.onUnbind(intent);   
	    }   

	
	@Override
	public void onDestroy(){
		// stop the log file updating
		UpdateGpxfile();
		Message msg = Message.obtain(worker.handler, STOP);
    	worker.handler.sendMessage(msg);
    	super.onDestroy();
		System.out.println("Service onDestroyed");
	}
	
	/**
     * write new log file information
     */
	private void UpdateLogfile(){
    	String content = "";
		content = Double.toString(latitude) + "," + Double.toString(longitude) + "," + Double.toString(distance) + "," + Double.toString(speed) + "\n";

		if (content != "") {
			try {
				FileOutputStream outStream = new FileOutputStream(logFile, true); 
				
				outStream.write(content.getBytes());

				outStream.flush();  
				outStream.close();
				System.out.println("Logfile updated!");
			} catch (FileNotFoundException e) {  
				e.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  

		}
		
    }
	
	private void UpdateGpxfile(){
		try {
			System.out.println("Update gpx");
			XmlSerializer serializer = Xml.newSerializer();
			FileOutputStream gpxOutStream = new FileOutputStream(gpxFile, false);
			
			/*
			<gpx
			  version="1.1"
			  creator="GPSBabel - http://www.gpsbabel.org"
			  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			  xmlns="http://www.topografix.com/GPX/1/1"
			  xsi:schemaLocation="http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd">
			<metadata>
			<time>2009-04-20T19:43:14Z</time>
			<bounds minlat="48.469001667" minlon="9.495335000" maxlat="48.530360000" maxlon="9.547900000"/>
			</metadata>
			<trk>
			<trkseg>
			*/
			serializer.setOutput(gpxOutStream, "UTF-8");
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "gpx");
			serializer.attribute("", "version", "1.1");
			serializer.attribute("", "creator", "GPSBabel - http://www.gpsbabel.org");
			serializer.attribute("", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			serializer.attribute("", "xmlns", "http://www.topografix.com/GPX/1/1");
			serializer.attribute("", "xsi:schemaLocation", "http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd");
			serializer.startTag("", "metadata");
			serializer.startTag("", "time");
			serializer.text("2009-04-20T19:43:14Z");
			serializer.endTag("", "time");
			serializer.startTag("", "bounds");
			serializer.attribute("", "minlat", "48.469001667");
			serializer.attribute("", "minlon", "9.495335000");
			serializer.attribute("", "maxlat", "48.530360000");
			serializer.attribute("", "maxlon", "9.547900000");
			serializer.endTag("", "bounds");
			serializer.endTag("", "metadata");
			serializer.startTag("", "trk");
			serializer.startTag("", "trkseg");
			if(locations.isEmpty() == false){
				/*
				 * <trkpt lat="48.484150000" lon="9.496003333">
				 * <time>2008-08-10T10:10:50Z</time>
			  	 * </trkpt>
				 */
			    Time gpxTime = new Time();
			    
				for(int i=0;i<locations.size();i++){
					serializer.startTag("", "trkpt");
					serializer.attribute("", "lat", String.valueOf(locations.get(i).getLatitude()));
					serializer.attribute("", "lon", String.valueOf(locations.get(i).getLongitude()));
					gpxTime.set(locations.get(i).getTime());
					serializer.startTag("", "time");
					serializer.text(gpxTime.format3339(false));
					serializer.endTag("", "time");
					serializer.endTag("", "trkpt");
				}
			}
			/*
			 * </trkseg>
			 * </trk>
			 * </gpx>
			 */
			serializer.endTag("", "trkseg");
			serializer.endTag("", "trk");
			serializer.endTag("", "gpx");
			serializer.endDocument();
			serializer.flush();
			gpxOutStream.close();

			System.out.println("Gpx file updated!");
			/* 
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println("IllegalArgumentException");
			e.printStackTrace();
			*/
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			System.out.println("IllegalStateException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException");
			e.printStackTrace();
		}
		
	}
	
}
	
	
	

