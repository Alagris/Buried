package net.swing.src.ent;

import net.swing.engine.WPS;
import net.swing.engine.graph.Animation;
import net.swing.engine.graph.AnimationsBase;
import net.swing.engine.graph.DisplayAnimation;
import net.swing.ground.Graphics;
import net.swing.src.ent.mind.AI;
import net.swing.src.ent.mind.AIChangeling;
import net.swing.src.ent.mind.AIEversor;
import net.swing.src.env.WorldSettings;

public final class EntityChangeling extends Entity
{
	private DisplayAnimation	r	= new DisplayAnimation(new Animation(AnimationsBase.TEST));
	private AIEversor			ai	= new AIChangeling();
	
	public EntityChangeling()
	{
		super(2, "changeling");
		health = 1;
		r.getRenderer().setSize(WorldSettings.collWidth() * 2, WorldSettings.collHeight() * pointsQuantity);
		setAffectedAsPoint(false);
	}
	
	@Override
	public void render(WPS movingDirection, WPS AIdesiredMovVector)
	{
		r.renderLeft(WorldSettings.WPS.getLocation(this));
	}
	
	@Override
	public AI getAI()
	{
		return ai;
	}
	
	@Override
	public float getMaxHealth()
	{
		return 100;
	}
	
	@Override
	public Graphics getGraphics()
	{
		return r.getRenderer();
	}
	
	@Override
	public boolean isTeleportationAllowed()
	{
		return false;
	}
	
	@Override
	public Entity createNewInstance()
	{
		return null;
	}
	
}
