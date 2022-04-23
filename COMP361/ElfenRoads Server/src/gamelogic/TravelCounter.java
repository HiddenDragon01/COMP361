package gamelogic;

import java.io.Serializable;

public class TravelCounter extends Counter {
	final private TransportationKind transportationKind;

	public TravelCounter(TransportationKind kind, boolean visible) {
		super(CounterKind.TRANSPORTATION, visible);
		transportationKind = kind;
		return;
	}
	
	public TransportationKind getTransportationKind() {
		return transportationKind;
	}
}
