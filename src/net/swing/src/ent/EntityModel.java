package net.swing.src.ent;

import net.swing.engine.WPS;
import net.swing.ground.Graphics;
import net.swing.src.ent.mind.AI;

public interface EntityModel extends AI
{
	
	public Graphics getGraphics();
	
	public float getMaxHealth();
	
	public String getName();
	
	public int getSize();
	
	public boolean isTeleportationAllowed();
	
	public void render(WPS movingDirection, WPS AIdesiredMovVector, Entity ent);
	
	public boolean isAffectedAsPoint();
}
