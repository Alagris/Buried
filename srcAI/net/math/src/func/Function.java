package net.math.src.func;

import net.math.src.SetNumeric;

public interface Function
{
	public abstract double doFunction(double x);
	
	public abstract SetNumeric getSupport();
	
	public abstract void setParameters(double... params);
	
}
