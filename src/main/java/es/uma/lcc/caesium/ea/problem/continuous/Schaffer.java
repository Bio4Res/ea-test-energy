package es.uma.lcc.caesium.ea.problem.continuous;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;


/**
 * Schaffer function no. 2
 * @author ccottap
 * @version 1.0
 *
 */
public class Schaffer extends ContinuousObjectiveFunction {
	/**
	 * function constant A
	 */
	private static final double A = 0.5;
	/**
	 * function constant B
	 */
	private static final double B = 0.001;
	/**
	 * a fixed term in the function that depends on the number of variables
	 */
	private double c;
	
	/**
	 * Basic constructor
	 * @param i the number of variables and their range
	 * @param v range of variables ([-v, v])
	 */
	public Schaffer(int i, double v) {
		super(i, -v, v);
		c = (double)(i-1)*A;
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
		double s = c;
		double v1 = (double)g.getGene(0);
		double v12 = v1 * v1;
		for (int j=1; j<numvars; j++) {
			double v2 = (double)g.getGene(j);
			double v22 = v2*v2;
			double d1 = v12 - v22;
			double d2 = v12 + v22;
			double t = Math.sin(d1);
			double u = 1 + B*d2;
			s += (t*t-A) / (u*u);
			v12 = v22;
		}
		return s;
	}


}
