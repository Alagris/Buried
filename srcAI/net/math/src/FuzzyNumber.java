package net.math.src;

import net.math.src.func.Function;

public abstract class FuzzyNumber extends FuzzySet
{
	
	private Function	f;
	
	/**
	 * @param X
	 *            - the best option is to put here {@link SetInfinitive}
	 * @param f
	 *            - pay attention to keep this function continuous and with max
	 *            height 1
	 */
	public FuzzyNumber(SetNumeric X, Function f)
	{
		super(X);
		this.f = f;
	}
	
	@Override
	public SetNumeric getSupport()
	{
		return f.getSupport();
	}
	
	@Override
	public double getMaxHeight()
	{
		return 1;
	}
}
