package pl.lpirek.analyzer.criterium;

import java.util.ArrayList;
import java.util.Collections;

import pl.lpirek.analyzer.EvaluationFunction;
import pl.lpirek.analyzer.Strategy;


public class WardCriterium implements ICrtierium {

	private ArrayList<Strategy> strategies;
	private ArrayList<Strategy> optimalStrategies;
	
	public WardCriterium(ArrayList<Strategy> strategies) {
		this.strategies = strategies;
		
		calculate();
	}

	public void calculate() {
		//wyznacz minimum
		ArrayList<Double> minEvaluationFunctions = new ArrayList<Double>();
		
		for(Strategy strategy : strategies) {
			double localMin = Double.MAX_VALUE;
			
			for(EvaluationFunction function : strategy.getEvaluationFunctions()) {
				if (function.getValue() < localMin) {
					localMin = function.getValue();
				}
			}
				
			minEvaluationFunctions.add(localMin);
		}
		
		//wyznacz maksimum
		double max = Collections.max(minEvaluationFunctions);
		optimalStrategies = new ArrayList<Strategy>();
		
		for(int k = 0; k < strategies.size(); k++) {
			if (minEvaluationFunctions.get(k) == max) {
				optimalStrategies.add(strategies.get(k));
			}
		}
	}
	
	public void showResult() {
		System.out.println("----------- Kryterium WARDA -----------");
		for (Strategy strategy : optimalStrategies) {
			System.out.println(strategy.toString());
		}
	}
	
	
	
}
