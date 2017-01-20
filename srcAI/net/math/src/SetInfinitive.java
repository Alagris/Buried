package net.math.src;

public class SetInfinitive extends SetNumeric
{
	
	public SetInfinitive()
	{
		super(0, 0, false);
	}
	
	@Override
	public boolean containsNumber(double x)
	{
		return true;
	}
}
