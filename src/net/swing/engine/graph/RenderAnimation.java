package net.swing.engine.graph;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import net.swing.ground.Delta;
import net.swing.ground.Dimensionf;
import net.swing.ground.Graphics;
import net.swing.ground.Pointf;

public class RenderAnimation extends Graphics
{
	
	protected int	timePast	= 0;
	protected int	frIndex		= 0;
	
	public void renderLeft(Animation anim)
	{
		renderLeft(anim, getX(), getY(), width, height);
	}
	
	public void renderRight(Animation anim)
	{
		renderRight(anim, getX(), getY(), width, height);
	}
	
	public void renderLeft(Animation anim, Pointf p, Dimensionf dim)
	{
		renderLeft(anim, p.getX(), p.getY(), dim.width, dim.height);
	}
	
	public void renderRight(Animation anim, Pointf p, Dimensionf dim)
	{
		renderRight(anim, p.getX(), p.getY(), dim.width, dim.height);
	}
	
	public void renderLeft(Animation anim, float x, float y, Dimensionf dim)
	{
		renderLeft(anim, x, y, dim.width, dim.height);
	}
	
	public void renderRight(Animation anim, float x, float y, Dimensionf dim)
	{
		renderRight(anim, x, y, dim.width, dim.height);
	}
	
	public void renderLeft(Animation anim, Pointf p, float width, float height)
	{
		renderLeft(anim, p.getX(), p.getY(), width, height);
	}
	
	public void renderRight(Animation anim, Pointf p, float width, float height)
	{
		renderRight(anim, p.getX(), p.getY(), width, height);
	}
	
	public void renderLeft(Animation anim, Pointf p)
	{
		renderLeft(anim, p.getX(), p.getY(), width, height);
	}
	
	public void renderRight(Animation anim, Pointf p)
	{
		renderRight(anim, p.getX(), p.getY(), width, height);
	}
	
	public void renderLeft(Animation anim, float x, float y)
	{
		renderLeft(anim, x, y, width, height);
	}
	
	public void renderRight(Animation anim, float x, float y)
	{
		renderRight(anim, x, y, width, height);
	}
	
	public void renderRight(Animation anim, float x, float y, float width, float height)
	{
		if (anim.texture == null)
		{
			TexturesBase.EMERGENCY.getTexture().bind();
			renderRectangle(x, y, width, height);
		}
		else
		{
			updateTime(anim);
			renderFrameRight(anim, frIndex, x, y, width, height);
		}
	}
	
	private void updateTime(Animation anim)
	{
		timePast += Delta.get();
		if (timePast >= anim.longTime)
		{
			timePast = 0;
			frIndex += timePast / anim.longTime;
			if (frIndex >= anim.size)
			{
				frIndex %= anim.size;
			}
		}
	}
	
	public void renderLeft(Animation anim, float x, float y, float width, float height)
	{
		if (anim.texture == null)
		{
			TexturesBase.EMERGENCY.getTexture().bind();
			renderRectangle(x, y, width, height);
		}
		else
		{
			updateTime(anim);
			renderFrameLeft(anim, frIndex, x, y, width, height);
		}
	}
	
	/** @throws IndexOutOfBoundsException */
	public void renderFrameRight(Animation anim, int index, float x, float y, float width, float height)
	{
		if (index > anim.size)
		{
			throw new IndexOutOfBoundsException();
		}
		else if (index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			anim.texture.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(anim.texture.getWidth() / anim.size * index, 0);
			glVertex2f(x, y + height);
			glTexCoord2f(anim.texture.getWidth() / anim.size * (index + 1), 0);
			glVertex2f(x + width, y + height);
			glTexCoord2f(anim.texture.getWidth() / anim.size * (index + 1), 1);
			glVertex2f(x + width, y);
			glTexCoord2f(anim.texture.getWidth() / anim.size * index, 1);
			glVertex2f(x, y);
			glEnd();
		}
	}
	
	/** @throws IndexOutOfBoundsException */
	public void renderFrameLeft(Animation anim, int index, float x, float y, float width, float height)
	{
		if (index > anim.size)
		{
			throw new IndexOutOfBoundsException();
		}
		else if (index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			anim.texture.bind();
			
			glBegin(GL_QUADS);
			glTexCoord2f(anim.texture.getWidth() / anim.size * (index + 1), 0);
			glVertex2f(x, y + height);
			
			glTexCoord2f(anim.texture.getWidth() / anim.size * index, 0);
			glVertex2f(x + width, y + height);
			
			glTexCoord2f(anim.texture.getWidth() / anim.size * index, 1);
			glVertex2f(x + width, y);
			
			glTexCoord2f(anim.texture.getWidth() / anim.size * (index + 1), 1);
			glVertex2f(x, y);
			
			glEnd();
		}
	}
	
}
