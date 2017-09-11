package test;

import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.BagScannedEvent;
import model.Flight;
import model.Location;

public class Test {
	private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

	private static KieSession kieSession;

	@BeforeClass
	public static void beforeClass() {
		KieServices kieServices = KieServices.Factory.get();
		// Load KieContainer from resources on classpath (i.e. kmodule.xml and rules).
		KieContainer kContainer = kieServices.getKieClasspathContainer();

		KieBaseConfiguration kbconf = kieServices.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kContainer.newKieBase(kbconf);

		// Initializing KieSession.
		LOGGER.info("Creating KieSession.");
		KieSessionConfiguration conf = KieServices.Factory.get()
		                                                  .newKieSessionConfiguration();
		conf.setOption(ClockTypeOption.get("pseudo"));

		kieSession = kbase.newKieSession(conf, null);
	}

	@AfterClass
	public static void afterClass() {
		kieSession.dispose();
	}
	
	//@org.junit.Test
	public void test1() {
		LOGGER.info("Test 1");
		BagScannedEvent bagEvent;
		
		bagEvent = new BagScannedEvent("01", Location.CHECK_IN, 10.0);
		kieSession.insert(bagEvent);
		kieSession.fireAllRules();		
	}

	@org.junit.Test
	public void test2() {
		LOGGER.info("Test 2");
		SessionPseudoClock clock = kieSession.getSessionClock();
		BagScannedEvent bagEvent;
		
		bagEvent = new BagScannedEvent("01", Location.CHECK_IN, 10.0);
		kieSession.insert(bagEvent);
		
		clock.advanceTime(10, TimeUnit.MINUTES);
		
		bagEvent = new BagScannedEvent("01", Location.SORTING, 10.0);
		kieSession.insert(bagEvent);
		kieSession.fireAllRules();
	}

	/**
	 * Test rules: 
	 * 
	 * - time window
	 * - occurrences window
	 */
	//@org.junit.Test
	public void test3() {
		LOGGER.info("Test 3");
		SessionPseudoClock clock = kieSession.getSessionClock();
		BagScannedEvent bagEvent;
		
		bagEvent = new BagScannedEvent("01", Location.CHECK_IN, 10.0);
		kieSession.insert(bagEvent);

		clock.advanceTime(3, TimeUnit.MINUTES);
		
		bagEvent = new BagScannedEvent("02", Location.CHECK_IN, 12.0);
		kieSession.insert(bagEvent);

		clock.advanceTime(8, TimeUnit.MINUTES);
		
		bagEvent = new BagScannedEvent("03", Location.CHECK_IN, 12.0);
		kieSession.insert(bagEvent);

		kieSession.fireAllRules();
		
	}

	/**
	 * decision table
	 */
	//@org.junit.Test
	public void test4() {
		LOGGER.info("Test 4");
		SessionPseudoClock clock = kieSession.getSessionClock();
		
		Flight flight = new Flight("AZ111");
		kieSession.insert(flight);
		
		BagScannedEvent bagEvent;
		
		bagEvent = new BagScannedEvent("01", Location.CHECK_IN, 10.0, "AZ111");
		kieSession.insert(bagEvent);

		clock.advanceTime(3, TimeUnit.MINUTES);
		
		bagEvent = new BagScannedEvent("02", Location.CHECK_IN, 12.0, "AZ111");
		kieSession.insert(bagEvent);

		clock.advanceTime(8, TimeUnit.MINUTES);
		
		bagEvent = new BagScannedEvent("03", Location.CHECK_IN, 80.0, "AZ111");
		kieSession.insert(bagEvent);

		kieSession.fireAllRules();
		
	}

}
