package demo.calculator;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * To set log levels across root. Expects 1 argument. 
 *  "0" : ALL
 *  "1" : ERROR
 *  "3" : DEBUG
 *  Else INFO
 * @param args
 */
public class LoggerConfig {

	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();
	
	// Sets log levels
	public void setLogLevel(String level) {
		LOG.log(Level.TRACE, "Entering setLogLevel()");
		
		Level newLevel;
		
		switch(level.trim()) {
			case "0": newLevel = Level.ALL; break;
			case "1": newLevel = Level.ERROR; break;
			case "2": newLevel = Level.INFO; break;
			case "3": newLevel = Level.DEBUG; break;
			case "4": newLevel = Level.TRACE; break;
			default: newLevel = Level.INFO; break;
		}
		
		 Configurator.setRootLevel(newLevel);

		 /*LOG.log(Level.ALL,"This is for all messages");
		 LOG.log(Level.ERROR,"This is for error messages");
		 LOG.log(Level.DEBUG,"This is for debug messages");
		 LOG.log(Level.INFO,"This is for info messages");
		 LOG.log(Level.TRACE,"This is for trace messages");*/
		 
	}
}