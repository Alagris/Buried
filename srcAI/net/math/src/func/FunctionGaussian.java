package net.math.src.func;

import net.math.src.SetNumeric;

public class FunctionGaussian implements Function
{
	
	private double	sigma;
	private double	center;
	
	/**
	 * @param center
	 *            - point where membership is 1
	 * @param sigma
	 *            - standard deviation
	 */
	public FunctionGaussian(double sigma, double center)
	{
		set(sigma, center);
	}
	
	@Override
	public double doFunction(double x)
	{
		return Math.exp(-Math.pow((x - center) / sigma, 2));
	}
	
	public void set(double sigma, double center)
	{
		setCenter(center);
		setSigma(sigma);
	}
	
	public double getCenter()
	{
		return center;
	}
	
	public void setCenter(double center)
	{
		this.center = center;
	}
	
	public double getSigma()
	{
		return sigma;
	}
	
	public void setSigma(double sigma)
	{
		this.sigma = sigma;
	}
	
	@Override
	public SetNumeric getSupport()
	{
		// TODO: ask a mathematician which function is correct
		return new SetNumeric(center - 4 * sigma, center + 4 * sigma, false);
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
