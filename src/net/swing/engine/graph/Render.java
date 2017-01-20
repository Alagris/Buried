package net.swing.engine.graph;

import net.swing.ground.Dimensionf;
import net.swing.ground.Pointf;

public interface Render
{
	
	public Dimensionf size();
	
	public void render(Pointf p);
	
	public void render(float x, float y);
}
