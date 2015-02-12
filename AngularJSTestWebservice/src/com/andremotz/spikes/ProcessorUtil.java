package com.andremotz.spikes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/*
 * Processor Utility-Class for useful stuff
 */
public class ProcessorUtil {
	static String soundcloudapikey;
	static boolean debugMode;

	public static boolean isDebugMode() {
		return debugMode;
	}


	public static void setDebugMode(boolean debugMode) {
		ProcessorUtil.debugMode = debugMode;
	}

	static {
		log4j = LogManager.getLogger(ProcessorUtil.class
		        .getName());
		getProperties();
	}
	
	static Logger log4j;
	
	/*
	 * Get properties from Property-file
	 */
	private static void getProperties() {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			String filename = "config.properties";
			input = ProcessorUtil.class.getClassLoader()
					.getResourceAsStream(filename);
			if (input == null) {
				log4j.debug("Sorry, unable to find " + filename);
			}

			// load a properties file from class path, inside static method
			prop.load(input);

			// get the property values
			soundcloudapikey = prop.getProperty("soundcloudapikey");
			if (prop.getProperty("debugMode").equals("false")) {
				log4j.trace("debugMode: false");
				debugMode = false;
			} else {
				log4j.trace("debugMode: true");
				debugMode = true;
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @return the soundcloudapikey
	 */
	public static String getSoundcloudapikey() {
		return soundcloudapikey;
	}

	/**
	 * @param soundcloudapikey the soundcloudapikey to set
	 */
	public void setSoundcloudapikey(String _soundcloudapikey) {
		soundcloudapikey = _soundcloudapikey;
	}
	
}
