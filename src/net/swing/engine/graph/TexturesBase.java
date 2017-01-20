package net.swing.engine.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public enum TexturesBase
{
	EXIT(load(generalPath() + "exit.png")),
	LIGHT(load(generalPath() + "light.png")),
	BUTTON(load(generalPath() + "button.png")),
	BUTTON_WHITE(load(generalPath() + "button_white.png")),
	BUTTON_DARK(load(generalPath() + "button_dark.png")),
	REMOVE(load(generalPath() + "remove.png")),
	SETTINGS(load(generalPath() + "settings.png")),
	BAR(load(generalPath() + "bar.png")),
	BAR_WHITE(load(generalPath() + "bar_white.png")),
	EMERGENCY(load("res/emergency/null.png")),
	LOGO(load(generalPath() + "logo256.png"));
	
	public Texture	texture;
	
	private TexturesBase(Texture tex)
	{
		texture = tex;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	private static Texture load(String path)
	{
		try
		{
			return TextureLoader.getTexture("png", new FileInputStream(new File(path)), GL11.GL_LINEAR);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			return TextureLoader.getTexture("png", new FileInputStream(new File("res/emergency/null.png")));
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
		return "res/gui/";
	}
}
