package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionSigmoidBipolar implements Function
{
	private double	beta;
	
	public FunctionSigmoidBipolar(double beta)
	{
		setBeta(beta);
	}
	
	@Override
	public double doFunction(double x)
	{
		double d = Math.exp(beta * x);
		return (1 - d) / (1 + d);
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return SetNumeric.infiniteSet;
	}
	
	@Override
	public void setParameters(double... params)
	{
		beta = params[0];
	}
	
	public double getBeta()
	{
		return beta;
	}
	
	public void setBeta(double beta)
	{
		this.beta = beta;
	}
	
}
