package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionHeavisideStepBipolar implements Function
{
	
	/**
	 * Returns always -1 (x<=0) or 1 (x>0)
	 */
	@Override
	public double doFunction(double x)
	{
		return (x > 0) ? 1 : -1;
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
