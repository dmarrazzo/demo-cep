package model;

@org.kie.api.definition.type.Role(org.kie.api.definition.type.Role.Type.EVENT)
@org.kie.api.definition.type.Expires("2m")
public class RiskyEvent extends PLCEvent {

	private static final long serialVersionUID = 1L;

	public RiskyEvent() {
	}

	public RiskyEvent(PLCEvent plcEvent) {
		super(plcEvent.getId(), plcEvent.getInput1(), plcEvent.getInput2(), plcEvent.getTimestamp());
	}

}
