package net.swing.src.obj;

import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;

public class CollAreaAir extends CollArea
{
	
	public CollAreaAir(String instruction)
	{
		super(instruction);
	}
	
	@Override
	public void run(Entity e, boolean wasAreaChanged)
	{
	}
	
	@Override
	public Action getAction()
	{
		return Action.NULL;
	}
	
}
