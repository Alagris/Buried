package net.swing.src.ent;

import net.swing.engine.WPS;
import net.swing.engine.graph.Animation;
import net.swing.engine.graph.RenderAnimation;
import net.swing.ground.Graphics;

/**
 * This type of entity model renders only one animation of entity. This
 * animation cannot be flipped. This is the simplest entity model that provides
 * animations
 */
public abstract class EntityModelAnimated implements EntityModel
{
	protected Animation			anim;
	protected RenderAnimation	r	= new RenderAnimation();
	
	/**
	 * This type of entity model renders only one animation of entity. This
	 * animation cannot be flipped. This is the simplest entity model that
	 * provides animations
	 */
	public EntityModelAnimated(Animation a)
	{
		setAnimation(a);
	}
	
	@Override
	public Graphics getGraphics()
	{
		return r;
	}
	
	@Override
	public void render(WPS movingDirection, WPS AIdesiredMovVector, Entity ent)
	{
		r.renderLeft(anim);
	}
	
	public Animation getAnimation()
	{
		return anim;
	}
	
	public void setAnimation(Animation anim)
	{
		this.anim = anim;
	}
	
}
