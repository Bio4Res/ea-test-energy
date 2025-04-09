package es.uma.lcc.caesium.ea.problem.continuous;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;


/**
 * Xin-She Yang function no. 2
 * @author ccottap
 * @version 1.0
 *
 */
public class XinSheYang2 extends ContinuousObjectiveFunction {
	
	
	/**
	 * Basic constructor
	 * @param i the number of variables and their range
	 * @param v range of variables ([-v, v])
	 */
	public XinSheYang2(int i, double v) {
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
			s1 += Math.abs(v);
			s2 += Math.sin(v*v);
		}
		return s1 / Math.exp(s2);
	}


}
