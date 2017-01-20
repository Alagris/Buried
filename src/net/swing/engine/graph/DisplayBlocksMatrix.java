package net.swing.engine.graph;

import static org.lwjgl.opengl.GL11.glCallList;

import net.LunarEngine2DMainClass;
import net.swing.ground.Pointf;
import net.swing.src.env.WorldSettings;

public class DisplayBlocksMatrix extends DisplayMatrix
{
	
	public DisplayBlocksMatrix(int rows, int columns, float x, float y)
	{
		super(rows, columns, WorldSettings.blockWidth, WorldSettings.blockHeight, x, y);
	}
	
	public DisplayBlocksMatrix(int rows, int columns, Pointf location)
	{
		super(rows, columns, WorldSettings.blockWidth, WorldSettings.blockHeight, location);
	}
	
	/** Renders matrix with attached blocks that are saved in array **/
	public void renderDisplay(int[] ids)
	{
		for (int index = 0; index < lists.length; index++)
		{
			// we don't want to render air
			if (ids[index] < 1) continue;
			// Gets texture of block and binds it somewhere in LWJGL's memory
			LunarEngine2DMainClass.getThings().getThingByID(ids[index]).tex.bind();
			// renders display list
			glCallList(lists[index]);
		}
	}
}
