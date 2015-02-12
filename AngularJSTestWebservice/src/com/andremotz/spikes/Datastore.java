package com.andremotz.spikes;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andremotz.spikes.datamodel.AngularTrack;
import com.andremotz.spikes.datamodel.GoogleLocation;
import com.andremotz.spikes.datamodel.SoundcloudTrack;
import com.andremotz.spikes.datamodel.SoundcloudUser;
import com.andremotz.spikes.webservice.GetLatestTracksWebservice;

public class Datastore {
	SoundcloudProcessor soundcloudProcessor;
	GoogleProcessor googleProcessor;

	public Datastore() {
		// Initialize
		soundcloudProcessor = new SoundcloudProcessor();
		googleProcessor = new GoogleProcessor();

	}
	
	Logger log4j = LogManager.getLogger(Datastore.class
	        .getName());

	/*
	 * Returns ArrayList-Objects from Soundcloud that 
	 * contain properties to display in FrontEnd
	 */
	public ArrayList<AngularTrack> getLatesTracks(String frontEndQuery) {

		// Fetch tracks from Soundcloud
		String jsonQuery = soundcloudProcessor
				.getCurrentJsonQueryForTracks(frontEndQuery);

		String jsonResponseString = soundcloudProcessor
				.getJsonFromQuery(jsonQuery);
		ArrayList<String> jsonResponses = soundcloudProcessor
				.getJsonArrayFromString(jsonResponseString);

		ArrayList<SoundcloudTrack> soundcloudTracks = new ArrayList<SoundcloudTrack>();

		for (String currentJsonResponseString : jsonResponses) {
			SoundcloudTrack currentSoundCloudTrack = new SoundcloudTrack();
			currentSoundCloudTrack = soundcloudProcessor
					.getSoundcloudTrackFromJsonResponse(currentJsonResponseString);

			if(currentSoundCloudTrack != null) {
				soundcloudTracks.add(currentSoundCloudTrack);
			}
		}

		// Prepare Tracks for FrontEnd
		ArrayList<AngularTrack> latestAngularTracks = new ArrayList<AngularTrack>();
		for (SoundcloudTrack currentSoundCloudTrack : soundcloudTracks) {
			try {
				AngularTrack currentAngularTrack = getAngularTrackOfSoundcloudTrack(currentSoundCloudTrack);

				if(currentAngularTrack.getLat() != 0 ) {
					latestAngularTracks.add(currentAngularTrack);
				}
			} catch (Exception e) {
				log4j.debug(e.getMessage());
			}
		}

		return latestAngularTracks;
	}

	/*
	 * returns AngularTrack-Object of SoundcloudTrack-Object 
	 * + additional Stuff from SoundcloudUser
	 */
	private AngularTrack getAngularTrackOfSoundcloudTrack(
			SoundcloudTrack currentSoundcloudTrack) {
		AngularTrack currentAngularTrack = new AngularTrack();

		try {
			// Merge User's City into AngularTrack
			long currentSoundcloudTrackUserId = currentSoundcloudTrack.getUser_id();
			
			SoundcloudUser currentSoundcloudUser = soundcloudProcessor.getSouncloudUserbyId(currentSoundcloudTrackUserId);
			
			currentAngularTrack.setCity(currentSoundcloudUser.getCity());

			// TODO Country
			currentAngularTrack.setCountry(null);

			currentAngularTrack.setCreated_at(currentSoundcloudTrack
					.getCreated_at());
			currentAngularTrack.setDuration(currentSoundcloudTrack.getDuration());

			// merge Current Track's User's City' Lat/LNG into AngularTrack
			String currentLocationJson = googleProcessor
					.getLocationOfString(currentSoundcloudUser.getCity());
			
			GoogleLocation currentLocation = new GoogleLocation();

			currentLocation = googleProcessor
					.getGoogleLocationOfGoogleJsonResponse(currentLocationJson);
			
			currentAngularTrack.setLat(currentLocation.getLat());
			currentAngularTrack.setLng(currentLocation.getLng());
			
			currentAngularTrack.setPermalink_url(currentSoundcloudTrack.getPermalink_url());
			currentAngularTrack.setTitle(currentSoundcloudTrack.getTitle());
			currentAngularTrack.setUsername(currentSoundcloudUser.getUsername());
			
			currentAngularTrack.setGenre(currentSoundcloudTrack.getGenre());
			currentAngularTrack.setDescription(currentSoundcloudTrack.getDescription());
			currentAngularTrack.setPlayback_count(currentSoundcloudTrack.getPlayback_count());
			
		} catch (Exception e) {
			currentAngularTrack = null;
		}

		return currentAngularTrack;
	}
	
	/*
	 * Returns sample-ArrayList-Objects that 
	 * contain properties to display in FrontEnd
	 */
	public ArrayList<AngularTrack> getSampleTracks(String frontEndQuery) {
		ArrayList<AngularTrack> sampleTracks = new ArrayList<AngularTrack>();
		
		if(frontEndQuery.equals("House")) {

			AngularTrack track1 = new AngularTrack();
			track1.setCountry("Austria");
			track1.setCity("Wien");
			track1.setLat(48.20);
			track1.setLng(16.37);
			track1.setUsername("Marc Renton");
			track1.setTitle("Titel1");
			track1.setCreated_at("2014/01/01 00:01:56 +0000");
			track1.setDuration(35553);
			track1.setPermalink_url("http://soundcloud.com/davidking-4/revenge");
			track1.setGenre("Genre1");
			track1.setDescription("Description Description Description Description Description Description Description Description Description Description ");
			track1.setPlayback_count(1000);
			sampleTracks.add(track1);
	
			AngularTrack track2 = new AngularTrack();
			track2.setCountry("Germany");
			track2.setCity("Endingen");
			track2.setLat(48.14);
			track2.setLng(7.70);
			track2.setUsername("User2");
			track2.setTitle("Titel2");
			track2.setCreated_at("2014/01/01 00:01:56 +0000");
			track2.setDuration(3553);
			track2.setPermalink_url("http://soundcloud.com/davidking-4/link2");
			track2.setGenre("Genre1");
			track2.setDescription("Description Description iption Description Description ");
			track2.setPlayback_count(500);
			sampleTracks.add(track2);
		} else {

			AngularTrack track3 = new AngularTrack();
			track3.setCountry("Schweiz");
			track3.setCity("Luzern");
			track3.setLat(47.05);
			track3.setLng(8.30);
			track3.setUsername("User3");
			track3.setTitle("Titel3");
			track3.setCreated_at("2014/01/01 00:01:56 +0000");
			track3.setDuration(355);
			track3.setPermalink_url("http://soundcloud.com/davidking-4/link3");
			track3.setGenre("Genre1");
			track3.setDescription("Description3 3 iption Description Description ");
			track3.setPlayback_count(99);
			sampleTracks.add(track3);
		}

		return sampleTracks;
	}

}
