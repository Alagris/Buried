package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionBellShaped implements Function
{
	private double	a;
	private double	b;
	private double	c;
	
	/**
	 * @param a
	 *            - the half width
	 * @param b
	 *            - controls the slopes at the crossover points
	 * @param c
	 *            - center
	 */
	public FunctionBellShaped(double a, double b, double c)
	{
		set(a, b, c);
	}
	
	@Override
	public double doFunction(double x)
	{
		return 1 / (1 + Math.pow(Math.abs((x - c) / a), 2 * b));
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
		return SetNumeric.infiniteSet;
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
}
