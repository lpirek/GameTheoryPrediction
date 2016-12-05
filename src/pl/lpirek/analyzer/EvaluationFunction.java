package pl.lpirek.analyzer;

import java.util.ArrayList;

public class EvaluationFunction {

	private ArrayList<Byte> assignments;
	private ArrayList<Byte> natureStates;
		
	private int TP;
	private int FP;
	private int TN;
	private int FN;
	
	public EvaluationFunction(ArrayList<Byte> assignments, ArrayList<Byte> natureStates) {
		this.assignments = assignments;
		this.natureStates = natureStates;
		
		TP = 0;
		FP = 0;
		TN = 0;
		FN = 0;
		
		calculate();
	}

	private void calculate() {
		
		for (int i = 0; i < assignments.size(); i++) {
			
			byte x = assignments.get(i);
			byte y = natureStates.get(i);
			
			if (x == 1 && y == 1) TP++;
			if (x == 1 && y == 0) FP++;
			if (x == 0 && y == 0) TN++;
			if (x == 0 && y == 1) FN++;
			
		}
		
	}

	public ArrayList<Byte> getNatureStates() {
		return natureStates;
	}

	public void summary() {
		System.out.print("TP - " + TP);
		System.out.print(", FP - " + FP);
		System.out.print(", TN - " + TN);
		System.out.println(", FN - " + FN);
	}

	public double getValue() {
		return Main.A * TP + Main.B * FP + Main.C * TN + Main.D * FN;
	}
	
}
