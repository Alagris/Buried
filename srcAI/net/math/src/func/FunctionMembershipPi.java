package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionMembershipPi implements Function
{
	
	private double	c;	// place where this function will return 1 - top point
	private double	b;	// length of function
	private FunctionMembershipS	back, front;
	
	/**
	 * @param c
	 *            - place where this function will return 1 - top point
	 * @param b
	 *            - length of function
	 */
	public FunctionMembershipPi(double c, double b)
	{
		this.b = b;
		this.c = c;
		front = new FunctionMembershipS(0, 0);
		back = new FunctionMembershipS(0, 0);
		resetS();
	}
	
	public void set(double c, double b)
	{
		setB(b);
		setC(c);
	}
	
	@Override
	public double doFunction(double x)
	{
		if (x >= c)
		{
			return 1 - back.doFunction(x);
		}
		else
		{ // x>c
			return front.doFunction(x);
		}
		
	}
	
	public double getC()
	{
		return c;
	}
	
	public void setC(double c)
	{
		this.c = c;
		resetS();
	}
	
	public double getB()
	{
		return b;
	}
	
	public void setB(double b)
	{
		this.b = b;
		resetS();
	}
	
	private void resetS()
	{
		front.set(c - b, c);
		back.set(c, c + b);
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return new SetNumeric(c - b, c + b, false);
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
}
