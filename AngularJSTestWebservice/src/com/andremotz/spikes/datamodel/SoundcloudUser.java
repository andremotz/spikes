package com.andremotz.spikes.datamodel;

/*
 * Class that contains necessary information
 * of a Soundcloud-User
 */
public class SoundcloudUser {

	public SoundcloudUser() {
		
	}
	
	String city;
	long id;
	String username;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
