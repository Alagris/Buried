package net.swing.src.obj;

import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;

public class CollAreaSolid extends CollArea
{
	
	public CollAreaSolid(String instruction)
	{
		super(instruction);
	}
	
	@Override
	public void run(Entity source, boolean wasAreaChanged)
	{
	}
	
	@Override
	public Action getAction()
	{
		return Action.SOLID;
	}
	
}
