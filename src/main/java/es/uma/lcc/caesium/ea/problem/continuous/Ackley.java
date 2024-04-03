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
public class Ackley extends ContinuousObjectiveFunction {
	/**
	 * function constant A
	 */
	private static final double A = 20.0;
	/**
	 * function constants B
	 */
	private static final double B = 0.2;
	/**
	 * function constant C
	 */
	private static final double C = 2.0*Math.PI;
	
	/**
	 * Basic constructor
	 * @param i the number of variables and their range
	 * @param v range of variables ([-v, v])
	 */
	public Ackley(int i, double v) {
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
		double s1 = 0.0;
		double s2 = 0.0;
		for (int j=0; j<numvars; j++) {
			double v = (double)g.getGene(j);
			s1 += v*v;
			s2 += Math.cos(C*v);
		}
		return -A*Math.exp(-B*Math.sqrt(s1/numvars)) - Math.exp(s2/numvars) + A + Math.E;
	}



}
