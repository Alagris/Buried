package net.swing.engine.graph;

import net.swing.ground.Dimensionf;
import net.swing.ground.Pointf;

public class DisplayAnimation
{
	private final RenderAnimation	render;
	private Animation				anim;
	
	public DisplayAnimation(Animation anim)
	{
		setAnimation(anim);
		render = new RenderAnimation();
	}
	
	public void renderLeft()
	{
		render.renderLeft(anim);
	}
	
	public void renderLeft(Pointf p, Dimensionf dim)
	{
		render.renderLeft(anim, p.getX(), p.getY(), dim.width, dim.height);
	}
	
	public void renderLeft(float x, float y, Dimensionf dim)
	{
		render.renderLeft(anim, x, y, dim.width, dim.height);
	}
	
	public void renderLeft(Pointf p, float width, float height)
	{
		render.renderLeft(anim, p.getX(), p.getY(), width, height);
	}
	
	public void renderRight()
	{
		render.renderRight(anim);
	}
	
	public void renderRight(Pointf p, Dimensionf dim)
	{
		render.renderRight(anim, p.getX(), p.getY(), dim.width, dim.height);
	}
	
	public void renderRight(float x, float y, Dimensionf dim)
	{
		render.renderRight(anim, x, y, dim.width, dim.height);
	}
	
	public void renderRight(Pointf p, float width, float height)
	{
		render.renderRight(anim, p.getX(), p.getY(), width, height);
	}
	
	public void renderLeft(float x, float y)
	{
		render.renderLeft(anim, x, y);
	}
	
	public void renderLeft(Pointf p)
	{
		render.renderLeft(anim, p.getX(), p.getY());
	}
	
	public void renderRight(Pointf p)
	{
		render.renderRight(anim, p.getX(), p.getY());
	}
	
	public void renderRight(float x, float y)
	{
		render.renderRight(anim, x, y);
	}
	
	public void renderRight(float x, float y, float width, float height)
	{
		render.renderRight(anim, x, y, width, height);
	}
	
	public void renderLeft(float x, float y, float width, float height)
	{
		render.renderLeft(anim, x, y, width, height);
	}
	
	public void setAnimation(Animation anim2)
	{
		if (this.anim == anim2) return;
		render.frIndex = 0;
		this.anim = anim2;
	}
	
	public Animation getAnimation()
	{
		return anim;
	}
	
	public RenderAnimation getRenderer()
	{
		return render;
	}
	
}
