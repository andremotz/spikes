package com.andremotz.spikes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;

import com.andremotz.spikes.datamodel.GoogleLocation;

public class GoogleProcessor {

	public GoogleProcessor() {

	}
	
	Logger log4j = LogManager.getLogger(GoogleProcessor.class
	        .getName());

	/*
	 * Return JSON as String from URL
	 */
	public String getJsonFromQuery(String jsonQuery) {
		String jsonResponse = "";

		jsonResponse = retryReadUrl(10, jsonQuery);

		return jsonResponse;
	}

	/*
	 * Recursive call of readUrl() for retry On fail wait for 1 second
	 */
	public String retryReadUrl(int limit, String jsonQuery) {
		String urlContent = "";

		try {
			urlContent = readUrl(jsonQuery);
		} catch (Exception e) {
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
		} catch (Exception e) {
			 log4j.error("failed! Eception..." + urlString);
			return null;
		} finally {
			 log4j.trace("success..." + urlString);
			if (reader != null)
				reader.close();
		}
	}

	/*
	 * Retuns raw JSON-String response from Google-Query
	 */
	public String getLocationOfString(String locationString) {

		String apiKey = "AIzaSyDDvGWYMFS2b26tv5gjzRmFmpxUTDJKZMM";
		String jsonQuery = "https://maps.googleapis.com/maps/api/geocode/json?address="
				+ locationString + "&key=" + apiKey;
		String jsonResponse = getJsonFromQuery(jsonQuery);

		return jsonResponse;
	}

	/*
	 * Returns GoogleLocation-Object of raw JSON-String response from Google-Query
	 */
	public GoogleLocation getGoogleLocationOfGoogleJsonResponse(
			String jsonResponse)  {

		GoogleLocation currentGoogleLocation = new GoogleLocation();
		try {
			String currentCity = getResultStringFromJsonResponseByKey(jsonResponse, "long_name");
			double currentLat = getResultDoubleFromJsonResponseByKey(jsonResponse, "lat");
			double currentLng = getResultDoubleFromJsonResponseByKey(jsonResponse, "lng");
			
			currentGoogleLocation.setLong_name(currentCity);
			currentGoogleLocation.setLat(currentLat);
			currentGoogleLocation.setLng(currentLng);
		} catch (Exception e) {
			currentGoogleLocation = null;
		}
		return currentGoogleLocation;
	}

	/*
	 * SUPER-DIRTY!!!
	 * returns Double from Json-attribute
	 */
	private double getResultDoubleFromJsonResponseByKey(String jsonResponse,
			String matchKey) {
		JSONParser parser = new JSONParser();
		KeyFinder finder = new KeyFinder();
		finder.setMatchKey(matchKey);
		
		ArrayList<Double> results = new ArrayList<Double>();
		try {
			while (!finder.isEnd()) {
				parser.parse(jsonResponse, finder, true);
				if (finder.isFound()) {
					finder.setFound(false);
					results.add((Double) finder.getValue());
				}
			}
		} catch (Exception pe) {
			results.add((double) 0);
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
		} catch (Exception pe) {
			results.add("");
		}
		
		return results.get(0);
		
	}
	
}
