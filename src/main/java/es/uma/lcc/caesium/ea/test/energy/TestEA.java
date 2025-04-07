package es.uma.lcc.caesium.ea.test.energy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import es.uma.lcc.caesium.ea.base.EvolutionaryAlgorithm;
import es.uma.lcc.caesium.ea.config.EAConfiguration;
import es.uma.lcc.caesium.ea.fitness.BinaryEncodedContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.problem.continuous.Ackley;
import es.uma.lcc.caesium.ea.problem.continuous.Griewank;
import es.uma.lcc.caesium.ea.problem.continuous.Rastrigin;
import es.uma.lcc.caesium.ea.problem.continuous.Rosenbrock;
import es.uma.lcc.caesium.ea.problem.continuous.Sphere;
import es.uma.lcc.caesium.ea.statistics.EntropyDiversity;
import es.uma.lcc.caesium.ea.statistics.VarianceDiversity;
import es.uma.lcc.caesium.ea.util.JsonUtil;

/**
 * Class for testing the evolutionary algorithm
 * @author ccottap
 * @version 1.0
 */
public class TestEA {

	/**
	 * Main method
	 * @param args command-line arguments
	 * @throws JsonException if the configuration file is not correctly formatted
	 * @throws IOException if file cannot be read or closed
	 */
	public static void main(String[] args) throws JsonException, IOException {
		String expName = (args.length < 1) ? "experiment.json" : args[0]; 
		FileReader expReader = new FileReader(expName);
		JsonObject experiment = (JsonObject) Jsoner.deserialize(expReader);
		expReader.close();
		
		String problem = (String)experiment.get("problem");
		int numvars    = JsonUtil.getInt(experiment, "numvars");
		double range   = JsonUtil.getDouble(experiment, "range");
		int numbits    = (experiment.containsKey("numbits")) ? JsonUtil.getInt(experiment, "numbits") : -1;
		int maxruns    = JsonUtil.getInt(experiment, "numruns");
 		
		String config  = (numbits > 0) ? "bitstring.json" : "numeric.json";
		FileReader reader = new FileReader(config);
		EAConfiguration conf = new EAConfiguration((JsonObject) Jsoner.deserialize(reader));
		File f = new File ("lastseed.txt");
		long seed;
		if (f.exists()) {
			Scanner scanner = new Scanner(Paths.get("lastseed.txt"));
			seed = scanner.nextLong() + 1;
			conf.setSeed(seed);
			scanner.close();
		}
		else {
			seed = conf.getSeed();
		}
		
		
		int numruns = conf.getNumRuns();
		System.out.println(conf);
		EvolutionaryAlgorithm myEA = new EvolutionaryAlgorithm(conf);
		ContinuousObjectiveFunction cf = create(problem, numvars, range);
		if (numbits > 0) {
			myEA.setObjectiveFunction(new BinaryEncodedContinuousObjectiveFunction(numbits, cf));
			myEA.getStatistics().setDiversityMeasure(new EntropyDiversity());
		}
		else {
			myEA.setObjectiveFunction(cf);
			myEA.getStatistics().setDiversityMeasure(new VarianceDiversity());
		}
		
		
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.print("Running the EA on " + problem + "(" + numvars + ", " + range + ")");
		if (numbits > 0) {
			System.out.print(" binarized with " + numbits + " bits per variable");
		}
		else {
			System.out.print(" on a continuous domain" + seed);
		}
		System.out.println(" with seed " + conf.getSeed());
		System.out.println("-------------------------------------------------------------------------------------");
		
		for (int i=0; i<numruns; i++) {
			myEA.run();
			System.out.println ("Run " + i + ": " + 
								String.format(Locale.US, "%.2f", myEA.getStatistics().getTime(i)) + "s\t" +
								myEA.getStatistics().getBest(i).getFitness());
		}
		PrintWriter file = new PrintWriter(problem + "-" + numvars + "-" + seed + "-stats.json");
		file.print(myEA.getStatistics().toJSON().toJson());
		file.close();
		
		seed += numruns - 1;
		if (seed < maxruns) {
			PrintWriter seedFile = new PrintWriter("lastseed.txt");
			seedFile.println(seed);
			seedFile.close();
		}
		else {
			f.delete();
		}
	}
	
	
	/**
	 * Creates a continuous objective function 
	 * @param problem name of the objective function
	 * @param numvars number of variables
	 * @param range range of the variables
	 * @return the objective function indicated
	 */
	private static ContinuousObjectiveFunction create(String problem, int numvars, double range) {
		switch (problem.toUpperCase()) {
		case "ACKLEY":
			return new Ackley(numvars, range);

		case "GRIEWANK":
			return new Griewank(numvars, range);
			
		case "RASTRIGIN": 
			return new Rastrigin(numvars, range);
			
		case "ROSENBROCK":
			return new Rosenbrock(numvars, range);
			
		case "SPHERE":
			return new Sphere(numvars, range);

		default:
			return null;
		}
	}
	

}
