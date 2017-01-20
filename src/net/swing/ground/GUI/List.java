package net.swing.ground.GUI;

import java.util.ArrayList;
import java.util.Arrays;

import net.swing.ground.Controls;
import net.swing.ground.Graphics;

public abstract class List<typeOfValues> extends Graphics implements GUIlist<typeOfValues>
{
	
	/** Elements displayed in list */
	protected ArrayList<typeOfValues>	elements;
	
	public List()
	{
		elements = new ArrayList<typeOfValues>();
	}
	
	public List(typeOfValues[] e)
	{
		elements = new ArrayList<typeOfValues>(Arrays.asList(e));
	}
	
	/** If is clicked will return true and do onClick() */
	public boolean checkList()
	{
		if (isPointInside(Controls.mouseX, Controls.mouseY))
		{
			
			if (Controls.dWheel != 0)
			{
				if (Controls.dWheel < 0)
				{
					onScrollDown();
				}
				else
				{
					onScrollUp();
				}
			}
			
			if (Controls.isMouse0clicked)
			{
				onClick(Controls.mouseX, Controls.mouseY);
				return true;
			}
			else
			{
				renderMouseEnter(Controls.mouseX, Controls.mouseY);
				return false;
			}
		}
		render();
		return false;
	}
	
	public void checkArrowKeys()
	{
		if (Controls.typedKey == 200)
		{
			onScrollUp();
		}
		else if (Controls.typedKey == 208)
		{
			onScrollDown();
		}
	}
	
	public abstract void clearSelection();
	
	public int getSize()
	{
		return elements.size();
	}
}
