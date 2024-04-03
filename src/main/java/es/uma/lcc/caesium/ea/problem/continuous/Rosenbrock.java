package es.uma.lcc.caesium.ea.problem.continuous;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;


/**
 * Rosenbrock function
 * @author ccottap
 * @version 1.0
 *
 */
public class Rosenbrock extends ContinuousObjectiveFunction {
	/**
	 * function constant
	 */
	private static final double A = 100.0;
	
	/**
	 * Basic constructor
	 * @param i the number of variables and their range
	 * @param v range of variables ([-v, v])
	 */
	public Rosenbrock(int i, double v) {
		super(i, -v, v);
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
		double c = 0;
		for (int j=1; j<numvars; j++) {
			double v1 = (double)g.getGene(j-1);
			double v2 = (double)g.getGene(j);
			double t1 = v2-v1*v1;
			double t2 = 1-v1;
			c += A*t1*t1 + t2*t2;
		}
		return c;
	}



}
