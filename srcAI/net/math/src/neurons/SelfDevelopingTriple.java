package net.math.src.neurons;

import net.math.src.func.FunctionSigmoidBipolar;
import net.math.src.genetics.BinaryDNA;
import net.math.src.genetics.GeneticCode;

public class SelfDevelopingTriple implements SelfDevelopingRecursiveNeuralNetwork
{
	/////////////////////////////////////////
	// FIXME: This class is not ready to use!
	/////////////////////////////////////////
	
	public static final float		DEVELOPMENT_VELOCITY	= 1.01f;
	
	/** Those networks are the two best networks generated so far */
	private NeurogliaRecursive[]	goodNets				= new NeurogliaRecursive[2];
	
	/** Score of networks at goodNets[0] */
	private double					firstNetworkScore;
	
	/** Score of networks at goodNets[1] */
	private double					secondNetworkScore;
	
	/** This networks is being tested */
	private NeurogliaRecursive		lastGeneratedNetwork;
	
	/** Score of lastGeneratedNetwork */
	private double					lastGeneratedNetworkScore;
	
	/** Time is measured in ticks (every single call of training method is a new tick) */
	private long					ticksPassed				= 0;
	
	private double					evolutionTime			= 1000;
	
	@Override
	public NeurogliaRecursive[] getNetworks()
	{
		return goodNets;
	}
	
	@Override
	public int getSize()
	{
		return goodNets.length;
	}
	
	@Override
	public NeurogliaRecursive getNetwork(int index)
	{
		return goodNets[index];
	}
	
	@Override
	public NeurogliaRecursive getBestNewtork()
	{
		return firstNetworkScore > secondNetworkScore ? goodNets[0] : goodNets[1];
	}
	
	@Override
	public NeurogliaRecursive getWorstNewtork()
	{
		return lastGeneratedNetwork;
	}
	
	@Override
	public NeurogliaRecursive getAlmostBestNewtork()
	{
		return firstNetworkScore < secondNetworkScore ? goodNets[0] : goodNets[1];
	}
	
	@Override
	public void sendEnvironmentFeedback(boolean netDoesWell, double intensity)
	{
		lastGeneratedNetworkScore += netDoesWell ? intensity : -intensity;
	}
	
	// ============Training methods=========
	/**
	 * @param input
	 *            -length must be equal to inputVectorSize
	 */
	@Override
	public void trainHebbianNoTeacher(double... input)
	{
		ticksPassed++;
		if (ticksPassed >= evolutionTime)
		{
			ticksPassed = 0;
			evolutionTime *= DEVELOPMENT_VELOCITY;// gets bigger
			
			if (lastGeneratedNetworkScore > firstNetworkScore)
			{
				if (lastGeneratedNetworkScore > secondNetworkScore)
				{
					if (firstNetworkScore > secondNetworkScore)
					{
						setLastGeneratedNetworkAsSecond();
					}
					else
					{
						setLastGeneratedNetworkAsFirst();
					}
				}
				else
				{
					setLastGeneratedNetworkAsFirst();
				}
			}
			else if (lastGeneratedNetworkScore > secondNetworkScore)
			{
				setLastGeneratedNetworkAsSecond();
			}
			else
			{
				BinaryDNA newDNA  = GeneticCode.recombinate_sexLikeMethod(goodNets[0].getGenetics(), goodNets[1].getGenetics(), 0.03);
			//	agents.get(i).getBeta() + (Math.random() > 0.98 ? Math.random() * (Math.random() > 5 ? 1 : -1) : 0;
				lastGeneratedNetwork = GeneticCode.parseNetworkRecursive(newDNA, 0.9, new FunctionSigmoidBipolar(beta), inputVectorSize, outputNeurons)
			}
		}
		else
		{
			
		}
	}
	
	private void setLastGeneratedNetworkAsSecond()
	{
	}
	
	private void setLastGeneratedNetworkAsFirst()
	{
	}
	
	@Override
	public void sendEnvironmentFeedback(boolean netDoesWell)
	{
		sendEnvironmentFeedback(netDoesWell, 1);
	}
	
}
