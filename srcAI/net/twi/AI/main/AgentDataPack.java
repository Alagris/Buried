package net.twi.AI.main;

import java.io.Serializable;

import net.math.src.genetics.BinaryDNA;

public class AgentDataPack implements Serializable
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8092601801121382978L;
	
	private double				beta;
	private int					generation;
	private BinaryDNA			genetics;
	
	public AgentDataPack(BinaryDNA genetics2, int generation2, double beta2)
	{
		setGenetics(genetics2);
		setGeneration(generation2);
		setBeta(beta2);
	}
	
	public double getBeta()
	{
		return beta;
	}
	
	public void setBeta(double beta)
	{
		this.beta = beta;
	}
	
	public int getGeneration()
	{
		return generation;
	}
	
	public void setGeneration(int generation)
	{
		this.generation = generation;
	}
	
	public BinaryDNA getGenetics()
	{
		return genetics;
	}
	
	public void setGenetics(BinaryDNA genetics)
	{
		this.genetics = genetics;
	}
}
