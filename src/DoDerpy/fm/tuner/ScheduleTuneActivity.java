package DoDerpy.fm.tuner;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class ScheduleTuneActivity extends Activity {
	public final static String TIME= "DoDerpy.fm.tuner.MESSAGET";
	public final static String DATE= "DoDerpy.fm.tuner.MESSAGED";
	private static final int UDP_SERVER_PORT = 21001;

	public String timeRes;
	public String dateRes;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	static final int TIME_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID = 998;
	
	
    @SuppressLint({ "NewApi", "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduletune_layout);
        Intent intent = getIntent();
        String stationId = intent.getStringExtra(Scanned_List.STATION_CHOSEN);
        //screw you API, heres this
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        		new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        }
        
        
        
     
        final TextView stationText =  (TextView) findViewById(R.id.stationPicked);
        stationText.setText(stationId);
        addListenerOnButtonT();
        addListenerOnButtonD();
        
        
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
    
    
    
    
    //****************************************************
    //**************Start of TIME dialog maddness*********
    //****************************************************
    public void addListenerOnButtonT(){
		Button timeChange = (Button) findViewById(R.id.timeChange);
		 timeChange.setOnClickListener(new OnClickListener(){
			 @Override
				public void onClick(View v) {
	 
					showDialog(TIME_DIALOG_ID);
	 
				}
			 
		 });
		
    }
    @Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, 
                                        timePickerListener, hour, minute,false);
 
		
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                      year, month,day);
		}
		return null;
	}
 
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// makes a string out of the time
			 timeRes = pad(hour) + ";" +pad(minute);

		}
	};
	
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
    
    
    //****************************************************
    //**************Start of DATE dialog maddness*********
    //****************************************************
	public void addListenerOnButtonD() {
		 
		Button dateChange = (Button) findViewById(R.id.dateChange);
 
		dateChange.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
				showDialog(DATE_DIALOG_ID);
 
			}
 
		});
 
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth + 1;
			day = selectedDay;
 
			dateRes = pad(month) + ";" + pad(day);
 
		}
	};
	
	
	
	
	
    
    
    //****************************************************
    //**************Start of UDP maddness*********
    //****************************************************
    public void udpSend(String stationPicked) throws IOException
    {
    	
    	String udpMsg = stationPicked+ ";" + dateRes + ";" + timeRes;	
       // String udpMsg = "hello world from UDP client " + UDP_SERVER_PORT;

        DatagramSocket ds = null;

        try {

            ds = new DatagramSocket();

            InetAddress serverAddr = InetAddress.getByName("137.140.8.82");

            DatagramPacket dp;

            dp = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, UDP_SERVER_PORT);

            ds.send(dp);

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
    	
    	
    	//switching page
        Intent onward = new Intent(this, Finished.class);
        onward.putExtra(TIME, timeRes);
        onward.putExtra(DATE, dateRes);
    	startActivity(onward);
    }

    
    

}
