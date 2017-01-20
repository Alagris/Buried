package net.math.src.func;

import net.math.src.SetNumeric;
import net.math.src.SetSemiInfinitive;

public class FunctionL implements Function
{
	
	private double	a;
	private double	b;
	
	/**
	 * @param b
	 *            - minimum x - lowest membership
	 * @param a
	 *            - top value where membership is 1
	 */
	public FunctionL(double a, double b)
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
		if (x <= a)
		{ // minimum reached
			return 1;
		}
		else if (x >= b)
		{// x is over top
			return 0;
		}
		else
		{ // a < x < b
			return (b - x) / (b - a);
		}
		
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
		return new SetSemiInfinitive(true, b, false);
	}
}
