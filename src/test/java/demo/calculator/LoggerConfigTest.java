package demo.calculator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import junit.framework.TestCase;

public class LoggerConfigTest extends TestCase {

	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();
	
	public void testAllLevel() {
		LoggerConfig.main(new String[] {"0"});
		LOG.log(Level.ALL,"----Tested ALL level----");
	}
	
	public void testErrorLevel() {
		LoggerConfig.main(new String[] {"1"});
		LOG.log(Level.ERROR,"----Tested ERROR level----");
	}
	
	public void testInfoLevel() {
		LoggerConfig.main(new String[] {"2"});
		LOG.log(Level.INFO,"----Tested INFO level----");
	}
	
	public void testDebugLevel() {
		LoggerConfig.main(new String[] {"3"});
		LOG.log(Level.DEBUG,"----Tested DEBUG level----");
	}
	
	public void testMissingArgument() {
		LoggerConfig.main(new String[] {});
	}
	
	public void testMissingArgument1() {
		LoggerConfig.main(new String[] {" "});
	}
}
