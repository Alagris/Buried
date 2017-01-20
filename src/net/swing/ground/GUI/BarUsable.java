package net.swing.ground.GUI;

import static org.lwjgl.opengl.GL11.*;

import javax.annotation.PreDestroy;

import net.swing.engine.graph.TexturesBase;
import net.swing.ground.Controls;
import net.swing.ground.Graphics;

import org.lwjgl.input.Mouse;

public class BarUsable extends Graphics implements GUIbar
{
	
	private float				value, max, min, absolute;
	private int					list				= glGenLists(1);
	private TexturesBase		texID;
	private boolean				hasClickedInside	= false;
	private byte				mode				= MODE_BAR;
	private final byte			orientation;
	
	public static final byte	MODE_BAR			= 1;
	public static final byte	MODE_ZIP			= 2;
	
	public BarUsable(float x, float y, float width, float height, float maximalBarValue)
	{
		this(x, y, width, height, 1, 1, 1, maximalBarValue, 0, maximalBarValue);
	}
	
	public BarUsable(float x, float y, float width, float height, float minimalbarValue, float maximalBarValue)
	{
		this(x, y, width, height, 1, 1, 1, maximalBarValue, minimalbarValue, maximalBarValue);
	}
	
	public BarUsable(float x, float y, float width, float height, float r, float g, float b, float maximalBarValue, float minimalbarValue, float startValue)
	{
		this(x, y, width, height, r, g, b, maximalBarValue, minimalbarValue, startValue, TexturesBase.BAR, ORIENTATION_HORIZONTAL);
	}
	
	public BarUsable(float x, float y, float width, float height, float maximalBarValue, byte orinetation)
	{
		this(x, y, width, height, 1, 1, 1, maximalBarValue, 0, maximalBarValue, orinetation);
	}
	
	public BarUsable(float x, float y, float width, float height, float minimalbarValue, float maximalBarValue, byte orinetation)
	{
		this(x, y, width, height, 1, 1, 1, maximalBarValue, minimalbarValue, maximalBarValue, orinetation);
	}
	
	public BarUsable(float x, float y, float width, float height, float r, float g, float b, float maximalBarValue, float minimalbarValue, float startValue, byte orinetation)
	{
		this(x, y, width, height, r, g, b, maximalBarValue, minimalbarValue, startValue, TexturesBase.BAR, orinetation);
	}
	
	public BarUsable(float x, float y, float width, float height, float r, float g, float b, float maximalBarValue, float minimalbarValue, float startValue, TexturesBase textureID, byte orinetation)
	{
		setSize(width, height);
		setLocation(x, y);
		setMaxValue(maximalBarValue);
		setMinValue(minimalbarValue);
		setColor(r, g, b);
		setBarValue(startValue);
		this.orientation = orinetation;
		value = startValue;
		texID = textureID;
		generateList();
	}
	
	private void generateList()
	{
		glNewList(list, GL_COMPILE);
		glBegin(GL_QUADS);
		if (getOrientation() == ORIENTATION_HORIZONTAL)
		{
			glTexCoord2f(0, 0);
			glVertex2f(getX(), getAbsoluteHeight());
			glTexCoord2f(1, 0);
			glVertex2f(getAbsoluteWidth(), getAbsoluteHeight());
			glTexCoord2f(1, 1);
			glVertex2f(getAbsoluteWidth(), getY());
			glTexCoord2f(0, 1);
			glVertex2f(getX(), getY());
		}
		else
		{
			glTexCoord2f(0, 0);
			glVertex2f(getX(), getAbsoluteHeight());
			glTexCoord2f(0, 1);
			glVertex2f(getAbsoluteWidth(), getAbsoluteHeight());
			glTexCoord2f(1, 1);
			glVertex2f(getAbsoluteWidth(), getY());
			glTexCoord2f(1, 0);
			glVertex2f(getX(), getY());
		}
		glEnd();
		glEndList();
	}
	
	@Override
	public void setMaxValue(float max)
	{
		this.max = max;
		resetAbsolute();
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
		else if (arg0 < min)
		{
			value = min;
		}
		else
			value = arg0;
	}
	
	private float getValueSelectedByMouse()
	{
		if (orientation == ORIENTATION_HORIZONTAL)
		{
			if (Mouse.getX() < getX()) return min;
			if (Mouse.getX() > getAbsoluteWidth()) return max;
			return (Mouse.getX() - getX()) / getWidth() * absolute + min;
		}
		else
		{
			if (Mouse.getY() < getY()) return min;
			if (Mouse.getY() > getAbsoluteHeight()) return max;
			return (Mouse.getY() - getY()) / getHeight() * absolute + min;
		}
	}
	
