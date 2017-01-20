package net.math.src.neurons;

import net.math.src.func.Function;

/**
 * Meaning of shorts:
 * 
 * <pre>
 * - d -
 * </pre>
 * 
 * correct (desired) output
 * 
 * <pre>
 * - Q -
 * </pre>
 * 
 * error. Formula for calculating it may depend on neuron model
 * 
 * <pre>
 * - s -
 * </pre>
 * 
 * sum of weights multiplied by input s = sum(X*W)
 * 
 * <pre>
 * - n -
 * </pre>
 * 
 * constant that indicates speed of learning
 * 
 * <pre>
 * - y -
 * </pre>
 * 
 * output of neuron y = func(s)
 * 
 * <pre>
 * - x -
 * </pre>
 * 
 * single input
 * 
 * <pre>
 * - X -
 * </pre>
 * 
 * vector (in java it's just array) of inputs
 * 
 * <pre>
 * - w -
 * </pre>
 * 
 * single weight
 * 
 * <pre>
 * - W -
 * </pre>
 * 
 * vector (in java it's just array) of weights
 * 
 * <pre>=
 * =========================
 * </pre>
 * 
 * 'Evolution' of neurons:
 * 
 * <pre>
 * - Neuron -
 * </pre>
 * 
 * getS() , getY() , getW() ,activate() , train()
 * 
 * <pre>
 * - Perceptron -
 * </pre>
 * 
 * adds getBias() and extends getS() (s = sum(XW)+bias )
 * 
 * <pre>
 * - Adaline -
 * </pre>
 * 
 * adds getN() and getE()
 */
public class Neuron implements NeuronInterface
{
	
	// ====================Variables=============================
	
	private double[]			W;
	/** Neuron activation function */
	private Function			function;
	/** learning velocity constant */
	private double				n					= LEARNING_VELOCITY;
	
	public static final double	LEARNING_VELOCITY	= 0.01;
	private double				bias;
	/** indexes of neurons from previous layer that are connected to this neuron */
	private int[]				inputs;
	/** Last generated output */
	private double				output;
	private double				s;
	
	// ====================Constructors=============================
	
	/**
	 * 
	 * @param func
	 *            -
	 * 
	 *            <pre>
	 * <b>for perceptron </b>- should be polar heaviside step function
	 * </pre>
	 * 
	 *            <pre>
	 * <b>for Hebbian </b>- should be bipolar (1 / -1) heaviside step function
	 * </pre>
	 */
	public Neuron(double bias, Function func, double... W)
	{
		// checking for errors
		if (func == null || W == null)
		{
			throw new NullPointerException();
		}
		if (W.length == 0)
		{
			throw new IllegalArgumentException("Weights array is empty");
		}
		
		// setting weights
		setWeights(W);
		
		sharedConstructor(n, bias, func);
	}
	
	/**
	 * 
	 * @param func
	 *            -
	 * 
	 *            <pre>
	 * <b>for perceptron </b>- should be polar heaviside step function
	 * </pre>
	 * 
	 *            <pre>
	 * <b>for Hebbian </b>- should be bipolar (1 / -1) heaviside step function
	 * </pre>
	 */
	public Neuron(double n, double bias, Function func, double... W)
	{
		// checking for errors
		if (func == null || W == null)
		{
			throw new NullPointerException();
		}
		if (W.length == 0)
		{
			throw new IllegalArgumentException("Weights array is empty");
		}
		
		// setting weights
		setWeights(W);
		
		sharedConstructor(n, bias, func);
	}
	
	public Neuron(double n, double bias, Function func, int inputsQuantity)
	{
		// checking for errors
		if (func == null)
		{
			throw new NullPointerException();
		}
		if (inputsQuantity < 1)
		{
			throw new IllegalArgumentException("Input quantity is lower than 1");
		}
		
		// Filling weights array with random values
		
		W = new double[inputsQuantity];
		
		for (int i = 0; i < W.length; i++)
		{
			W[i] = Math.random();
		}
		
		sharedConstructor(n, bias, func);
	}
	
	private void sharedConstructor(double n, double bias, Function func)
	{
		// setting function
		setFunction(func);
		
		// setting bias
		setBias(bias);
		
		// setting learning velocity constant
		setN(n);
	}
	
	public Neuron(double n, double bias, Function func, int[] inputs, double[] W)
	{
		this(n, bias, func, W);
		
		if (W.length != inputs.length)
		{
			throw new IllegalArgumentException("Quantity of weights must be the same as quantity of dendrites");
		}
		setInputs(inputs);
	}
	
