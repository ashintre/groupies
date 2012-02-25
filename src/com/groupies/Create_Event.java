package com.groupies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.EditText;

public class Create_Event extends Activity{

	LinearLayout l,chkbx;
	CheckBox[] boxes; 
	int pos;
	String[] temp;
	String[] selected;
	EditText eventnm,starttime,stoptime,eventloc;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_new);
	
		
	       // l=(LinearLayout) findViewById(R.id.main);
	        chkbx=(LinearLayout) findViewById(R.id.checkBox);
	        eventnm = (EditText) findViewById(R.id.event_edittext);
	        starttime = (EditText) findViewById(R.id.event_start_time_edittext);
	        stoptime = (EditText) findViewById(R.id.event_stop_time_edittext);
	        eventloc = (EditText) findViewById(R.id.event_location_edittext);
	        
	        HttpClient httpclient = new DefaultHttpClient();
			//HttpPost httppost = new HttpPost("http://10.0.2.2/display_rules.php");
			HttpPost httppost = new HttpPost("http://192.168.16.1/select_users_hack.php");
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
				nameValuePairs.add(new BasicNameValuePair("username", GlobalData.username));
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
				String result = builder.toString();
				 temp  = result.split(",");
				 boxes = new CheckBox[temp.length];
				 for(int i=0; i < temp.length; i++){
					 
					 boxes[i]= new CheckBox(this);
					 boxes[i].setText(temp[i]);
					 chkbx.addView(boxes[i]);
				 }
				 
				  /* given string will be split by the argument delimiter provided. */
				
				  /* print substrings */
				 
				 
				 final Button button1 = (Button) findViewById(R.id.create_login_btn);
					button1.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							int j=0;	
							String utemp = new String();
							for(int i=0; i < temp.length; i++){
								if(boxes[i].isChecked()){ 
									selected[j] = (String) boxes[i].getText();
									utemp = utemp + (String) boxes[i].getText() + ",";
									j++;
								}
							}
							
							HttpClient httpclient = new DefaultHttpClient();
							//HttpPost httppost = new HttpPost("http://10.0.2.2/user_insert.php");
							HttpPost httppost = new HttpPost("http://192.168.16.1/insert_event_group_hack.php");
							
								// Add your data
								List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
								nameValuePairs.add(new BasicNameValuePair("username", GlobalData.username));
								nameValuePairs.add(new BasicNameValuePair("eventname", eventnm.getText().toString()));
								nameValuePairs.add(new BasicNameValuePair("starttime", starttime.getText().toString()));
								nameValuePairs.add(new BasicNameValuePair("stoptime", stoptime.getText().toString()));
								nameValuePairs.add(new BasicNameValuePair("destination", eventloc.getText().toString()));
								nameValuePairs.add(new BasicNameValuePair("ugroup", utemp));
								
								
								try {
									List<Address> foundGeocode = null;
									/* find the addresses  by using getFromLocationName() method with the given address*/
									foundGeocode = new Geocoder(getApplicationContext()).getFromLocationName(eventloc.getText().toString(), 1);
									GlobalData.destination_latitude = foundGeocode.get(0).getLatitude(); //getting latitude
									GlobalData.destination_longitude = foundGeocode.get(0).getLongitude();//getting longitude
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								
								try {
									httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
									HttpResponse response = httpclient.execute(httppost);
									System.out.println("Response ->"+response.toString());
									String eid = response.toString();
									GlobalData.eid = eid;
									Intent i = new Intent(getBaseContext(),Display.class);
									startActivity(i); 

								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								// Execute HTTP Post Request
							
					}
					});
					 
				 
						
				
			}
			catch (ClientProtocolException e) {
				System.out.println("exception 1");
				// TODO Auto-generated catch block
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("error ");
				// TODO Auto-generated catch block
			}
		
	}
	
	
	
}
