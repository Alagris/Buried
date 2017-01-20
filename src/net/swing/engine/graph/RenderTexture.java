package net.swing.engine.graph;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import net.swing.ground.Dimensionf;
import net.swing.ground.Graphics;
import net.swing.ground.Pointf;

public class RenderTexture extends Graphics
{
	
	// =======Constructors======
	public RenderTexture()
	{
	}
	
	public RenderTexture(float x, float y, float width, float height)
	{
		set(x, y, width, height);
	}
	
	public RenderTexture(Pointf p, float width, float height)
	{
		set(p.getX(), p.getY(), width, height);
	}
	
	public RenderTexture(float x, float y, Dimensionf d)
	{
		set(x, y, d.width, d.height);
	}
	
	public RenderTexture(Pointf p, Dimensionf d)
	{
		set(p.getX(), p.getY(), d.width, d.height);
	}
	
	// =====texture======
	/**
	 * Renders texture with default color (white ; use setColor() to change it)
	 * and at default position (specified in constructor; use setX(), setY(),
	 * setWidth() etc. to change it). If you need to change those values all the
	 * time then it will be better to use methods renderColor() and
	 * renderTexture() .
	 */
	public void render(Texture tex)
	{
		tex.bind();
		renderRectangle();
	}
	
	/**
	 * Renders texture with default color (white ; use setColor() to change it)
	 * and at default position (specified in constructor; use setX(), setY(),
	 * setWidth() etc. to change it). If you need to change those values all the
	 * time then it will be better to use methods renderColor() and
	 * renderTexture() .
	 */
	public void render(Texture tex, float x, float y, float width, float height)
	{
		tex.bind();
		renderRectangle(x, y, width, height);
	}
	
	/**
	 * Renders texture with default color (white ; use setColor() to change it)
	 * and at default position (specified in constructor; use setX(), setY(),
	 * setWidth() etc. to change it). If you need to change those values all the
	 * time then it will be better to use methods renderColor() and
	 * renderTexture() .
	 */
	public void render(Texture tex, Pointf p, float width, float height)
	{
		tex.bind();
		renderRectangle(p.getX(), p.getY(), width, height);
	}
	
	/**
	 * Renders texture with default color (white ; use setColor() to change it)
	 * and at default position (specified in constructor; use setX(), setY(),
	 * setWidth() etc. to change it). If you need to change those values all the
	 * time then it will be better to use methods renderColor() and
	 * renderTexture() .
	 */
	public void render(Texture tex, float x, float y, Dimensionf d)
	{
		tex.bind();
		renderRectangle(x, y, d.width, d.height);
	}
	
	/**
	 * Renders texture with default color (white ; use setColor() to change it)
	 * and at default position (specified in constructor; use setX(), setY(),
	 * setWidth() etc. to change it). If you need to change those values all the
	 * time then it will be better to use methods renderColor() and
	 * renderTexture() .
	 */
	public void render(Texture tex, Pointf p)
	{
		tex.bind();
		renderRectangle(p.getX(), p.getY());
	}
	
	/**
	 * Renders texture with default color (white ; use setColor() to change it)
	 * and at default position (specified in constructor; use setX(), setY(),
	 * setWidth() etc. to change it). If you need to change those values all the
	 * time then it will be better to use methods renderColor() and
	 * renderTexture() .
	 */
	public void render(Texture tex, Pointf p, Dimensionf d)
	{
		tex.bind();
		renderRectangle(p.getX(), p.getY(), d.width, d.height);
	}
	
	// ======color======
	public void renderColor(Color c)
	{
		if (c == null) return;
		glDisable(GL_TEXTURE_2D);
		c.bind();
		renderRectangleNonColored();
		glEnable(GL_TEXTURE_2D);
	}
	
	public void renderColor(Color c, float x, float y, float width, float height)
	{
		if (c == null) return;
		glDisable(GL_TEXTURE_2D);
		c.bind();
		renderRectangleNonColored(x, y, width, height);
		glEnable(GL_TEXTURE_2D);
	}
	
	public void renderColor(Color c, Pointf p, float width, float height)
	{
		renderColor(c, p.getX(), p.getY(), width, height);
	}
	
	public void renderColor(Color c, float x, float y, Dimensionf d)
	{
		renderColor(c, x, y, d.width, d.height);
	}
	
	public void renderColor(Color c, Pointf p)
	{
		renderColor(c, p.getX(), p.getY(), width, height);
	}
	
	public void renderColor(Color c, float x, float y)
	{
		renderColor(c, x, y, width, height);
	}
	
	public void renderColor(Color c, Pointf p, Dimensionf d)
	{
		renderColor(c, p.getX(), p.getY(), d.width, d.height);
	}
	
	// ======color and texture=======
	public void renderTexture(Texture tex, Color c)
	{
		if (c == null || tex == null) return;
		tex.bind();
		c.bind();
		renderRectangle();
	}
	
	public void renderTexture(Texture tex, float x, float y, float width, float height, Color c)
	{
		if (c == null || tex == null) return;
		tex.bind();
		c.bind();
		renderRectangleNonColored(x, y, width, height);
	}
	
	public void renderTexture(Texture tex, Pointf p, float width, float height, Color c)
	{
		renderTexture(tex, p.getX(), p.getY(), width, height, c);
	}
	
	public void render(Texture tex, float x, float y, Dimensionf d, Color c)
	{
		renderTexture(tex, x, y, d.width, d.height, c);
	}
	
	public void render(Texture tex, Pointf p, Dimensionf d, Color c)
	{
		renderTexture(tex, p.getX(), p.getY(), d.width, d.height, c);
	}
}
