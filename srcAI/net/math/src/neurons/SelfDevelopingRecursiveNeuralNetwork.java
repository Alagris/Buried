package net.math.src.neurons;

public interface SelfDevelopingRecursiveNeuralNetwork
{
	public NeurogliaRecursive[] getNetworks();
	
	public int getSize();
	
	public NeurogliaRecursive getNetwork(int index);
	
	public NeurogliaRecursive getBestNewtork();
	
	public NeurogliaRecursive getAlmostBestNewtork();
	
	public NeurogliaRecursive getWorstNewtork();
	
	/**
	 * if parameter is set to true it's a message for neural network that current design returned
	 * some good results. It false then neural network will know that it did something wrong.
	 * Intensity is 1 by default
	 */
	public void sendEnvironmentFeedback(boolean netDoesWell);
	
	/**
	 * if parameter is set to true it's a message for neural network that current design returned
	 * some good results. It false then neural network will know that it did something wrong.
	 * Intensity is specified by parameter (should be positive number but doesn't have to)
	 */
	void sendEnvironmentFeedback(boolean netDoesWell, double intensity);
	
	void trainHebbianNoTeacher(double... input);
}
