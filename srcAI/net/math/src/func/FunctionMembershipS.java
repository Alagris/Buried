package net.math.src.func;

import net.math.src.SetNumeric;
import net.math.src.SetSemiInfinitive;

public class FunctionMembershipS implements Function
{
	
	private double	a;	// minimum x
	private double	b;	// middle value
	private double	c;	// maximum x
						
	/**
	 * @param a
	 *            - minimum x - lowest membership
	 * @param b
	 *            - middle value where membership is a half
	 * @param c
	 *            - maximum x - highest membership
	 */
	public FunctionMembershipS(double a, double c)
	{
		set(a, c);
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
		if (x > c)
		{ // over max
			return 1;
		}
		else if (x <= a)
		{ // minimum reached
			return 0;
		}
		else if (a < x && x <= b)
		{
			return 2 * (Math.pow(x - a, 2) / Math.pow(c - a, 2));
		}
		else
		{ // b < x <= c
			return 1 - 2 * (Math.pow(x - c, 2) / Math.pow(c - a, 2));
		}
		
	}
	
	public void set(double a, double c)
	{
		setA(a);
		setC(c);
		setB();
	}
	
	public double getB()
	{
		return b;
	}
	
	public void setB()
	{
		this.b = a + (c - a) / 2;
	}
	
	public double getC()
	{
		return c;
	}
	
	public void setC(double c)
	{
		this.c = c;
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
		return new SetSemiInfinitive(false, a, false);
	}
}