	public Neuron(double n, double bias, Function func, int[] inputs)
	{
		this(n, bias, func, inputs.length);
		setInputs(inputs);
	}
	
	public Neuron(double bias, Function func, int[] inputs)
	{
		this(LEARNING_VELOCITY, bias, func, inputs.length);
		setInputs(inputs);
	}
	
	// ====================Setters and getters=============================
	
	public double getS()
	{
		return s;
	}
	
	@Override
	public int[] getInputs()
	{
		return inputs;
	}
	
	@Override
	public void setInputs(int[] inputs)
	{
		this.inputs = inputs;
	}
	
	@Override
	public int getInputsQuantity()
	{
		return inputs.length;
	}
	
	@Override
	public double getOutput()
	{
		return output;
	}
	
	@Override
	public void setOutput(double output)
	{
		this.output = output;
	}
	
	/** This variable indicates speed of weights change */
	@Override
	public double getN()
	{
		return n;
	}
	
	/** This variable indicates speed of weights change */
	@Override
	public void setN(double n)
	{
		if (n < 0)
		{
			throw new IllegalArgumentException("N must be positive");
		}
		this.n = n;
	}
	
	@Override
	public double[] getW()
	{
		return W;
	}
	
	@Override
	public void setWeights(double[] W)
	{
		this.W = W;
	}
	
	/** Neuron activation function */
	@Override
	public Function getFunction()
	{
		return function;
	}
	
	/** Neuron activation function */
	@Override
	public void setFunction(Function function)
	{
		this.function = function;
	}
	
	@Override
	public double getBias()
	{
		return bias;
	}
	
	@Override
	public void setBias(double bias)
	{
		this.bias = bias;
	}
	
	@Override
	public String getWeightsString()
	{
		String s = "";
		for (int i = 0; i < W.length; i++)
		{
			s = s + "[" + i + "]=" + W[i] + " ";
		}
		return "bias= " + getBias() + " w: " + s;
	}
	
	@Override
	public String getInputsString()
	{
		if (inputs == null) return "inputs null";
		String s = "";
		for (int i = 0; i < inputs.length; i++)
		{
			s = s + "[" + i + "]=" + inputs[i] + " ";
		}
		return "inputs: " + s;
	}
	
	@Override
	public String toString()
	{
		return "Neuron {" + getWeightsString() + "} OUTPUT:" + output + "{" + getInputsString() + "}";
	}
	
	// ================Neuron calculation methods=======================
	
	private void changeBias_useN(double Q, double n)
	{
		bias += n * Q * bias;
	}
	
	/**
	 * @param i
	 *            - index of changed neuron dendrite
	 * @param x
	 *            - input at index i
	 */
	private void changeWeight_useN(int i, double Q, double n, double x)
	{
		W[i] += n * Q * x;
	}
	
	/**
	 * w = w + n*Q*x
	 * Bias should not be changed but if you really need to change that just use changeBias_useN()
	 */
	private void changeAllWeights_useN(double Q, double n, double... inputs)
	{
		for (int i = 0; i < inputs.length; i++)
		{
			changeWeight_useN(i, Q, n, inputs[i]);
		}
		changeBias_useN(Q, n);
	}
	
	/** w = w + n*Q*x */
	private void changeAllWeights_useN(double Q, double n, NeuronInterface[] previousLayer)
	{
		for (int i = 0; i < inputs.length; i++)
		{
			changeWeight_useN(i, Q, n, previousLayer[inputs[i]].getOutput());
		}
		changeBias_useN(Q, n);
	}
	
	private void changeBias_useN_useFunction(double Q, double n, Function f)
	{
		bias = f.doFunction(bias + n * Q * bias);
	}
	
	/**
	 * @param i
	 *            - index of changed neuron dendrite
	 * @param x
	 *            - input at index i
	 * @param f
	 */
	private void changeWeight_useN_useFunction(int i, double Q, double n, double x, Function f)
	{
		W[i] = f.doFunction(W[i] + n * Q * x);
	}
	
	/** w = f(w + n*Q*x) */
	private void changeAllWeights_useN_useFunction(double Q, double n, NeuronInterface[] previousLayer, Function f)
	{
		for (int i = 0; i < inputs.length; i++)
		{
			changeWeight_useN_useFunction(i, Q, n, previousLayer[inputs[i]].getOutput(), f);
		}
		changeBias_useN_useFunction(Q, n, f);
	}
	
