package com.groupies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GroupiesActivity extends Activity {
    /** Called when the activity is first created. */
	LinearLayout l;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button button = (Button) findViewById(R.id.login_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              	Intent i = new Intent(getBaseContext(),Login.class);
                startActivity(i); 
              }
        });
        
        final Button button1 = (Button) findViewById(R.id.new_user_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent i = new Intent(getBaseContext(),create_new.class);
                startActivity(i); 
            }
        });
    }
}


	
    