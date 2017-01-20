package net.swing.ground.GUI;

import net.swing.ground.Graphics;

public class Scroll extends Graphics implements GUIscroll
{
	
	protected float		positon;
	protected boolean	isVerticallyScrolled;
	
	public Scroll(float x, float y, float length, float thickness, boolean vertical)
	{
		this(vertical);
		setLocation(x, y);
		if (vertical)
		{
			setWidth(thickness);
			setHeight(length);
		}
		else
		{
			setHeight(thickness);
			setWidth(length);
		}
	}
	
	public Scroll(boolean vertical)
	{
		isVerticallyScrolled = vertical;
		setColor(224, 224, 224);
	}
	
	@Override
	public float getPosition()
	{
		return positon;
	}
	
	/**
	 * @param zipperPosition
	 *            - position of zip puller must be between 0 and 1
	 */
	@Override
	public void setPosition(float zipperPosition)
	{
		if (zipperPosition > 1)
		{
			zipperPosition = 1;
		}
		else if (zipperPosition < 0)
		{
			zipperPosition = 0;
		}
		positon = zipperPosition;
		
	}
	
	/**
	 * @param zipperPosition
	 *            - position of zip puller must be between 0 and 1
	 */
	public void setLocation(float zipperPosition)
	{
		if (zipperPosition > 1)
		{
			zipperPosition = 1;
		}
		else if (zipperPosition < 0)
		{
			zipperPosition = 0;
		}
		positon = zipperPosition;
		
	}
	
	@Override
	public void update()
	{
		
		render();
	}
	
	@Override
	public void render()
	{
		renderZipPuller();
		renderRectangle();
	}
	
	private void renderZipPuller()
	{
		if (isVerticallyScrolled)
		{
			renderQuad(getX(), positon, positon, positon);
		}
		else
		{
			renderQuad(positon, getY(), positon, positon);
		}
	}
	
	public boolean isVerticallyScrolled()
	{
		return isVerticallyScrolled;
	}
	
	public void setVerticallyScrolled(boolean isVerticallyScrolled)
	{
		this.isVerticallyScrolled = isVerticallyScrolled;
	}
	
}
