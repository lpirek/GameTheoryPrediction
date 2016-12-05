package pl.lpirek.analyzer.criterium;

import java.util.ArrayList;
import java.util.Collections;

import pl.lpirek.analyzer.EvaluationFunction;
import pl.lpirek.analyzer.Strategy;

public class HurwitzCriterium implements ICrtierium {

	private ArrayList<Strategy> strategies;
	private ArrayList<Strategy> optimalStrategies;
	private double alfa;
	
	public HurwitzCriterium(ArrayList<Strategy> strategies, double alfa) {
		this.strategies = strategies;
		this.alfa = alfa;
		
		calculate();
	}

	public void calculate() {
		//wyznacz przeciêtna wygran¹ dla ka¿dej strategii
		ArrayList<Double> avgEvaluationFunctions = new ArrayList<Double>();
		
		for(Strategy strategy : strategies) {
			
			ArrayList<Double> functionsValues = new ArrayList<Double>();
			
			for(EvaluationFunction function : strategy.getEvaluationFunctions()) {
				functionsValues.add(function.getValue());
			}
			
			double min = Collections.min(functionsValues);
			double max = Collections.max(functionsValues);
			
			double avg = alfa * min + (1 - alfa) * max;
			
			avgEvaluationFunctions.add(avg);
		}
		
		//wyznacz maksimum
		double max = Collections.max(avgEvaluationFunctions);
		optimalStrategies = new ArrayList<Strategy>();
		
		for(int k = 0; k < strategies.size(); k++) {
			if (avgEvaluationFunctions.get(k) == max) {
				optimalStrategies.add(strategies.get(k));
			}
		} 
	}
	
	public void showResult() {
		System.out.println("----------- Kryterium HURWITZA -----------");
		for (Strategy strategy : optimalStrategies) {
			System.out.println(strategy.toString());
		}
		System.out.println();
	}
	

}