	/** w = w + Q*x */
	private void changeAllWeights_ignoreN(double Q, double... inputs)
	{
		// when n = 1 everything works as there was no n at all.
		changeAllWeights_useN(Q, 1, inputs);
	}
	
	/***/
	private double getY(double s)
	{
		return function.doFunction(s);
	}
	
	/**
	 * Returns error (also called delta)
	 * 
	 * @param d
	 *            - correct output
	 * @param s
	 *            - multiplication of input vector(array) and weight
	 *            vector(array)
	 */
	private double getQ(double d, double s)
	{
		return d - s;
	}
	
	/**
	 * s = sum(X*W)
	 * 
	 * @param X
	 *            - input vector(array)
	 * @param W
	 *            - weights vector(array)
	 */
	private void calculateS(double[] X, double[] W)
	{
		s = calculateS_withoutBias(X, W) + bias;
	}
	
	/**
	 * s = sum(X*W)
	 * 
	 * @param X
	 *            - input vector(array)
	 * @param W
	 *            - weights vector(array)
	 */
	private double calculateS_withoutBias(double[] X, double[] W)
	{
		double sum = 0;
		for (int i = 0; i < W.length; i++)
		{
			sum += X[i] * W[i];
		}
		return sum;
	}
	
	/**
	 * s = sum(X*W)
	 * 
	 * @param X
	 *            - input vector(array)
	 * @param W
	 *            - weights vector(array)
	 */
	private void calculateS(NeuronInterface[] previousLayer, double[] W)
	{
		s = calculateS_withoutBias(previousLayer, W) + bias;
	}
	
	/**
	 * s = sum(X*W)
	 * 
	 * @param X
	 *            - input vector(array)
	 * @param W
	 *            - weights vector(array)
	 */
	private double calculateS_withoutBias(NeuronInterface[] previousLayer, double[] W)
	{
		double sum = 0;
		for (int i = 0; i < inputs.length; i++)
		{
			sum += previousLayer[inputs[i]].getOutput() * W[i];
		}
		return sum;
	}
	
	// ====================Training methods=============================
	
	/**
	 * Quantity of inputs must be equal to quantity of weights. Use it to design
	 * fixed networks.
	 */
	@Override
	public double activateNeuron(double... X)
	{
		if (X.length == W.length)
		{
			calculateS(X, W);
			return (output = getY(getS()));
		}
		else
		{
			System.err.println("Inputs[" + X.length + "] != Weights[" + W.length + "]");
			throw new IllegalArgumentException("Input vector must be the same size as weights vector!");
		}
	}
	
