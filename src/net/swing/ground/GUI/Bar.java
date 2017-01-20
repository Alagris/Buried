package net.swing.ground.GUI;

import static org.lwjgl.opengl.GL11.*;
import net.swing.engine.graph.TexturesBase;
import net.swing.ground.Graphics;

public class Bar extends Graphics implements GUIbar
{
	
	private float			value, max;
	private int				list		= glGenLists(1);
	private TexturesBase	texID		= TexturesBase.BAR;
	
	private byte			orientation	= ORIENTATION_HORIZONTAL;
	
	public Bar(float x, float y, float width, float height, float maximalBarValue)
	{
		setSize(width, height);
		setLocation(x, y);
		max = maximalBarValue;
		value = max;
		
		generateList();
	}
	
	public Bar(float x, float y, float width, float height, float r, float g, float b, float maximalBarValue, float startValue)
	{
		setSize(width, height);
		setLocation(x, y);
		max = maximalBarValue;
		setBarColor(r, g, b);
		setBarValue(startValue);
		
		generateList();
	}
	
	public Bar(float x, float y, float width, float height, float r, float g, float b, float maximalBarValue, float startValue, TexturesBase textureID)
	{
		setSize(width, height);
		setLocation(x, y);
		max = maximalBarValue;
		setBarColor(r, g, b);
		setBarValue(startValue);
		texID = textureID;
		
		generateList();
		
	}
	
	private void generateList()
	{
		glNewList(list, GL_COMPILE);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(getX(), getAbsoluteHeight());
		glTexCoord2f(1, 0);
		glVertex2f(getAbsoluteWidth(), getAbsoluteHeight());
		glTexCoord2f(1, 1);
		glVertex2f(getAbsoluteWidth(), getY());
		glTexCoord2f(0, 1);
		glVertex2f(getX(), getY());
		glEnd();
		glEndList();
	}
	
	@Override
	public void setMaxValue(float max)
	{
		this.max = max;
		
	}
	
	@Override
	public float getBarValue()
	{
		return value;
	}
	
	@Override
	public void setBarValue(float arg0)
	{
		if (arg0 > max)
		{
			value = max;
		}
		else if (arg0 < 0)
		{
			value = 0;
		}
		else
			value = arg0;
	}
	
	public void setBarColor(float r, float g, float b)
	{
		setColor(r, g, b);
	}
	
	@Override
	public void render()
	{
		renderBarValue();
	}
	
	/**
	 * Renders bar with specified texture and without rendering progress (just
	 * bar)
	 */
	public void showTextured(TexturesBase tex)
	{
		tex.getTexture().bind();
		glCallList(list);
	}
	
	/**
	 * Renders bar with default (specified in constructor) texture and without
	 * rendering progress (just bar)
	 */
	public void showTextured()
	{
		texID.getTexture().bind();
		glCallList(list);
	}
	
	/** Renders bar with typed texture */
	public void renderTextured(TexturesBase tex)
	{
		
		tex.getTexture().bind();
		glCallList(list);
		
		renderBarValue();
	}
	
	/** Renders bar with default texture */
	public void renderTextured()
	{
		renderTextured(texID);
	}
	
	private void renderBarValue()
	{
		
		glDisable(GL_TEXTURE_2D);
		
		glColor3f(RGB.r, RGB.g, RGB.b);
		glBegin(GL_QUADS);
		if (orientation == ORIENTATION_HORIZONTAL)
		{
			glVertex2f(getX(), getAbsoluteHeight());
			glVertex2f(getX() + value * (getWidth() / max), getAbsoluteHeight());
			glVertex2f(getX() + value * (getWidth() / max), getY());
			glVertex2f(getX(), getY());
		}
		else
		{
			glVertex2f(getX(), getY() + value * (getHeight() / max));
			glVertex2f(getAbsoluteWidth(), getY() + value * (getHeight() / max));
			glVertex2f(getAbsoluteWidth(), getY());
			glVertex2f(getX(), getY());
		}
		glEnd();
		glColor3f(1, 1, 1);
		glEnable(GL_TEXTURE_2D);
		
	}
	
	public void removeBar()
	{
		glDeleteLists(list, 1);
	}
	
	public byte getOrientation()
	{
		return orientation;
	}
	
	public void setOrientation(byte orientation)
	{
		this.orientation = orientation;
	}
	
}
