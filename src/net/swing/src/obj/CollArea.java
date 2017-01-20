package net.swing.src.obj;

import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;

public abstract class CollArea
{
	/**
	 * Ignore this constructor. It doesn't really do anything. It's here just to
	 * make sure that all classes extending CollArea will have the same
	 * parameters for constructor.
	 */
	protected CollArea(String instructions)
	{
	}
	
	public abstract Action getAction();
	
	/**
	 * All variables in classes extending CollArea should be final and
	 * initialized in constructor. Then in void run() those variables should be
	 * used to execute action properly.
	 * 
	 * @param source
	 *            - entity that activated this action area
	 */
	public abstract void run(Entity source, boolean wasAreaChanged);
	
}
