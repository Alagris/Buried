package net.math.src.neurons;

import net.math.src.func.Function;

public class InputNeuron implements NeuronInterface
{
	
	/** Last generated output */
	private double	output;
	
	public InputNeuron()
	{
	}
	
	@Override
	public double trainAdaline(double d, double... X)
	{
		return 0;
	}
	
	@Override
	public double trainHebbianNoTeacher(NeuronInterface[] previousLayer)
	{
		return 0;
	}
	
	@Override
	public double trainHebbianWithTeacher(double d, double... X)
	{
		return 0;
	}
	
	@Override
	public double trainPerceptron(double d, double... X)
	{
		return 0;
	}
	
	@Override
	public double activateNeuron(double... X)
	{
		return 0;
	}
	
	@Override
	public String toString()
	{
		return "Input neuron {current output= " + getOutput() + "}";
	}
	
	@Override
	public String getWeightsString()
	{
		return "Input neuron {current output= " + getOutput() + "}";
	}
	
	@Override
	public String getInputsString()
	{
		return "Input neuron {current output= " + getOutput() + "}";
	}
	
	@Override
	public double getN()
	{
		return 0;
	}
	
	@Override
	public void setN(double n)
	{
	}
	
	@Override
	public double[] getW()
	{
		return null;
	}
	
	@Override
	public void setWeights(double[] W)
	{
	}
	
	@Override
	public Function getFunction()
	{
		return null;
	}
	
	@Override
	public void setFunction(Function function)
	{
	}
	
	@Override
	public void setBias(double bias)
	{
	}
	
	@Override
	public double getBias()
	{
		return 0;
	}
	
	@Override
	public void setOutput(double output)
	{
		this.output = output;
	}
	
	@Override
	public double getOutput()
	{
		return output;
	}
	
	@Override
	public int getInputsQuantity()
	{
		return 0;
	}
	
	@Override
	public void setInputs(int[] inputs)
	{
	}
	
	@Override
	public int[] getInputs()
	{
		return null;
	}
}
