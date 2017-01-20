package net.swing.src.obj;

import org.newdawn.slick.opengl.Texture;

public class Block extends Thing
{
	
	/**
	 * @param ID
	 *            - must be unique for every thing
	 * @param name
	 *            - the same name as texture name in res/objects/blocks folder
	 */
	public Block(int ID, String name, Texture tex)
	{
		super(ID, Domain.BLOCK, name, tex);
	}
	
	/**
	 * @param ID
	 *            - must be unique for every thing
	 * @param name
	 *            - the same name as texture name in res/objects/blocks folder
	 */
	public Block(int ID, Domain d, String name, Texture tex)
	{
		super(ID, d, name, tex);
	}
	
}
