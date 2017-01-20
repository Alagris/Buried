package net.math.src;

public abstract class FuzzySetFOU extends FuzzySet
{
	
	public static final FuzzySetFOU	nullarSet	= new FuzzySetFOUNullar();
	
	public FuzzySetFOU()
	{
		super(new SetNumeric(0, 1, true));
	}
	
	/**
	 * This method is the same as doMembershipFunction() but this name makes
	 * operations of fuzzy sets of type 2 easier
	 */
	public double doSecondaryMembership(double y)
	{
		return doMembershipFunction(y);
	}
}
