package DoDerpy.fm.tuner;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class ScheduleTuneActivity extends Activity {
	private static final int DATE_DIALOG_ID = 0;
	private DatePicker dpResult;
	private int year, month, day;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduletune_layout);
        Intent intent = getIntent();
        String stationId = intent.getStringExtra(Scanned_List.STATION_CHOSEN);
        
        final TextView stationText =  (TextView) findViewById(R.id.stationPicked);
        stationText.setText(stationId);
        
        
        //going back.
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				goBack();
				
			}
		});
        
        
        //Hitting Sched
        Button sched = (Button) findViewById(R.id.sendSched);
        sched.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View arg0){
        		try {
					udpSend(stationText.getText().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
        
    }
    //default
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scheduletune_layout, menu);
        return true;
    }
    //Brings back the scanned list screen
    public void goBack(){
    	Intent back = new Intent(this, Scanned_List.class);
    	startActivity(back);
    }
    public void udpSend(String stationPicked) throws IOException
    {
    	int server_port = 5037;
    	
    	DatagramSocket socks = new DatagramSocket();
    	InetAddress local = InetAddress.getByName("120.0.0.0");
    	int msg_length = stationPicked.length();
    	byte[] message = stationPicked.getBytes();
    	DatagramPacket pcket = new DatagramPacket(message, msg_length, local, server_port);
    	socks.send(pcket);
    }

    
    

}
