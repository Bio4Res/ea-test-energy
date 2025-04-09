package es.uma.lcc.caesium.ea.problem.continuous;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;


/**
 * Solomon function
 * @author ccottap
 * @version 1.0
 *
 */
public class Solomon extends ContinuousObjectiveFunction {
	/**
	 * function constant
	 */
	private static final double A = 2.0*Math.PI;
	/**
	 * function constant
	 */
	private static final double B = 0.1;
	
	/**
	 * Basic constructor
	 * @param i the number of variables and their range
	 * @param v range of variables ([-v, v])
	 */
	public Solomon(int i, double v) {
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
		double s = 0.0;
		for (int j=0; j<numvars; j++) {
			double v = (double)g.getGene(j);
			s += v*v;
		}
		s = Math.sqrt(s);
		return 1.0 - Math.cos(A*s) + B*s;
	}


}