	/**
	 * Trains this neuron as a perceptron.
	 * 
	 * @param d
	 *            - f(sum(XW)) - correct output of neuron .Perceptron's output
	 *            must be -1 or 1 for bipolar function or 0 or 1 for unipolar
	 *            function
	 * @return Returns y
	 */
	@Override
	public double trainPerceptron(double d, double... X)
	{
		if (X.length == W.length)
		{
			calculateS(X, getW());
			double y = getY(getS());
			
			if (y != d)
			{/*
			 * this is not really necessary because if output = correctOutput
			 * the error is 0 and nothing will change but stopping algorithm
			 * here could save a lot of unnecessary calculation
			 */
				changeAllWeights_ignoreN(d, X);
			}
			return (output = y);
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * @param d
	 *            - correct s value = sum(X*W) - sum of inputs multiplied by
	 *            their weights
	 */
	@Override
	public double trainAdaline(double d, double... X)
	{
		if (X.length == W.length)
		{
			calculateS(X, getW());
			
			if (getS() != d)
			{/*
			 * this is not really necessary because if output = correctOutput
			 * the error is 0 and nothing will change but stopping algorithm
			 * here could save a lot of unnecessary calculation
			 */
				// w = w + n*Q*x
				changeAllWeights_useN(getQ(d, getS()), getN(), X);
			}
			return (output = getS());
		}
		else
		{
			throw new IllegalArgumentException("Input vector must be the same size as weights vector!");
		}
	}
	
	@Override
	public double trainHebbianWithTeacher(double d, double... X)
	{
		if (X.length == W.length)
		{
			calculateS(X, getW());
			double y = getY(getS());
			
			/*
			 * In this algorithm error is not calculated. Weights are updated
			 * similarly but instead of using Q it uses d
			 */
			// w = w + n*d*x
			changeAllWeights_useN(d, getN(), X);
			return (output = y);
		}
		else
		{
			throw new IllegalArgumentException("Input vector must be the same size as weights vector!");
		}
	}
	
	@Override
	public double trainHebbianNoTeacher(NeuronInterface[] previousLayer)
	{
		return trainHebbianNoTeacher_v5(previousLayer);
	}
	
	private double trainHebbianNoTeacher_v1(NeuronInterface[] previousLayer)
	{
		
		calculateS(previousLayer, getW());
		double y = getY(getS());
		
		/*
		 * In this algorithm error is not calculated. Weights are updated
		 * similarly but instead of using Q it uses y
		 */
		// w = w + n*y*x
		changeAllWeights_useN(y, getN(), previousLayer);
		
		return (output = y);
	}
	
	private double trainHebbianNoTeacher_v2(NeuronInterface[] previousLayer)
	{
		
		calculateS(previousLayer, getW());
		
		/*
		 * In this algorithm error is not calculated. Weights are updated
		 * similarly but instead of using Q it uses a=s/number of neurons
		 * (a is average)
		 */
		// w = f(w + n*a*x)
		changeAllWeights_useN_useFunction(getS() / previousLayer.length, getN(), previousLayer, function);
		return (output = getY(getS()));
	}
	
	private double trainHebbianNoTeacher_v3(NeuronInterface[] previousLayer)
	{
		
		calculateS(previousLayer, getW());
		
		/*
		 * In this algorithm error is not calculated. Weights are updated
		 * similarly but instead of using Q it uses a=s/number of neurons
		 * (a is average)
		 */
		// w += n*a*x
		
		double a = getS() / previousLayer.length;
		for (int i = 0; i < inputs.length; i++)
		{
			changeWeight_useN(i, a, getN(), previousLayer[inputs[i]].getOutput());
			W[i] = (W[i] < -1) ? (-1) : (W[i] > 1 ? 1 : W[i]);
		}
		changeBias_useN(a, getN());
		bias = (bias < -1) ? (-1) : (bias > 1 ? 1 : bias);
		return (output = getY(getS()));
	}
	
	private double trainHebbianNoTeacher_v4(NeuronInterface[] previousLayer)
	{
		
		calculateS(previousLayer, getW());
		
		// a = S/number of neurons
		// w += x*a*(1-w) when w increases (Long-Term Potentiation)
		// w += x*a*w when w decreases (Long-Term Depression)
		// 0<=x<=1
		// 0<w<1
		// notice that this method doesn't use neither N,bias nor beta
		double a = getS() / previousLayer.length;
		
		for (int i = 0; i < inputs.length; i++)
		{
			double x = previousLayer[inputs[i]].getOutput();
			if (a * x < 0)
			{
				// System.out.print("(xaw=" + (x * a * W[i]) + ") (x=" + x + " a=" + a + " w=" + W[i]);
				W[i] += x * a * W[i];
				// System.out.println(") (w =" + W[i] + ") ");
			}
			else
			{
				// System.out.print("(xa[1-w]=" + (x * a * W[i]) + ") (x=" + x + " a=" + a + " w=" + W[i]);
				W[i] += x * a * (1 - W[i]);
				// System.out.println(") (w =" + W[i] + ") ");
			}
		}
		return (output = a);
	}
	
	private double trainHebbianNoTeacher_v5(NeuronInterface[] previousLayer)
	{
		
		calculateS(previousLayer, getW());
		
		// a = S/number of neurons
		// w += x*a*(1-w) when w increases (Long-Term Potentiation)
		// w += x*a*w when w decreases (Long-Term Depression)
		// -1<=x<=1
		// 0<w<1
		// notice that this method doesn't use neither N,bias nor beta
		double a = getS() / previousLayer.length;
		
		for (int i = 0; i < inputs.length; i++)
		{
			double x = previousLayer[inputs[i]].getOutput();
			if (a * x < 0)
			{
				// System.out.print("(xaw=" + (x * a * W[i]) + ") (x=" + x + " a=" + a + " w=" + W[i]);
				W[i] += x * a * W[i];
				// System.out.println(") (w =" + W[i] + ") ");
			}
			else
			{
				// System.out.print("(xa[1-w]=" + (x * a * W[i]) + ") (x=" + x + " a=" + a + " w=" + W[i]);
				W[i] += x * a * (1 - W[i]);
				// System.out.println(") (w =" + W[i] + ") ");
			}
		}
		return (output = a);
	}
}
