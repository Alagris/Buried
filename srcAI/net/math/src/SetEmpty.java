package net.math.src;

public class SetEmpty extends SetNumeric
{
	
	public SetEmpty()
	{
		super(0, 0, false);
	}
	
	@Override
	public boolean containsNumber(double x)
	{
		return false;
	}
	
}
