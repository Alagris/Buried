package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionParabola implements Function
{
	
	private double	focusPointX, focusPointY;
	
	public FunctionParabola(double x, double y)
	{
		set(x, y);
	}
	
	@Override
	public double doFunction(double x)
	{
		return (Math.pow(focusPointY, 2) - Math.pow((x - focusPointX), 2)) / (-2 * focusPointY) + focusPointY;
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return SetNumeric.emptySet;
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
	
	private void set(double x, double y)
	{
		setFocusPointX(x);
		setFocusPointY(y);
	}
	
	public double getFocusPointY()
	{
		return focusPointY;
	}
	
	public void setFocusPointY(double focusPointY)
	{
		this.focusPointY = focusPointY;
	}
	
	public double getFocusPointX()
	{
		return focusPointX;
	}
	
	public void setFocusPointX(double focusPointX)
	{
		this.focusPointX = focusPointX;
	}
	
}
