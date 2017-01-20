package net.math.src.func;

import net.math.src.func.Function;
import net.math.src.func.FunctionBellShaped;
import net.math.src.func.FunctionGaussian;
import net.math.src.func.FunctionInterval;
import net.math.src.func.FunctionL;
import net.math.src.func.FunctionMembershipGamma;
import net.math.src.func.FunctionMembershipPi;
import net.math.src.func.FunctionMembershipS;
import net.math.src.func.FunctionSingleton;
import net.math.src.func.FunctionT;

public class EnumFunctions
{
	
	public static final Function	FunctionT				= new FunctionT(1, 1, 1);
	public static final Function	FunctionBellShaped		= new FunctionBellShaped(1, 1, 1);
	public static final Function	FunctionSingleton		= new FunctionSingleton(1);
	public static final Function	FunctionMembershipGamma	= new FunctionMembershipGamma(1, 1);
	public static final Function	FunctionMembershipPi	= new FunctionMembershipPi(1, 1);
	public static final Function	FunctionMembershipS		= new FunctionMembershipS(1, 1);
	public static final Function	FunctionInterval		= new FunctionInterval(1, 1);
	public static final Function	FunctionL				= new FunctionL(1, 1);
	public static final Function	FunctionGaussian		= new FunctionGaussian(1, 1);
	public static final Function	FunctionCircleHalf		= new FunctionCircleHalf(1, 1);
	public static final Function	FunctionOval			= new FunctionOval(1, 1, 1);
	public static final Function	FunctionParabola		= new FunctionParabola(1, 1);
	
	public static final Function	FunctionPolar			= new FunctionPolar(-1, 1, true);
	public static final Function	FunctionBipolar			= new FunctionBipolar();
	public static final Function	FunctionSigmoidBipolar	= new FunctionSigmoidBipolar(0.1f);
	public static final Function	FunctionSigmoidUnipolar	= new FunctionSigmoidUnipolar(0.2f);
	public static final Function	FunctionUnipolar		= new FunctionUnipolar();
	public static final Function[]	all						= { FunctionT, FunctionBellShaped, FunctionSingleton, FunctionMembershipGamma, FunctionMembershipPi, FunctionMembershipS, FunctionInterval, FunctionL, FunctionGaussian,
			FunctionCircleHalf, FunctionOval, FunctionParabola, FunctionPolar, FunctionBipolar, FunctionSigmoidBipolar, FunctionSigmoidUnipolar, FunctionUnipolar };
	
	public static final String[]	allnames				= { "FunctionT", "FunctionBellShaped", "FunctionSingleton", "FunctionMembershipGamma", "FunctionMembershipPi", "FunctionMembershipS", "FunctionInterval", "FunctionL",
			"FunctionGaussian", "FunctionCircleHalf", "FunctionOval", "FunctionParabola" ,"FunctionPolar", "FunctionBipolar", "FunctionSigmoidBipolar", "FunctionSigmoidUnipolar", "FunctionUnipolar"};
	
}
