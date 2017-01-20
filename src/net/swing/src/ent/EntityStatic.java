package net.swing.src.ent;

import net.swing.src.ent.mind.AI;
import net.swing.src.env.Room;

public abstract class EntityStatic extends Entity
{
	
	protected EntityStatic(int sizeInAreas, String name)
	{
		super(sizeInAreas, name);
		state = EntState.FROZEN;
	}
	
	protected EntityStatic(EntState s, int sizeInAreas, String name)
	{
		super(sizeInAreas, name);
		state = s;
	}
	
	@Override
	public void update(Mind program, Room r)
	{
		super.update(program, r);
	}
	
	@Override
	public AI getAI()
	{
		return null;
	}
	
}
