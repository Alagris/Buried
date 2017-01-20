package net.math.src.neurons;

import net.math.src.genetics.BinaryDNA;

public class NeurogliaRecursive
{
	// ============Variables=========
	/** The first few neurons are input neurons. They can't be trained */
	private final NeuronInterface[]	neurons;
	private final int				inputVectorSize;
	private final int[]				outputNeurons;
	private BinaryDNA				genetics;
	
	// ============Constructors=========
	/**
	 * @param neuronsNumber
	 *            - number of hidden and output neurons (excluding input neurons)
	 */
	public NeurogliaRecursive(int neuronsNumber, int inputVectorSize, int[] outputNeurons, BinaryDNA dna)
	{
		neurons = new NeuronInterface[neuronsNumber + inputVectorSize];
		for (int i = 0; i < inputVectorSize; i++)
		{
			neurons[i] = new InputNeuron();
		}
		this.outputNeurons = outputNeurons;
		this.inputVectorSize = inputVectorSize;
		setGenetics(dna);
	}
	
	// ============Getters and setters=========
	public void setNeuron(int index, NeuronInterface neuron)
	{
		neurons[index + inputVectorSize] = neuron;
	}
	
	public NeuronInterface[] getNeurons()
	{
		return neurons;
	}
	
	/** Number of neurons (sum of input ,output and hidden neurons) */
	public int getSize()
	{
		return neurons.length;
	}
	
	public NeuronInterface getNeuron(int index)
	{
		return neurons[index];
	}
	
	public int[] getOutputNeurons()
	{
		return outputNeurons;
	}
	
	/**
	 * collects output of all neurons that are set as those which build output layer
	 * (their indexes are contained in array outputNeurons)
	 */
	public double[] getGeneratedOutput()
	{
		double[] i = new double[outputNeurons.length];
		
		for (int j = 0; j < i.length; j++)
		{// Adding values of hidden neurons
			i[j] = neurons[outputNeurons[j]].getOutput();
		}
		return i;
	}
	
	public BinaryDNA getGenetics()
	{
		return genetics;
	}
	
	public void setGenetics(BinaryDNA genetics)
	{
		this.genetics = genetics;
	}
	
	// ============Training methods=========
	/**
	 * @param input
	 *            -length must be equal to inputVectorSize
	 */
	public void trainHebbianNoTeacher(double... input)
	{
		if (input.length != inputVectorSize) throw new IllegalArgumentException("Input length must be equal to inputVectorSize");
		
		for (int i = 0; i < inputVectorSize; i++)
		{
			neurons[i].setOutput(input[i]);
		}
		
		for (int i = inputVectorSize; i < neurons.length; i++)
		{
			neurons[i].trainHebbianNoTeacher(neurons);
		}
	}
	
	// ============Other methods=========
	
	@Override
	public String toString()
	{
		String s = "";
		for (int i = 0, j = 0; i < neurons.length; i++)
		{
			s += "\n[" + i + "]" + neurons[i].getOutput();
			if (i < inputVectorSize) s += " I";
			if (j < outputNeurons.length && i == outputNeurons[j])
			{
				j++;
				s += " O";
			}
		}
		s += "\ntotal I =" + inputVectorSize + " total O =" + outputNeurons.length;
		return s;
	}
}
