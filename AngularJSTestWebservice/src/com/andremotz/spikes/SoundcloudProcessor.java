package com.andremotz.spikes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.andremotz.spikes.datamodel.SoundcloudTrack;
import com.andremotz.spikes.datamodel.SoundcloudUser;


/*
 * Processing-Class for Soundcloud-Stuff
 */
public class SoundcloudProcessor {
	
	public SoundcloudProcessor() {
		
	}
	
	Logger log4j = LogManager.getLogger(SoundcloudProcessor.class
	        .getName());
	
	/*
	 * Retrieve JSON response from Soundcloud as String 
	 */
	public String getCurrentJsonQueryForTracks(String query) {

		String stringToReturn = "https://api.soundcloud.com/tracks.json"
				+ "?client_id=" + ProcessorUtil.getSoundcloudapikey() + "&q="
				+ query + "&limit=20";

		return stringToReturn;

	}
	
	/*
	 * Returns JSON-Array from plain JSON-String
	 */
	public ArrayList<String> getJsonArrayFromString(String jsonResponseAsString) {
		ArrayList<String> list = new ArrayList<String>();
		JSONArray jsonArray = new JSONArray(jsonResponseAsString);
		if (jsonArray != null) {
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				list.add(jsonArray.get(i).toString());
			}
		}
		return list;
	}
	
	/*
	 * Return JSON as String from URL
	 */
	public String getJsonFromQuery(String jsonQuery) {
		String jsonResponse = "";

		jsonResponse = retryReadUrl(3, jsonQuery);

		return jsonResponse;
	}

	/*
	 * Recursive call of readUrl() for retry
	 * On fail wait for 1 second
	 */
	public String retryReadUrl(int limit, String jsonQuery) {
		String urlContent = "";

		try {
			urlContent = readUrl(jsonQuery);
		} catch (Exception e) {
			log4j.debug("retry counter:" + limit);
			if (limit < 0) {
				log4j.error("failed!", e);
			} else {
	            try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					log4j.error("failed!", e1);
				}
				retryReadUrl(limit - 1, jsonQuery);
			}
		}
		return urlContent;
	}

	/*
	 * Return content of URL as String
	 */
	private String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	/*
	 * Returns SoundcloudTrack-Object from json-Response
	 */
	public SoundcloudTrack getSoundcloudTrackFromJsonResponse(String jsonResponse) {
		SoundcloudTrack currentSoundcloudTrack = new SoundcloudTrack();
		log4j.trace(jsonResponse);
		
		try {
			currentSoundcloudTrack.setArtwork_url(getResultStringFromJsonResponseByKey(jsonResponse, "artwork_url"));
			
			// TODO bpm not working for weird reasons...
//			currentSoundcloudTrack.setBpm(getResultLongFromJsonResponseByKey(jsonResponse, "bpm"));
			currentSoundcloudTrack.setCreated_at(getResultStringFromJsonResponseByKey(jsonResponse, "created_at"));
			currentSoundcloudTrack.setDescription(getResultStringFromJsonResponseByKey(jsonResponse, "description"));
			currentSoundcloudTrack.setDuration(getResultLongFromJsonResponseByKey(jsonResponse, "duration"));
			currentSoundcloudTrack.setGenre(getResultStringFromJsonResponseByKey(jsonResponse, "genre"));
			currentSoundcloudTrack.setPermalink_url(getResultStringFromJsonResponseByKey(jsonResponse, "permalink_url"));
			currentSoundcloudTrack.setPlayback_count(getResultLongFromJsonResponseByKey(jsonResponse, "playback_count"));
			currentSoundcloudTrack.setTitle(getResultStringFromJsonResponseByKey(jsonResponse, "title"));
			currentSoundcloudTrack.setUser_id(getResultLongFromJsonResponseByKey(jsonResponse, "user_id"));
		} catch (Exception e) {
			log4j.trace(e.getMessage());
		}
			return currentSoundcloudTrack;
			
	}
	
	
	/*
	 * SUPER-DIRTY!!!
	 * returns long from Json-attribute
	 */
	private long getResultLongFromJsonResponseByKey(String jsonResponse,
			String matchKey) {
		JSONParser parser = new JSONParser();
		KeyFinder finder = new KeyFinder();
		finder.setMatchKey(matchKey);
		
		ArrayList<Long> results = new ArrayList<Long>();
		
		try {
			while (!finder.isEnd()) {
				parser.parse(jsonResponse, finder, true);
				if (finder.isFound()) {
					finder.setFound(false);
					results.add((Long) finder.getValue());
				}
			}
		} catch (Exception pe) {
			results = new ArrayList<Long>();
			results.add((new Long(0)));
		}
		
		return results.get(0);
	}

	
	/*
	 * SUPER-DIRTY!!!
	 * returns String from Json-attribute
	 */
	private String getResultStringFromJsonResponseByKey(String jsonResponse, String matchKey) {
		
		JSONParser parser = new JSONParser();
		KeyFinder finder = new KeyFinder();
		finder.setMatchKey(matchKey);
		
		ArrayList<String> results = new ArrayList<String>();
		try {
			while (!finder.isEnd()) {
				parser.parse(jsonResponse, finder, true);
				if (finder.isFound()) {
					finder.setFound(false);
					results.add((String) finder.getValue());
				}
			}
		} catch (ParseException pe) {
			results.add("");
		}
		
		return results.get(0);
		
	}

	/*
	 * Returns SoundcloudUser-Object from specific user-id
	 */
	public SoundcloudUser getSouncloudUserbyId(long currentSoundcloudTrackUserId) {
		SoundcloudUser currentSoundcloudUser = new SoundcloudUser();
		String jsonQuery = "https://api.soundcloud.com/users/" + currentSoundcloudTrackUserId + ".json?client_id=" + ProcessorUtil.getSoundcloudapikey();
		String jsonResponse = getJsonFromQuery(jsonQuery);
		
		currentSoundcloudUser.setCity(getResultStringFromJsonResponseByKey(jsonResponse, "city"));
		currentSoundcloudUser.setId(currentSoundcloudTrackUserId);
		currentSoundcloudUser.setUsername(getResultStringFromJsonResponseByKey(jsonResponse, "username"));
		
		return currentSoundcloudUser;
	}

}
