package net.swing.src.ent.mind;

import net.swing.engine.WPS;
import net.swing.src.ent.Entity;
import net.swing.src.ent.Mind;
import net.swing.src.env.Room;

/** This class is the simples possible implementation of AI interface. */
public class AISimple implements AI
{
	@Override
	public WPS think(Entity ent, Mind program, Room r)
	{
		return new WPS(0, 0);
	}
	
}
