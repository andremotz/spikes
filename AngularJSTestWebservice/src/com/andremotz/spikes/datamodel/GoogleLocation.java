package com.andremotz.spikes.datamodel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Class that contains useful attributes of a Google-Geolocation
 * response
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleLocation {
	
	double lat;
	double lng;
	
	String long_name;
	
	public GoogleLocation() {
		
	}

	@JsonProperty("long_name")
	public String getLong_name() {
		return long_name;
	}
	
	@JsonProperty("long_name")
	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}
	
	@JsonProperty("lat")
	public double getLat() {
		return lat;
	}
	
	@JsonProperty("lat")
	public void setLat(double currentLat) {
		this.lat = currentLat;
	}
	
	@JsonProperty("lng")
	public double getLng() {
		return lng;
	}
	@JsonProperty("lng")
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	

}
