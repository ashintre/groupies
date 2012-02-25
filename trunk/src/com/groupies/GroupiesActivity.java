package com.groupies;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GroupiesActivity extends Activity{
    /** Called when the activity is first created. */
	LinearLayout l;
	 private boolean gps_enabled = false;
     private boolean network_enabled = false;
     Location lastKnownLocation;
     LocationManager locManager;
     private LocationListener locListener = new MyLocationListener() ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
    	try {
    		gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	}catch (Exception ex) {
    		
    	}
    		
    	try {
    		network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    	}catch (Exception ex) {
    		
    	}
    		// don't start listeners if no provider is enabled
    		
    		if (!gps_enabled && !network_enabled) {
    	   		System.out.println("NOT ENABLED !!!");
    		}
 
    		if (gps_enabled) {
    			//lastKnownLocation = locManager.getLastKnownLocation(locationProvider);
    			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
    		}
    		
    		if (network_enabled) {
    			//lastKnownLocation = locManager.getLastKnownLocation(locationProvider);
    			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
    		}

    	//Location lastKnownLocation = locManager.getLastKnownLocation(locationProvider);
    	if(lastKnownLocation!=null){
	    	System.out.println("lat ->"+lastKnownLocation.getLatitude());
	    	System.out.println("long ->"+lastKnownLocation.getLongitude());
    	}
        System.out.println("before button2");
        final Button button2 = (Button) findViewById(R.id.login_btn);
        System.out.println("button2 ->"+button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              	Intent i = new Intent(getBaseContext(),Login.class);
                startActivity(i); 
              }
        });
        
        final Button button1 = (Button) findViewById(R.id.new_user_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent i = new Intent(getBaseContext(),Create_New.class);
                startActivity(i); 
            }
        });
    }

    
    class MyLocationListener implements LocationListener {
	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			// This needs to stop getting the location data and save the battery power.
					locManager.removeUpdates(locListener);
					String longitude = "Longitude: " + location.getLongitude();
					String latitude = "Latitude: " + location.getLatitude();
					String altitiude = "Altitiude: " + location.getAltitude();
					String accuracy = "Accuracy: " + location.getAccuracy();
					String time = "Time: " + location.getTime();
					System.out.println("Longitude: " + longitude + "Latitude: " + latitude);
		}
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
    }
}

	
    