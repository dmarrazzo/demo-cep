package model;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

@Role(Type.EVENT)
@Expires("5d")
public class BagScannedEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String id;
	private final Location location;
	private final double weight;
	private final String flightName;

	public BagScannedEvent(String id, Location location, double weight) {
		super();
		this.id = id;
		this.location = location;
		this.weight = weight;
		this.flightName = "";
	}

	public BagScannedEvent(String id, Location location, double weight, String flightName) {
		super();
		this.id = id;
		this.location = location;
		this.weight = weight;
		this.flightName = flightName;
	}

	@Override
	public String toString() {
		return String.format("BagScannedEvent [id=%s, location=%s, weight=%s]", id, location, weight);
	}

	public String getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public double getWeight() {
		return weight;
	}

	public String getFlightName() {
		return flightName;
	}

}
