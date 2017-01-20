package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionInterval implements Function
{
	
	private double	a;
	private double	b;
	
	/**
	 * @param a
	 *            - minimal accepted x
	 * @param b
	 *            - maximal accepted x
	 */
	public FunctionInterval(double a, double b)
	{
		set(a, b);
	}
	
	@Override
	public void setParameters(double... params)
	{
		try
		{
			set(params[0], params[1]);
		}
		catch (IndexOutOfBoundsException e)
		{
		}
	}
	
	@Override
	public double doFunction(double x)
	{
		if (x < a)
		{
			return 0;
		}
		else if (x > b)
		{
			return 0;
		}
		return 1;
		
	}
	
	public void set(double a, double b)
	{
		setA(a);
		setB(b);
	}
	
	public double getB()
	{
		return b;
	}
	
	public void setB(double b)
	{
		this.b = b;
	}
	
	public double getA()
	{
		return a;
	}
	
	public void setA(double a)
	{
		this.a = a;
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return new SetNumeric(a, b, true);
	}
}
