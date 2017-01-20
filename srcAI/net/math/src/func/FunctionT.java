package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionT implements Function
{
	
	private double	a;
	private double	b;
	private double	c;
	
	/**
	 * @param a
	 *            - back x - lowest membership
	 * @param b
	 *            - middle value where membership is 1
	 * @param c
	 *            - front x - lowest membership
	 */
	public FunctionT(double a, double b, double c)
	{
		set(a, b, c);
	}
	
	@Override
	public void setParameters(double... params)
	{
		try
		{
			set(params[0], params[1], params[2]);
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
			return 0;
		}
		else if (x >= c)
		{// x is over top
			return 0;
		}
		else
		{ // a < x < c
			if (x > b)
			{
				return (c - x) / (c - b);
			}
			else
			{ // x<=b
				return (x - a) / (b - a);
			}
		}
		
	}
	
	public void set(double a, double b, double c)
	{
		setA(a);
		setB(b);
		setC(c);
	}
	
	public double getB()
	{
		return b;
	}
	
	public void setB(double b)
	{
		this.b = b;
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
		return new SetNumeric(a, c, false);
	}
	
}
