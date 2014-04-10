package jobs;

import models.Person;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;
import util.Properties;
import play.exceptions.ConfigurationException;

@OnApplicationStart
public class Bootstrap extends Job {
	@Override
	public void doJob() {
		if (Person.count() == 0) {
			Fixtures.loadModels("initial-data.yml");
		}
		analyseConfigFile();
	}
	
	public void analyseConfigFile(){
		for (String var : Properties.mridb_optional_configuration_variables){
			if (Properties.getString(var) == null){
				Logger.info("Configuration: Using %s default value (see conf/default.conf)", var);
			}
		}
		
		for (String var : Properties.mridb_mandatory_configuration_variables){
			if (Properties.getString(var) == null){
				Logger.error("Configuration: %s not found CRITICAL (see conf/default.conf)", var);
				throw new ConfigurationException("CRITICAL (see conf/default.conf) mandatory parameter  not found: " + var);
			}
		}
		
	}
}
