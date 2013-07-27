package DoDerpy.fm.tuner;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Welcome_Page extends Activity {

    private static final String STATION_CHOSEN = "DoDerpy.fm.tuner.MESSAGE";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__page);
        
        
        Button schedButton = (Button) findViewById(R.id.tune);
        schedButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				schedule();
				
			}
		});
        
        Button scanButton = (Button) findViewById(R.id.scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				scan();
			}
		});
        Button openUrl = (Button) findViewById(R.id.listen);
        openUrl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				openWebURL("http://agile02.cs.newpaltz.edu:8001/example1.ogg");
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_welcome__page, menu);
        return true;
    }
    public void schedule(){
    	Intent deliverSched = new Intent(this, ScheduleTuneActivity.class);
    	deliverSched.putExtra(STATION_CHOSEN, "Your Station Here");
    	startActivity(deliverSched);
    }
    public void scan(){
    	Intent scanList = new Intent(this, Scanned_List.class);
		startActivity(scanList);
    }
    public void openWebURL( String inURL ) {
        Intent goToIce = new Intent( Intent.ACTION_VIEW , Uri.parse( inURL ) );

        startActivity( goToIce );
    }
}
