package net.swing.src.obj;

import net.LunarEngine2DMainClass;
import net.swing.engine.WPS;
import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;
import net.swing.src.ent.Living;
import net.swing.src.ent.Mind;
import net.swing.src.ent.Mind.DefaultMind;

public final class CollAreaSpawn extends CollArea
{
	private final Entity	objectForMakingInstances;
	private final Mind		mind;
	private final WPS		spawnLocation;
	
	public CollAreaSpawn(String instruction)
	{
		super(instruction);
		String[] s = instruction.split(" ");
		objectForMakingInstances = LunarEngine2DMainClass.getWorld().getEntities().parseEntity(s[0]);
		// TODO: add entity mind parser
		mind = new Mind(DefaultMind.MINDLESS);
		spawnLocation = WPS.parseCoordinates(s[1]);
	}
	
	@Override
	public Action getAction()
	{
		return Action.SPAWN;
	}
	
	@Override
	public void run(Entity source, boolean wasAreaChanged)
	{
		if (wasAreaChanged)
		{
			int i = LunarEngine2DMainClass.getRoom().getLivings().add(new Living(objectForMakingInstances, mind));
			LunarEngine2DMainClass.getRoom().getLivings().getEntityAtIndex(i).getEnt().set(spawnLocation);
		}
	}
	
}
