package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionSign implements Function
{
	
	public FunctionSign()
	{
	}
	
	@Override
	public double doFunction(double x)
	{
		return Math.signum(x);
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return SetNumeric.infiniteSet;
	}
	
	@Override
	public void setParameters(double... params)
	{
	}
	
}
