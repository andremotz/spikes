package com.andremotz.spikes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.andremotz.spikes.datamodel.AngularTrack;

public class JsonSerializer {

	public JsonSerializer() {

	}

	/*
	 * Returns serialized Json-String from ArrayList<AngularTrack>
	 * that can be sent to the client
	 */
	public String getJsonStringFromArrayListTracks(
			ArrayList<AngularTrack> currentTracks) throws JsonGenerationException,
			JsonMappingException, IOException {

		String jsonString = null;
		OutputStream out = new ByteArrayOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, currentTracks);
		byte[] data = ((ByteArrayOutputStream) out).toByteArray();

		// Wäre für einzelne Json-Antworten richtig, nicht aber für Json-Arrays
		// jsonString = mapper.writeValueAsString(currentTracks);
		
		jsonString = new String(data);
		return jsonString;
	}

}
