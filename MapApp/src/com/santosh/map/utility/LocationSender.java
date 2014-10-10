package com.santosh.map.utility;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santosh.map.MapActivity;



import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationSender {

	Context context;
	LocationManager locationManager;
	Location location;
	 static LatLng ulocation = null;
	 int check = 0;
	 public static Marker ham = null;
	public LocationSender(Context ctx) {
		context=ctx;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public void stop()
	{
		locationManager.removeUpdates(locationListener);
	}
	// Acquire a reference to the system Location Manager
	public void getNewLocation(Location location)
	{
		String latLongString = "";
        if (location != null) {
        	this.location=location;
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
            Log.d("Location Found", latLongString);
            MapActivity.latitudeGPS = lat;
            MapActivity.logitudeGPS = lng;
            ulocation = new LatLng(lat, lng);
           ham = MapActivity.map.addMarker(new MarkerOptions().position(ulocation)
        	        .title("Your location").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
           
        	 if(check==0)
        	 { 
        		 MapActivity.id = ham.getId();
        	// MapActivity.map.moveCamera(CameraUpdateFactory.newLatLngZoom(ulocation, 15));
        	 check = 1;
        	 }   
        } else
        {
        	location=null;
            latLongString = "No location found";
        }
        Toast.makeText(context, latLongString, Toast.LENGTH_LONG).show();
	}
	 

	// Define a listener that responds to location updates
	LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	      // Called when a new location is found by the network location provider.
	      getNewLocation(location);
	    }

	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    	
	    	Toast.makeText(context, "Provider: "+provider+" : status: "+status, Toast.LENGTH_LONG).show();
	    	
	    }

	    public void onProviderEnabled(String provider) {
	    	
	    	Toast.makeText(context, "Provider: "+provider+" : Enabled", Toast.LENGTH_LONG).show();
	    }

	    public void onProviderDisabled(String provider) {
	    	
	    	Toast.makeText(context, "Provider: "+provider+" : disabled", Toast.LENGTH_LONG).show();
	    }
	  };

	// Register the listener with the Location Manager to receive location updates
	//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

}
