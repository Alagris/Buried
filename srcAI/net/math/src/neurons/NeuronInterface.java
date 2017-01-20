package net.math.src.neurons;

import net.math.src.func.Function;

public interface NeuronInterface
{
	
	double trainAdaline(double d, double... X);
	
	double trainHebbianNoTeacher(NeuronInterface[] previousLayer);
	
	double trainHebbianWithTeacher(double d, double... X);
	
	double trainPerceptron(double d, double... X);
	
	double activateNeuron(double... X);
	
	double getN();
	
	void setN(double n);
	
	double[] getW();
	
	void setWeights(double[] W);
	
	Function getFunction();
	
	void setFunction(Function function);
	
	void setBias(double bias);
	
	String getWeightsString();
	
	double getBias();
	
	String getInputsString();
	
	void setOutput(double output);
	
	double getOutput();
	
	int getInputsQuantity();
	
	void setInputs(int[] inputs);
	
	int[] getInputs();
	
}
