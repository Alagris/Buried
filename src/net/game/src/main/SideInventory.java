package net.game.src.main;

import javax.annotation.PreDestroy;

import net.swing.ground.Graphics;

public final class SideInventory extends Graphics
{
	
	public SideInventory(float x, float y, float width, float height)
	{
		super(x, y, width, height);
	}
	
	/** This is needed always after new room loading */
	public void resetInventory()
	{// use this method if there is something needed to refresh every inventory activation
	}
	
	/** Checks buttons,renders text etc. */
	public void update()
	{
	}
	
	/** Only renders buttons and doesn't check if they are clicked */
	public void render()
	{
	}
	
	@PreDestroy
	public void cleanUp()
	{
	}
	
}
