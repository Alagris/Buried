package net.math.src;

/** Fuzzy set of type 2 */
public abstract class FuzzySetT2
{
	
	protected SetNumeric	X;
	
	/**
	 * @param X
	 *            - the best option is to put here {@link SetInfinitive}
	 */
	public FuzzySetT2(SetNumeric X)
	{
		this.X = X;
	}
	
	public FuzzySetFOU doPrimaryMembership(double x)
	{
		if (X.containsNumber(x))
		{
			return primaryMembership(x);
		}
		return FuzzySetFOU.nullarSet;
	}
	
	protected abstract FuzzySetFOU primaryMembership(double x);
	
	public double doMembershipFunction(double x, double y)
	{
		return doPrimaryMembership(x).doSecondaryMembership(y);
	}
}
