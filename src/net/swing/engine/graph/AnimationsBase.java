package net.swing.engine.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public enum AnimationsBase
{
	
	TEST(load(generalPath() + "player/test.png"), 7, 350), PLAYER_STANDING(load(generalPath() + "player/standing.png"), 11, 350), PLAYER_WALKING(load(generalPath() + "player/walking.png"), 16, 500);
	
	public Texture	texture;
	/** Quantity of frames */
	public int		size;
	/** frame rate time */
	public int		longTime;
	
	private AnimationsBase(Texture tex, int s, int longTime)
	{
		texture = tex;
		size = s;
		this.longTime = longTime;
	}
	
	private static Texture load(String path)
	{
		try
		{
			return TextureLoader.getTexture("png", new FileInputStream(new File(path)));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String generalPath()
	{
		return "res/objects/entities/";
	}
}
