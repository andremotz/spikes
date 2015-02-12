package com.andremotz.spikes.datamodel;

import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Class that will be processed by FrontEnd
 */
public class AngularTrack {

	String city;
	String username;
	String created_at;
	String title;
	String permalink_url;
	double lat;
	double lng;
	double duration;
	private String country;
	
	String genre;
	double playback_count;
	String description;
	

	public AngularTrack() {
		
	}
	
	@JsonProperty("genre")
	public String getGenre() {
		return genre;
	}

	@JsonProperty("genre")
	public void setGenre(String genre) {
		this.genre = genre;
	}


	@JsonProperty("playback_count")
	public double getPlayback_count() {
		return playback_count;
	}

	@JsonProperty("playback_count")
	public void setPlayback_count(double playback_count) {
		this.playback_count = playback_count;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}



	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}




	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}
	
	@JsonProperty("city")
	public String getCity() {
		return city;
	}
	
	@JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}
	
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}
	
	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonProperty("created_at")
	public String getCreated_at() {
		return created_at;
	}
	
	@JsonProperty("created_at")
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	@JsonProperty("permalink_url")
	public String getPermalink_url() {
		return permalink_url;
	}
	
	@JsonProperty("permalink_url")
	public void setPermalink_url(String permalink_url) {
		this.permalink_url = permalink_url;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	
	@JsonProperty("lat")
	public double getLat() {
		return lat;
	}

	@JsonProperty("lat")
	public void setLat(double d) {
		this.lat = d;
	}

	@JsonProperty("lng")
	public double getLng() {
		return lng;
	}

	@JsonProperty("lng")
	public void setLng(double lng) {
		this.lng = lng;
	}

	@JsonProperty("duration")
	public double getDuration() {
		return duration;
	}

	@JsonProperty("duration")
	public void setDuration(double duration) {
		this.duration = duration;
	}

	
	
}
