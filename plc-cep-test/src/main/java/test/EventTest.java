package test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
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

import model.PLCEvent;

public class EventTest {
	private static final Logger log = LoggerFactory.getLogger(EventTest.class);

	public static void main(String[] args) throws InterruptedException {

		KieServices kieServices = KieServices.Factory.get();
		// Load KieContainer from resources on classpath (i.e. kmodule.xml and rules).
		KieContainer kieContainer = kieServices.getKieClasspathContainer();

		KieBaseConfiguration kbconf = kieServices.
				newKieBaseConfiguration();
				kbconf.setOption(EventProcessingOption.STREAM);
				KieBase kbase = kieContainer.newKieBase(kbconf);
				
		// Initializing KieSession.
		log.info("Creating KieSession.");
		KieSessionConfiguration conf = KieServices.Factory.get().newKieSessionConfiguration();
		conf.setOption(ClockTypeOption.get("pseudo"));

		KieSession kieSession = kbase.newKieSession(conf, null);
		
		SessionPseudoClock clock = kieSession.getSessionClock();
		
		try {
			// Event 1
			PLCEvent plcEvent = new PLCEvent("0001", true, 41.0, new Date());
			kieSession.insert(plcEvent);
			kieSession.fireAllRules();

			// Event 2
			clock.advanceTime(10, TimeUnit.SECONDS);
			plcEvent = new PLCEvent("0002", true, 20.0, new Date());
			kieSession.insert(plcEvent);
			kieSession.fireAllRules();
			
			// Event 3
			clock.advanceTime(20, TimeUnit.SECONDS);
			plcEvent = new PLCEvent("0003", true, 41.0, new Date());
			kieSession.insert(plcEvent);
			kieSession.fireAllRules();
		} finally {
			log.info("Disposing session.");
			kieSession.dispose();
		}

	}

}
