package net.swing.src.obj;

import net.LunarEngine2DMainClass;
import net.swing.engine.WPS;
import net.swing.src.data.QuickRoomsManager;
import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;
import net.swing.src.ent.Entity.EntState;

public final class CollAreaTeleported extends CollArea
{
	
	private final WPS		destination;
	private final String	room;
	
	public CollAreaTeleported(String instruction)
	{
		super(instruction);
		if (instruction.contains(" "))
		{
			String[] s = instruction.split(" ");
			if (QuickRoomsManager.existsRoom(s[1],LunarEngine2DMainClass.getOriginSave()))
			{
				room = s[1];
			}
			else
			{
				System.err.println("Room " + s[1] + " doesn't exist!");
				room = null;
			}
			destination = WPS.parseCoordinates(s[0]);
		}
		else
		{
			room = null;
			destination = WPS.parseCoordinates(instruction);
		}
	}
	
	@Override
	public Action getAction()
	{
		return Action.TELEPORT;
	}
	
	@Override
	public void run(Entity ent, boolean wasAreaChanged)
	{
		if (wasAreaChanged)
		{
			if (room == null)
			{
				ent.set(destination);
			}
			else
			{
				if (ent.isTeleportationAllowed())
				{
					LunarEngine2DMainClass.getScreen().getGameState().getGameView().getRoom().getLivings().removeEntity(ent.index);
					LunarEngine2DMainClass.getScreen().getGameState().getGameView().loadRoom(room, true);
					LunarEngine2DMainClass.getScreen().getGameState().getGameView().getRoom().getLivings().addThisInstance(ent);
				}
				else
				{
					ent.setState(EntState.VANISHED);
				}
				
			}
		}
		
	}
	
}
