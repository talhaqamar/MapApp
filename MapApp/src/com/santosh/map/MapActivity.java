	package com.santosh.map;
	
	import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santosh.map.utility.LocationSender;
import com.santosh.map.utility.Utility;
	
	public class MapActivity extends Activity implements OnInfoWindowClickListener,android.view.View.OnClickListener{
		  static final LatLng KIEL = new LatLng(53.551, 9.993);
		  public static GoogleMap map=null;
		  String result = "";
		  public static double latitudeGPS = 0.0;
		  public static double logitudeGPS = 0.0;
		  static LatLng ulocation = null;
		  LocationSender l = null;
		  public static String id = null;
		  List<String> ziplist = new ArrayList<String>();
		  List<Double> dislist = new ArrayList<Double>();
		  Utility u = null;//new Utility();
		  List<Utility> list = new ArrayList<Utility>();
		  ProgressDialog p = null;
		  Double sd = null;
		  Button button = null;
		   EditText input = null;
			
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_map);
		   
		    Intent in = getIntent();
		    result = in.getStringExtra("result");
		    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		    button = (Button)findViewById(R.id.button);
		    l = new LocationSender(MapActivity.this);
		    button.setOnClickListener(this);
		    
		    
		    ulocation = new LatLng(latitudeGPS, logitudeGPS);
		    //calculate(result);
		    new CalculateOperation().execute(result);
		    
		    try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    for(int seedha=0;seedha<1;seedha++)
		    {
		    	Log.d("before sort",String.valueOf(dislist.get(seedha)));
		    }
		    Collections.sort(dislist);
		    //Collections.reverse(dislist);
		    for(int ulta=0;ulta<1;ulta++)
		    {
		    	Log.d("before sort",String.valueOf(dislist.get(ulta)));
		    }
		    for(int i=0;i<10;i++)
		    {
		    	 sd = dislist.get(i);
		    	
		    	for(Utility l : list)
		    	{
		    		if(l.get_distance().equals(sd))
		    		{
		    			LatLng ll = new LatLng(l.get_lat(), l.get_lng());
		    	Marker hamburg = map.addMarker(new MarkerOptions().position(ll)
				 	        .title("ZipCode :"+l.get_zipcode())); 
		    		}
		    	}
		    }
		    
		    
		    map.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				 String i = marker.getId();
				 Log.d("marker id",i);
				 Log.d("id",id);
				 
				 if(i.equals(MapActivity.id) || i.contains(MapActivity.id))
				 {
					 marker.setSnippet("");
				 
				  }
				 else 
				 {
					 LatLng l = marker.getPosition();
						double dis = gps2m(
								Float.parseFloat(String.valueOf(ulocation.latitude)),
								Float.parseFloat(String.valueOf(ulocation.longitude)),
								Float.parseFloat(String.valueOf(l.latitude)),
								Float.parseFloat(String.valueOf(l.longitude))
								);
						
				//			marker.setSnippet("Distance b/w current and this loc:"+String.valueOf(dis)+" miles");
						
				 }
				return false;
			}
		});
		    map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				
				@Override
				public void onInfoWindowClick(Marker marker) {
				}
			});
		    /*Marker kiel = map.addMarker(new MarkerOptions()
		        .position(KIEL)
		        .title("Kiel")
		        .snippet("Kiel is cool")
		        .icon(BitmapDescriptorFactory
		            .fromResource(R.drawable.ic_launcher)));
	
		    // Move the camera instantly to hamburg with a zoom of 15.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
	
		    // Zoom in, animating the camera.
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	*/	 
		  }
		  private double gps2m(float lat_a, float lng_a, float lat_b, float lng_b) {
			    float pk = (float) (180/3.14169);
	
			    float a1 = lat_a / pk;
			    float a2 = lng_a / pk;
			    float b1 = lat_b / pk;
			    float b2 = lng_b / pk;
	
			    float t1 = FloatMath.cos(a1)*FloatMath.cos(a2)*FloatMath.cos(b1)*FloatMath.cos(b2);
			    float t2 = FloatMath.cos(a1)*FloatMath.sin(a2)*FloatMath.cos(b1)*FloatMath.sin(b2);
			    float t3 = FloatMath.sin(a1)*FloatMath.sin(b1);
			    double tt = Math.acos(t1 + t2 + t3);
			    tt = 6366000*tt;
			    tt = tt * 0.6214;
			    return tt;
			}
			private class CalculateOperation extends AsyncTask<String, Void, String> {

		        @Override
		        protected String doInBackground(String... params) {
		        		String data = params[0];
		        	calculate(data);
		            return "Executed";
		        }

		        @Override
		        protected void onPostExecute(String result) 
		        {
		        p.dismiss();	
		        }

		        @Override
		        protected void onPreExecute()
		        {
		        	   p = ProgressDialog.show(MapActivity.this, "",
		                        "Loading...");
		        	
		        }

		        @Override
		        protected void onProgressUpdate(Void... values) {}
		    
		}

	void calculate(String result)
	{
		try {
			JSONObject ja = new JSONObject(result);
			JSONArray j = new JSONArray(ja.getString("GetPartListResult"));
			Log.d("length",String.valueOf(j.length()));
			for(int i=0;i< j.length();i++)
			{	
				JSONObject j1 = j.getJSONObject(i);
				final Geocoder geocoder = new Geocoder(this);
				List<Address> addresses = geocoder.getFromLocationName(j1.getString("ZipCode"), 1);
				  if (addresses != null && !addresses.isEmpty()) {
				    Address address = addresses.get(0);
				    String message = String.format("Latitude: %f, Longitude: %f", 
				    address.getLatitude(), address.getLongitude());
				    LatLng HAMBURG = new LatLng(address.getLatitude(),address.getLongitude());
				 /*   Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
				 	        .title("ZipCode :"+j1.getString("ZipCode"))); 
				 */  
				    double dis = gps2m(
							Float.parseFloat(String.valueOf(ulocation.latitude)),
							Float.parseFloat(String.valueOf(ulocation.longitude)),
							Float.parseFloat(String.valueOf(address.getLatitude())),
							Float.parseFloat(String.valueOf(address.getLongitude()))
							);
				    Log.d("dis",String.valueOf(dis));
				   dislist.add(new Double(dis));
				   ziplist.add(j1.getString("ZipCode"));
				   u = new Utility();
				   u.set_zipcode(j1.getString("ZipCode"));
				   u.set_lat(address.getLatitude());
				   u.set_lng(address.getLongitude());
				   u.set_distance(dis);
				   u.set_quantity(Integer.parseInt(j1.getString("TotalQty")));
				   list.add(u);
				      } else {
				    Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show(); 
				  }
				
			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
	}
	
	
	@Override
	public void onInfoWindowClick(Marker marker) {
			marker.showInfoWindow();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			Toast.makeText(MapActivity.this, "Clicked", 5000).show();
			input = new EditText(MapActivity.this);
			input.setHint("Enter Zip code");
			input.setInputType(InputType.TYPE_CLASS_NUMBER);
			AlertDialog.Builder alert = new AlertDialog.Builder(MapActivity.this);

			alert.setTitle("Title");
			alert.setMessage("Message");

			// Set an EditText view to get user input 
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
			  if(value.length() == 0)
			  {
				  Toast.makeText(MapActivity.this,"Zip Code cant be empty", 5000).show();
			  }
				else if(value.length() >= 0 && value.length() <5)
			  {
					  Toast.makeText(MapActivity.this,"Invalid ZipCode.Length should be 5 characters e.g 22180", 5000).show();
				  
			  }
				else if(value.length() == 5)
				{
					 try {
						Geocoder geocoder1 = new Geocoder(MapActivity.this);
						List<Address> addresses = geocoder1.getFromLocationName(value, 1);
						  if (addresses != null && !addresses.isEmpty()) {
						    Address address = addresses.get(0);
						    String message = String.format("Latitude: %f, Longitude: %f", 
						    address.getLatitude(), address.getLongitude());
						 /*ulocation.latitude = address.getLatitude();
						 ulocation.longitude = address.getLongitude();
						  */  ulocation = new LatLng(address.getLatitude(),address.getLongitude());
						    l.stop();
						    map.clear();
						    map.animateCamera(CameraUpdateFactory.zoomTo(0.0f), 1000, null);
						    ziplist = null;
						    	dislist = null;
							    u = null;
							    list = null;
							   
							    ziplist = new ArrayList<String>();
								dislist = new ArrayList<Double>();
								u = new Utility();
								list = new ArrayList<Utility>();
						    
								new CalculateOperation().execute(result);
								try {
									Thread.sleep(15000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							    
							    for(int seedha=0;seedha<1;seedha++)
							    {
							    	Log.d("before sort",String.valueOf(dislist.get(seedha)));
							    }
							    Collections.sort(dislist);
							    //Collections.reverse(dislist);
							    for(int ulta=0;ulta<1;ulta++)
							    {
							    	Log.d("before sort",String.valueOf(dislist.get(ulta)));
							    }
							    for(int i=0;i<10;i++)
							    {
							    	 sd = dislist.get(i);
							    	
							    	for(Utility l : list)
							    	{
							    		if(l.get_distance().equals(sd))
							    		{
							    			LatLng ll = new LatLng(l.get_lat(), l.get_lng());
							    	Marker hamburg = map.addMarker(new MarkerOptions().position(ll)
									 	        .title("ZipCode: "+l.get_zipcode()).snippet("Quantity: "+String.valueOf(l.get_quantity()))); 
							    		}
							    	}
							    }
							  //  LocationSender.ham.remove();
							    ulocation = new LatLng(ulocation.latitude + 0.01,ulocation.longitude + 0.01);
							    LocationSender.ham = MapActivity.map.addMarker(new MarkerOptions().position(ulocation)
					        	        .title("Your location").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
							  
						  }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
					  
					  
				}
				else 
				{
					  Toast.makeText(MapActivity.this,"Invalid Zip Code", 5000).show();

				}
				
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});

			alert.show();
			break;

		default:
			break;
		}
		
	}
		} 