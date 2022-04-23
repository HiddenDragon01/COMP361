package gamelogic;

import java.io.Serializable;

public class TravelCard extends Card {
	final private TransportationKind transportationKind;
	
	public TravelCard(TransportationKind t) {
		super(CardKind.TRAVEL);
		transportationKind = t;
	}
	
	public TransportationKind getTransportationKind() {
		return transportationKind;
	}
}
