package net.swing.src.obj;

import java.util.ArrayList;

import net.swing.src.ent.Entity;

public class StackOfCollAreas extends ArrayList<CollArea>
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/** Default capacity is 3 */
	public StackOfCollAreas()
	{
		super(3);
	}
	
	public StackOfCollAreas(CollArea initialArea)
	{
		super(1);
		add(initialArea);
	}
	
	public void run(Entity source, boolean wasAreaChanged)
	{
		for (CollArea a : toCollAreaArray())
		{
			a.run(source, wasAreaChanged);
		}
	}
	
	public CollArea[] toCollAreaArray()
	{
		return toArray(new CollArea[0]);
	}
}
