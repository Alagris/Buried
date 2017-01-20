package net.swing.ground.GUI;

import javax.annotation.PreDestroy;

import net.swing.ground.BitmapFont;
import net.swing.ground.Graphics;
import net.swing.ground.Window;

public abstract class Button extends Graphics implements GUIbutton
{
	@PreDestroy
	public abstract void removeButton();
	
	protected String		name;
	protected float			textX			= 0, textY = 0;
	protected BitmapFont	textDisplayer	= Window.textDisplayer;
	
	/** If is clicked will return true and do renderClick() */
	public boolean checkButton(float mouseX, float mouseY, boolean isClicked)
	{
		
		if (isPointInside(mouseX, mouseY))
		{
			if (isClicked)
			{
				onClick();
				return true;
			}
			else
			{
				renderMouseEnter();
				return false;
			}
		}
		render();
		return false;
	}
	
}
