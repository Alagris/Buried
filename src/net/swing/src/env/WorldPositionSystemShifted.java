package net.swing.src.env;

import net.swing.engine.WPS;
import net.swing.ground.Pointf;

public class WorldPositionSystemShifted extends Pointf
{
	
	private WorldPositionSystem	WPS;
	
	public WorldPositionSystemShifted(float x, float y, float width, float height)
	{
		super(x, y);
		WPS = new WorldPositionSystem(width, height);
	}
	
	public WorldPositionSystemShifted(Pointf location, float width, float height)
	{
		super(location);
		WPS = new WorldPositionSystem(width, height);
	}
	
	public double getA(float x)
	{
		return WPS.getA(x - getX());
	}
	
	public double getB(float y)
	{
		return WPS.getB(y - getY());
	}
	
	public float getX(double a)
	{
		return WPS.getX(a) + getX();
	}
	
	public float getY(double b)
	{
		return WPS.getY(b) + getY();
	}
	
	public WPS getWPS(Pointf location)
	{
		return new WPS(getA(location.getX()), getB(location.getY()));
	}
	
	public WPS getWPS(float x, float y)
	{
		return new WPS(getA(x), getB(y));
	}
	
	public Pointf getLocation(WPS wps)
	{
		return new Pointf(getX(wps.a), getY(wps.b));
	}
	
	public Pointf getLocation(double a, double b)
	{
		return new Pointf(getX(a), getY(b));
	}
	
	public WorldPositionSystem getWorldPositionSystem()
	{
		return WPS;
	}
	
}
