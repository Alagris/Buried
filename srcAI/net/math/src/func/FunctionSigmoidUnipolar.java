package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionSigmoidUnipolar implements Function
{
	private double	beta;
	
	public FunctionSigmoidUnipolar(double beta)
	{
		setBeta(beta);
	}
	
	@Override
	public double doFunction(double x)
	{
		return 1 / (1 + Math.exp(-beta * x));
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
