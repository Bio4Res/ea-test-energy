package es.uma.lcc.caesium.ea.problem.continuous;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;


/**
 * Schwefel function
 * @author ccottap
 * @version 1.0
 *
 */
public class Schwefel extends ContinuousObjectiveFunction {
	/**
	 * function constant
	 */
	private static final double A = 418.9829;
	/**
	 * a fixed term in the function that depends on the number of variables
	 */
	private double c;
	
	/**
	 * Basic constructor
	 * @param i the number of variables and their range
	 * @param v range of variables ([-v, v])
	 */
	public Schwefel(int i, double v) {
		super(i, -v, v);
		c = (double)i*A;
	}
	
	/**
	 * Indicates whether the goal is maximization or minimization
	 * @return the optimization sense
	 */
	public OptimizationSense getOptimizationSense()
	{
		return OptimizationSense.MINIMIZATION;
	}
	

	@Override
	protected double _evaluate(Individual i) {
		Genotype g = i.getGenome();
		double s = 0.0;
		for (int j=0; j<numvars; j++) {
			double v = (double)g.getGene(j);
			s += v*Math.sin(Math.sqrt(Math.abs(v)));
		}
		return c - s;
	}


}
