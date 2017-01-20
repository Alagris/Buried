package net.swing.src.ent.mind;

import org.lwjgl.input.Keyboard;

import net.swing.engine.WPS;
import net.swing.ground.Controls;
import net.swing.src.ent.Entity;
import net.swing.src.ent.Mind;
import net.swing.src.env.Cell;
import net.swing.src.env.Room;
import net.swing.src.env.WorldSettings;
import net.swing.src.obj.CollAreas;

public final class AIPlayer implements AI
{
	
	private Cell[]		path	= new Cell[0];
	private int			i		= 0;
	
	private static char	mode	= 'i';
	
	/**
	 * Entity must be set to "affectedAsPoint" at the very beginning. Otherwise
	 * there could occur some problems.
	 */
	@Override
	public WPS think(Entity ent, Mind program, Room r)
	{
		if (Controls.typedChar != 0)
		{
			mode = Character.toLowerCase(Controls.typedChar);
			ent.setAffectedAsPoint(mode == 'i');
		}
		if (Controls.isMouse0clicked)
		{
			System.out.println("hit: "
					+ AImath.findObstacles(WorldSettings.cSelector.getCell(WorldSettings.WPS.getX(ent.getA()), WorldSettings.WPS.getY(ent.getB())), WorldSettings.cSelector.getCell(Controls.mouseX, Controls.mouseY), r.getCollArea(),
							CollAreas.solidBlock));
		}
		
		switch (mode)
		{
			case 'i':
				return pathControl(ent, r);
			case 'o':
				return mouseControl(ent);
			case 'p':
				return keysControl();
		}
		
		return keysControl();
	}
	
	private WPS pathControl(Entity ent, Room r)
	{
		if (Controls.isMouse0clicked)
		{
			path = AImath.trackPathForEntity(ent, r, WorldSettings.cSelector.getCell(Controls.mouseX, Controls.mouseY));
			i = path.length - 1;
		}
		if (i < 0)
		{
			return new WPS(0, 0);
		}
		else if (path.length <= 0)
		{
			return new WPS(0, 0);
		}
		
		if (WorldSettings.cSelector.getCell(WorldSettings.WPS.getX(ent.a), WorldSettings.WPS.getY(ent.b)).equalsCell(path[i]))
		{
			if (i == 0)
			{
				i = -1;
				return new WPS(0, 0);
			}
			else
			{
				i--;
			}
		}
		return AImath.aimCell(ent, path[i], 0.001);
	}
	
	private WPS mouseControl(Entity ent)
	{
		if (Controls.isMouse0pressed)
		{
			return AImath.aimWPS(ent, WorldSettings.WPS.getWPS(Controls.mouseX, Controls.mouseY).shiftA(-ent.getWidthWPS() / 2), 0.001);
		}
		else
		{
			return new WPS(0, 0);
		}
	}
	
	private WPS keysControl()
	{
		WPS v = new WPS(0, 0);
		float speed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 0.003f : 0.001f;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			v.addA(-speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			v.addA(speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			v.addB(speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			v.addB(-speed);
		}
		return v;
	}
	
}
