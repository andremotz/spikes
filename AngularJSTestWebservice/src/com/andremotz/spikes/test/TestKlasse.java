package com.andremotz.spikes.test;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.andremotz.spikes.Datastore;
import com.andremotz.spikes.GoogleProcessor;
import com.andremotz.spikes.JsonSerializer;
import com.andremotz.spikes.SoundcloudProcessor;
import com.andremotz.spikes.datamodel.AngularTrack;
import com.andremotz.spikes.datamodel.GoogleLocation;
import com.andremotz.spikes.datamodel.SoundcloudTrack;
import com.andremotz.spikes.datamodel.SoundcloudUser;

public class TestKlasse {

	@Ignore
	@Test
	public void testCreateJSON() {

		Datastore datastore = new Datastore();

		String jsonResponse = null;

		JsonSerializer jsonSerializer = new JsonSerializer();
		try {
			jsonResponse = jsonSerializer
					.getJsonStringFromArrayListTracks(datastore
							.getSampleTracks("House"));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonResponse);

	}

	@Ignore
	@Test
	public void testGetLatLngFromGoogle() {
		String locationString = "Stockholm";
		GoogleProcessor googleProcessor = new GoogleProcessor();

		String currentLocationJson = googleProcessor
				.getLocationOfString(locationString);
		GoogleLocation currentLocation = new GoogleLocation();

		currentLocation = googleProcessor
				.getGoogleLocationOfGoogleJsonResponse(currentLocationJson);

		Assert.assertEquals(59.1, currentLocation.getLat(), 1);
		Assert.assertEquals(18.1, currentLocation.getLng(), 1);

	}

	@Ignore
	@Test
	public void testGetLatestSoundcloudTracks() {
		SoundcloudProcessor soundcloudProcessor = new SoundcloudProcessor();

		String jsonQuery = soundcloudProcessor
				.getCurrentJsonQueryForTracks("House");
		String jsonResponseString = soundcloudProcessor
				.getJsonFromQuery(jsonQuery);
		ArrayList<String> jsonResponses = soundcloudProcessor
				.getJsonArrayFromString(jsonResponseString);

	}

	@Ignore
	@Test
	public void testCreateSoundcloudTrackFromJsonResponse() {
		SoundcloudProcessor soundcloudProcessor = new SoundcloudProcessor();

		String jsonQuery = soundcloudProcessor
				.getCurrentJsonQueryForTracks("House");
		String jsonResponseString = soundcloudProcessor
				.getJsonFromQuery(jsonQuery);
		ArrayList<String> jsonResponses = soundcloudProcessor
				.getJsonArrayFromString(jsonResponseString);
		
		SoundcloudTrack soundcloudTrack = soundcloudProcessor.getSoundcloudTrackFromJsonResponse(jsonResponses.get(0));
		

	}
	
	@Ignore
	@Test
	public void getSouncloudUserofId() {
		SoundcloudProcessor soundcloudProcessor = new SoundcloudProcessor();
		
		SoundcloudUser soundcloudUser = soundcloudProcessor.getSouncloudUserbyId(577498);
		Assert.assertEquals("Vienna", soundcloudUser.getCity());
		Assert.assertEquals("Marc Renton", soundcloudUser.getUsername());
		
	}
	
	@Test
	public void testNullValues() {
		String jsonResponse = "{     'genre':'Skippa Da Flippa', "
				+ "  'track_type':'',   'isrc':'',   'release_day':null,  "
				+ " 'release_year':null,   'state':'finished', "
				+ "  'favoritings_count':19995,   'download_count':38926, "
				+ "  'artwork_url':'https://i1.sndcdn.com/artworks-000078169748-vbb2hu-large.jpg', "
				+ "  'kind':'track',   'downloadable':true, "
				+ "  'id':147444154,   'last_modified':'2014/12/30 16:47:22 +0000', "
				+ "  'title':'Skippa Da Flippa ft. Migos & Rich The Kid  - Safe House', "
				+ "  'sharing':'public',   'label_name':'',   'video_url':null, "
				+ "  'download_url':'https://api.soundcloud.com/tracks/147444154/download',"
				+ "   'description':'*New*\r\nSkippa Da Flippa ft Migos & Rich the Kid \'Safe House\' produced by Zaytoven.',   'streamable':true,   'created_at':'2014/05/01 20:21:39 +0000',   'permalink_url':'http://soundcloud.com/digital-trapstars/skippa-da-flippa-ft-migos-rich-the-kid-safe-house',   'user_id':91875479,   'original_format':'mp3',   'original_content_size':9380340,   'license':'all-rights-reserved',   'embeddable_by':'all',   'commentable':true,   'attachments_uri':'https://api.soundcloud.com/tracks/147444154/attachments',   'comment_count':539,   'purchase_url':null,   'playback_count':1406803,   'stream_url':'https://api.soundcloud.com/tracks/147444154/stream',   'label_id':null,   'uri':'https://api.soundcloud.com/tracks/147444154',   'key_signature':'',   'bpm':null,   'duration':234758,   permalink':'skippa-da-flippa-ft-migos-rich-the-kid-safe-house',   'tag_list':'Migos \'Rich the Kid\' \'Safe House\' QC',   release_month':null,   'policy':'ALLOW',   'purchase_title':null,   'waveform_url':'https://w1.sndcdn.com/Yh9wGtl8rP3E_m.png',   'user':{        'id':91875479,      'avatar_url':'https://i1.sndcdn.com/avatars-000122905470-osnp66-large.jpg',      'last_modified':'2015/02/07 20:25:25 +0000',      'username':'Digital Trapstars',      'permalink':'digital-trapstars',    "
				+ "  'permalink_url':'http://soundcloud.com/digital-trapstars',"
				+ "     'uri':'https://api.soundcloud.com/users/91875479',   "
				+ "   'kind':'user'   },   'release':''}";
		SoundcloudProcessor soundcloudProcessor = new SoundcloudProcessor();
		soundcloudProcessor.getSoundcloudTrackFromJsonResponse(jsonResponse);
	}
	
//	@Ignore
	@Test
	public void testGetLatesTracks() {
		Datastore datastore = new Datastore();
		
		ArrayList<AngularTrack> latestTracks = datastore.getLatesTracks("House");
		
	}

}
