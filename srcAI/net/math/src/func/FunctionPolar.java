package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionPolar implements Function
{
	
	private double	negativeResul, positiveResult;
	private boolean	zeroEnabled;
	
	public FunctionPolar(double neg, double pos, boolean zeroAsNegative)
	{
		setResult(neg, pos);
		setZeroEnabled(zeroAsNegative);
	}
	
	public void setResult(double neg, double pos)
	{
		setNegativeResul(neg);
		setPositiveResult(pos);
	}
	
	@Override
	public double doFunction(double x)
	{
		if (x > 0) return positiveResult;
		if (isZeroEnabled())
		{
			if (x < 0) return negativeResul;
		}
		else
		{
			if (x <= 0) return negativeResul;
		}
		return 0;
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
	
	public double getNegativeResul()
	{
		return negativeResul;
	}
	
	public void setNegativeResul(double negativeResul)
	{
		this.negativeResul = negativeResul;
	}
	
	public double getPositiveResult()
	{
		return positiveResult;
	}
	
	public void setPositiveResult(double positiveResult)
	{
		this.positiveResult = positiveResult;
	}
	
	public boolean isZeroEnabled()
	{
		return zeroEnabled;
	}
	
	public void setZeroEnabled(boolean zeroEnabled)
	{
		this.zeroEnabled = zeroEnabled;
	}
	
}
