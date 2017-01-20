package net.swing.ground.GUI;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import net.LunarEngine2DMainClass;
import net.swing.engine.graph.RenderTexture;
import net.swing.ground.Controls;
import net.swing.ground.Graphics;
import net.swing.ground.Rectanglef;
import net.swing.src.obj.Thing;
import net.swing.src.obj.Things;

public class ThingsDisplay extends Graphics
{
	
	protected RenderTexture	render	= new RenderTexture();
	protected float			width, height, p;
	protected int			r, c, highl_x = 1, highl_y = 1;
	protected int[]			things;
	protected float			paddingX, paddingY;
	
	/**
	 * @param proportion
	 *            - proportion between cell and thing in that cell
	 */
	public ThingsDisplay(Rectanglef area, int rows, int collumns, float proportion)
	{
		this.set(area);
		r = rows;
		c = collumns;
		width = getWidth() / collumns;
		height = getHeight() / rows;
		clearList();
		p = (width > height) ? height * proportion : width * proportion;
		setColor(1, 0.2f, 0.2f);
		paddingX = width / 2 - p / 2;
		paddingY = height / 2 - p / 2;
	}
	
	/**
	 * @param proportion
	 *            - proportion between cell and thing in that cell
	 */
	public ThingsDisplay(float x, float y, int rows, int collumns, float rowHeight, float collWidth, float proportion)
	{
		r = rows;
		c = collumns;
		width = collWidth;
		height = rowHeight;
		set(x, y, width * c, height * r);
		clearList();
		p = (width > height) ? height * proportion : width * proportion;
		setColor(1, 0.2f, 0.2f);
		paddingX = width / 2 - p / 2;
		paddingY = height / 2 - p / 2;
	}
	
	/** WARNING: resizing causes erasing everything from display */
	public void resize(float x, float y, int rows, int collumns, float rowHeight, float collWidth, float proportion)
	{
		r = rows;
		c = collumns;
		width = collWidth;
		height = rowHeight;
		set(x, y, width * c, height * r);
		
		clearList();
		p = (width > height) ? height * proportion : width * proportion;
		setColor(1, 0.2f, 0.2f);
		paddingX = width / 2 - p / 2;
		paddingY = height / 2 - p / 2;
	}
	
	public int[] getThings()
	{
		return things;
	}
	
	/** returns id of selected thing */
	public int getSelectedThingIndex()
	{
		return things[highl_x - 1 + highl_y * c - c];
	}
	
	public Thing getSelectedThing()
	{
		return LunarEngine2DMainClass.getThings().getThingByID(getSelectedThingIndex());
	}
	
	public void put(int thingID, float x, float y)
	{
		if (isPointInside(x, y))
		{
			putAt(thingID, (int) Math.ceil((double) (x - getX()) / width), (int) Math.ceil((double) (y - getY()) / height));
		}
	}
	
	public void putAt(int thingID, int x, int y)
	{
		try
		{
			things[x - 1 + y * c - c] = thingID;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			
		}
	}
	
	/** Checks is mouse selects any of things. Selected thing is highlighted */
	public void updateStatic()
	{
		isMouseInside = isPointInside(Controls.mouseX, Controls.mouseY);
		if (Controls.isMouse0clicked) if (isMouseInside())
		{
			highl_x = (int) Math.ceil((double) (Controls.mouseX - getX()) / width);
			highl_y = (int) Math.ceil((double) (Controls.mouseY - getY()) / height);
		}
		render();
		if (isHighl) renderHighlight(highl_x, highl_y);
		
	}
	
	private boolean	isMouseInside, isHighl = true;
	private int		thingDropped;
	private Thing	pickedThing	= Things.air;
	
	public Thing getPickedThing()
	{
		return pickedThing;
	}
	
	public void clearList()
	{
		things = new int[(r * c)];
	}
	
	public void fillWithThings(Thing[] things, int start)
	{
		clearList();
		for (int i = 0; i < this.things.length && start < things.length; start++, i++)
		{
			this.things[i] = things[start].ID;
		}
	}
	
	/** Checks is mouse selects any of things. Selected thing is highlighted */
	public void updateInteractive()
	{
		isMouseInside = isPointInside(Controls.mouseX, Controls.mouseY);
		render.render(pickedThing.tex, Controls.mouseX, Controls.mouseY, p, p);
		
		thingDropped = 0;
		if (isMouseInside())
		{
			highl_x = (int) Math.ceil((double) (Controls.mouseX - getX()) / width);
			highl_y = (int) Math.ceil((double) (Controls.mouseY - getY()) / height);
			if (pickedThing.ID == 0)
			{
				if (Controls.isMouse0clicked)
				{
					pickedThing = getSelectedThing();
				}
			}
		}
		
		if (!Controls.isMouse0pressed)
		{
			if (pickedThing.ID != 0)
			{
				if (isMouseInside())
				{
					putAt(pickedThing.ID, highl_x, highl_y);
				}
				else
				{
					thingDropped = pickedThing.ID;
				}
				pickedThing = Things.air;
			}
		}
		render();
		
		if (isHighl) if (isMouseInside()) renderHighlight(highl_x, highl_y);
		
	}
	
	private void renderHighlight(float row, float collumn)
	{
		float x = getX() + (row - 1) * width;
		float y = getY() + (collumn - 1) * height;
		glColor3f(RGB.r, RGB.g, RGB.b);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
		glEnable(GL_TEXTURE_2D);
		glColor3f(1, 1, 1);
	}
	
	public void render()
	{
		float x = getX(), y = getY();
		for (int i = 1; i <= things.length; i++)
		{
			if (things[i - 1] != 0)
			{
				render.render(LunarEngine2DMainClass.getThings().getThingByID(things[i - 1]).tex, paddingX + x, paddingY + y, p, p);
			}
			if (i % c == 0)
			{
				x = getX();
				y += height;
			}
			else
			{
				x += width;
			}
		}
	}
	
	public boolean isMouseInside()
	{
		return isMouseInside;
	}
	
	public int getThingDropped()
	{
		return thingDropped;
	}
	
	public void enableHighLight(boolean b)
	{
		isHighl = b;
	}
	
}
