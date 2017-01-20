package net.math.src;

public abstract class FuzzySet
{
	
	protected SetNumeric	X;
	
	/**
	 * @param X
	 *            - the best option is to put here {@link SetInfinitive}
	 */
	public FuzzySet(SetNumeric X)
	{
		this.X = X;
	}
	
	public double doMembershipFunction(double x)
	{
		if (X.containsNumber(x))
		{
			return membershipFunction(x);
		}
		else
		{
			return 0;
		}
	}
	
	protected abstract double membershipFunction(double x);
	
	public abstract SetNumeric getSupport();
	
	public abstract double getMaxHeight();
	
	// ====operations====
	
	/** max(fs1(x) membership,fs2(x) membership) */
	public double union(FuzzySet fs2, double x)
	{
		double x1, x2;
		x1 = doMembershipFunction(x);
		x2 = fs2.doMembershipFunction(x);
		if (x1 > x2)
		{
			return x1;
		}
		else
		{
			return x2;
		}
	}
	
	/** min(fs1(x) membership,fs2(x) membership) */
	public double intersection(FuzzySet fs2, double x)
	{
		double x1, x2;
		x1 = doMembershipFunction(x);
		x2 = fs2.doMembershipFunction(x);
		if (x1 < x2)
		{
			return x1;
		}
		else
		{
			return x2;
		}
	}
	
	/** fs1(x) membership * fs2(x) membership */
	public double algebraicProduct(FuzzySet fs2, double x)
	{
		return doMembershipFunction(x) * fs2.doMembershipFunction(x);
	}
	
	public double complement(double x)
	{
		return 1 - doMembershipFunction(x);
	}
	
	/** True if fs1(x) membership > fs2(x) membership **/
	public boolean contains(FuzzySet fs2, double x)
	{
		return doMembershipFunction(x) > fs2.doMembershipFunction(x);
	}
	
	/** True if fs1(x) membership = fs2(x) membership **/
	public boolean equals(FuzzySet fs2, double x)
	{
		return doMembershipFunction(x) == fs2.doMembershipFunction(x);
	}
	
	/** fs1(x) membership ^ 2 */
	public double concentration(double x)
	{
		return Math.pow(doMembershipFunction(x), 2);
	}
	
	/** fs1(x) membership ^ 0.5 */
	public double dilatation(double x)
	{
		return Math.pow(doMembershipFunction(x), 0.5);
	}
	
	public double normalization(double x)
	{
		return doMembershipFunction(x) / getMaxHeight();
	}
	
	public boolean isNormalized()
	{
		return getMaxHeight() == 1;
	}
	// TODO: add extensions
}
