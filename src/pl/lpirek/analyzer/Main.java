package pl.lpirek.analyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import pl.lpirek.analyzer.criterium.HurwitzCriterium;
import pl.lpirek.analyzer.criterium.LaplaceCriterium;
import pl.lpirek.analyzer.criterium.OptimisticCriterium;
import pl.lpirek.analyzer.criterium.SavageCriterium;
import pl.lpirek.analyzer.criterium.WardCriterium;

public class Main {

	public static double alfa = 0.4;
	public static double A = 1; //TP
	public static double B = -0.8; //FP
	public static double C = 0.5; //TN
	public static double D = -0.2; //FN
	
	
	public static void main(String[] args) throws Exception {
		
		if (args.length < 2) throw new Exception("Nie zdefiniowano parametrów pocz¹tkowych");
		
		String csvFilePath1 = args[0];
		String csvFilePath2 = args[1];
		
		try {
			
			//Wykonanie obliczen
			Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("rscript C:\\mgr\\prediction.R " + csvFilePath1 + " " + csvFilePath2);
            
            System.out.println("Waiting for rscript file ...");
            p.waitFor();
            System.out.println("Rscript file done.");
            
            ArrayList<PredictionModel> predictionModels = new ArrayList<PredictionModel>();
            
            String line = null; 
            String[] assignments = null;
            PredictionModel predictionModel = null;
            
            //Wczytanie danych
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            while ((line = in.readLine()) != null) {
            	if (!line.startsWith("[1]")) {
           
            		//Definicja modelu
            		if (line.startsWith("MODEL")) {
            			
            			predictionModel = new PredictionModel(line);
            			
            		} else if (line.startsWith("END")) {
            			
            			if (predictionModel != null) {
            				predictionModels.add(predictionModel);
            			}
            
            			predictionModel = null;
            			
            		} else {
            			
            			assignments = line.split(" ");
            			for (String assignment : assignments) {
            				predictionModel.addAssignment(Byte.parseByte(assignment));
            			}
            			
            		}
            	
            	}
            }
            
            in.close();
            
            System.out.println("Define nature states...");
            
            //Zdefiniowanie stanów natury
            ArrayList<State> states = new ArrayList<State>();
            
            int substateCount = assignments.length;
            long stateCount = (long) Math.pow(2, substateCount);
            
            for (long i = 0L; i < stateCount; i++) {
            	String number = String.format("%0" + substateCount + "d", new BigInteger(Long.toBinaryString(i)));
            	
            	ArrayList<Byte> substates = new ArrayList<Byte>();
            	for (int k = 0; k < substateCount; k++) {
            		substates.add(number.charAt(k) == '1' ? (byte)1 : (byte)0 );
            	}
            	
            	states.add(new State(substates));
            }
            
            System.out.println("Calculate evaluation functions.");
            
            //Zdefiniowanie strategii i wyliczenie funkcji oceny
            ArrayList<Strategy> strategies = new ArrayList<Strategy>();
            Strategy strategy = null;
            
            int i = 1;
            
            for(PredictionModel model : predictionModels) {
            	
            	strategy = new Strategy(i++, model);
            	
            	for(State state : states) {
            		strategy.addEvaluationFunction(model.getAssignments(), state.getSubstates());
            	}
            	
            	
            	strategy.summary();
            	strategies.add(strategy);
            }
            
            System.out.println("Show results:");
            
            //Wyznaczenie wyników na podstawie kryteriów
            new WardCriterium(strategies).showResult();
            new OptimisticCriterium(strategies).showResult();
            new SavageCriterium(strategies).showResult();
            new LaplaceCriterium(strategies).showResult();
            new HurwitzCriterium(strategies, alfa).showResult();
          
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	
}
