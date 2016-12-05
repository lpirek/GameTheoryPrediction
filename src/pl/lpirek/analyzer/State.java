package pl.lpirek.analyzer;

import java.util.ArrayList;

public class State {

	ArrayList<Byte> substates = new ArrayList<Byte>();
	
	public State() {
		
	}
	
	public State(ArrayList<Byte> substates) {
		this.substates = substates;
	}
	
	public ArrayList<Byte> getSubstates() {
		return substates;
	}
	
}
