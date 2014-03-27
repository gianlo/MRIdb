package util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import models.Filesystem;
import play.Play;

public class Properties {
	//this eases the access to mridb configurations from conf/application.conf
	
	// This are optional configuration variables
	public static final List<String> mridb_optional_configuration_variables = Arrays.asList("use_weasis", "dcm4chee.protocol",
			"dcm4chee.address", "dcm4chee.port");
	//this are mandatory configuration variables
	public static final List<String> mridb_mandatory_configuration_variables = Arrays.asList("dcm4chee.location");

	
	//if the hardcoded default value is here changes, please update the conf/default.conf text file
	public static boolean useWeasis(){
		return Play.configuration.getProperty("use_weasis") != null ?
				Play.configuration.getProperty("use_weasis").equalsIgnoreCase("true") : false;
	}
	
	//if the hardcoded default value is here changes, please update the conf/default.conf text file
	private static final String dcm4chee_protocol = Play.configuration.getProperty("dcm4chee.protocol") != null ?
			Play.configuration.getProperty("dcm4chee.protocol") : "http";

	//if the hardcoded default value is here changes, please update the conf/default.conf text file
	private static final String dcm4chee_address = Play.configuration.getProperty("dcm4chee.address") != null ?
			Play.configuration.getProperty("dcm4chee.address") : "localhost";
			
	//if the hardcoded default value is here changes, please update the conf/default.conf text file
	private static final String dcm4chee_port = Play.configuration.getProperty("dcm4chee.port") != null ?
			Play.configuration.getProperty("dcm4chee.port") : "8080";
			
	public static int getInt(String key) {
		return Integer.parseInt(Play.configuration.getProperty(key));
	}

	public static String getString(String key) {
		return Play.configuration.getProperty(key);
	}

	public static String getDcm4cheeURL(){
		return String.format("%s://%s:%s", dcm4chee_protocol,
				dcm4chee_address, dcm4chee_port);
	}
	
	public static File getArchive() {
		File archive = new File(Filesystem.<Filesystem>findAll().get(0).dirpath);
		return archive.isAbsolute() ? archive : new File(getString("dcm4chee.location"), String.format("server/default/%s", archive.getPath()));
	}

	public static int pageSize() {
		return Integer.parseInt(Play.configuration.getProperty("page.size", String.valueOf(20)));
	}

	public static File getDownloads() {
		File downloads = new File(Play.tmpDir, "downloads");
		downloads.mkdir();
		return downloads;
	}

	public static File getCollations() {
		File downloads = new File(Play.tmpDir, "collations");
		downloads.mkdir();
		return downloads;
	}

}
