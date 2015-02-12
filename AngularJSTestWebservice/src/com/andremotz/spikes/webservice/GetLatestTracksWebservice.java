package com.andremotz.spikes.webservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.andremotz.spikes.Datastore;
import com.andremotz.spikes.JsonSerializer;
import com.andremotz.spikes.ProcessorUtil;
import com.andremotz.spikes.datamodel.AngularTrack;

/**
 * Servlet implementation class GetLatestTracks Webservice that will be called
 * by Frontend
 */
@WebServlet("/GetLatestTracks")
public class GetLatestTracksWebservice extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger log4j = LogManager.getLogger(GetLatestTracksWebservice.class
			.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetLatestTracksWebservice() {
		super();
	}

	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();

		// Super-Dirty!
		String queryUnencoded = request.getQueryString();
		String query = queryUnencoded.substring(6);

		log4j.trace("query: " + query);

		ArrayList<AngularTrack> currentTracks;
		String jsonResponse = null;
		Datastore datastore = new Datastore();

		if (ProcessorUtil.isDebugMode()) {
			currentTracks = datastore.getSampleTracks(query);
		} else {
			currentTracks = datastore.getLatesTracks(query);
		}

		JsonSerializer jsonSerializer = new JsonSerializer();
		try {
			jsonResponse = jsonSerializer
					.getJsonStringFromArrayListTracks(currentTracks);
		} catch (JsonGenerationException e) {
			log4j.debug(e);
		} catch (JsonMappingException e) {
			log4j.debug(e);
		} catch (IOException e) {
			log4j.debug(e);
		}

		writer.println(jsonResponse);
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
