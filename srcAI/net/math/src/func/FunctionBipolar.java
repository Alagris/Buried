package net.math.src.func;

public class FunctionBipolar extends FunctionPolar
{
	/** Returns always -1 (x<=0) or 1 (x>0) */
	public FunctionBipolar()
	{
		super(-1, 1, true);
	}
	
}
