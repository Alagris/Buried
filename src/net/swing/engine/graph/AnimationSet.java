package net.swing.engine.graph;

import net.swing.ground.Graphics;

public class AnimationSet
{
	private final Animation[]		animations;
	/***/
	private final RenderAnimation	r;
	
	/**
	 * This class allows to easily manipulate set of animations. by default all
	 * animations are displayed the same as they look in file so they are
	 * rendered on left.
	 */
	public AnimationSet(Animation... animations)
	{
		this.animations = animations;
		r = new RenderAnimation();
	}
	
	public Animation[] getAnimations()
	{
		return animations;
	}
	
	public Graphics getGraphics()
	{
		return r;
	}
	
	public RenderAnimation getRenderer()
	{
		return r;
	}
	
	/**
	 * Left rendering is displays animation exactly as it is in original file.
	 * Right rendering causes animation is rendered inverted in y axis. For
	 * example there is a file with black left arrow. When it is rendered on
	 * right it becomes right arrow. It it already was right arrow then it
	 * becomes left.
	 */
	public void renderAnimation(int index, boolean renderOnLeft)
	{
		if (renderOnLeft)
		{
			r.renderLeft(animations[index]);
		}
		else
		{
			r.renderRight(animations[index]);
		}
	}
	
	public void renderAnimationLeft(int index)
	{
		r.renderLeft(animations[index]);
	}
	
	public void renderAnimationRight(int index)
	{
		r.renderRight(animations[index]);
	}
	
}
