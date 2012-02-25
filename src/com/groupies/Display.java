package com.groupies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Display extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		
		final Button button1 = (Button) findViewById(R.id.map_btn);
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("Map button pressed");
				Intent i = new Intent(getBaseContext(),Map_Display.class);
				startActivity(i); 
			}
		});
		
		final Button button2 = (Button) findViewById(R.id.text_btn);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("Text button pressed");
				Intent i = new Intent(getBaseContext(),Text_Display.class);
				startActivity(i); 
			}
		});
		
		final Button button3 = (Button) findViewById(R.id.exit_btn);
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
	}
}
