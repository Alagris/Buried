package net.math.src;

public class FuzzySetFOUNullar extends FuzzySetFOU
{
	
	@Override
	protected double membershipFunction(double x)
	{
		return 0;
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return new SetNumeric(0, 0, false);
	}
	
	@Override
	public double getMaxHeight()
	{
		return 0;
	}
	
}
