package DoDerpy.fm.tuner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Finished extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        Intent intent = getIntent();
        
        TextView timeText = (TextView) findViewById(R.id.timeText);
        
        String timeInproper = intent.getStringExtra(ScheduleTuneActivity.TIME);
        String timeProper = timeInproper.replace(';', ':');
        timeText.setText(timeProper);
        
        TextView date = (TextView) findViewById(R.id.dateText);
        String dateInProper = intent.getStringExtra(ScheduleTuneActivity.DATE);
        String dateProper = dateInProper.replace(';', '/');
        date.setText(dateProper);
        
        
        Button homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				goHome();
				
			}
		});
        
        Button exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_finished, menu);
        return true;
    }
    public void goHome(){
    	Intent returnHome = new Intent(this, Welcome_Page.class);
    	startActivity(returnHome);
    }
}
