package net.swing.src.ent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import net.swing.engine.WPS;
import net.swing.engine.graph.RenderTexture;
import net.swing.ground.Graphics;
import net.swing.src.env.WorldSettings;

/**
 * This type of entity model renders only one texture of entity. This texture
 * will never change or even rotate. This is the simplest entity model and the
 * fastest one. It is possible to change it's size (use getGraphics() )
 */
public abstract class EntityModelTextured implements EntityModel
{
	protected Texture		texture;
	protected RenderTexture	r	= new RenderTexture();
	
	/**
	 * This type of entity model renders only one texture of entity. This
	 * texture will never change or even rotate. This is the simplest entity
	 * model and the fastest one. It is possible to change it's size (use
	 * getGraphics() )
	 */
	public EntityModelTextured(Texture a)
	{
		setTexture(a);
	}
	
	/**
	 * This type of entity model renders only one texture of entity. This
	 * texture will never change or even rotate. This is the simplest entity
	 * model and the fastest one. It is possible to change it's size (use
	 * getGraphics() )
	 * 
	 * @param format
	 * @param filePath
	 * @param filter
	 */
	public EntityModelTextured(String format, String filePath, int filter)
	{
		try
		{
			setTexture(TextureLoader.getTexture(format, new FileInputStream(new File(filePath)), filter));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This type of entity model renders only one texture of entity. This
	 * texture will never change or even rotate. This is the simplest entity
	 * model and the fastest one. It is possible to change it's size (use
	 * getGraphics() )
	 * 
	 * @param format
	 * @param in
	 * @param filter
	 * @throws IOException
	 */
	public EntityModelTextured(String format, InputStream in, int filter)
	{
		try
		{
			setTexture(TextureLoader.getTexture(format, in, filter));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This type of entity model renders only one texture of entity. This
	 * texture will never change or even rotate. This is the simplest entity
	 * model and the fastest one. It is possible to change it's size (use
	 * getGraphics() )
	 * 
	 * @param format
	 * @param filePath
	 * @param filter
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public EntityModelTextured(String format, String filePath)
	{
		try
		{
			setTexture(TextureLoader.getTexture(format, new FileInputStream(new File(filePath))));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This type of entity model renders only one texture of entity. This
	 * texture will never change or even rotate. This is the simplest entity
	 * model and the fastest one. It is possible to change it's size (use
	 * getGraphics() )
	 * 
	 * @param format
	 * @param in
	 * @param filter
	 * @throws IOException
	 */
	public EntityModelTextured(String format, InputStream in)
	{
		try
		{
			setTexture(TextureLoader.getTexture(format, in));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public Graphics getGraphics()
	{
		return r;
	}
	
	@Override
	public void render(WPS movingDirection, WPS AIdesiredMovVector, Entity ent)
	{
		r.render(texture, WorldSettings.WPS.getLocation(ent));
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public void setTexture(Texture texture)
	{
		assert texture != null : "Texture in EntityModeltextured is set to null";
		this.texture = texture;
	}
	
}
