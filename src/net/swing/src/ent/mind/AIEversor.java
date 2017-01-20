package net.swing.src.ent.mind;

import net.swing.engine.WPS;
import net.swing.src.ent.BindingList;
import net.swing.src.ent.Entity;
import net.swing.src.env.WorldSettings;

/** Basic AI of every aggressive agent */
public abstract class AIEversor implements AI
{
	
	/**
	 * Returns true if entity at index is near enough to this entity If the
	 * distance (A nd B value) is inside WorldSettings.entityFocusRange (smaller
	 * that focus range A and B) this method will return true.
	 */
	protected boolean checkListOfEnts(Entity thisEntity, int index, BindingList list)
	{
		return isPointNearEnough(list.getEntityAtIndex(index).getEnt(), thisEntity, WorldSettings.entityFocusRange);
	}
	
	private boolean isPointNearEnough(WPS p1, WPS p2, WPS minDistance)
	{
		return isPointNearEnough(p1, p2, minDistance.getA(), minDistance.getB());
	}
	
	protected boolean isPointNearEnough(WPS p1, WPS p2, double minDistA, double minDistB)
	{
		if (Math.abs(p1.getA() - p1.getA()) <= minDistA)
		{
			if (Math.abs(p1.getB() - p1.getB()) <= minDistB)
			{
				return true;
			}
		}
		return false;
	}
	
}
