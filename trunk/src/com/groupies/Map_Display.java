package com.groupies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.groupies.HelloItemizedOverlay;
import com.groupies.R;
import com.groupies.Text_Display.MyLocationListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;



public class Map_Display extends MapActivity {
	Location lastKnownLocation;
    public LocationManager locManager;
    public LocationListener locListener = new MyLocationListener();
    public boolean gps_enabled = false;
    public boolean network_enabled = false;

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.pin);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable,this);
		
		
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
    		Reader in = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			    StringBuilder builder= new StringBuilder();
			    char[] buf = new char[1000];
			    int l = 0;
			    while (l >= 0) {
			        builder.append(buf, 0, l);
			        l = in.read(buf);
			    }
			System.out.println("Response ->"+builder.toString());
			HashMap<String,String> res = parse(builder.toString());
			System.out.println(res);
			String[] curr_locations = new String[res.size()];
			int cnt=0;
			
			GeoPoint point = new GeoPoint((int)GlobalData.destination_latitude, (int)GlobalData.destination_longitude);
			OverlayItem overlayitem = new OverlayItem(point, "Destination", "This is the meeting place!");
			itemizedoverlay.addOverlay(overlayitem);
				
			int mcnt=1;
			for(String temp: res.keySet())
			{
				
				String curr;
				String[] location = res.get(temp).split(",");
				String latitude = location[0];
				String longitude = location[1];
				curr_locations[cnt++] = "Username :"+temp+"\n"+"Latitude :"+latitude+"Longitude :"+longitude;
				
				double distance;  
				 
				GeoPoint point2 = new GeoPoint((int)Double.parseDouble(latitude),(int)Double.parseDouble(longitude));
				OverlayItem overlayitem2 = new OverlayItem(point2, "Source"+mcnt, "I'm in currently here!");
				mcnt++;
				itemizedoverlay.addOverlay(overlayitem2);
								  
				
			}
    	} catch (ClientProtocolException e) {
    		System.out.println("exception 1");
    		// TODO Auto-generated catch block
    	} catch (IOException e) {
    		e.printStackTrace();
    		System.out.println("error ");
    		// TODO Auto-generated catch block
    	}
		

		mapOverlays.add(itemizedoverlay);
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



HashMap<String,String> parse(String result)
{
	 System.out.println("result ->"+result);
	 HashMap<String,String> res_map = new HashMap<String,String>();
	 String[] temp;
	 String delimiter = ";";
	 String delimiter2= ",";
	 String[] temp2;
	  /* given string will be split by the argument delimiter provided. */
	  temp = result.split(delimiter);
	  /* print substrings */
	  for(int i =0; i < temp.length ; i++)
	  {
		  
		  temp2 = temp[i].split(delimiter2);
		  temp2[0]=temp2[0].replace("{","");
		  
		  temp2[2]=temp2[2].replace("}","");
		  
		  String temp3 = temp2[1]+","+temp2[2];
		  
		  res_map.put(temp2[0], temp3);
	  }
	 
	 
	return res_map;
	
}

}
