package com.santosh.map;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.santosh.map.utility.JSONParser;


public class MainActivity extends Activity  implements OnClickListener{
	Spinner customer_spinner,region_spinner,part_spinner;
	Button get_button;
	static boolean status = false ;
	JSONParser j= null;
	String result = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		setOnClicklistner();
	new CheckWorkingInternet().execute("");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	private void setOnClicklistner() {
		get_button.setOnClickListener(this);
	}

	private void initialize() {
		customer_spinner = (Spinner)findViewById(R.id.customer_Spinner);
		region_spinner = (Spinner)findViewById(R.id.region_Spinner);
		part_spinner = (Spinner)findViewById(R.id.part_Spinner);
		get_button = (Button)findViewById(R.id.get_button);
		
		j = new JSONParser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private class CheckWorkingInternet extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	try {
	            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
	            urlc.setRequestProperty("User-Agent", "Test");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(1000); 
	            urlc.connect();
	            Log.d("response",String.valueOf(urlc.getResponseCode()));
	            if(urlc.getResponseCode() == 200)
	            {   	MainActivity.status = true;
        	}else 
	            {   	MainActivity.status = false;
	           
        	}
	            Log.d("before","https access");
	           
				
	        } catch (IOException e) {
	            Log.e("LOG_TAG", "Error checking internet connection", e);
	        }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    
}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.get_button:
			if(status || status == true){
			
			new LongOperation().execute("");
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Intent intent = new Intent(getApplicationContext(), MapActivity.class);
			intent.putExtra("result",result.toString());
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			
			/* * {"GetPartListResult":
			 *  [
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"ABQ - Albuquerque -- US ABQ-002","ZipCode":"87106"}
			 *  ,{"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"ALB - Albany -- US ALB-002","ZipCode":"12205"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"ALW - Pendelton -- US ALW-002","ZipCode":"97801"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"AMA - Amarillo -- US AMA-003","ZipCode":"79104"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"ASN - Austin -- US AUS-001","ZipCode":"78721"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"ATL - Kennesaw -- US ATL-007","ZipCode":"30144"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BDL - Hartford -- US BDL-001","ZipCode":"06108"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BGR - Bangor -- US BGR-002","ZipCode":"04401"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BHM - Birmingham -- US BHM-002","ZipCode":"35209"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BIL - Billings -- US BIL-001","ZipCode":"59101"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BIS - Bismarck -- US BIS-002","ZipCode":"58501"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BMI - Bloomington -- US BMI-003","ZipCode":"61704"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BNA - Nashville -- US BNA-001","ZipCode":"37204"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BOS - Cambridge -- US BOS-006","ZipCode":"02138-1128"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BSE - Boise -- US BOI-001","ZipCode":"83709"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BTR - Baton Rouge -- US BTR-002","ZipCode":"70809"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BTV - Burlington -- US BTV-001","ZipCode":"05495"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"BUF - Buffalo -- US BUF-002","ZipCode":"14225"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CAE - Lexington -- US CAE-001","ZipCode":"29072"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CDC - Cedar City -- US CDC-001","ZipCode":"84720"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CID - Cedar Rapids -- US CID-001","ZipCode":"52404"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CLE - Cleveland -- US CLE-001","ZipCode":"44149"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CLT - Charlotte CLT-002 -- US","ZipCode":"28208"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CMH - Columbus -- US CMH-001","ZipCode":"43222"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"COU - FultonColumbia -- US COU-002","ZipCode":"65201"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CPR - Casper -- US CPR-001","ZipCode":"82602"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CRP - Corpus Christi -- US CRP-001","ZipCode":"78408"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CRW - South Charleston -- US CRW-00","ZipCode":"25313"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CVG - Cincinnati Rush CVG-004","ZipCode":"45241"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CWA - Weston -- US CWA-001","ZipCode":"54476"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"CYS - Cheyenne -- US CYS-001","ZipCode":"82007"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"DCA - Vienna -- US IAD-002","ZipCode":"22180"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"DDC - Garden City -- US GCK-001","ZipCode":"67846"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":"DFW - Dallas -- US DFW-002","ZipCode":"75006"},
			 *  {"PartNumber":"1941-2901-FANBLWR=","TotalQty":"1","WareHouseName":
			 */ 
			 

			
			
			//if(checkMobileDataAvailable()){
				//Toast.makeText(MainActivity.this,"Select some value from part", 5000).show();
		/*		Intent intent = new Intent(getApplicationContext(), MapActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		*/	/*}
			else{	Toast.makeText(MainActivity.this, "no data",5000).show();
			}
			*/
			}
			else 
			{
				Toast.makeText(MainActivity.this, "No internet connection is available", 5000).show();
			}
			break;

		default:
			break;
		}
	}
	private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	
        	try {
        		JSONObject authObj = new JSONObject();
				try {
					authObj.put("Customer", customer_spinner.getSelectedItem().toString());
				
				authObj.put("PartNumber", part_spinner.getSelectedItem().toString());
				authObj.put("Region", region_spinner.getSelectedItem().toString());
				Log.d("jsonO",authObj.toString());
				result  = j.performGet("http://173.12.35.106/FlashSearch/SearchParts.svc/GetParts", authObj);
				//Toast.makeText(MainActivity.this, result.toString(),5000).show();
				Log.d("result",result.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    
}
}
