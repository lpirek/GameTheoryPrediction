package pl.lpirek.analyzer.criterium;

import java.util.ArrayList;
import java.util.Collections;

import pl.lpirek.analyzer.EvaluationFunction;
import pl.lpirek.analyzer.Strategy;

public class LaplaceCriterium implements ICrtierium {

	private ArrayList<Strategy> strategies;
	private ArrayList<Strategy> optimalStrategies;
	
	public LaplaceCriterium(ArrayList<Strategy> strategies) {
		this.strategies = strategies;
		
		calculate();
	}

	public void calculate() {
		//wyznacz sumy
		ArrayList<Double> sumEvaluationFunctions = new ArrayList<Double>();
		
		for(Strategy strategy : strategies) {
			double sum = 0;
			
			for(EvaluationFunction function : strategy.getEvaluationFunctions()) {
				sum += function.getValue();
			}
				
			sumEvaluationFunctions.add(sum);
		}
		
		//wyznacz maksimum
		double max = Collections.max(sumEvaluationFunctions);
		optimalStrategies = new ArrayList<Strategy>();
		
		for(int k = 0; k < strategies.size(); k++) {
			if (sumEvaluationFunctions.get(k) == max) {
				optimalStrategies.add(strategies.get(k));
			}
		}
	}
	
	public void showResult() {
		System.out.println("----------- Kryterium Laplace -----------");
		for (Strategy strategy : optimalStrategies) {
			System.out.println(strategy.toString());
		}
	}
	

}
