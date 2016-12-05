package pl.lpirek.analyzer;

import java.util.ArrayList;

public class PredictionModel {

	String name;
	
	ArrayList<Byte> assignments = new ArrayList<Byte>();
	
	public PredictionModel(String name) {
		this.name = name;
	}
	
	public void addAssignment(byte predict) {
		this.assignments.add(predict);
	}

	public ArrayList<Byte> getAssignments() {
		return assignments;
	}

	public String getName() {
		return name;
	}
	
	
}
