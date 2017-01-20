package net.swing.src.ent.mind;

import net.swing.engine.WPS;
import net.swing.src.ent.Entity;
import net.swing.src.ent.Mind;
import net.swing.src.env.Room;

public interface AI
{
	
	/**
	 * Returns vector of movement.
	 * 
	 * @param program
	 *            - everything that entity would need to think
	 */
	public WPS think(Entity ent, Mind program, Room r);
}
