package net.math.src;

public class SetSemiInfinitive extends SetNumeric
{
	
	private boolean	noTopLimit;
	
	public SetSemiInfinitive(boolean noTopLimit, double limit, boolean enableBorders)
	{
		super(0, 0, enableBorders);
		this.noTopLimit = noTopLimit;
		if (noTopLimit)
		{
			min = limit;
		}
		else
		{
			max = limit;
		}
	}
	
	@Override
	public boolean containsNumber(double x)
	{
		if (noTopLimit)
		{
			if (isBorders())
			{
				if (x >= min)
				{
					return true;
				}
			}
			else
			{
				if (x > min)
				{
					return true;
				}
			}
		}
		else
		{
			if (isBorders())
			{
				if (x <= max)
				{
					return true;
				}
			}
			else
			{
				if (x < max)
				{
					return true;
				}
			}
		}
		return false;
	}
	
}