	@Override
	public void render()
	{
		bindColor();
		renderBarValue();
	}
	
	/** Renders bar with typed texture */
	public void renderTextured(TexturesBase tex)
	{
		bindColor();
		
		tex.getTexture().bind();
		glCallList(list);
		
		renderBarValue();
	}
	
	/** Renders bar with default texture */
	public void renderTextured()
	{
		renderTextured(texID);
	}
	
	public void update()
	{
		bindColor();
		checkMouse();
		renderBarValue();
	}
	
	/** updates bar with specified texture */
	public void updateTextured(TexturesBase tex)
	{
		bindColor();
		checkMouse();
		
		tex.getTexture().bind();
		glCallList(list);
		
		renderBarValue();
	}
	
	/** updates bar with default (or specified in constructor) texture */
	public void updateTextured()
	{
		updateTextured(texID);
	}
	
	private void renderBarValue()
	{
		glDisable(GL_TEXTURE_2D);
		
		glBegin(GL_QUADS);
		switch (mode)
		{
			case MODE_BAR:
				if (orientation == ORIENTATION_HORIZONTAL)
				{
					glVertex2f(getX(), getAbsoluteHeight());
					glVertex2f(getX() + (value - min) * (getWidth() / absolute), getAbsoluteHeight());
					glVertex2f(getX() + (value - min) * (getWidth() / absolute), getY());
					glVertex2f(getX(), getY());
				}
				else
				{
					glVertex2f(getX(), getY() + (value - min) * (getHeight() / absolute));
					glVertex2f(getAbsoluteWidth(), getY() + (value - min) * (getHeight() / absolute));
					glVertex2f(getAbsoluteWidth(), getY());
					glVertex2f(getX(), getY());
				}
				
				break;
			default:// MODE_ZIP and the rest
				if (orientation == ORIENTATION_HORIZONTAL)
				{
					glVertex2f(getX() + (value - min) * (getWidth() / absolute) - 5 - getWidth() / 60, getAbsoluteHeight());
					glVertex2f(getX() + (value - min) * (getWidth() / absolute) + 5 + getWidth() / 60, getAbsoluteHeight());
					glVertex2f(getX() + (value - min) * (getWidth() / absolute) + 5 + getWidth() / 60, getY());
					glVertex2f(getX() + (value - min) * (getWidth() / absolute) - 5 - getWidth() / 60, getY());
				}
				else
				{
					glVertex2f(getX(), getY() + (value - min) * (getHeight() / absolute) + 5 + getHeight() / 60);
					glVertex2f(getAbsoluteWidth(), getY() + (value - min) * (getHeight() / absolute) + 5 + getHeight() / 60);
					glVertex2f(getAbsoluteWidth(), getY() + (value - min) * (getHeight() / absolute) - 5 - getHeight() / 60);
					glVertex2f(getX(), getY() + (value - min) * (getHeight() / absolute) - 5 - getHeight() / 60);
				}
				
				break;
		}
		
		glEnd();
		
		// glColor3f(1, 1, 1);
		glEnable(GL_TEXTURE_2D);
	}
	
	private void checkMouse()
	{
		if (Controls.isMouse0pressed)
		{
			if (!hasClickedInside) if (isMouseInside())
			{
				hasClickedInside = true;
			}
		}
		else
		{
			hasClickedInside = false;
		}
		
		if (hasClickedInside)
		{
			value = getValueSelectedByMouse();
		}
	}
	
	private boolean isMouseInside()
	{
		if (Controls.mouseX > getX() && Controls.mouseY > getY() && Controls.mouseX < getAbsoluteWidth() && Controls.mouseY < getAbsoluteHeight())
		{
			return true;
		}
		return false;
	}
	
	@PreDestroy
	public void removeBar()
	{
		glDeleteLists(list, 1);
	}
	
	public boolean isBarInUse()
	{
		return hasClickedInside;
	}
	
	public float getMinValue()
	{
		return min;
	}
	
	public void setMinValue(float min)
	{
		this.min = min;
		resetAbsolute();
	}
	
	private void resetAbsolute()
	{
		absolute = max - min;
	}
	
	public short getMode()
	{
		return mode;
	}
	
	public void setMode(byte mode)
	{
		this.mode = mode;
	}
	
	public byte getOrientation()
	{
		return orientation;
	}
}
