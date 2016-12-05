package pl.lpirek.analyzer.criterium;

import java.util.ArrayList;
import java.util.Collections;

import pl.lpirek.analyzer.EvaluationFunction;
import pl.lpirek.analyzer.Strategy;

public class SavageCriterium  implements ICrtierium {

	private ArrayList<Strategy> strategies;
	private ArrayList<Strategy> optimalStrategies;
	
	public SavageCriterium(ArrayList<Strategy> strategies) {
		this.strategies = strategies;
		
		calculate();
	}

	public void calculate() {
		//wyznacz maksimum w kolumnach
		ArrayList<Double> maxColumns = new ArrayList<Double>();
		
		int columnsCount = strategies.get(0).getPredictionModel().getAssignments().size();
		for(int k = 0; k < columnsCount; k++) {
			maxColumns.add(Double.MIN_VALUE);
		}
		
		for(Strategy strategy : strategies) {
			ArrayList<EvaluationFunction> functions = strategy.getEvaluationFunctions();
			
			for(int k = 0; k < columnsCount; k++) {
				if (functions.get(k).getValue() > maxColumns.get(k)) {
					maxColumns.set(k, functions.get(k).getValue());
				}
			}
		}
		
		//wyznacz maksimum dla strategii
		ArrayList<Double> maxLossFunctions = new ArrayList<Double>();
		
		for(Strategy strategy : strategies) {
			double localMax = Double.MIN_VALUE;
			ArrayList<EvaluationFunction> functions = strategy.getEvaluationFunctions();
			
			for(int k = 0; k < columnsCount; k++) {
				if ((maxColumns.get(k) - functions.get(k).getValue()) > localMax) {
					localMax = (maxColumns.get(k) - functions.get(k).getValue());
				}
			}
				
			maxLossFunctions.add(localMax);
		}
		
		//wyznacz minumum
		double min = Collections.min(maxLossFunctions);
		optimalStrategies = new ArrayList<Strategy>();
		
		for(int k = 0; k < strategies.size(); k++) {
			if (maxLossFunctions.get(k) == min) {
				optimalStrategies.add(strategies.get(k));
			}
		}
	}
	
	public void showResult() {
		System.out.println("----------- Kryterium SAVAGE -----------");
		for (Strategy strategy : optimalStrategies) {
			System.out.println(strategy.toString());
		}
	}

}
