package net.math.src.func;

public class FunctionUnipolar extends FunctionPolar
{
	
	/** Returns always 0 (x<=0) or 1 (x>0) */
	public FunctionUnipolar()
	{
		super(0, 1, true);
	}
	
}
