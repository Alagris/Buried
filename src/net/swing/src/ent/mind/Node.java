package net.swing.src.ent.mind;

import net.swing.src.env.Cell;

public final class Node extends Cell
{
	
	private Cell	parent	= new Cell();
	
	public Node(int x, int y, int Px, int Py)
	{
		super(x, y);
		setParent(Px, Py);
	}
	
	public Node(int x, int y, Cell parent)
	{
		super(x, y);
		setParent(parent);
	}
	
	public Node(Cell parent)
	{
		setParent(parent);
	}
	
	public boolean isItTheLastNode()
	{
		return parent.equalsCell(this);
	}
	
	public Node(Cell nodeLocation, Cell parent)
	{
		this(nodeLocation, nodeLocation.x, nodeLocation.y);
	}
	
	public Node(int x, int y)
	{
		super(x, y);
	}
	
	public Node()
	{
	}
	
	public Node(Cell nodeLocation, int parentX, int parentY)
	{
		super(parentX, parentY);
		setParent(parent);
	}
	
	public Cell getParent()
	{
		return parent;
	}
	
	public void setParent(Cell parent)
	{
		this.parent.set(parent);
	}
	
	public void setParent(int x, int y)
	{
		this.parent.set(x, y);
	}
}
