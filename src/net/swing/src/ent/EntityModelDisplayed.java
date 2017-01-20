package net.swing.src.ent;

import net.swing.engine.WPS;
import net.swing.engine.graph.Animation;
import net.swing.engine.graph.RenderAnimation;
import net.swing.ground.Graphics;

/**
 * This type of entity model renders only one animation of entity. This
 * animation cannot be flipped and the direction of texture depends on AI WPS
 * vector. If value A of AIdesiredMovVector is greater negative then the
 * animation is going to be rendered on right. Otherwise it's rendered on left
 */
public abstract class EntityModelDisplayed implements EntityModel
{
	protected Animation			anim;
	protected RenderAnimation	r	= new RenderAnimation();
	
	/**
	 * This type of entity model renders only one animation of entity. This
	 * animation can be flipped and the direction of texture depends on AI WPS
	 * vector. If value A of AIdesiredMovVector is greater negative then the
	 * animation is going to be rendered on right. Otherwise it's rendered on
	 * left
	 */
	public EntityModelDisplayed(Animation a)
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
		if (AIdesiredMovVector.getA() < 0)
		{
			r.renderRight(anim);
		}
		else
		{
			r.renderLeft(anim);
		}
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
