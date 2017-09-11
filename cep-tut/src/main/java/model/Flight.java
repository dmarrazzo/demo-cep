package model;

import java.io.Serializable;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

@Role(Type.FACT)
public class Flight implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private double maxWeight;
	private double actualWeight;
	private boolean init = false;
	
	public Flight(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}

	public boolean getInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	@Override
	public String toString() {
		return String.format("Flight [id=%s, maxWeight=%s, actualWeight=%s, init=%s]", id, maxWeight, actualWeight,
		        init);
	}
}
