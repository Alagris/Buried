package net.twi.AI.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.annotation.PostConstruct;

import net.math.src.func.FunctionSigmoidBipolar;
import net.math.src.genetics.BinaryDNA;
import net.math.src.genetics.GeneticCode;
import net.math.src.genetics.LethalMutationException;
import net.math.src.neurons.NeurogliaRecursive;
import net.swing.ground.Graphics;
import net.swing.src.env.Cell;
import net.swing.src.env.CellSelector;
import net.swing.src.env.Matrix;
import static net.twi.AI.main.R.*;

public class Agent extends Graphics
{
	
	private static final double DEFAULT_BIAS = 0;

	private NeurogliaRecursive	neuroglia;
	
	private int					collectedFood	= 0;
	private long				hungerTime		= 0;
	private double				beta			= 0;
	private int					generation		= 0;
	private final Cell[]		viewArea		= new Cell[21];
	
	public Agent(BinaryDNA geneticInformation, int generation, double beta, int[] outputNeurons)
	{
		constructor(geneticInformation, generation, beta, outputNeurons);
	}
	
	public Agent(File dataFile, int[] outputNeurons) throws LethalMutationException
	{
		try
		{
			AgentDataPack data = readFromFile(dataFile);
			constructor(data.getGenetics(), data.getGeneration(), data.getBeta(), outputNeurons);
		}
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	private void constructor(BinaryDNA geneticInformation, int generation, double beta, int[] outputNeurons)
	{
		setSize(CELL_WIDTH, CELL_HEIGHT);
		setLocation(WIDTH * (float) Math.random(), HEIGHT * (float) Math.random());
		setColor(1, 0, 0);
		setNeuroglia(GeneticCode.parseNetworkRecursive(geneticInformation, DEFAULT_BIAS, new FunctionSigmoidBipolar(beta), viewArea.length, outputNeurons));
		setGeneration(generation);
		setBeta(beta);
	}
	
	public AgentDataPack packCurrentData()
	{
		return new AgentDataPack(getGenetics(), getGeneration(), getBeta());
	}
	
	public void update(CellSelector blockSelector, Matrix matrix, int w, int h)
	{
		updateNoRender(blockSelector, matrix, w, h);
		render();
	}
	
	public void updateNoRender(CellSelector blockSelector, Matrix matrix, int w, int h)
	{
		hungerTime++;
		
		getCellsAround(blockSelector.getCell(getCenterLocation()));
		think(matrix, viewArea);
		
		if (getX() > w-10)
		{
			setX(20);
		}
		else if (getX() < 10)
		{
			setX(w-20);
		}
		if (getY() > h-10)
		{
			setY(20);
		}
		else if (getY() < 10)
		{
			setY(h-20);
		}
	}
	
	private void think(Matrix matrix, Cell[] c)
	{
		
		if (matrix.validateCell(c[0]) && matrix.getID(c[0]) == foodCellSign)
		{
			collectedFood++;
			matrix.putBlock(emptyCellSign, c[0]);
			hungerTime = 0;
		}
		neuroglia.trainHebbianNoTeacher(convertCellsIntoBinaryInput(matrix, c));
		
		Dir dir = Dir.UP;
		
		for (int n : neuroglia.getOutputNeurons())
		{
			switch (dir)
			{
				case DOWN:
					setY((float) (getY() - neuroglia.getNeuron(n).getOutput() * SPEED));
					dir = Dir.LEFT;
					break;
				case LEFT:
					setX((float) (getX() - neuroglia.getNeuron(n).getOutput() * SPEED));
					dir = Dir.RIGHT;
					break;
				case RIGHT:
					setX((float) (getX() + neuroglia.getNeuron(n).getOutput() * SPEED));
					dir = Dir.UP;
					break;
				case UP:
					setY((float) (getY() + neuroglia.getNeuron(n).getOutput() * SPEED));
					dir = Dir.DOWN;
					break;
			
			}
		}
	}
	
	public static final double	SPEED	= 3;
	
	private enum Dir
	{
		LEFT, UP, DOWN, RIGHT;
	}
	
	private double[] convertCellsIntoBinaryInput(Matrix matrix, Cell[] c)
	{
		double[] d = new double[c.length];
		for (int i = 0; i < d.length; i++)
		{
			try
			{
				d[i] = matrix.getID(c[i]);
			}
			catch (IndexOutOfBoundsException e)
			{
				d[i] = -1;
			}
		}
		
		return d;
	}
	
	private void getCellsAround(Cell c)
	{
		if (c.equalsCell(viewArea[0])) return;
		// Hard-coded but really fast
		// center cell
		viewArea[0] = c;
		// first layer
		viewArea[1] = new Cell(c.x - 1, c.y);
		viewArea[2] = new Cell(c.x - 2, c.y);
		viewArea[3] = new Cell(c.x + 1, c.y);
		viewArea[4] = new Cell(c.x + 2, c.y);
		// second bottom layer
		viewArea[5] = new Cell(c.x - 1, c.y - 1);
		viewArea[6] = new Cell(c.x - 2, c.y - 1);
		viewArea[7] = new Cell(c.x + 1, c.y - 1);
		viewArea[8] = new Cell(c.x + 2, c.y - 1);
		viewArea[9] = new Cell(c.x, c.y - 1);
		// second top layer
		viewArea[10] = new Cell(c.x - 1, c.y + 1);
		viewArea[11] = new Cell(c.x - 2, c.y + 1);
		viewArea[12] = new Cell(c.x + 1, c.y + 1);
		viewArea[13] = new Cell(c.x + 2, c.y + 1);
		viewArea[14] = new Cell(c.x, c.y + 1);
		// third bottom layer
		viewArea[15] = new Cell(c.x - 1, c.y + 2);
		viewArea[16] = new Cell(c.x, c.y + 2);
		viewArea[17] = new Cell(c.x + 1, c.y + 2);
		// third top layer
		viewArea[18] = new Cell(c.x - 1, c.y - 2);
		viewArea[19] = new Cell(c.x, c.y - 2);
		viewArea[20] = new Cell(c.x + 1, c.y - 2);
		
	}
	
	public void render()
	{
		renderRectangle();
	}
	
	public NeurogliaRecursive getNeuroglia()
	{
		return neuroglia;
	}
	
	public void setNeuroglia(NeurogliaRecursive neuroglia)
	{
		this.neuroglia = neuroglia;
	}
	
	public int getCollectedFood()
	{
		return collectedFood;
	}
	
	public void setCollectedFood(int collectedFood)
	{
		this.collectedFood = collectedFood;
	}
	
	public long getLifeTime()
	{
		return hungerTime;
	}
	
	public void setLifeTime(long lifeTime)
	{
		this.hungerTime = lifeTime;
	}
	
	public void takeCollectedFood(int childcost)
	{
		collectedFood -= childcost;
	}
	
	public BinaryDNA getGenetics()
	{
		return neuroglia.getGenetics();
	}
	
	public void setGenetics(BinaryDNA genetics)
	{
		neuroglia.setGenetics(genetics);
	}
	
	public int getGeneration()
	{
		return generation;
	}
	
	public void setGeneration(int generation)
	{
		this.generation = generation;
	}
	
	public double getBeta()
	{
		return beta;
	}
	
	public void setBeta(double beta)
	{
		this.beta = beta;
	}
	
	public long getHungerTime()
	{
		return hungerTime;
	}
	
	public void setHungerTime(long hungerTime)
	{
		this.hungerTime = hungerTime;
	}
	
	@Override
	public String toString()
	{
		return "Agent{ beta:" + getBeta() + " genetarion:" + getGeneration() + " ht:" + getHungerTime() + " food: " + getCollectedFood() + "}" + super.toString();
	}
	
	// ==============Encoding data to file======================
	
	public void writeToFile(File file) throws IOException
	{
		if (!file.exists()) file.createNewFile();
		ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(file));
		s.writeObject(packCurrentData());
		s.close();
	}
	
	// ==============Decoding data from file====================
	
	private AgentDataPack readFromFile(File file) throws IOException, ClassNotFoundException
	{
		ObjectInputStream s = new ObjectInputStream(new FileInputStream(file));
		Object o = s.readObject();
		s.close();
		if (o instanceof AgentDataPack)
		{
			return (AgentDataPack) o;
		}
		return null;
	}
	
}
