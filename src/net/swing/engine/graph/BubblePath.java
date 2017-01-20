package net.swing.engine.graph;

import static org.lwjgl.opengl.GL11.*;
import net.swing.ground.Graphics;

import org.newdawn.slick.Color;

public class BubblePath
{
	private Color	color	= new Color(1, 1, 1);
	
	private int[]	lists;
	
	public BubblePath(int bubblesQuantity)
	{
		lists = new int[bubblesQuantity];
	}
	
	/**
	 * @param startX
	 *            - x coordinates of first bubble
	 * @param startY
	 *            - y coordinates of first bubble
	 * @param endX
	 *            - x coordinates of last bubble
	 * @param endY
	 *            - y coordinates of last bubble
	 * @param startRadius
	 *            - radius of first bubble
	 * @param endRadius
	 *            - radius of last bubble
	 */
	public void createPath(float startX, float startY, float endX, float endY, float startRadius, float endRadius, Color bubbleColor, Color outlineColor)
	{
		/* Now x stores distance between start and end points */
		float x = endX - startX;
		// y = endY - startY
		float n = (endY - startY) / (x * x);
		/* And now x stores distance between each one of points */
		x = x / lists.length;
		float r = (endRadius - startRadius) / lists.length;
		
		for (int i = 0; i < lists.length; i++)
		{
			// y = n * x * x
			lists[i] = glGenLists(1);
			glNewList(lists[i], GL_COMPILE);
			bubbleColor.bind();
			Graphics.renderFilledCircle(startX + x * i, startY + n * x * x * i * i, startRadius + r * i, 0.2f);
			outlineColor.bind();
			Graphics.renderOutlineCircle(startX + x * i, startY + n * x * x * i * i, startRadius + r * i, 0.2f);
			glEndList();
		}
	}
	
	public void deletePath()
	{
		for (int i = 0; i < lists.length; i++)
		{
			glDeleteLists(lists[i], 1);
		}
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void render()
	{
		for (int i = 0; i < lists.length; i++)
		{
			glCallList(lists[i]);
		}
	}
	
}
