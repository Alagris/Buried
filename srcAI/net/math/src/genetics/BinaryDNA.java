package net.math.src.genetics;

import java.util.BitSet;

public class BinaryDNA extends BitSet
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1026860141011735749L;
	
	public BinaryDNA()
	{
		super();
	}

	public BinaryDNA(int nbits)
	{
		super(nbits);
		setSequenceLength(nbits);
	}
	
	public BinaryDNA(BitSet genetics)
	{
		this(genetics.size());
		for (int i = 0; i < genetics.size(); i++)
		{
			set(i, genetics.get(i));
		}
	}
	public BinaryDNA(boolean...genetics)
	{
		this(genetics.length);
		for (int i = 0; i < genetics.length; i++)
		{
			set(i, genetics[i]);
		}
	}

	private int sequenceLength;

	public int getSequenceLength()
	{
		return sequenceLength;
	}

	public void setSequenceLength(int sequenceLength)
	{
		this.sequenceLength = sequenceLength;
	}
}
