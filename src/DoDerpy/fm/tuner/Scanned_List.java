package DoDerpy.fm.tuner;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Scanned_List extends Activity {
	public final static String STATION_CHOSEN= "DoDerpy.fm.tuner.MESSAGE";
	public static String stationId =" ";
	public static String[] values = new String[] { "Station1", "Station2", "Station3",
          "Station4", "Station5"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_with_buttons);
        ListView listView = (ListView) findViewById(R.id.mylist);
//        try {
//			String[] stationList = udpScanRequest();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        //This array will be taken from scheduler.
        

        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);
        
        
        
        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        
        //setting up for Onclick of the list view item
        
        listView.setOnItemClickListener(new OnItemClickListener() {
        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view,
        	    int position, long id) {
        		
        	    stationId = values[position];
        	  }
        	}); 
        
        
        Button schedButton = (Button) findViewById(R.id.schedule);
        schedButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				schedule();
				
			}
		});
        
        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				goHome();
				
			}
		});

        
    }
    
  //Called when clicking the schedule button. Changes screen and loads selected station to 
    //the next page

    public void schedule(){
    	Intent deliverSched = new Intent(this, ScheduleTuneActivity.class);
    	deliverSched.putExtra(STATION_CHOSEN, stationId);
    	startActivity(deliverSched);
    }
    public void goHome(){
    	Intent returnHome = new Intent(this, Welcome_Page.class);
    	startActivity(returnHome);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_with_buttons, menu);
        return true;
    }
//    public String[] udpScanRequest() throws IOException
//    {
//    	int server_port = 5037;
//    	String s = "s";
//    	DatagramSocket socks = new DatagramSocket();
//    	InetAddress local = InetAddress.getByName("120.0.0.0");
//    	int msg_length = s.length();
//    	byte[] message = s.getBytes();
//    	DatagramPacket pcket = new DatagramPacket(message, msg_length, local, server_port);
//    	socks.send(pcket);
//    }
    
}
