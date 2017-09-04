package utils;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.services.api.ProcessService;
import org.kie.server.services.api.KieServerExtension;
import org.kie.server.services.impl.KieServerImpl;
import org.kie.server.services.impl.KieServerLocator;

public class ProcFunctions {
	private static String deploymentId = "example:plc-proc:1.1-SNAPSHOT";
	private static String processId = "plc-cep.Warning";

	public final static void startProcess(String eventId) {

		KieServerImpl kieServer = KieServerLocator.getInstance();
		KieServerExtension ext = ((KieServerImpl) kieServer).getServerRegistry()
		                                                    .getServerExtension("jBPM");
		ProcessService processService = (ProcessService) ext.getServices()
		                                                    .stream()
		                                                    .filter(service -> service instanceof ProcessService)
		                                                    .findFirst()
		                                                    .get();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("eventId", eventId);
		processService.startProcess(deploymentId, processId, paramMap);
	}
}
