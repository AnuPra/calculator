package demo.calculator;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

public class LoggerConfig {

	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();
	
	public void setLogLevel(String level) {
		Level newLevel;
		
		switch(level.trim()) {
			case "0": newLevel = Level.ALL; break;
			case "1": newLevel = Level.ERROR; break;
			case "3": newLevel = Level.DEBUG; break;
			default: newLevel = Level.INFO; break;
		}
		
		 Configurator.setRootLevel(newLevel);

		 LOG.log(Level.ALL,"This is for all messages");
		 LOG.log(Level.ERROR,"This is for error messages");
		 LOG.log(Level.DEBUG,"This is for debug messages");
		 LOG.log(Level.INFO,"This is for info messages");
		 
	}
	
	public static void main(String[] args) {
		if (args.length <=0  || args[0].trim().isEmpty()) {
			LOG.log(Level.ERROR, "Missing expression");
			return;
		}
		
		LoggerConfig obj = new LoggerConfig();
		obj.setLogLevel(args[0]);
	}
}