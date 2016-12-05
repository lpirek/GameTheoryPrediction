package pl.lpirek.analyzer.criterium;

import java.util.ArrayList;
import java.util.Collections;

import pl.lpirek.analyzer.EvaluationFunction;
import pl.lpirek.analyzer.Strategy;

public class OptimisticCriterium implements ICrtierium {

	private ArrayList<Strategy> strategies;
	private ArrayList<Strategy> optimalStrategies;
	
	public OptimisticCriterium(ArrayList<Strategy> strategies) {
		this.strategies = strategies;
		
		calculate();
	}

	public void calculate() {
		//wyznacz maksimum
		ArrayList<Double> maxEvaluationFunctions = new ArrayList<Double>();
		
		for(Strategy strategy : strategies) {
			double localMax = Double.MIN_VALUE;
			
			for(EvaluationFunction function : strategy.getEvaluationFunctions()) {
				if (function.getValue() > localMax) {
					localMax = function.getValue();
				}
			}
				
			maxEvaluationFunctions.add(localMax);
		}
		
		//wyznacz maksimum
		double max = Collections.max(maxEvaluationFunctions);
		optimalStrategies = new ArrayList<Strategy>();
		
		for(int k = 0; k < strategies.size(); k++) {
			if (maxEvaluationFunctions.get(k) == max) {
				optimalStrategies.add(strategies.get(k));
			}
		}
	}
	
	public void showResult() {
		System.out.println("----------- Kryterium Optymistyczne -----------");
		for (Strategy strategy : optimalStrategies) {
			System.out.println(strategy.toString());
		}
	}
	

}
