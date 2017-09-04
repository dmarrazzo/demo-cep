package client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.PLCEvent;


public class Main {

	final static Logger log =  LoggerFactory.getLogger(Main.class);
	
	private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
	private static final String user = "donato";
	private static final String password = "donato";
	private static final String CONTAINER = "example:plc-cep:1.0-SNAPSHOT";
	private static final String KSESSION = "ksessionCep";

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ksFireAllRule();
		//ksStartRuleFlow();
		long end = System.currentTimeMillis();
		System.out.println("time elapsed: "+ (end-start));
	}

	@SuppressWarnings("rawtypes")
	public static void ksFireAllRule() {
		try {
			
			KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(URL, user, password);
			// Marshalling
			Set<Class<?>> extraClasses = new HashSet<Class<?>>();	
			extraClasses.add(PLCEvent.class);

			config.addExtraClasses(extraClasses);
			config.setMarshallingFormat(MarshallingFormat.JSON);
			Map<String, String> headers = null;
			config.setHeaders(headers);
			
			KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
			RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

			
			KieCommands cmdFactory = KieServices.Factory.get().getCommands();
			List<Command> commands = new ArrayList<Command>();

			// -------------
			PLCEvent plcEvent = new PLCEvent("0001", true, 11.0, new Date());
			
			// INSERT WM
			commands.add(cmdFactory.newInsert(plcEvent, "plcEvent"));
						
			// GET ALL THE VM
			commands.add(cmdFactory.newGetObjects("objs"));

			// FIRE RULES
			commands.add(cmdFactory.newFireAllRules("fireAllRules"));
		    
			BatchExecutionCommand command = cmdFactory.newBatchExecution(commands, KSESSION);
			
			ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER, command);

			TimeUnit.SECONDS.sleep(1);
			// -------------
			plcEvent = new PLCEvent("0002", true, 11.0, new Date());

			// INSERT WM
			commands.add(cmdFactory.newInsert(plcEvent, "plcEvent"));
						
			// GET ALL THE VM
			commands.add(cmdFactory.newGetObjects("objs"));

			// FIRE RULES
			commands.add(cmdFactory.newFireAllRules("fireAllRules"));
		    
			command = cmdFactory.newBatchExecution(commands, KSESSION);
			
			response = ruleClient.executeCommandsWithResults(CONTAINER, command);
			TimeUnit.SECONDS.sleep(1);
			// -------------
			plcEvent = new PLCEvent("0003", true, 15.0, new Date());

			// INSERT WM
			commands.add(cmdFactory.newInsert(plcEvent, "plcEvent"));
						
			// GET ALL THE VM
			commands.add(cmdFactory.newGetObjects("objs"));

			// FIRE RULES
			commands.add(cmdFactory.newFireAllRules("fireAllRules"));
		    
			command = cmdFactory.newBatchExecution(commands, KSESSION);
			
			response = ruleClient.executeCommandsWithResults(CONTAINER, command);

			//RESULTS
			ExecutionResults results = response.getResult();

			if (results==null)
				throw new Exception(response.toString());
			
			Collection<String> identifiers = results.getIdentifiers();
			for (String identifier : identifiers) {
				Object object = results.getValue(identifier);
				log.info("result {}", results);
				log.info("WM object: {} {}", identifier, object);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
