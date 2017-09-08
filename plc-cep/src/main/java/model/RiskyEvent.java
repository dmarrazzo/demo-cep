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

	@Override
	public String toString() {
		return String.format("RiskyEvent [getId()=%s, getInput1()=%s, getInput2()=%s]", getId(), getInput1(),
		        getInput2());
	}

}
