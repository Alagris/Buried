package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionCircleHalf implements Function
{
	
	/** o - center of circle, r - radius */
	protected double	o, r;
	
	/** o - center of circle, r - radius */
	public FunctionCircleHalf(double r, double o)
	{
		set(r, o);
	}
	
	@Override
	public double doFunction(double x)
	{
		return Math.sqrt(r * r - Math.pow(Math.abs(o - x), 2)) / 200;
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return new SetNumeric(o - 1, o + r, true);
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
	
	/** o - center of circle, r - radius */
	public void set(double r, double o)
	{
		setO(o);
		setR(r);
	}
	
	/** o - center of circle, r - radius */
	public double getO()
	{
		return o;
	}
	
	/** o - center of circle, r - radius */
	public void setO(double o)
	{
		this.o = o;
	}
	
	/** o - center of circle, r - radius */
	public double getR()
	{
		return r;
	}
	
	/** o - center of circle, r - radius */
	public void setR(double r)
	{
		this.r = r;
	}
	
}
