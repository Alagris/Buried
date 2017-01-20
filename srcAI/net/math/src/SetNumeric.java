package net.math.src;

public class SetNumeric
{
	
	public static final SetNumeric	infiniteSet	= new SetInfinitive();
	public static final SetNumeric	emptySet	= new SetEmpty();
	
	protected double				min, max;
	protected boolean				borderNegative, borderPositive;
	
	public SetNumeric(double min, double max, boolean enableBorders)
	{
		set(min, max);
		setBorders(enableBorders);
	}
	
	public void set(double min, double max)
	{
		setMax(max);
		setMin(min);
	}
	
	public double getMax()
	{
		return max;
	}
	
	public void setMax(double max)
	{
		this.max = max;
	}
	
	public double getMin()
	{
		return min;
	}
	
	public void setMin(double min)
	{
		this.min = min;
	}
	
	public boolean isBorders()
	{
		return isBorderNegative() && isBorderPositive();
	}
	
	public void setBorders(boolean borders)
	{
		setBorderNegative(borders);
		setBorderPositive(borders);
	}
	
	public boolean isInPositiveBorder(double x)
	{
		if (isBorderPositive())
		{
			return x <= max;
		}
		else
		{
			return x < max;
		}
	}
	
	public boolean isInNegativeBorder(double x)
	{
		if (isBorderNegative())
		{
			return x >= min;
		}
		else
		{
			return x > min;
		}
	}
	
	public boolean containsNumber(double x)
	{
		return isInNegativeBorder(x) && isInPositiveBorder(x);
	}
	
	/**
	 * if this number is in set x will be returned. Otherwise method will return
	 * 0 . If border is enabled then x CAN be EQUAL to max/min value
	 */
	public double getNumber(double x)
	{
		if (containsNumber(x))
		{
			return x;
		}
		else
		{
			return Double.NaN;
		}
	}
	
	public boolean isBorderNegative()
	{
		return borderNegative;
	}
	
	public void setBorderNegative(boolean borderNegative)
	{
		this.borderNegative = borderNegative;
	}
	
	public boolean isBorderPositive()
	{
		return borderPositive;
	}
	
	public void setBorderPositive(boolean borderPositive)
	{
		this.borderPositive = borderPositive;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		if (isBorderNegative())
		{
			s = s + "[";
		}
		else
		{
			s = s + "(";
		}
		s = s + min + ";" + max;
		if (isBorderPositive())
		{
			s = s + "]";
		}
		else
		{
			s = s + ")";
		}
		return s;
	}
}
