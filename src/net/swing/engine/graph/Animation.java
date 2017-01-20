package net.swing.engine.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Animation
{
	
	public Texture		texture;
	/** Quantity of frames */
	public final int	size;
	/** frame rate time */
	public int			longTime;
	
	public Animation(AnimationsBase anim)
	{
		setLongTime(anim.longTime);
		setTexture(anim.texture);
		size = anim.size;
	}
	
	public Animation(Texture tex, int longTime, int size)
	{
		setLongTime(longTime);
		setTexture(texture);
		this.size = size;
	}
	
	/** This will automatically detect animation */
	public Animation(File file, int frameWidth, int longTime)
	{
		try
		{
			setTexture(TextureLoader.getTexture("PNG", new FileInputStream(file)));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		setLongTime(longTime);
		size = (int) Math.ceil((double) getTexture().getTextureWidth() / frameWidth);
		if (size != (double) getTexture().getTextureWidth() / frameWidth)
		{
			System.err.println("Illegal animation size: " + file.getPath());
		}
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public int getLongTime()
	{
		return longTime;
	}
	
	public void setLongTime(int longTime)
	{
		this.longTime = longTime;
	}
}
