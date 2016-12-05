package pl.lpirek.analyzer;

import java.util.ArrayList;

public class Strategy {

	private int number;
	private PredictionModel predictionModel;
	private ArrayList<EvaluationFunction> evaluationFunctions = new ArrayList<EvaluationFunction>();
	
	public Strategy(int number, PredictionModel predictionModel) {
		this.number = number;
		this.predictionModel = predictionModel;
	}
	
	public void addEvaluationFunction(ArrayList<Byte> assignments, ArrayList<Byte> natureStates) {
		evaluationFunctions.add(new EvaluationFunction(assignments, natureStates));
	} 

	public void summary() {
		System.out.println("Strategia - " + predictionModel.getName());
		System.out.println("Dopasowanie: " + predictionModel.getAssignments());
		
		/*
		for(EvaluationFunction evaluationFunction : evaluationFunctions) {
			System.out.println("Stan: " + evaluationFunction.getNatureStates());
			evaluationFunction.summary();
		}
		*/
	}
	
	public ArrayList<EvaluationFunction> getEvaluationFunctions() {
		return evaluationFunctions;
	}
	
	public PredictionModel getPredictionModel() {
		return predictionModel;
	}
	
	@Override
	public String toString() {
		return number + " - " + predictionModel.getName();
		
	}
}
