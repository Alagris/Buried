package net.swing.src.ent.mind;

import java.util.ArrayList;

import net.swing.src.env.Cell;
import net.swing.src.env.Matrix;

public final class NodesMatrix
{
	/**
	 * Array of IDs of blocks (saving only numbers wastes much less memory than
	 * saving whole {@link CollArea} instance)
	 */
	protected Node[]	blocks;
	/** rows and columns */
	protected int		r, c;
	
	/**
	 * Creation of empty room (everything is air)
	 * 
	 * @param columns
	 * @param rows
	 */
	public NodesMatrix(int rows, int columns)
	{
		this.r = rows;
		this.c = columns;
		blocks = new Node[r * c];
		
	}
	
	/** Creation of room filled with blocks from an array */
	public NodesMatrix(int rows, int columns, Node[] IDsToFillMatrix)
	{
		blocks = new Node[IDsToFillMatrix.length];
		this.r = rows;
		this.c = columns;
		for (int i = 0; i < blocks.length; i++)
		{
			blocks[i] = IDsToFillMatrix[i];
		}
	}
	
	public NodesMatrix(Matrix matrix)
	{
		this.r = matrix.getRows();
		this.c = matrix.getColumns();
		blocks = new Node[r * c];
	}
	
	/** Returns ID of block which is pointed by cell */
	public Node getNode(Cell location) throws IndexOutOfBoundsException
	{
		return blocks[getIndex(location)];
	}
	
	/** Returns ID of block which is pointed by cell */
	public Node getNode(int x, int y) throws IndexOutOfBoundsException
	{
		return blocks[getIndex(x, y)];
	}
	
	/** Returns ID of block which is pointed by cell */
	public Node getNode(int index) throws IndexOutOfBoundsException
	{
		return blocks[index];
	}
	
	/** Returns index which is pointed by cell */
	public int getIndex(int x, int y)
	{
		return (y - 1) * c + x - 1; // This line calculates index
	}
	
	/** Returns index which is pointed by cell */
	public int getIndex(Cell location)
	{
		return getIndex(location.x, location.y);
	}
	
	public Node[] getNodesArray()
	{
		return blocks;
	}
	
	public void putBlock(Cell cell, Node node)
	{
		try
		{
			blocks[getIndex(cell)] = node;
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
	}
	
	/** true if cell is valid */
	public boolean validateCell(Cell arg0)
	{
		return validateCell(arg0.x, arg0.y);
	}
	
	/** true if cell is valid */
	public boolean validateCell(int x, int y)
	{
		if (x > 0) if (x <= c) if (y > 0) if (y < r)
		{
			return true;
		}
		
		return false;
	}
	
	public int size()
	{
		return blocks.length;
	}
	
	public Cell[] trackPath(Cell location)
	{
		return trackPath(location.x, location.y);
	}
	
	public Cell[] trackPath(int x, int y)
	{
		return trackPath(getIndex(x, y));
	}
	
	public Cell[] trackPath(int index)
	{
		ArrayList<Cell> path = new ArrayList<Cell>(r * c / 3);
		Node n = getNode(index);
		if (n == null)
		{
			return new Cell[0];
		}
		while (true)
		{
			if (n.isItTheLastNode())
			{
				break;
			}
			else
			{
				path.add(n);
				n = getNode(n.getParent());
			}
		}
		return path.toArray(new Cell[0]);
	}
	
}
