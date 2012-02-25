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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Create_New extends Activity {
	/** Called when the activity is first created. */
	LinearLayout l;
	EditText username,password,re_password;
	 Location lastKnownLocation;
     public LocationManager locManager;
     public LocationListener locListener = new MyLocationListener();
     public boolean gps_enabled = false;
     public boolean network_enabled = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_new);

		username = (EditText) findViewById(R.id.username_edittext);
		password = (EditText) findViewById(R.id.password_edittext);
		re_password = (EditText) findViewById(R.id.re_password_edittext);
	//l=(LinearLayout) findViewById(R.id.main);
				final Button button1 = (Button) findViewById(R.id.create_login_btn);
				button1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						
						// Perform action on click
						if(password.getText().toString().equals(re_password.getText().toString()))
						{
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
									//HttpPost httppost = new HttpPost("http://10.0.2.2/user_insert.php");
									HttpPost httppost = new HttpPost("http://192.168.16.1/user_insert_hack.php");
									try {
										// Add your data
										List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
										nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString()));
										nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));
										nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(GlobalData.latitude)));
					            		nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(GlobalData.longitude)));
										//also send lat and long
										httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

										// Execute HTTP Post Request
										HttpResponse response = httpclient.execute(httppost);
										System.out.println("Response ->"+response.toString());
										GlobalData.username = username.getText().toString();
										Intent i = new Intent(getBaseContext(),Create_Event.class);
										startActivity(i); 
									} catch (ClientProtocolException e) {
										System.out.println("exception 1");
										// TODO Auto-generated catch block
									} catch (IOException e) {
										e.printStackTrace();
										System.out.println("error ");
										// TODO Auto-generated catch block
									}

						
						}
						else
						{
							AlertDialog.Builder builder = new AlertDialog.Builder(Create_New.this);
							builder.setCancelable(true);
							builder.setTitle("Error: Passwords don't match!!");
							builder.setInverseBackgroundForced(true);
							builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							  @Override
							  public void onClick(DialogInterface dialog, int which) {
							    dialog.dismiss();
							    password.setText("");
							    re_password.setText("");
							  }
							});
							
							AlertDialog alert = builder.create();
							alert.show();

						}
					}
				});
				

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

