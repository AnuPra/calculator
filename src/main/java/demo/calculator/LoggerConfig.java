package demo.calculator;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

public class LoggerConfig {

	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();
	
	public static void setLogLevel(String level) {
		Level newLevel;
		
		switch(level.trim()) {
			case "0": newLevel = Level.ALL; break;
			case "1": newLevel = Level.ERROR; break;
			case "3": newLevel = Level.DEBUG; break;
			default: newLevel = Level.INFO; break;
		}
		
		 Configurator.setRootLevel(newLevel);

		 System.out.println("Log level changed to"+ newLevel);
	}
	
	public static void main(String[] args) {
		if (args.length <=0  || args[0].isEmpty()) {
			LOG.log(Level.ERROR, "Missing expression");
			return;
		}
		setLogLevel(args[0]);
	}
}