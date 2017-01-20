package net.math.src.genetics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.BitSet;

import net.math.src.func.Function;
import net.math.src.neurons.InputNeuron;
import net.math.src.neurons.NeurogliaRecursive;
import net.math.src.neurons.Neuron;
import net.math.src.neurons.NeuronInterface;

public class GeneticCode
{
	
	// ==============Encoding data to file======================
	
	public static void writeToFile(BinaryDNA dna, File file) throws IOException
	{
		ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(file));
		s.writeObject(dna);
		s.close();
	}
	
	// ==============Decoding data from file====================
	
	public static BinaryDNA readFromFile(File file) throws IOException, ClassNotFoundException
	{
		ObjectInputStream s = new ObjectInputStream(new FileInputStream(file));
		Object o = s.readObject();
		s.close();
		if (o instanceof BinaryDNA)
		{
			return (BinaryDNA) o;
		}
		return null;
	}
	
	// ===================Parsing DNA===================
	
	/**
	 * DNA of recursive ANN works a bit different than for layered ANN.
	 * The general structure of DNA: <neuron1><neuron2><neuron3>...<neuron n>
	 * Structure of single <neuron> gene: sequence of n digits.
	 * Quantity of neurons = quantity of digits building one neuron.
	 * The total quantity of digits is: n^2 = n*n = quantity of neurons * quantity of digits building one neuron
	 * To calculate how many neurons are encoded just use:
	 * (int) (Math.sqrt(code.length()))
	 * Because max size of String is 2^31 there is also a limit of neurons:
	 * max n = 2^15 = 32768
	 * 
	 * Notice there are only digits 1 and 0 . Digit 2 is not necessary and will be treated as 0 (in fact everything that is not 1 is parsed as 0)
	 * 
	 * @param code
	 *            is e.g. 10100101101011010101010001010101110111010010
	 * 
	 * @param outputNeurons
	 *            -Array of neurons that generate output
	 * @param inputVectorSize
	 *            -quantity of neurons in the input layer (the first and only layer).
	 *            Input neurons are the only one that cannot receive input from any other neuron.
	 *            They are not even trained. They only keep value of output,
	 *            which is exactly the same as value of input received from environment.
	 * 
	 *            <pre>
	 * <b>IMPORTANT: the first x neurons are input neurons (x = inputVectorSize)
	 *             and all the neurons of indexes between x and 
	 *             x+n-1 inclusive are the hidden neurons. Indexes of input
	 *             neurons are between 0 and x-1 inclusive</b>
	 * </pre>
	 * 
	 * @throws LethalMutationException
	 */
	public static NeurogliaRecursive parseNetworkRecursive(BinaryDNA dna, double bias, Function func, int inputVectorSize, int[] outputNeurons)
	{
		// at first calculate number of neurons encrypted in DNA
		int hiddenAndOutputNeuronsNumber = (int) getEncryptedNeuronsNumber(dna.getSequenceLength(), inputVectorSize);
		int totalNeuronsNumber = hiddenAndOutputNeuronsNumber + inputVectorSize;
		// for every neuron from hidden layer is going to be parsed its set of dendrites
		// neurons from input layer cannot be connected to other neurons
		// neurons can be even connected to themselves (except input neurons)
		NeuronInterface[] neurons = new NeuronInterface[hiddenAndOutputNeuronsNumber];
		
		for (int currentNeuronIndex = 0; currentNeuronIndex < hiddenAndOutputNeuronsNumber; currentNeuronIndex++)
		{
			int[] dendrites = collectDendritesAtIntervalOfBinaryDNA(dna, currentNeuronIndex * totalNeuronsNumber, totalNeuronsNumber);
			if (dendrites.length < 1)
			{
				neurons[currentNeuronIndex] = new InputNeuron();
			}
			else
			{
				neurons[currentNeuronIndex] = new Neuron(bias, func, dendrites);
			}
		}
		
		// initialize neuroglia object with all given parameters
		NeurogliaRecursive neurogliaRecursive = new NeurogliaRecursive(neurons.length, inputVectorSize, outputNeurons, dna);
		for (int i = 0; i < neurons.length; i++)
		{
			neurogliaRecursive.setNeuron(i, neurons[i]);
		}
		
		// checking for lethal mutations
		if (inputVectorSize + neurons.length <= max(outputNeurons)) throw new LethalMutationException();
		
		return neurogliaRecursive;
	}
	
	private static int max(int[] array)
	{
		int max = Integer.MIN_VALUE;
		for (int i : array)
		{
			if (i > max) max = i;
		}
		return max;
	}
	
	/**
	 * @param DNAlenght
	 *            > inputNeuronsNumber>0
	 * @param inputNeuronsNumber
	 *            < DNAlenght
	 */
	public static double getEncryptedNeuronsNumber(int DNAlenght, int inputNeuronsNumber)
	{
		return Math.sqrt(DNAlenght + inputNeuronsNumber * inputNeuronsNumber / 4) - inputNeuronsNumber / 2;
	}
	
	/**
	 * Returns array with all indexes of neurons that dendrites of neuron (with is DNA code)
	 * will be connected to .
	 * e.g.
	 * {1,4,19}
	 * neuron with 3 dendrites that are connected to neurons of index 1,4 and 19
	 **/
	public static int[] parseNeuronIndexes(BitSet dna)
	{
		int[] indexes = new int[dna.cardinality()];
		for (int lastSetIndex = -1, j = 0; (lastSetIndex = dna.nextSetBit(lastSetIndex + 1)) != -1;)
		{
			indexes[j++] = lastSetIndex;
		}
		return indexes;
	}
	
	private static int[] collectDendritesAtIntervalOfBinaryDNA(BinaryDNA dna, int startIndex_including, int sizeOfInterval)
	{
		return parseNeuronIndexes(dna.get(startIndex_including, startIndex_including + sizeOfInterval));
	}
	
	// ===================Random Generating===================
	
	public static BinaryDNA generateRandomCode(int length)
	{
		BinaryDNA code = new BinaryDNA(length);
		for (int i = 0; i < length; i++)
		{
			code.set(i, Math.random() < 0.5);
		}
		return code;
	}
	
	// ===================Random Mutations===================
	
	// recombination with unlimited mutations number
	public static BinaryDNA recombinate(BinaryDNA genetics, double insertionMutationFrequency, double substitutionMutationFrequency)
	{
		BinaryDNA newBinaryDNA = (BinaryDNA) genetics.clone();
		while (Math.random() < substitutionMutationFrequency)
		{
			newBinaryDNA.set((int) (newBinaryDNA.getSequenceLength() * Math.random()), Math.random() > 0.5);
		}
		// insertion or deletion of a single bit
		if (Math.random() < insertionMutationFrequency)
		{
			int index = (int) (newBinaryDNA.getSequenceLength() * Math.random());
			
			if (Math.random() > 0.5)
			{
				System.out.println(index + " insert " + newBinaryDNA.getSequenceLength());
				insertBitAt(newBinaryDNA, index, Math.random() > 0.5);
			}
			else
			{
				System.out.println(index + " delete " + newBinaryDNA.getSequenceLength());
				deleteBitAt(newBinaryDNA, index);
			}
		}
		
		return newBinaryDNA;
	}
	
	public static BinaryDNA recombinate_sexLikeMethod(BinaryDNA parent1Genetics, BinaryDNA parent2Genetics, double crossingOverFrequency)
	{
		int max = Math.max(parent1Genetics.getSequenceLength(), parent2Genetics.getSequenceLength());
		BinaryDNA dna = new BinaryDNA(max);
		
		boolean takeFromParent1 = Math.random() > 0.5;
		
		for (int i = 0; i < max; i++)
		{
			dna.set(i, takeFromParent1 ? parent1Genetics.get(i) : parent2Genetics.get(i));
			if (Math.random() < crossingOverFrequency) takeFromParent1 = !takeFromParent1;
		}
		return dna;
	}
	
	public static void insertBitAt(BinaryDNA set, int index, boolean isBinaryDNAToTrue)
	{
		set.setSequenceLength(set.getSequenceLength() + 1);
		
		for (int i = set.getSequenceLength(); i > index; i--)
		{
			set.set(i, set.get(i - 1));
		}
		set.set(index, isBinaryDNAToTrue);
	}
	
	public static void deleteBitAt(BinaryDNA set, int index)
	{
		set.setSequenceLength(set.getSequenceLength() - 1);
		
		for (int i = index; i < set.getSequenceLength(); i++)
		{
			set.set(i, set.get(i + 1));
		}
		
	}
	
	public static int calculateLengthOfRecursiveNetworkDNA(int neuronsNumber, int inputNeuronsNumber)
	{
		return (inputNeuronsNumber + neuronsNumber) * neuronsNumber;
	}
	
	// ===========OLD ,NOT COMPATIBLE METHODS================================
	// @Deprecated
	// /**
	// * code is e.g. 10100101101012010101010201010101210111010010
	// *
	// * @throws LethalMutationException
	// */
	// public static NeurogliaLayered parseNetworkLayered(String code, int inputVectorSize, double bias, Function func)
	// {
	// // First layer consists of as many neurons as large the input vector and
	// // each neuron has only one dendrite that receives that input
	// NeuronsLayer firstLayer = new NeuronsLayer(inputVectorSize, bias, func, 1);
	//
	// ArrayList<String> parts = new ArrayList<String>();// collected layers
	// String currentString = "";// string with current layer
	// int i = 0;// counts how many digits are in current layer so far
	// int codonSize = inputVectorSize;// indicates how many digits build one neuron
	// int newCodonSize = 0;
	// int separatorsQuantity = 0;
	//
	// for (char c : code.toCharArray())
	// {
	// // checking if new codon is ready
	// if (i == codonSize)
	// {
	// if (currentString.contains("1"))
	// {
	// parts.add(currentString);// adding empty part...
	// newCodonSize++;
	// }
	// currentString = "";// ..and reseting collected layer
	// i = 0;// reseting digits count
	//
	// }
	// /** splits code to 1010010110101 010101010 01010101 10111010010 */
	// // digit '2' separates layers. If separated layer is too small it will be merged with the next one
	// if (c == '2' && newCodonSize > 1)
	// {
	// // Empty parts indicate end of layers
	// parts.add(" ");// adding empty part...
	// separatorsQuantity++;
	// currentString = "";// ..and reseting collected layer
	// i = 0;// reseting digits count
	// codonSize = newCodonSize;
	// newCodonSize = 0;
	// }
	// else if (c == '1' || c == '0')// digits 1 and 0 indicate dendrites
	// {
	// currentString += c;
	// i++;// raises when digit is added to current part
	// // if i has been just reseted it will make i equal 1 (because it's the first digit in the new codon)
	// }
	// }
	//
	// // checking for lethal mutations
	// if (parts.size() < 2) throw new LethalMutationException("Network has not enough layers");
	//
	// NeurogliaLayered neurogliaLayered = new NeurogliaLayered(separatorsQuantity + 1/* hidden layers */+ 1/* plus the input layer */);
	// // input layer doesn't have to be parsed. it's generated before loop
	// neurogliaLayered.putLayer(0, firstLayer);
	// i = 1;
	//
	// ArrayList<String> subArrayForSingleLayer = new ArrayList<String>();
	//
	// // loop that parses hidden layers
	// for (String part : parts)
	// {
	// if (part.equals(" "))
	// {// sub-arrays are separated by empty parts
	//
	// try
	// {
	// // layers 1 to neurogliaLayered.size()-1 are parsed here
	// neurogliaLayered.putLayer(i, parseLayer(subArrayForSingleLayer, inputVectorSize, bias, func));
	//
	// i++;
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// inputVectorSize = subArrayForSingleLayer.size();
	// subArrayForSingleLayer.clear();
	//
	// }
	// else
	// {
	// // parts of genetic information that encrypts single layer are packed in a sub-array
	// subArrayForSingleLayer.add(part);
	// }
	// }
	//
	// try
	// {// one final parsing of output layer occurs outside of loop
	// neurogliaLayered.putLayer(i, parseLayer(subArrayForSingleLayer, inputVectorSize, bias, func));
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	//
	// // returning network generated from genetics information
	// return neurogliaLayered;
	// }
	//
	// @Deprecated
	// /** code is e.g. the first part 1010010110101 */
	// private static NeuronsLayer parseLayer(ArrayList<String> neuronsCode, int inputVectorSize, double bias, Function func) throws Exception
	// {
	// Neuron[] neurons = new Neuron[neuronsCode.size()];
	// for (int i = 0; i < neurons.length; i++)
	// {
	// try
	// {
	// neurons[i] = new Neuron(Neuron.LEARNING_VELOCITY, bias, func, parseNeuronIndexes(neuronsCode.get(i)));
	// }
	// catch (IllegalArgumentException e)
	// {
	// throw new LethalMutationException("Network contains neurons without dendrites");
	// }
	// }
	//
	// return new NeuronsLayer(neurons);
	// }
	// @Deprecated
	// /**
	// * @param inputLayerlength
	// * @param outputLayerlength
	// * @param lengthOfOtherLayers
	// * @param frequancyOfLayerSeparators
	// * - has value between 0 and 1. The higher the more separators are created and the smaller layers are generated
	// */
	// public static String generateRandomCode(int inputLayerlength, int outputLayerlength, int lengthOfOtherLayers)
	// {
	// String code = "";
	// for (int i = 0; i < inputLayerlength; i++)
	// {
	// code += Math.random() < dendritesFrequency ? '1' : '0';
	// }
	// code += '2';
	// for (int i = 0; i < lengthOfOtherLayers; i++)
	// {
	// code += Math.random() < frequancyOfLayerSeparators ? '2' : (Math.random() < dendritesFrequency ? '1' : '0');
	// }
	// code += '2';
	// for (int i = 0; i < outputLayerlength; i++)
	// {
	// code += Math.random() < dendritesFrequency ? '1' : '0';
	// }
	// return code;
	// }
	//
	// public static String recombinate(String genetics)
	// {
	// // System.out.println("recombination start: " + genetics);
	// String newGenetics = "";
	// char[] c = genetics.toCharArray();
	// for (int i = 0; i < c.length; i++)
	// {
	// if (Math.random() < insertionMutationFrequency)
	// {
	// if (Math.random() > 0.5)
	// {
	// // gene is added
	// newGenetics += '0';
	// }
	// else
	// {
	// // gene is lost
	// continue;
	// }
	// }
	// newGenetics += Math.random() < substitutionMutationFrequency ? (Math.random() < frequancyOfLayerSeparators ? '2' : (Math.random() < dendritesFrequency ? '1' : '0')) : c[i];
	// }
	// // System.out.println("recombination  end : " + newGenetics);
	// return newGenetics;
	// }
	//
	// @Deprecated
	// public static Integer[] parseNeuronIndexes(String code)
	// {
	// ArrayList<Integer> indexes = new ArrayList<Integer>(code.length());
	//
	// for (int i = 0; i < code.length(); i++)
	// {
	// if (code.charAt(i) == '1')
	// {
	// indexes.add(i);
	// }
	// }
	// return indexes.toArray(new Integer[0]);
	// }
	//
}
