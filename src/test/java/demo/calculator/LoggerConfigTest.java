package demo.calculator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import junit.framework.TestCase;

public class LoggerConfigTest extends TestCase {

	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();
	
	public void testAllLevel() {
		Calculator.main(new String[] {"add(1,2)","0"});
		LOG.log(Level.ALL,"----Tested ALL level----");
	}
	
	public void testErrorLevel() {
		Calculator.main(new String[] {"add(1,2)","1"});
		LOG.log(Level.ERROR,"----Tested ERROR level----");
	}
	
	public void testInfoLevel() {
		Calculator.main(new String[] {"add(1,2)","2"});
		LOG.log(Level.INFO,"----Tested INFO level----");
	}
	
	public void testDebugLevel() {
		Calculator.main(new String[] {"add(1,2)","3"});
		LOG.log(Level.DEBUG,"----Tested DEBUG level----");
	}
	
	public void testTraceLevel() {
		Calculator.main(new String[] {"add(1,2)","4"});
		LOG.log(Level.TRACE,"----Tested TRACE level----");
	}
	
	public void testDefaultLevel() {
		Calculator.main(new String[] {"add(1,2)","4"});
		LOG.log(Level.INFO,"----Tested INFO level----");
	}
}
