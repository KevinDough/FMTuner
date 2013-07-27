package DoDerpy.fm.tuner;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Scanned_List extends Activity {
	public final static String STATION_CHOSEN= "DoDerpy.fm.tuner.MESSAGE";
	private static final int UDP_SERVER_PORT = 21001;
	private static final int MAX_UDP_DATAGRAM_LEN = 1024;
	public static String stationId =" ";
	public static String[] values = new String[] {"87.2","'88.3","89.0","92.1","94.3","95.1","97.6",
			"98.3", "99.7", "100.1" , "101.5", "104.5" , "106.3", "107.6", "107.7"};
	
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_with_buttons);
        ListView listView = (ListView) findViewById(R.id.mylist);
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        		new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        }
        
        //send "s"
        sendRequest();
        //wait for signal to get back, delay currently 10 seconds
        //runUdpServer();
		//values is the parsed list
        
        
        //ARRAY ADDAPTER INSTRUCTIONS
        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);
        
        
        
        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        
        //setting up for Onclick of the list view item
        
        listView.setOnItemClickListener(new OnItemClickListener() {
        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view,
        	    int position, long id) {
        		
        	    stationId = values[position];
        	    Toast.makeText(getBaseContext(), stationId, stationId.length()).show();
        	  }
        	}); 
        
        //set up sched button that moves program along
        Button schedButton = (Button) findViewById(R.id.schedule);
        schedButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				schedule();
				
			}
		});
        //sets up return home button
        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				goHome();
				
			}
		});
        //sets up refresh button
        Button refresh = (Button) findViewById(R.id.scan);
        refresh.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				sendRequest();

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
    
    //Brings the person back to the titles
    public void goHome(){
    	Intent returnHome = new Intent(this, Welcome_Page.class);
    	startActivity(returnHome);
    }
    //default thing
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_with_buttons, menu);
        return true;
    }
    
    //************************************************************
    //*****************Running listener, waits for list***********
    //************************************************************
    
    
    private void runUdpServer() {

        String lText;

        byte[] lMsg = new byte[MAX_UDP_DATAGRAM_LEN];

        DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);

        DatagramSocket ds = null;

        try {

            ds = new DatagramSocket(UDP_SERVER_PORT);

            //disable timeout for testing

            ds.setSoTimeout(10000);

            ds.receive(dp);

            lText = new String(lMsg, 0, dp.getLength());
            
            lText.replaceAll("\0", "");

            Log.i("UDP packet received", lText);
            
            String[] parsedList = lText.split(";");
            
            values = parsedList;

        } catch (SocketException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (ds != null) {

                ds.close();

            }

        }
		//values = new String[]{ "eStation1", "eStation2","eStation3","eStation4","please refresh in a moment"};

    }
    
    //************************************************************
    //*****************Sends "s" to Scheduler*********************
    //************************************************************
    
    
    public void sendRequest(){
    	 String udpMsg = "s";
    	 
         DatagramSocket ds = null;

         try {

             ds = new DatagramSocket();

             InetAddress serverAddr = InetAddress.getByName("137.140.8.82");//address of Agile 02

             DatagramPacket dps; //for sending

             dps = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, UDP_SERVER_PORT);

             
             //sent, now receive
              
             String lText;

             byte[] lMsg = new byte[MAX_UDP_DATAGRAM_LEN];
             
             ds.send(dps);
             
             DatagramPacket dpr = new DatagramPacket(lMsg, lMsg.length);
             
             ds.receive(dpr);
             ds.setSoTimeout(10000);
             lText = new String(lMsg, 0, dpr.getLength());
             
             lText.replaceAll("\0", "");

             Log.i("UDP packet received", lText);
             
             String[] parsedList = lText.split(";");
             
             values = parsedList;
             
             ds.close();
             

         } catch (SocketException e) {

             e.printStackTrace();

         }catch (UnknownHostException e) {

             e.printStackTrace();

         } catch (IOException e) {

             e.printStackTrace();

         } catch (Exception e) {

             e.printStackTrace();

         } finally {

             if (ds != null) {

                 ds.close();

             }

         }
    }

}
