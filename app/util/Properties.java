package util;

import java.io.File;

import models.Filesystem;
import play.Play;

public class Properties {
	
	private static final boolean use_weasis = Play.configuration.getProperty("use_weasis") != null ?
			Play.configuration.getProperty("use_weasis").equalsIgnoreCase("true") : false;
	
	private static final String dcm4chee_protocol = Play.configuration.getProperty("dcm4chee.protocol") != null ?
			Play.configuration.getProperty("dcm4chee.protocol") : "http";

	private static final String dcm4chee_hostname = Play.configuration.getProperty("dcm4chee.hostname") != null ?
			Play.configuration.getProperty("dcm4chee.hostname") : "localhost";
			
	private static final String dcm4chee_port = Play.configuration.getProperty("dcm4chee.port") != null ?
			Play.configuration.getProperty("dcm4chee.port") : "8080";
			
	private static final String dcm4chee_url = String.format("%s://%s:%s", dcm4chee_protocol,
			dcm4chee_hostname, dcm4chee_port);
	
	public static boolean useWeasis(){
		return use_weasis;
		
	}
	
	public static int getInt(String key) {
		return Integer.parseInt(Play.configuration.getProperty(key));
	}

	public static String getString(String key) {
		return Play.configuration.getProperty(key);
	}

	public static String getDcm4cheeURL(){
		return dcm4chee_url;
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
