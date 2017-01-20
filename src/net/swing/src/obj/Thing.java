package net.swing.src.obj;

import org.newdawn.slick.opengl.Texture;

public class Thing
{
	
	public final int		ID;
	public final Domain		domain;
	public final Texture	tex;
	public final String		name;
	
	public Thing(int ID, Domain d, String name, Texture tex)
	{
		this.ID = ID;
		domain = d;
		this.name = name;
		this.tex = tex;
	}
	
	@Override
	public String toString()
	{
		return "[" + ID + "]" + name;
	}
	
}
