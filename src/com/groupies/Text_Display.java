package com.groupies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.groupies.Login.MyLocationListener;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class Text_Display extends Activity{
	
	 Location lastKnownLocation;
     public LocationManager locManager;
     public LocationListener locListener = new MyLocationListener();
     public boolean gps_enabled = false;
     public boolean network_enabled = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		
		locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (!gps_enabled && !network_enabled) {
	   		System.out.println("NOT ENABLED !!!");
		}

		if (gps_enabled) {
			lastKnownLocation = locManager.getLastKnownLocation(locationProvider);
			Toast.makeText(getApplicationContext(), "BEFORE UPDATE", Toast.LENGTH_LONG).show();
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
		
			Toast.makeText(getApplicationContext(), "AFTER UPDATE", Toast.LENGTH_LONG).show();
		}
		
		if (network_enabled) {
			Toast.makeText(getApplicationContext(), "BEFORE UPDATE", Toast.LENGTH_LONG).show();
			lastKnownLocation = locManager.getLastKnownLocation(locationProvider);
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
			
			Toast.makeText(getApplicationContext(), "AFTER UPDATE", Toast.LENGTH_LONG).show();
		}
		
		HttpClient httpclient = new DefaultHttpClient();
    	//HttpPost httppost = new HttpPost("http://10.0.2.2/user_select.php");
    	HttpPost httppost = new HttpPost("http://192.168.16.1/refresh_hack_user_location.php");
    	try {
    		// Add your data
    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    		nameValuePairs.add(new BasicNameValuePair("username", GlobalData.username.toString()));
    		nameValuePairs.add(new BasicNameValuePair("eid", GlobalData.eid.toString()));
    		nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(GlobalData.latitude)));
    		nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(GlobalData.longitude)));
    		//nameValuePairs.add(new BasicNameValuePair("latitude", lastKnownLocation.getLatitude()));
    		
    		//also send lat and long
    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    		// Execute HTTP Post Request
    		HttpResponse response = httpclient.execute(httppost);
    	} catch (ClientProtocolException e) {
    		System.out.println("exception 1");
    		// TODO Auto-generated catch block
    	} catch (IOException e) {
    		e.printStackTrace();
    		System.out.println("error ");
    		// TODO Auto-generated catch block
    	}
	}
	
	 class MyLocationListener implements LocationListener {
			@Override
			public void onLocationChanged(Location location) {
				if(location == null){
					Toast.makeText(getApplicationContext(), "LOCATION IS NULL", Toast.LENGTH_LONG).show();
				}
					// This needs to stop getting the location data and save the battery power.
				GlobalData.longitude = location.getLongitude();
				GlobalData.latitude = location.getLatitude();
				Toast.makeText(getApplicationContext(), "Longitude: " + location.getLongitude() + "Latitude: " + location.getLatitude(), Toast.LENGTH_LONG).show();
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
