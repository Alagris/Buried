package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionLinear implements Function
{
	private double	a;
	private double	b;
	
	public FunctionLinear(double a, double b)
	{
		set(a, b);
	}
	
	/** Standard linear function y = x*a + b */
	@Override
	public double doFunction(double x)
	{
		return x * a + b;
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
			set(params[0], params[1]);
		}
		catch (IndexOutOfBoundsException e)
		{
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
	
}
