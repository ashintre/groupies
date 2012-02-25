package com.groupies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Login extends Activity{

	   /** Called when the activity is first created. */
	LinearLayout l;
	EditText username,password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        username=(EditText) findViewById(R.id.username_edittext);
        password=(EditText) findViewById(R.id.password_edittext);
        
        //l=(LinearLayout) findViewById(R.id.main);
               
           
        
        final Button button1 = (Button) findViewById(R.id.login_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            	String locationProvider = LocationManager.NETWORK_PROVIDER;

            	Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            	
            	System.out.println("lat ->"+lastKnownLocation.getLatitude());
            	System.out.println("long ->"+lastKnownLocation.getLongitude());
            	
            	HttpClient httpclient = new DefaultHttpClient();
            	//HttpPost httppost = new HttpPost("http://10.0.2.2/user_select.php");
            	HttpPost httppost = new HttpPost("http://192.168.16.1/login_check_hack.php");
            	try {
            		// Add your data
            		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            		nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString()));
            		nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));
            		//nameValuePairs.add(new BasicNameValuePair("latitude", lastKnownLocation.getLatitude()));
            		
            		//also send lat and long
            		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            		// Execute HTTP Post Request
            		HttpResponse response = httpclient.execute(httppost);
            		//String res = EntityUtils.toString(response.getEntity());
            		BufferedReader rd = new BufferedReader(new InputStreamReader(
        					response.getEntity().getContent()));
        			String res = new String();
        			res = rd.readLine();
        			System.out.println("Response ->"+res);
            		if(Integer.parseInt(res)==1)
            		{
            			GlobalData.username = username.getText().toString();
            			Intent i = new Intent(getBaseContext(),Create_Event.class);
            			startActivity(i);
		
            		}
            		else
            		{
            			AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
						builder.setCancelable(true);
						builder.setTitle("Error: Incorrect Password or Username!!");
						builder.setInverseBackgroundForced(true);
						builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						  @Override
						  public void onClick(DialogInterface dialog, int which) {
						    dialog.dismiss();
						    username.setText("");
						    password.setText("");
						  }
						});
						
						AlertDialog alert = builder.create();
						alert.show();
            		}
            	} catch (ClientProtocolException e) {
            		System.out.println("exception 1");
            		// TODO Auto-generated catch block
            	} catch (IOException e) {
            		e.printStackTrace();
            		System.out.println("error ");
            		// TODO Auto-generated catch block
            	}


            }
        });

            }

			
    	
    }

