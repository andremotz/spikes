package com.andremotz.spikes.datamodel;

import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Class that contains necessary information of a
 * Soundcloud-Track response
 */
public class SoundcloudTrack {

	public SoundcloudTrack() {
		
	}
	
	String artwork_url;
	long bpm;
	String created_at;
	String description;
	long duration;
	String genre;
	String permalink_url;
	long playback_count;
	String title;
	long user_id;
	
	@JsonProperty("artwork_url")
	public String getArtwork_url() {
		return artwork_url;
	}
	
	@JsonProperty("artwork_url")
	public void setArtwork_url(String artwork_url) {
		this.artwork_url = artwork_url;
	}
	
	@JsonProperty("bpm")
	public long getBpm() {
		return bpm;
	}
	
	@JsonProperty("bpm")
	public void setBpm(long bpm) {
		this.bpm = bpm;
	}
	
	@JsonProperty("created_at")
	public String getCreated_at() {
		return created_at;
	}
	
	@JsonProperty("created_at")
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty("duration")
	public long getDuration() {
		return duration;
	}
	
	@JsonProperty("duration")
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	@JsonProperty("genre")
	public String getGenre() {
		return genre;
	}
	
	@JsonProperty("genre")
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	@JsonProperty("permalink_url")
	public String getPermalink_url() {
		return permalink_url;
	}
	
	@JsonProperty("permalink_url")
	public void setPermalink_url(String permalink_url) {
		this.permalink_url = permalink_url;
	}
	
	@JsonProperty("playback_count")
	public long getPlayback_count() {
		return playback_count;
	}
	
	@JsonProperty("playback_count")
	public void setPlayback_count(long playback_count) {
		this.playback_count = playback_count;
	}
	
	@JsonProperty("title")
	public String getTitle() {
		return title;
	}
	
	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonProperty("user_id")
	public long getUser_id() {
		return user_id;
	}
	
	@JsonProperty("user_id")
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	
}
