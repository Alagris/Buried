package net.math.src.func;

public class FunctionOval extends FunctionCircleHalf implements Function
{
	
	private double	c;
	
	public FunctionOval(double r, double o, double c)
	{
		super(r, o);
		setC(c);
	}
	
	@Override
	public double doFunction(double x)
	{
		return super.doFunction(x) * c / r;
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
	
	public void set(double r, double o, double c)
	{
		super.set(r, o);
		setC(c);
	}
	
	public double getC()
	{
		return c;
	}
	
	public void setC(double c)
	{
		this.c = c;
	}
	
}
