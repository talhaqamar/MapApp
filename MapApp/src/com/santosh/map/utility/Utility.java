package com.santosh.map.utility;

public class Utility {
 
	String zipcode;
	Double lat;
	Double lng;
	Double distance;
	int quantity;
	
	public Utility(String zc,Double la,Double ln,Double d,int q)
	{
		this.zipcode = zc;
		this.lat = la;
		this.lng = ln;
		this.distance = d;
		this.quantity = q;
	}
	public Utility(){}
	
	public void set_zipcode(String z)
	{
		this.zipcode = z;
	}
	public String get_zipcode()
	{
	return this.zipcode;
	}
	public void set_lat(Double l)
	{
		this.lat = l;
	}
	public Double get_lat()
	{
		return this.lat;
	}
	public void set_lng(Double l)
	{
		this.lng = l;
	}
	public Double get_lng()
	{
		return this.lng;
	}
	public void set_distance(Double l)
	{
		this.distance = l;
	}
	public Double get_distance()
	{
		return this.distance;
	}
	public void set_quantity(int l)
	{
		this.quantity = l;
	}
	public int get_quantity()
	{
		return this.quantity;
	}
		


}
