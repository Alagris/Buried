package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionSingleton implements Function
{
	private double	x;
	
	/**
	 * @param x
	 *            - value that is accepted
	 */
	public FunctionSingleton(double x)
	{
		setX(x);
	}
	
	@Override
	public void setParameters(double... params)
	{
		try
		{
			setX(params[0]);
		}
		catch (IndexOutOfBoundsException e)
		{
		}
	}
	
	@Override
	public double doFunction(double x)
	{
		if (this.x == x)
		{
			return 1;
		}
		else
		{
			return 0;
		}
		
	}
	
	public double getX()
	{
		return x;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return new SetNumeric(x, x, true);
	}
}
