package com.groupies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GroupiesActivity extends Activity{
    /** Called when the activity is first created. */
	 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
    	
    		Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_LONG).show();
    		
    		
    	
    		// don't start listeners if no provider is enabled
   		
    		

    	//Location lastKnownLocation = locManager.getLastKnownLocation(locationProvider);
    /*	if(lastKnownLocation!=null){
	    	System.out.println("lat ->"+lastKnownLocation.getLatitude());
	    	System.out.println("long ->"+lastKnownLocation.getLongitude());
    	}*/
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

    
   
}

	
    